package michele.meninno.coolgifs.feature.trending.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.R;
import michele.meninno.coolgifs.core.BaseActivity;
import michele.meninno.coolgifs.core.Resource;
import michele.meninno.coolgifs.di.module.ViewModelFactory;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.model.TrendingModel;
import michele.meninno.coolgifs.feature.trending.view.adapter.TrendingGifsAdapter;
import michele.meninno.coolgifs.feature.trending.view.adapter.TrendingGifsAdapterElement;
import michele.meninno.coolgifs.feature.trending.view.adapter.TrendingGifsImageViewElement;
import michele.meninno.coolgifs.feature.trending.viewmodel.GiphyViewModel;

public class TrendingGifsActivity extends BaseActivity {

    public static final int INITIAL_OFFSET = 0;
    private RecyclerView gifsList;
    private TrendingGifsAdapter trendingGifsAdapter;
    private FrameLayout progressBar;
    private TextView errorLabel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject ViewModelFactory viewModelFactory;
    private GiphyViewModel trendingsViewModel;

    private Observer<Resource<TrendingModel>> resourceObserver = gifs -> {
        if (gifs != null) {
            switch (gifs.getStatus()) {
                case SUCCESS:
                    if (gifs.getData() != null) {
                        compositeDisposable.add(Observable.fromCallable((Callable<List<TrendingGifsAdapterElement>>) () -> {
                            ArrayList<TrendingGifsAdapterElement> elements = new ArrayList<>();
                            for (GifModel gifModel : gifs.getData().getGifs()) {
                                elements.add(new TrendingGifsImageViewElement(gifModel));
                            }
                            return elements;
                        }).subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<List<TrendingGifsAdapterElement>>() {
                                    @Override
                                    public void accept(List<TrendingGifsAdapterElement> trendingGifsAdapterElements) throws Exception {
                                        hideLoader();
                                        trendingGifsAdapter.appendsElements(trendingGifsAdapterElements);
                                    }
                                }));
                    }
                    break;
                case ERROR:
                    hideLoader();
                    gifsList.setVisibility(View.INVISIBLE);
                    errorLabel.setVisibility(View.VISIBLE);
                    break;
                case LOADING:
                    showLoader();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gifsList = findViewById(R.id.trending_gifs_rw);
        progressBar = findViewById(R.id.trending_gifs_progress);
        errorLabel = findViewById(R.id.error_label);
        setUI();
        trendingsViewModel.getTrendingGifs(INITIAL_OFFSET);
    }

    private void setUI() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gifsList.setLayoutManager(layoutManager);
        trendingGifsAdapter = new TrendingGifsAdapter();
        trendingsViewModel = ViewModelProviders.of(this, viewModelFactory).get(GiphyViewModel.class);
        trendingsViewModel.getTrendingLiveData().observe(this, resourceObserver);
        gifsList.setAdapter(trendingGifsAdapter);
        trendingGifsAdapter.setOnGifClickListener(onGifClickListener);
        setInfiniteScroll();
    }

    private void setInfiniteScroll() {
        trendingGifsAdapter.setOnLastElementVisibleListener(index -> trendingsViewModel.getTrendingGifs(index));
    }

    private void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        trendingsViewModel.clearPreviousCall();
    }

    private TrendingGifsAdapter.OnGifClickListener onGifClickListener = model -> {
        Intent i = new Intent();
        i.putExtra(GifDetailActivity.EXTRA_GIF, model);
        i.setClass(TrendingGifsActivity.this, GifDetailActivity.class);
        startActivity(i);
    };
}
