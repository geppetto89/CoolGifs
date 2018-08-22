package michele.meninno.coolgifs.factory;


import michele.meninno.coolgifs.repository.GiphyRepository;

/**
 * Provides a list of repository to build in the project
 *
 * @author Michele Meninno
 */
public interface RepositoryFactory {

    GiphyRepository makeGiphyRepository();

}
