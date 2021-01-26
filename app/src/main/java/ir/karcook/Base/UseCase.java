package ir.karcook.Base;

import android.content.Context;

/**
 * Created by Emami on 5/23/2018.
 */

public interface UseCase<P, R> {
    interface CallBack<R> {
        void onSuccess(R response);

        void onError(String error);
    }

    void execute(P parameter, CallBack<R> callBack, Context context);
}
