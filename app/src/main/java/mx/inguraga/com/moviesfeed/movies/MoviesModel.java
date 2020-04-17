package mx.inguraga.com.moviesfeed.movies;

import io.reactivex.Observable;

public class MoviesModel implements MoviesMVP.Model {

    private Repository repository;

    public MoviesModel(Repository repository){
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(repository.getResultData(), repository.getCountryData(),
                (result, country) -> {
                    return new ViewModel(result.getTitle(),country);
                });
    }
}
