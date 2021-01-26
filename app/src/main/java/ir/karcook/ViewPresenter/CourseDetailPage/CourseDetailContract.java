package ir.karcook.ViewPresenter.CourseDetailPage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.CourseDetailAPIModel;

public interface CourseDetailContract {

    interface presenter extends BasePresnter {
        Context getContext();

        CourseDetailListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(CourseDetailListAdapter.viewHolder holder, int position);

        int getItemCount();

        void itemClicked(int adapterPosition);

        void getCourseDetail(int coursePackageId , int courseId);

    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void itemClicked(int adapterPosition);

        void getCourseDetailSuccess(CourseDetailAPIModel model);
        void getCourseDetailFailed(String error);

    }

}