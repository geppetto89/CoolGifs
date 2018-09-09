package michele.meninno.coolgifs.feature.trending.model.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.core.CoolGifApplication;
import michele.meninno.coolgifs.core.NetworkState;
import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.network.GiphyService;
import michele.meninno.coolgifs.network.dto.TrendingResponse;
import michele.meninno.coolgifs.repository.NewTrendingGIfsMapper;

@Singleton
public class GifDataSource extends ItemKeyedDataSource<Integer, GifModel> {

    private GiphyService giphyService;
    private NewTrendingGIfsMapper trendingGifsMapper = new NewTrendingGIfsMapper();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<NetworkState> paginatedNetworkStateLiveData;
    private MutableLiveData<NetworkState> initialLoadStateLiveData;

    public MutableLiveData<NetworkState> getPaginatedNetworkStateLiveData() {
        return paginatedNetworkStateLiveData;
    }

    public MutableLiveData<NetworkState> getInitialLoadStateLiveData() {
        return initialLoadStateLiveData;
    }

    public static final String TAG = GifDataSource.class.getSimpleName();

    private int offset = 0;

    @Inject
    public GifDataSource(GiphyService giphyService) {
        this.giphyService = giphyService;
        initialLoadStateLiveData = new MutableLiveData<>();
        paginatedNetworkStateLiveData = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<GifModel> callback) {
        Log.d(TAG, "load inital called!: "+ offset);
        initialLoadStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.LOADING).build());
        Disposable disposable = giphyService.getTrendings(offset, CoolGifApplication.apikey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendingResponse ->
                                onInitialSuccess(trendingResponse,callback),
                                this::onError
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<GifModel> callback) {
        Log.d(TAG, "load after called!");
        paginatedNetworkStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.LOADING).build());
        Disposable disposable = giphyService.getTrendings(params.key, CoolGifApplication.apikey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendingResponse ->
                                onMoreSuccess(trendingResponse,callback),
                                this::onError
                );
        compositeDisposable.add(disposable);
    }

    private void onInitialSuccess(TrendingResponse response, LoadInitialCallback<GifModel> callback){
        initialLoadStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.SUCCESS).build());
        callback.onResult(trendingGifsMapper.map(response));
        offset += response.getGifs().size();
    }

    private void onMoreSuccess(TrendingResponse response,  LoadCallback<GifModel> callback){
        paginatedNetworkStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.SUCCESS).build());
        callback.onResult(trendingGifsMapper.map(response));
        offset += response.getGifs().size();
    }

    private void onError(Throwable throwable) {
        initialLoadStateLiveData.postValue(NetworkState.builder()
                .status(NetworkState.Status.ERROR)
                .message(throwable.getMessage()).build());
        Log.e(TAG, throwable.getMessage());
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<GifModel> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull GifModel item) {
        Log.d(TAG, item.getId() + " " + offset);
        return offset;
    }

}
