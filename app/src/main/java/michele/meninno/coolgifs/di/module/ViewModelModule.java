package michele.meninno.coolgifs.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import michele.meninno.coolgifs.feature.trending.viewmodel.GiphyDetailViewModel;
import michele.meninno.coolgifs.feature.trending.viewmodel.GiphyViewModel;

@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GiphyViewModel.class)
    abstract ViewModel bindGiphyViewModel(GiphyViewModel giphyViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GiphyDetailViewModel.class)
    abstract ViewModel bindRandomGiphyViewModel(GiphyDetailViewModel giphyDetailViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
