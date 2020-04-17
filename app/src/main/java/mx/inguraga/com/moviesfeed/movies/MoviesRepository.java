package mx.inguraga.com.moviesfeed.movies;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import mx.inguraga.com.moviesfeed.http.MoviesApiServices;
import mx.inguraga.com.moviesfeed.http.MoviesExtraInfoApisServices;
import mx.inguraga.com.moviesfeed.http.apimodel.OmdbApi;
import mx.inguraga.com.moviesfeed.http.apimodel.Result;
import mx.inguraga.com.moviesfeed.http.apimodel.TopMoviesRated;

public class MoviesRepository implements Repository {

    private MoviesApiServices moviesApiServices;
    private MoviesExtraInfoApisServices extraInfoApisServices;

    private List<String> countries;
    private List<Result> results;
    private long lastTimestamp;
    private final long CACHE_LIFETIME = 20 * 1000; // cache durara 20 segundos

    public MoviesRepository(MoviesApiServices mServices, MoviesExtraInfoApisServices eServices) {
        this.moviesApiServices = mServices;
        this.extraInfoApisServices = eServices;

        this.lastTimestamp = System.currentTimeMillis();

        this.countries = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    private boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<Result> getResultFromNetwork() {
        Observable<TopMoviesRated> topMoviesRatedObservable = moviesApiServices.getTopMoviesRated(1)
                .concatWith(moviesApiServices.getTopMoviesRated(2))
                .concatWith(moviesApiServices.getTopMoviesRated(3));
        return topMoviesRatedObservable
                .concatMap((Function<TopMoviesRated, Observable<Result>>)
                        topMoviesRated -> Observable.fromIterable(topMoviesRated.getResults()))
                .doOnNext(result -> results.add(result));
    }

    @Override
    public Observable<Result> getResultFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(results);
        } else {
            lastTimestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Result> getResultData() {
        return getResultFromCache().switchIfEmpty(getResultFromNetwork());
    }

    @Override
    public Observable<String> getCountryFromNetwork() {
        return getResultFromNetwork().concatMap(new Function<Result, Observable<OmdbApi>>() {
            @Override
            public Observable<OmdbApi> apply(Result result) {
                return extraInfoApisServices.getExtraInfoMovie(result.getTitle());
            }
        }).concatMap(new Function<OmdbApi, Observable<String>>() {
            @Override
            public Observable<String> apply(OmdbApi omdbApi) {
                if (omdbApi == null || omdbApi.getCountry() == null) {
                    return Observable.just("Desconocido");
                } else {
                    return Observable.just(omdbApi.getCountry());
                }
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String country) {
                countries.add(country);
            }
        });
    }

    @Override
    public Observable<String> getCountryFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(countries);
        } else {
            lastTimestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCountryData() {
        return getCountryFromCache().switchIfEmpty(getCountryFromNetwork());
    }
}
