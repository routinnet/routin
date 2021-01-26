package ir.karcook.ViewPresenter.CategoryPage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.CategoryApiModel;

public interface CategoryContract {

    interface presenter extends BasePresnter {
        Context getContext();

        CategoryListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(CategoryListAdapter.viewHolder holder, int position);

        int getItemCount();

        void itemClicked(int adapterPosition);

        void getCategoryList();
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void getCategoryListSuccess(CategoryApiModel model);

        void getCategoryListFailed(String error);

        void itemClicked(int adapterPosition);
    }

}