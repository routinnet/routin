package ir.karcook.ViewPresenter.SubCategoryPage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.SubCategoryApiModel;

public interface SubCategoryContract {

    interface presenter extends BasePresnter {
        Context getContext();

        void onBindViewHolder(SubCategoryListAdapter.viewHolder holder, int position);

        int getItemCount();

        SubCategoryListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void itemClicked(int adapterPosition);

        void getSubCategory(int id);
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void getSubCategorySuccess(SubCategoryApiModel model);

        void getSubCategoryFailed(String error);

        void goToVideoList(int adapterPosition);

        void goToVoiceList(int adapterPosition);
    }

}