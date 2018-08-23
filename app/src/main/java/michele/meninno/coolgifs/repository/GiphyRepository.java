package michele.meninno.coolgifs.repository;

import io.reactivex.Observable;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.model.TrendingModel;

public interface GiphyRepository {

    Observable<TrendingModel> trendingGifs(int offset);

    Observable<GifModel> getRandomGif();

}
