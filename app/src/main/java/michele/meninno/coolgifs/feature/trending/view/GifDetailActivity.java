package michele.meninno.coolgifs.feature.trending.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
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
    private ImageView imageView;
    private GiphyViewModel giphyViewModel;
    private GifModel myGifModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Observer<Resource<GifModel>> gifModelObserver = gifs -> {
        if (gifs != null) {
            switch (gifs.getStatus()) {
                case SUCCESS:
                    myGifModel = gifs.getData();
                    setCurrentGif();
                    break;
                case ERROR:

                    break;
                case LOADING:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_detail_activity);
        imageView = findViewById(R.id.gif_detail);
        giphyViewModel = ViewModelProviders.of(this).get(GiphyViewModel.class);
        giphyViewModel.getRandomGifLiveData().observe(this, gifModelObserver);
        myGifModel = (GifModel) getIntent().getSerializableExtra(EXTRA_GIF);
        setCurrentGif();
        compositeDisposable.add(Observable.interval(10, TimeUnit.SECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(tick -> giphyViewModel.getRandomGif())
                .subscribe());
    }

    private void setCurrentGif() {
        String url = myGifModel.getGifUrl();
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
