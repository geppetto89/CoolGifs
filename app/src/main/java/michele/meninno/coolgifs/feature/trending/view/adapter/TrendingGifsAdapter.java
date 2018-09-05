package michele.meninno.coolgifs.feature.trending.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import michele.meninno.coolgifs.R;
import michele.meninno.coolgifs.feature.trending.model.GifModel;

public class TrendingGifsAdapter extends RecyclerView.Adapter<TrendingGifViewHolder> {

    private List<TrendingGifsAdapterElement> elements;
    private OnGifClickListener onGifClickListener;

    public void setOnGifClickListener(OnGifClickListener onGifClickListener) {
        this.onGifClickListener = onGifClickListener;
    }

    public void appendsElements(List<TrendingGifsAdapterElement> elements) {
        int size = 0;
        if (this.elements != null) {
            size = this.elements.size();
        } else {
            this.elements = new ArrayList<>();
        }
        this.elements.addAll(elements);
        notifyItemRangeInserted(size, elements.size());
    }

    @NonNull
    @Override
    public TrendingGifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrendingGifViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_gifs_element, parent, false), onGifClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingGifViewHolder holder, int position) {
        holder.onBind(((TrendingGifsImageViewElement) elements.get(position)).getGifModel());
    }

    @Override
    public int getItemCount() {
        if (elements != null) {
            return elements.size();
        }
        return 0;
    }

    //listener for intercepting click on a gif
    public interface OnGifClickListener {
        void onGifClicked(GifModel model);
    }
}
