package michele.meninno.coolgifs.feature.trending.view.adapter;

import michele.meninno.coolgifs.feature.trending.model.GifModel;

import static michele.meninno.coolgifs.feature.trending.view.adapter.TrendingGifsAdapter.TRENDING_GIF_ELEMENT;

public class TrendingGifsImageViewElement implements TrendingGifsAdapterElement {

    private GifModel gifModel;

    public TrendingGifsImageViewElement(GifModel gifModel) {
        this.gifModel = gifModel;
    }

    public GifModel getGifModel() {
        return gifModel;
    }

    public void setGifModel(GifModel gifModel) {
        this.gifModel = gifModel;
    }

    @Override
    public int getItemType() {
        return TRENDING_GIF_ELEMENT;
    }
}
