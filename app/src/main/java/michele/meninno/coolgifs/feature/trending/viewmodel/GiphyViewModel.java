package michele.meninno.coolgifs.feature.trending.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.core.CoolGifApplication;
import michele.meninno.coolgifs.core.NetworkState;
import michele.meninno.coolgifs.core.Resource;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.model.TrendingModel;
import michele.meninno.coolgifs.feature.trending.model.datasource.GifDataSourceFactory;
import michele.meninno.coolgifs.repository.GiphyRepository;
import michele.meninno.coolgifs.repository.GiphyRepositoryImpl;

public class GiphyViewModel extends ViewModel {

    public static final int REFRESH_GIF_TIME = 10;


    private GiphyRepository repository;
    private CompositeDisposable compositeDisposable;
    //gif preview
    private MutableLiveData<Resource<TrendingModel>> trendingLiveData;
    private Resource<TrendingModel> trendingModelResource;

    private final GifDataSourceFactory gifDataSourceFactory;
    private LiveData<PagedList<GifModel>> trendingGifs;

    public MutableLiveData<Resource<TrendingModel>> getTrendingLiveData() {
        return trendingLiveData;
    }

    @Inject
    public GiphyViewModel(GiphyRepositoryImpl repository,  GifDataSourceFactory gifDataSourceFactory){
        this.repository = repository;
        this.gifDataSourceFactory = gifDataSourceFactory;
        compositeDisposable = new CompositeDisposable();
        trendingLiveData = new MutableLiveData<>();
        trendingModelResource = new Resource<>(Resource.Status.EMPTY, null, null);
        initGifSourceFactory();
    }


    public void initGifSourceFactory() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(25)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(40)
                .build();
        trendingGifs = new LivePagedListBuilder<>(gifDataSourceFactory, config).build();
    }

    public LiveData<PagedList<GifModel>> getTrendingGifs() {
        return trendingGifs;
    }

    public void getTrendingGifs(int offset) {
        if(!trendingModelResource.getStatus().equals(Resource.Status.LOADING)) {
            trendingModelResource.setStatus(Resource.Status.LOADING);
            trendingLiveData.setValue(trendingModelResource);
            compositeDisposable.add(repository
                    .trendingGifs(offset)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(gifs -> {
                        trendingModelResource.setStatus(Resource.Status.SUCCESS);
                        trendingModelResource.setData(gifs);
                        trendingLiveData.setValue(trendingModelResource);
                    }, throwable -> {
                        trendingModelResource.setStatus(Resource.Status.ERROR);
                        trendingModelResource.setThrowable(throwable);
                        trendingLiveData.setValue(trendingModelResource);
                    }));
        }
    }

    public LiveData<NetworkState> initialLoadState() {
        return gifDataSourceFactory.getGifDataSource().getInitialLoadStateLiveData();
    }

    public LiveData<NetworkState> paginatedLoadState() {
        return gifDataSourceFactory.getGifDataSource().getPaginatedNetworkStateLiveData();
    }

    public GiphyRepository getRepository() {
        return repository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
