package michele.meninno.coolgifs.repository;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.feature.trending.model.TrendingModel;
import michele.meninno.coolgifs.network.GiphyService;
import michele.meninno.coolgifs.network.RetrofitClient;

public class GiphyRepositoryImpl implements GiphyRepository {

    private static final String apikey = "dc6zaTOxFJmzC";
    private TrendingGifsMapper mapper;
    private GiphyService service;

    public GiphyRepositoryImpl() {
        service = RetrofitClient.getClient().create(GiphyService.class);
        mapper = new TrendingGifsMapper();
    }

    @Override
    public Observable<TrendingModel> trendingGifs(int offset) {
        return service.getTrendings(offset, apikey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(mapper::map);
    }
}
