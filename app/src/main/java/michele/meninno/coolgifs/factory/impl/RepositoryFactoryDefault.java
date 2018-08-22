package michele.meninno.coolgifs.factory.impl;


import michele.meninno.coolgifs.factory.RepositoryFactory;
import michele.meninno.coolgifs.repository.GiphyRepository;
import michele.meninno.coolgifs.repository.GiphyRepositoryImpl;

public class RepositoryFactoryDefault implements RepositoryFactory {

    @Override
    public GiphyRepository makeGiphyRepository() {
        return new GiphyRepositoryImpl();
    }
}
