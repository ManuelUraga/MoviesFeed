package mx.inguraga.com.moviesfeed.http;

import io.reactivex.Observable;
import mx.inguraga.com.moviesfeed.http.apimodel.OmdbApi;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesExtraInfoApisServices {
    @GET("/")
    Observable<OmdbApi> getExtraInfoMovie(@Query("t")String title);
}
