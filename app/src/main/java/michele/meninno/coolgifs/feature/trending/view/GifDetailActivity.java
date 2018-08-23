package michele.meninno.coolgifs.feature.trending.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import michele.meninno.coolgifs.R;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.viewmodel.GiphyViewModel;

public class GifDetailActivity extends AppCompatActivity {

    public static final String EXTRA_GIF = "EXTRA_GIF";
    private ImageView imageView;
    private GiphyViewModel giphyViewModel;
    private GifModel myGifModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_detail_activity);
        imageView = findViewById(R.id.gif_detail);
        giphyViewModel = ViewModelProviders.of(this).get(GiphyViewModel.class);
        myGifModel = (GifModel) getIntent().getSerializableExtra(EXTRA_GIF);
        String url = myGifModel.getGifUrl();
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
