package ir.karcook.ViewPresenter.HomePage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.HomeDataAPIModel;

public interface HomeContract {

    interface presenter extends BasePresnter {
        Context getContext();

        void getHomeData();

        Home_newListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(Home_newListAdapter.viewHolder holder, int position);

        int getItemCount();

        void itemClicked(int adapterPosition);

        Home_freeListAdapter.viewHolder onCreateViewHolder_free(ViewGroup parent, int viewType);

        int getItemCount_free();

        void onBindViewHolder_free(Home_freeListAdapter.viewHolder holder, int position);

        void itemClicked_free(int adapterPosition);

        void getCourseDetail(int coursePackageId , int courseId);
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void getHomeDataSuccess(HomeDataAPIModel model);
        void getHomeDataFailed(String error);

        void getCourseDetailSuccess(CourseDetailAPIModel model);
        void getCourseDetailFailed(String error);

        void lastItemClicked(int adapterPosition);

        void itemClicked_free(int adapterPosition);
    }

}