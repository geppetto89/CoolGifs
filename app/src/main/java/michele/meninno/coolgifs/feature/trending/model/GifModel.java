package michele.meninno.coolgifs.feature.trending.model;

public class GifModel {

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
