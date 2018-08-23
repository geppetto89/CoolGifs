package michele.meninno.coolgifs.feature.trending.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.R;
import michele.meninno.coolgifs.core.Resource;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.viewmodel.GiphyViewModel;

public class GifDetailActivity extends AppCompatActivity {

    public static final String EXTRA_GIF = "EXTRA_GIF";
    public static final int REFRESH_GIF_TIME = 10;
    private ImageView imageView;
    private TextView errorLabel;
    private GiphyViewModel giphyViewModel;
    private FrameLayout progressBar;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Observer<Resource<GifModel>> gifModelObserver = gif -> {
        if (gif != null) {
            switch (gif.getStatus()) {
                case SUCCESS:
                    if (gif.getData() != null) {
                        setCurrentGif(gif.getData());
                        hideLoader();
                    }
                    break;
                case ERROR:
                    hideLoader();
                    imageView.setVisibility(View.INVISIBLE);
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
        setContentView(R.layout.gif_detail_activity);
        imageView = findViewById(R.id.gif_detail);
        errorLabel = findViewById(R.id.error_label);
        giphyViewModel = ViewModelProviders.of(this).get(GiphyViewModel.class);
        giphyViewModel.getRandomGifLiveData().observe(this, gifModelObserver);
        progressBar = findViewById(R.id.trending_gifs_progress);
        setGifShuffling();
        if (getIntent().hasExtra(EXTRA_GIF)) {
            setCurrentGif((GifModel) getIntent().getSerializableExtra(EXTRA_GIF));
        }
    }

    private void setGifShuffling() {
        compositeDisposable.add(Observable.interval(REFRESH_GIF_TIME, TimeUnit.SECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(tick -> giphyViewModel.getRandomGif())
                .subscribe());
    }

    private void setCurrentGif(GifModel gif) {
        String url = gif.getGifUrl();
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView).onLoadStarted(ContextCompat.getDrawable(this, R.drawable.place_holder_shape));
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
}
