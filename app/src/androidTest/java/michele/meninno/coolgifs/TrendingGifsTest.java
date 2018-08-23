package michele.meninno.coolgifs;

import android.arch.lifecycle.ViewModelProviders;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.TestObserver;
import michele.meninno.coolgifs.feature.trending.view.TrendingGifsActivity;
import michele.meninno.coolgifs.feature.trending.viewmodel.GiphyViewModel;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static michele.meninno.coolgifs.TrendingGifsTest.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(AndroidJUnit4.class)

public class TrendingGifsTest {

    @Rule
    public ActivityTestRule<TrendingGifsActivity> mActivityRule = new ActivityTestRule<>(TrendingGifsActivity.class);


    @Test
    public void testRecyclerViewAfterComplete() {
        GiphyViewModel viewModel = ViewModelProviders.of(mActivityRule.getActivity()).get(GiphyViewModel.class);
        TestObserver observer = new TestObserver();
        viewModel.getRepository().trendingGifs(0).subscribeWith(observer);
        observer.awaitTerminalEvent();
        onView(withId(R.id.trending_gifs_rw)).check(withItemCount(greaterThan(0)));
    }


    public static class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final Matcher<Integer> matcher;

        public static RecyclerViewItemCountAssertion withItemCount(Matcher<Integer> matcher) {
            return new RecyclerViewItemCountAssertion(matcher);
        }

        private RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {
            this.matcher = matcher;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), matcher);
        }
    }


}
