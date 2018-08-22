package michele.meninno.coolgifs.network.dto;

import com.google.gson.annotations.SerializedName;

public class SingleGifStillResponse {

    @SerializedName("url")
    private String url;

    @SerializedName("width")
    private long width;

    @SerializedName("height")
    private long height;

    public String getUrl() {
        return url;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }
}
