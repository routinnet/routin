package ir.karcook.ViewPresenter.PlayVideoPage;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import ir.karcook.databinding.RowAnswerBinding;
import ir.karcook.databinding.RowAnswerDialogBinding;


/**
 * Created by Adak on 21/02/2018.
 */

public class PlayVideoListAdapter extends RecyclerView.Adapter<PlayVideoListAdapter.viewHolder> {

    PlayVideoContract.presenter ip;

    public PlayVideoListAdapter(PlayVideoContract.presenter ip) {
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
        public RowAnswerDialogBinding binding;

        public viewHolder(RowAnswerDialogBinding binding, final PlayVideoContract.presenter ip) {
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