package michele.meninno.coolgifs.feature.trending.model.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import michele.meninno.coolgifs.feature.trending.model.GifModel;
import michele.meninno.coolgifs.network.dto.TrendingResponse;

/**
 *
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */

@Singleton
public class GifDataSourceFactory extends DataSource.Factory<Integer, GifModel> {

    private final GifDataSource gifDataSource;
    private MutableLiveData<GifDataSource> gifDataSourceMutableLiveData;

    public GifDataSource getGifDataSource() {
        return gifDataSource;
    }

    @Inject
    public GifDataSourceFactory(GifDataSource gifDataSource) {
        this.gifDataSource = gifDataSource;
        gifDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, GifModel> create() {
        gifDataSourceMutableLiveData.postValue(gifDataSource);
        return gifDataSource;
    }

    public MutableLiveData<GifDataSource> getGifDataSourceMutableLiveData() {
        return gifDataSourceMutableLiveData;
    }
}
