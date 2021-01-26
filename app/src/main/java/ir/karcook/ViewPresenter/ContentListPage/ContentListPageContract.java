package ir.karcook.ViewPresenter.ContentListPage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.CourseListApiModel;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchSendModel;

public interface ContentListPageContract {

    interface presenter extends BasePresnter {
        Context getContext();

        void getNewList(SearchSendModel model);

        ContentListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(ContentListAdapter.viewHolder holder, int position);

        int getItemCount();

        void itemClicked(int adapterPosition);

        void getCourseDetail(int coursePackageId, int courseId);

        void getCourseListById(int coursePackageId);
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void getNewListSuccess(SearchAPIModel model);

        void getNewListFailed(String error);


        void emptyList();

        void itemClicked(int adapterPosition);

        void getCourseDetailSuccess(CourseDetailAPIModel model);

        void getCourseDetailFailed(String error);

        void getCourseListByIdSuccess(SearchAPIModel model);

        void getCourseListByIdFailed(String error);


    }

}