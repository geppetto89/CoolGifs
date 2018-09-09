package michele.meninno.coolgifs.feature.trending.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;
import michele.meninno.coolgifs.R;
import michele.meninno.coolgifs.core.BaseActivity;
import michele.meninno.coolgifs.core.NetworkState;
import michele.meninno.coolgifs.di.module.ViewModelFactory;
import michele.meninno.coolgifs.feature.trending.view.adapter.RetryCallback;
import michele.meninno.coolgifs.feature.trending.view.adapter.TrendingGIfsNewAdapter;
import michele.meninno.coolgifs.feature.trending.view.adapter.TrendingGifsAdapter;
import michele.meninno.coolgifs.feature.trending.viewmodel.GiphyViewModel;

public class TrendingGifsActivity extends BaseActivity implements RetryCallback {

    private RecyclerView gifsList;
    private TrendingGIfsNewAdapter trendingGIfsNewAdapter;
    private FrameLayout progressBar;
    private TextView errorLabel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ViewModelFactory viewModelFactory;
    private GiphyViewModel trendingsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gifsList = findViewById(R.id.trending_gifs_rw);
        progressBar = findViewById(R.id.trending_gifs_progress);
        errorLabel = findViewById(R.id.error_label);
        setUI();
    }

    private void setUI() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gifsList.setLayoutManager(layoutManager);
        trendingsViewModel = ViewModelProviders.of(this, viewModelFactory).get(GiphyViewModel.class);
        trendingsViewModel.initialLoadState().observe(this, this::setProgress);
        trendingsViewModel.paginatedLoadState().observe(this, this::setProgress);
        initAdapter();
    }

    private void initAdapter() {
        trendingGIfsNewAdapter = new TrendingGIfsNewAdapter( this);
        gifsList.setAdapter(trendingGIfsNewAdapter);
        trendingGIfsNewAdapter.setOnGifClickListener(onGifClickListener);
        trendingsViewModel.getTrendingGifs().observe(this, shows -> trendingGIfsNewAdapter.submitList(shows));
    }

    private void setProgress(NetworkState initialLoadState) {
        switch (initialLoadState.status()) {
            case NetworkState.Status.SUCCESS:
                hideLoader();
                break;
            case NetworkState.Status.ERROR:
                hideLoader();
                gifsList.setVisibility(View.INVISIBLE);
                errorLabel.setVisibility(View.VISIBLE);
                break;
            case NetworkState.Status.LOADING:
            default:
                showLoader();
                break;
        }
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
    }

    private TrendingGifsAdapter.OnGifClickListener onGifClickListener = model -> {
        Intent i = new Intent();
        i.putExtra(GifDetailActivity.EXTRA_GIF, model);
        i.setClass(TrendingGifsActivity.this, GifDetailActivity.class);
        startActivity(i);
    };

    @Override
    public void retry() {
        // TODO: 09/09/18 to complete the retry logic
    }
}
