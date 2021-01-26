package ir.karcook.ViewPresenter.SearchPage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchSendModel;

public interface SearchPageContract {

    interface presenter extends BasePresnter {
        Context getContext();
        
        void doSearch(SearchSendModel model);

        SearchPageListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        int getItemCount();

        void onBindViewHolder(SearchPageListAdapter.viewHolder holder, int position);

        void itemClicked(int adapterPosition);

        void getCourseDetail(int coursePackageId , int courseId);

    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void doSearchSuccess(SearchAPIModel model);

        void doSearchFailed(String error);

        void emptyList();

        void itemClicked(int adapterPosition);

        void getCourseDetailSuccess(CourseDetailAPIModel model);
        void getCourseDetailFailed(String error);

    }

}