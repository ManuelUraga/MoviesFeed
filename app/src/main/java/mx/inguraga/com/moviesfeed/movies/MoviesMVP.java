package mx.inguraga.com.moviesfeed.movies;


import io.reactivex.Observable;

public interface MoviesMVP {
    interface View{
        void updateData(ViewModel viewModel);
        void showSnackBar(String s);

    }
    interface  Presenter{
        void loadData();
        void rxJavaUnsubscribe();
        void setView(MoviesMVP.View view);
    }
    interface Model{
        Observable<ViewModel> result();
    }
}
