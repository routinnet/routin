package ir.karcook.ViewPresenter.VoiceListPage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.CourseListApiModel;
import ir.karcook.Models.SearchAPIModel;

public interface VoiceListContract {

    interface presenter extends BasePresnter {
        Context getContext();

        VoiceListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(VoiceListAdapter.viewHolder holder, int position);

        int getItemCount();

        void itemClicked(int adapterPosition);

        void getCourseListById(int coursePackageId);

        void getCourseDetail(int coursePackageId, int courseId);

        void playBtnClicked(int adapterPosition);

        void doSearch(int coursePkgId, String text);
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void getCourseListByIdSuccess(CourseListApiModel model);

        void getCourseListByIdFailed(String error);

        void itemClicked(int adapterPosition);

        void getCourseDetailSuccess(CourseDetailAPIModel model);

        void getCourseDetailFailed(String error);

        void playBtnClicked(int adapterPosition);

        void doSearchFailed(String error);

        void emptyList();
    }

}