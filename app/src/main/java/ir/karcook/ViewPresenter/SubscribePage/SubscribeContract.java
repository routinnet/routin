package ir.karcook.ViewPresenter.SubscribePage;

import android.content.Context;
import android.view.ViewGroup;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;
import ir.karcook.Models.CreateFactorIdAPIModel;
import ir.karcook.Models.SubscribeAPIModel;

public interface SubscribeContract {

    interface presenter extends BasePresnter {
        Context getContext();

        SubscribeListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(SubscribeListAdapter.viewHolder holder, int position);

        int getItemCount();

        void itemClicked(int adapterPosition);

        void getSubscribeList();

        void createFactorId(int id);
    }

    interface view extends BaseView<presenter> {

        Context getContext();

        void getSubscribeListSuccess(SubscribeAPIModel model);

        void getSubscribeListFailed(String error);

        void itemClicked(int adapterPosition);

        void createFactorIdSuccess(CreateFactorIdAPIModel model);

        void createFactorIdFailed(String error);
    }

}