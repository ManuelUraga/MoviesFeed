package mx.inguraga.com.moviesfeed;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.inguraga.com.moviesfeed.movies.ListAdapter;
import mx.inguraga.com.moviesfeed.movies.MoviesMVP;
import mx.inguraga.com.moviesfeed.movies.ViewModel;
import mx.inguraga.com.moviesfeed.root.App;

public class MainActivity extends AppCompatActivity implements MoviesMVP.View {

    private final String TAG = MainActivity.class.getName();

    @BindView(R.id.activity_root_view)
    ViewGroup rootView;
    @BindView(R.id.rv_movies)
    RecyclerView recyclerView;
    @Inject
    MoviesMVP.Presenter presenter;

    private ListAdapter listAdapter;
    private List<ViewModel> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ((App)getApplication()).getComponent().inject(this);

        listAdapter = new ListAdapter(resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        recyclerView.setHasFixedSize(false); //No haya filas mas grandes
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxJavaUnsubscribe();
        resultList.clear();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);
        listAdapter.notifyItemInserted(resultList.size()-1);
        Log.d(TAG,"Informacion nueva: "+viewModel.getName());
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(rootView,message,Snackbar.LENGTH_SHORT).show();
    }
}
