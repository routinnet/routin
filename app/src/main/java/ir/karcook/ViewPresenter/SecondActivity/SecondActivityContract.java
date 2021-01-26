package ir.karcook.ViewPresenter.SecondActivity;

import android.content.Context;

import ir.karcook.Base.BasePresnter;
import ir.karcook.Base.BaseView;

public interface SecondActivityContract {

    interface presenter extends BasePresnter {
        Context getContext();

    }

    interface view extends BaseView<presenter> {

        Context getContext();

    }

}