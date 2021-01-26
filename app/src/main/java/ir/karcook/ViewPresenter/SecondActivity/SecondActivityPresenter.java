package ir.karcook.ViewPresenter.SecondActivity;

import android.content.Context;
import android.content.SharedPreferences;

public class SecondActivityPresenter implements SecondActivityContract.presenter {
    private SecondActivityContract.view iv;
    private Context context;

    public SecondActivityPresenter(SecondActivityContract.view iv) {
        this.iv = iv;
        setContext(iv.getContext());
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

}