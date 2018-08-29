package michele.meninno.coolgifs.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import michele.meninno.coolgifs.feature.trending.view.GifDetailActivity;
import michele.meninno.coolgifs.feature.trending.view.TrendingGifsActivity;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract TrendingGifsActivity bindTrendingGiftActivity();

    @ContributesAndroidInjector
    abstract GifDetailActivity bindGifDetailActivity();
}
