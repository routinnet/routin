package ir.karcook.FCM;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.UseCase.SendTokenToServer_useCase;

public class SendTokenToServer {

    private static SendTokenToServer sendTokenToServer;
    private boolean sendingToken = false;

    public static SendTokenToServer getInstance() {
        if (sendTokenToServer == null)
            sendTokenToServer = new SendTokenToServer();
        return sendTokenToServer;
    }


    public void sendTokenToServer(final SendTokenToServer_useCase useCase, final String token, final Context context) {
        if (!Shared_Prefrences.getInstance(context).getSp().getBoolean(context.getString(R.string.fcm_sent), false)) {
            if (token == null || token.equals("")) {
                sendEgain(useCase, token, context);
            } else {
                if (!sendingToken) {
                    sendingToken = true;
                    useCase.execute(token, new UseCase.CallBack<MessageAPIModel>() {
                        @Override
                        public void onSuccess(MessageAPIModel response) {
                                sendingToken = false;
                                SharedPreferences.Editor editor = Shared_Prefrences.getInstance(context).getSp().edit();
                                editor.putBoolean(context.getString(R.string.fcm_sent), true);
                                editor.apply();
                                Log.i("auto", "fcm sent");


                        }

                        @Override
                        public void onError(String error) {
                            sendingToken = false;
                            sendEgain(useCase, token, context);
                            Log.i("auto", "fcm error");
                        }
                    }, context);
                }
            }
        }
    }

    private void sendEgain(final SendTokenToServer_useCase useCase, final String token, final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendTokenToServer(useCase, token, context);
            }
        }, 4000);
    }
}
