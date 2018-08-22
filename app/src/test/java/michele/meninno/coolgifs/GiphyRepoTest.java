package michele.meninno.coolgifs;

import org.junit.Test;

import io.reactivex.observers.TestObserver;
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
        TestObserver observer = new TestObserver();
        giphyRepository.trendingGifs(999999).subscribeWith(observer);
        observer.awaitTerminalEvent();
        observer.assertComplete();
    }

}
