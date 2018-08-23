package michele.meninno.coolgifs.repository;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.model.TrendingModel;
import michele.meninno.coolgifs.network.GiphyService;
import michele.meninno.coolgifs.network.RetrofitClient;

public class GiphyRepositoryImpl implements GiphyRepository {

    private static final String apikey = "dc6zaTOxFJmzC";
    private TrendingGifsMapper trendingGifsMapper;
    private RandomGifMapper gifMapper;
    private GiphyService service;

    public GiphyRepositoryImpl() {
        service = RetrofitClient.getClient().create(GiphyService.class);
        trendingGifsMapper = new TrendingGifsMapper();
        gifMapper = new RandomGifMapper();
    }

    @Override
    public Observable<TrendingModel> trendingGifs(int offset) {
        return service.getTrendings(offset, apikey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(trendingGifsMapper::map);
    }

    @Override
    public Observable<GifModel> getRandomGif() {
        return service.getRandomGif(apikey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(gifMapper::map);
    }
}
