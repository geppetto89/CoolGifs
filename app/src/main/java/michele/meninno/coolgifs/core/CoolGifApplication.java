package michele.meninno.coolgifs.core;

import android.app.Application;

import michele.meninno.coolgifs.factory.RepositoryFactory;
import michele.meninno.coolgifs.factory.impl.RepositoryFactoryDefault;

public class CoolGifApplication extends Application {

    private RepositoryFactory repositoryFactory;

    private static CoolGifApplication instance;

    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    public static CoolGifApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        repositoryFactory = new RepositoryFactoryDefault();

    }

}
