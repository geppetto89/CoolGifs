package michele.meninno.coolgifs.network;


import io.reactivex.Observable;
import michele.meninno.coolgifs.network.dto.RandomGifResponse;
import michele.meninno.coolgifs.network.dto.TrendingResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyService {

    @GET("/v1/gifs/trending")
    Observable<TrendingResponse> getTrendings(@Query("offset") int offset, @Query("api_key") String key);

    @GET("/v1/gifs/random")
    Observable<RandomGifResponse> getRandomGif(@Query("api_key") String key);
}
