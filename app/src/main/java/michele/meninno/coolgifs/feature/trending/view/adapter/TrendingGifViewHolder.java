package michele.meninno.coolgifs.feature.trending.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import michele.meninno.coolgifs.R;
import michele.meninno.coolgifs.feature.trending.model.GifModel;

public class TrendingGifViewHolder  extends RecyclerView.ViewHolder{

    private ImageView imageView;

    public TrendingGifViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.trending_gifs_list_iw);
    }

    public void onBind(GifModel gifModel){
        String url = gifModel.getFramePreviewUrl();
        Glide.with(itemView.getContext())
                .load(url)
                .into(imageView);
    }



}
