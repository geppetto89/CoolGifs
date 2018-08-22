package michele.meninno.coolgifs.network.dto;

import com.google.gson.annotations.SerializedName;

public class SingleGifImages {

    @SerializedName("fixed_height_still")
    private SingleGifStillResponse fixedHeightStill;

    @SerializedName("fixed_height")
    private SingleGifAnimatedResponse fixedHeightAnimated;

    public SingleGifStillResponse getFixedHeightStill() {
        return fixedHeightStill;
    }

    public SingleGifAnimatedResponse getFixedHeightAnimated() {
        return fixedHeightAnimated;
    }
}
