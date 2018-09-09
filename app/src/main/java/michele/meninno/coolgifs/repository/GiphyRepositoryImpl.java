package michele.meninno.coolgifs.repository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.model.TrendingModel;
import michele.meninno.coolgifs.network.GiphyService;

import static michele.meninno.coolgifs.core.CoolGifApplication.apikey;

public class GiphyRepositoryImpl implements GiphyRepository {

    private TrendingGifsMapper trendingGifsMapper;
    private RandomGifMapper gifMapper;
    private GiphyService service;

    public GiphyRepositoryImpl() {
    }

    @Inject
    public GiphyRepositoryImpl(GiphyService service) {
        this.service = service;
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
