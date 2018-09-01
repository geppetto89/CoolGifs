package michele.meninno.coolgifs.feature.trending.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.core.CoolGifApplication;
import michele.meninno.coolgifs.core.Resource;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.model.TrendingModel;
import michele.meninno.coolgifs.repository.GiphyRepository;
import michele.meninno.coolgifs.repository.GiphyRepositoryImpl;

public class GiphyViewModel extends ViewModel {

    public static final int REFRESH_GIF_TIME = 10;


    private GiphyRepository repository;
    private CompositeDisposable compositeDisposable;
    //gif preview
    private MutableLiveData<Resource<TrendingModel>> trendingLiveData;
    private Resource<TrendingModel> trendingModelResource;

    public MutableLiveData<Resource<TrendingModel>> getTrendingLiveData() {
        return trendingLiveData;
    }

    @Inject
    public GiphyViewModel(GiphyRepositoryImpl repository){
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
        trendingLiveData = new MutableLiveData<>();
        trendingModelResource = new Resource<>(Resource.Status.EMPTY, null, null);
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

    public GiphyRepository getRepository() {
        return repository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
