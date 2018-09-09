package michele.meninno.coolgifs.feature.trending.view.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Objects;

import michele.meninno.coolgifs.R;
import michele.meninno.coolgifs.feature.trending.model.GifModel;

public class TrendingGIfsNewAdapter extends PagedListAdapter<GifModel, TrendingGifViewHolder> {


    public TrendingGIfsNewAdapter(RetryCallback callback) {
        super(UserDiffCallback);
    }

    private TrendingGifsAdapter.OnGifClickListener onGifClickListener;

    public void setOnGifClickListener(TrendingGifsAdapter.OnGifClickListener onGifClickListener) {
        this.onGifClickListener = onGifClickListener;
    }

    @NonNull
    @Override
    public TrendingGifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrendingGifViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_gifs_element, parent, false), onGifClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingGifViewHolder holder, int position) {
        holder.onBind(((GifModel) Objects.requireNonNull(getItem(position))));
    }

    private static DiffUtil.ItemCallback<GifModel> UserDiffCallback = new DiffUtil.ItemCallback<GifModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull GifModel oldItem, @NonNull GifModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull GifModel oldItem, @NonNull GifModel newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

}
