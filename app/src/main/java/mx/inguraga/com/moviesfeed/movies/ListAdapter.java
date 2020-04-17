package mx.inguraga.com.moviesfeed.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.inguraga.com.moviesfeed.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemViewHolder> {

    private List<ViewModel> list;
    public ListAdapter(List<ViewModel> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        holder.tvItemName.setText(list.get(position).getName());
        holder.tvItemCountry.setText(list.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        public TextView tvItemName;
        @BindView(R.id.tv_country)
        public TextView tvItemCountry;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
