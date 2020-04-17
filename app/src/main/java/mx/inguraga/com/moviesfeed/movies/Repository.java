package mx.inguraga.com.moviesfeed.movies;

import io.reactivex.Observable;
import mx.inguraga.com.moviesfeed.http.apimodel.Result;

public interface Repository {

    Observable<Result> getResultFromNetwork();
    Observable<Result> getResultFromCache();
    Observable<Result> getResultData();

    Observable<String> getCountryFromNetwork();
    Observable<String> getCountryFromCache();
    Observable<String> getCountryData();

}
