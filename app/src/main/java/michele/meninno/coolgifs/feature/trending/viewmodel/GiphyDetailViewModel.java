package michele.meninno.coolgifs.feature.trending.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.core.Resource;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.repository.GiphyRepository;
import michele.meninno.coolgifs.repository.GiphyRepositoryImpl;

public class GiphyDetailViewModel extends ViewModel {

    public static final int REFRESH_GIF_TIME = 10;

    private GiphyRepository repository;
    private CompositeDisposable compositeDisposable;

    //gif detail
    private MutableLiveData<Resource<GifModel>> randomGifLiveData;
    private Resource<GifModel> gifModelResource;

    public MutableLiveData<Resource<GifModel>> getRandomGifLiveData() {
        return randomGifLiveData;
    }

    @Inject
    public GiphyDetailViewModel(GiphyRepositoryImpl repository){
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
        randomGifLiveData = new MutableLiveData<>();
        gifModelResource = new Resource<>(Resource.Status.EMPTY, null, null);
    }

    public void getRandomGifEveryTenSecond(){
        gifModelResource.setStatus(Resource.Status.LOADING);
        randomGifLiveData.setValue(gifModelResource);
        compositeDisposable.add(Observable.interval(REFRESH_GIF_TIME, TimeUnit.SECONDS, Schedulers.computation())
                .concatMap(aLong -> repository.getRandomGif())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gif -> {
                    gifModelResource.setStatus(Resource.Status.SUCCESS);
                    gifModelResource.setData(gif);
                    randomGifLiveData.setValue(gifModelResource);
                    gifModelResource.setStatus(Resource.Status.LOADING);
                    randomGifLiveData.setValue(gifModelResource);
                }, throwable -> {
                    gifModelResource.setStatus(Resource.Status.ERROR);
                    gifModelResource.setThrowable(throwable);
                    randomGifLiveData.setValue(gifModelResource);
                }));
    }

    public void stopRandomCall(){
        compositeDisposable.clear();
    }


}
