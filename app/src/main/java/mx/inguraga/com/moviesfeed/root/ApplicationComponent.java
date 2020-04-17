package mx.inguraga.com.moviesfeed.root;

import javax.inject.Singleton;

import dagger.Component;
import mx.inguraga.com.moviesfeed.MainActivity;
import mx.inguraga.com.moviesfeed.http.MovieExtraInfoApiModule;
import mx.inguraga.com.moviesfeed.http.MovieTitleApiModule;
import mx.inguraga.com.moviesfeed.movies.MoviesModule;

@Singleton
@Component(modules ={ApplicationModule.class,
        MoviesModule.class,
        MovieTitleApiModule.class,
        MovieExtraInfoApiModule.class})
public interface ApplicationComponent {
    void inject(MainActivity target);
}
