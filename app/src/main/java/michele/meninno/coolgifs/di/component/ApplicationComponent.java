package michele.meninno.coolgifs.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;
import michele.meninno.coolgifs.core.CoolGifApplication;
import michele.meninno.coolgifs.di.module.ActivityBindingModule;
import michele.meninno.coolgifs.di.module.ApplicationModule;
import michele.meninno.coolgifs.di.module.ContextModule;

@Singleton
@Component(modules =
        {
                ContextModule.class,
                ApplicationModule.class,
                AndroidSupportInjectionModule.class,
                ActivityBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(CoolGifApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }

}
