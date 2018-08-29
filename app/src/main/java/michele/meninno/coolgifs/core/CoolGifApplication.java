package michele.meninno.coolgifs.core;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import michele.meninno.coolgifs.di.component.ApplicationComponent;
import michele.meninno.coolgifs.di.component.DaggerApplicationComponent;

public class CoolGifApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);
        return component;
    }
}
