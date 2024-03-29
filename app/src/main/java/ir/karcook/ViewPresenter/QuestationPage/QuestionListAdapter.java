package ir.karcook.ViewPresenter.QuestationPage;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import ir.karcook.Tools.G;
import ir.karcook.databinding.RowAnswerBinding;


/**
 * Created by Adak on 21/02/2018.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.viewHolder> {

    QuestionContract.presenter ip;

    public QuestionListAdapter(QuestionContract.presenter ip) {
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
        public RowAnswerBinding binding;

        public viewHolder(RowAnswerBinding binding, final QuestionContract.presenter ip) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    G.getInstance().alphaAnimation(itemView);
                    ip.itemClicked(getAdapterPosition());
                }
            });
        }
    }
}