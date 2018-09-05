package michele.meninno.coolgifs.feature.trending.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import michele.meninno.coolgifs.R;
import michele.meninno.coolgifs.core.BaseActivity;
import michele.meninno.coolgifs.core.Resource;
import michele.meninno.coolgifs.di.module.ViewModelFactory;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.viewmodel.GiphyDetailViewModel;

public class GifDetailActivity extends BaseActivity {

    public static final String EXTRA_GIF = "EXTRA_GIF";
    //injected view
    @BindView(R.id.gif_detail)
    ImageView imageView;
    @BindView(R.id.error_label)
    TextView errorLabel;
    @BindView(R.id.title_label)
    TextView titleLabel;
    @BindView(R.id.trending_gifs_progress)
    FrameLayout progressBar;
    private GiphyDetailViewModel giphyViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ViewModelFactory viewModelFactory;

    private Observer<Resource<GifModel>> gifModelObserver = gif -> {
        if (gif != null) {
            switch (gif.getStatus()) {
                case SUCCESS:
                    if (gif.getData() != null) {
                        setCurrentGif(gif.getData());
                    }
                    break;
                case ERROR:
                    imageView.setVisibility(View.INVISIBLE);
                    errorLabel.setVisibility(View.VISIBLE);
                    break;
                case LOADING:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        giphyViewModel.getRandomGifEveryTenSecond();
    }


    @Override
    protected void onPause() {
        super.onPause();
        giphyViewModel.stopRandomCall();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_detail_activity);
        ButterKnife.bind(this);
        giphyViewModel = ViewModelProviders.of(this, viewModelFactory).get(GiphyDetailViewModel.class);
        giphyViewModel.getRandomGifLiveData().observe(this, gifModelObserver);
        progressBar = findViewById(R.id.trending_gifs_progress);
        if (getIntent().hasExtra(EXTRA_GIF)) {
            setCurrentGif(getIntent().getParcelableExtra(EXTRA_GIF));
        }
    }


    private void setCurrentGif(GifModel gif) {
        String url = gif.getGifUrl();
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView).onLoadStarted(ContextCompat.getDrawable(this, R.drawable.place_holder_shape));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
