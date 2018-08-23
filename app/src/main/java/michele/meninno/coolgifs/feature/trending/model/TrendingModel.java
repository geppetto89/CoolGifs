package michele.meninno.coolgifs.feature.trending.model;

import java.util.List;

/**
 * model to dispatch to the view
 *
 * @author Michele Meninno
 */
public class TrendingModel {

    private boolean hasMoreElement;
    private List<GifModel> gifs;

    public List<GifModel> getGifs() {
        return gifs;
    }

    public void setGifs(List<GifModel> gifs) {
        this.gifs = gifs;
    }

    public boolean isHasMoreElement() {
        return hasMoreElement;
    }

    public void setHasMoreElement(boolean hasMoreElement) {
        this.hasMoreElement = hasMoreElement;
    }
}
