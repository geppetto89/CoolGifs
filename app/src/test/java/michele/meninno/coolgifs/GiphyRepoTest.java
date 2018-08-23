package michele.meninno.coolgifs;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import michele.meninno.coolgifs.feature.trending.model.TrendingModel;
import michele.meninno.coolgifs.repository.GiphyRepositoryImpl;

public class GiphyRepoTest {

    @Test
    public void trendingGifsCallTest() {
        GiphyRepositoryImpl giphyRepository = new GiphyRepositoryImpl();
        TestObserver observer = new TestObserver();
        giphyRepository.trendingGifs(0).subscribeWith(observer);
        observer.awaitTerminalEvent();
        observer.assertComplete();
    }

    @Test
    public void trendingGifsNoMoreElement() {
        GiphyRepositoryImpl giphyRepository = new GiphyRepositoryImpl();
        giphyRepository.trendingGifs(999999).blockingSubscribe(new Consumer<TrendingModel>() {
            @Override
            public void accept(TrendingModel trendingModel) throws Exception {
                Assert.assertTrue(trendingModel.getGifs().size() == 0);
            }
        });
    }

    @Test
    public void randomGifDetail() {
        GiphyRepositoryImpl giphyRepository = new GiphyRepositoryImpl();
        TestObserver observer = new TestObserver();
        giphyRepository.getRandomGif().subscribeWith(observer);
        observer.awaitTerminalEvent();
        observer.assertComplete();
    }

}
