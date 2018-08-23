package michele.meninno.coolgifs;

import android.arch.lifecycle.ViewModelProviders;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import io.reactivex.observers.TestObserver;
import michele.meninno.coolgifs.core.Resource;
import michele.meninno.coolgifs.feature.trending.view.GifDetailActivity;
import michele.meninno.coolgifs.feature.trending.viewmodel.GiphyViewModel;


public class GifDetailTest {

    @Rule
    public ActivityTestRule<GifDetailActivity> mActivityRule = new ActivityTestRule<>(GifDetailActivity.class);


    @Test
    public void testGetRandomIsCalledAfter15Sec() throws InterruptedException {

        GiphyViewModel viewModel = ViewModelProviders.of(mActivityRule.getActivity()).get(GiphyViewModel.class);
        TestObserver observer = new TestObserver();
        observer.await(15, TimeUnit.SECONDS);
        Assert.assertTrue(!viewModel.getRandomGifLiveData().getValue().getStatus().equals(Resource.Status.EMPTY));

    }




}
