package ir.karcook.ViewPresenter.SubscribePage;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import ir.karcook.databinding.RowSubscribeBinding;


/**
 * Created by Adak on 21/02/2018.
 */

public class SubscribeListAdapter extends RecyclerView.Adapter<SubscribeListAdapter.viewHolder> {

    SubscribeContract.presenter ip;

    public SubscribeListAdapter(SubscribeContract.presenter ip) {
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
        public RowSubscribeBinding binding;

        public viewHolder(RowSubscribeBinding binding, final SubscribeContract.presenter ip) {
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