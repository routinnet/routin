package ir.karcook.ViewPresenter.VoiceListPage;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import ir.karcook.databinding.RowVoiceListBinding;


/**
 * Created by Adak on 21/02/2018.
 */

public class VoiceListAdapter extends RecyclerView.Adapter<VoiceListAdapter.viewHolder> {

    VoiceListContract.presenter ip;

    public VoiceListAdapter(VoiceListContract.presenter ip) {
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
        public RowVoiceListBinding binding;

        public viewHolder(RowVoiceListBinding binding, final VoiceListContract.presenter ip) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ip.itemClicked(getAdapterPosition());
                }
            });

            binding.playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ip.playBtnClicked(getAdapterPosition());
                }
            });
        }
    }
}