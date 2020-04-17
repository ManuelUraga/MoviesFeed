package mx.inguraga.com.moviesfeed.root;

import android.app.Application;

import mx.inguraga.com.moviesfeed.http.MovieExtraInfoApiModule;
import mx.inguraga.com.moviesfeed.http.MovieTitleApiModule;
import mx.inguraga.com.moviesfeed.movies.MoviesModule;

public class App extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .moviesModule(new MoviesModule())
                .movieTitleApiModule(new MovieTitleApiModule())
                .movieExtraInfoApiModule(new MovieExtraInfoApiModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
