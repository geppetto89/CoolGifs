package michele.meninno.coolgifs.feature.trending.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import michele.meninno.coolgifs.R;
import michele.meninno.coolgifs.feature.trending.model.GifModel;

public class TrendingGifViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView;
    private GifModel gifModel;
    private TrendingGifsAdapter.OnGifClickListener onGifClickListener;

    public TrendingGifViewHolder(View itemView, TrendingGifsAdapter.OnGifClickListener onGifClickListener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.trending_gifs_list_iw);
        this.onGifClickListener = onGifClickListener;
        itemView.setOnClickListener(this);
    }

    public void onBind(GifModel gifModel) {
        this.gifModel = gifModel;
        String url = gifModel.getFramePreviewUrl();
        Glide.with(itemView.getContext())
                .load(url)
                .into(imageView);
    }

    @Override
    public void onClick(View v) {
        if (onGifClickListener != null) {
            onGifClickListener.onGifClicked(gifModel);
        }
    }
}
