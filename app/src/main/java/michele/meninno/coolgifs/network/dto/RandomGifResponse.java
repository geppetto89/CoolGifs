package michele.meninno.coolgifs.network.dto;

import com.google.gson.annotations.SerializedName;

public class RandomGifResponse {

    @SerializedName("data")
    private GifResponse gif;

    public GifResponse getGif() {
        return gif;
    }
}
