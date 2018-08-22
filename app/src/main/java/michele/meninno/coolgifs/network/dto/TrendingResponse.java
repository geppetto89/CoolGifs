package michele.meninno.coolgifs.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrendingResponse {

    @SerializedName("data")
    private List<GifResponse> gifs;

    @SerializedName("pagination")
    private Pagination pagination;

    public List<GifResponse> getGifs() {
        return gifs;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
