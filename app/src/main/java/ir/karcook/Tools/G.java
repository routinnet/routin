package ir.karcook.Tools;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.io.File;

import ir.karcook.BuildConfig;
import ir.karcook.R;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;

/**
 * Created by Adak on 16/01/2018.
 */

public class G {

    public static G g;

    public static G getInstance() {
        if (g == null)
            g = new G();
        return g;
    }

    public static final String BASE_URL = "https://karcook.ir/api/";
    public static final String BASE_DOCUMENT_URL = "https://karcook.ir/api/Document?DocumentId=";

    public static String[] educationList = {"پیش دبستانی", "ابتدائی", "متوسطه", "دانشجو"};


    public static enum ContentPage {
        newList,
        freeList,
        byId
    }

    public void customSnackBar(Context context, View v, String text) {
        Snackbar snackbar;
        snackbar = Snackbar.make(v, text, Snackbar.LENGTH_LONG).setAction("تایید", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.purple));
        TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        TextView action = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_action);
        textView.setTextSize(13);
        textView.setTextColor(Color.parseColor("#ffffff"));
        action.setTextColor(context.getResources().getColor(R.color.orangeLight));
        textView.setGravity(Gravity.END);
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.iran_sans_lightt));
        action.setTypeface(ResourcesCompat.getFont(context, R.font.iran_sans_lightt));
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackBarView.getLayoutParams();
//        params.gravity = Gravity.TOP | Gravity.CENTER;
//        snackBarView.setLayoutParams(params);

        snackbar.show();

    }

    public void toast_custom(Context context, String text, int duration) {
        Toast toast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_toast, null);
        toast.setView(v);
        toast.setDuration(duration);
        TextView txt = (TextView) v.findViewById(R.id.txt);
        txt.setText(text);
        toast.show();
    }


    /**
     * delete cache directory
     * <p>
     * i called this method when poster registered or edited
     */
/*    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }*/
    public boolean deleteFileFromPath(Context context, String fileName) {
        boolean deleted = false;
        File f = new File(context.getCacheDir().getAbsolutePath() + "/" + fileName);
        if (f.exists())
            deleted = f.delete();
        return deleted;
    }

    public String replaceEnglishNumber(String text) {
        text = text
                .replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹");
        return text;
    }

    public String replaceFarsiNumber(String text) {
        text = text
                .replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9");
        return text;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .addQueryParam("query", "0")
//              .logger(new Logger() {
//                  @Override
//                  public void log(int level, String tag, String msg) {
//                      Log.w(tag, msg);
//                  }
//              })

                .build());
        return client.build();
    }


    public void alphaAnimation(View v) {
        ObjectAnimator.ofFloat(v, "alpha", 0, 1).
                setDuration(900).                // Duration in milliseconds
                start();

    }

    public void animationScale0To1(View v, long duration) {
        ObjectAnimator.ofFloat(v, "scaleX", 0f, 1f)
                .setDuration(duration).start();
        ObjectAnimator.ofFloat(v, "scaleY", 0f, 1f)
                .setDuration(duration)
                .start();
    }

    public void animationScale1To0(View v, long duration) {
        ObjectAnimator.ofFloat(v, "scaleX", 1f, 0f)
                .setDuration(duration).start();
        ObjectAnimator.ofFloat(v, "scaleY", 1f, 0f)
                .setDuration(duration)
                .start();
    }

    public void animationRotate180(View v) {
        ObjectAnimator.ofFloat(v, "rotation", 0, 180).setDuration(500)
                .start();

    }
/*    public void tokenExpiredDialog(final Activity context, String errorMessage) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.token_expire_dialog);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        Button ok = (Button) dialog.findViewById(R.id.ok);
        dialog.show();
        dialog.setCancelable(false);
        text.setText(errorMessage);
        SharedPreferences sp = Shared_Prefrences.getInstance(context).getSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(context.getResources().getString(R.string.logged), false);
        editor.apply();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }*/

    public void hideKeyBoard(Activity context) {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void showKeyBoard(Activity context) {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void animateUp(View v, int value, int duration) {
        ObjectAnimator.ofFloat(v, "translationY", value, 0).setDuration(duration)
                .start();

    }

    public void animateDown(View v, int value, int duration) {
        ObjectAnimator.ofFloat(v, "translationY", 0, value).setDuration(duration)
                .start();

    }

    public void animateRight(View v, int from, int to, int duration) {
        ObjectAnimator.ofFloat(v, "translationX", from, to).setDuration(duration)
                .start();

    }

    public void animateLeft(View v, int from, int to, int duration) {
        ObjectAnimator.ofFloat(v, "translationX", from, -to).setDuration(duration)
                .start();

    }

    public void showMainProgress(Activity activity) {
        alphaAnimation((activity.findViewById(R.id.progressLayout)));
        (activity.findViewById(R.id.progressLayout)).setVisibility(View.VISIBLE);
        ((TextView) activity.findViewById(R.id.progress_text)).setText("لطفا منتظر بمانید");
        (activity.findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
        (activity.findViewById(R.id.refresh)).setVisibility(View.GONE);

    }

    public void hideMainProgress(final Activity activity) {
        ObjectAnimator.ofFloat((activity.findViewById(R.id.progressLayout)), "alpha", 1, 0).
                setDuration(900).                // Duration in milliseconds
                start();
        (activity.findViewById(R.id.progressLayout)).postDelayed(new Runnable() {
            @Override
            public void run() {
                (activity.findViewById(R.id.progressLayout)).setVisibility(View.GONE);
            }
        }, 900);

    }

    public void errorMainProgress(Activity activity) {
        (activity.findViewById(R.id.progressLayout)).setVisibility(View.VISIBLE);
        ((TextView) activity.findViewById(R.id.progress_text)).setText("تلاش مجدد");
        ((ProgressBar) activity.findViewById(R.id.progressBar)).setVisibility(View.GONE);
        ((ImageView) activity.findViewById(R.id.refresh)).setVisibility(View.VISIBLE);

    }

    public void refreshClickedMainProgress(Activity activity) {
        ((TextView) activity.findViewById(R.id.progress_text)).setText("لطفا منتظر بمانید");
        (activity.findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
        (activity.findViewById(R.id.refresh)).setVisibility(View.GONE);

    }

    public FragmentTransaction fragmentAnimation(FragmentManager fragmentManager) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        return ft;
    }

    public FragmentTransaction fragmentAnimationAlpha(FragmentManager fragmentManager) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        return ft;
    }
}
