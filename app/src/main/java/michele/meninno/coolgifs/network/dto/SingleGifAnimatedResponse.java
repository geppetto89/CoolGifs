package michele.meninno.coolgifs.network.dto;

import com.google.gson.annotations.SerializedName;

public class SingleGifAnimatedResponse {

    @SerializedName("url")
    private String url;

    @SerializedName("webp")
    private String webp;

    @SerializedName("width")
    private long width;

    @SerializedName("height")
    private long height;

    public String getUrl() {
        return url;
    }

    public String getWebp() {
        return webp;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }
}
