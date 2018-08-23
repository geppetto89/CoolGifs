package michele.meninno.coolgifs.feature.trending.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import michele.meninno.coolgifs.core.CoolGifApplication;
import michele.meninno.coolgifs.core.Resource;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.feature.trending.model.TrendingModel;
import michele.meninno.coolgifs.repository.GiphyRepository;

public class GiphyViewModel extends ViewModel {

    private GiphyRepository repository;
    private CompositeDisposable compositeDisposable;
    //gif preview
    private MutableLiveData<Resource<TrendingModel>> trendingLiveData;
    private Resource<TrendingModel> trendingModelResource;
    //gif detail
    private MutableLiveData<Resource<GifModel>> randomGifLiveData;
    private Resource<GifModel> gifModelResource;

    public MutableLiveData<Resource<GifModel>> getRandomGifLiveData() {
        return randomGifLiveData;
    }

    public MutableLiveData<Resource<TrendingModel>> getTrendingLiveData() {
        return trendingLiveData;
    }

    public GiphyViewModel() {
        compositeDisposable = new CompositeDisposable();
        trendingLiveData = new MutableLiveData<>();
        randomGifLiveData = new MutableLiveData<>();
        repository = CoolGifApplication.getInstance().getRepositoryFactory().makeGiphyRepository();
        trendingModelResource = new Resource<>(Resource.Status.EMPTY, null, null);
        gifModelResource = new Resource<>(Resource.Status.EMPTY, null, null);
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

    public void getRandomGif(){
        gifModelResource.setStatus(Resource.Status.LOADING);
        randomGifLiveData.setValue(gifModelResource);
        compositeDisposable.add(repository
                .getRandomGif()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gif -> {
                    gifModelResource.setStatus(Resource.Status.SUCCESS);
                    gifModelResource.setData(gif);
                    randomGifLiveData.setValue(gifModelResource);
                }, throwable -> {
                    gifModelResource.setStatus(Resource.Status.ERROR);
                    gifModelResource.setThrowable(throwable);
                    randomGifLiveData.setValue(gifModelResource);
                }));
    }

    public GiphyRepository getRepository() {
        return repository;
    }

    public void clearPreviousCall(){
        compositeDisposable.clear();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
