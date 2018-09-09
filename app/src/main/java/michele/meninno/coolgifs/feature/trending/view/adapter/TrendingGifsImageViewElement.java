package michele.meninno.coolgifs.feature.trending.view.adapter;

import michele.meninno.coolgifs.feature.trending.model.GifModel;

/**
 * Model used by the trending gifs adapter
 *
 * @author Michele Meninno
 */
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
    public String getId() {
        return gifModel.getId();
    }
}
