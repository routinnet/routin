package ir.karcook.ViewPresenter.CategoryPage;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import ir.karcook.databinding.RowCategoryBinding;
import ir.karcook.databinding.RowContentBinding;
import ir.karcook.databinding.RowHomeNewBinding;


/**
 * Created by Adak on 21/02/2018.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.viewHolder> {

    CategoryContract.presenter ip;

    public CategoryListAdapter(CategoryContract.presenter ip) {
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
        public RowCategoryBinding binding;

        public viewHolder(RowCategoryBinding binding, final CategoryContract.presenter ip) {
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