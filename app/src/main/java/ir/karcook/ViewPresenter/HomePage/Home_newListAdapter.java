package ir.karcook.ViewPresenter.HomePage;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import ir.karcook.databinding.RowHomeNewBinding;


/**
 * Created by Adak on 21/02/2018.
 */

public class Home_newListAdapter extends RecyclerView.Adapter<Home_newListAdapter.viewHolder> {

    HomeContract.presenter ip;

    public Home_newListAdapter(HomeContract.presenter ip) {
        this.ip = ip;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ip.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        ip.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return ip.getItemCount();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        public RowHomeNewBinding binding;

        public viewHolder(RowHomeNewBinding binding, final HomeContract.presenter ip) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ip.itemClicked(getAdapterPosition());
                }
            });
        }
    }
}