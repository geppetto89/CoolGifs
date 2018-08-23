package michele.meninno.coolgifs.feature.trending.model;

import java.io.Serializable;

/**
 * Model to dispatch to the view
 * @author Michele Meninno
 */
public class GifModel implements Serializable {

    private String framePreviewUrl;
    private String gifUrl;

    public GifModel(String framePreviewUrl, String gifUrl) {
        this.framePreviewUrl = framePreviewUrl;
        this.gifUrl = gifUrl;
    }

    public String getFramePreviewUrl() {
        return framePreviewUrl;
    }

    public void setFramePreviewUrl(String framePreviewUrl) {
        this.framePreviewUrl = framePreviewUrl;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }
}
