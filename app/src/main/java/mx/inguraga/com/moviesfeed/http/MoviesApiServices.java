package mx.inguraga.com.moviesfeed.http;

import io.reactivex.Observable;
import mx.inguraga.com.moviesfeed.http.apimodel.TopMoviesRated;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiServices {

    @GET("top_rated")
    Observable<TopMoviesRated> getTopMoviesRated(@Query("page")Integer page);
}
