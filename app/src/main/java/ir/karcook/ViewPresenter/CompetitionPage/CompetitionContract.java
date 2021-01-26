package ir.karcook.ViewPresenter.CompetitionPage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.CompetitionListAPIModel;

public interface CompetitionContract {

    interface presenter extends BasePresnter {
        Context getContext();

        CompetitionListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(CompetitionListAdapter.viewHolder holder, int position);

        int getItemCount();

        void getCompetitionList();

        void itemClicked(int adapterPosition);
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void getCompetitionListSuccess(CompetitionListAPIModel model);

        void getCompetitionListFailed(String error);

        void itemClicked(int adapterPosition);

        void emptyList();
    }

}