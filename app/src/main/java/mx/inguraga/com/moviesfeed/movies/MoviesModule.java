package mx.inguraga.com.moviesfeed.movies;

import dagger.Module;
import dagger.Provides;
import mx.inguraga.com.moviesfeed.http.MoviesApiServices;
import mx.inguraga.com.moviesfeed.http.MoviesExtraInfoApisServices;

@Module
public class MoviesModule {

    @Provides
    public MoviesMVP.Presenter provideMoviesPresenter(MoviesMVP.Model moviesModel){
        return new MoviesPresenter(moviesModel);
    }

    @Provides
    public MoviesMVP.Model provideMoviesModel(Repository repository){
        return new MoviesModel(repository);
    }

    @Provides
    public Repository provideMoviesRepository(MoviesApiServices moviesApiServices, MoviesExtraInfoApisServices extraInfoApisServices){
        return new MoviesRepository(moviesApiServices,extraInfoApisServices);
    }

}
