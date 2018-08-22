package michele.meninno.coolgifs.network.dto;

import com.google.gson.annotations.SerializedName;

public class GifResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("images")
    private SingleGifImages images;

    public SingleGifImages getImages() {
        return images;
    }

    public String getId() {
        return id;
    }
}
