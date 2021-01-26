package ir.karcook.ViewPresenter.PlayVideoPage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.AnswerVideoAPIModel;

public interface PlayVideoContract {

    interface presenter extends BasePresnter {
        Context getContext();

        PlayVideoListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(PlayVideoListAdapter.viewHolder holder, int position);

        int getItemCount();

        void itemClicked(int adapterPosition);

        void answer(int id ,int courseId);
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void itemClicked(int adapterPosition);

        void answerTrue();
        void answerFalse();

        void answerFailed(String error);
    }

}