package ir.karcook.ViewPresenter.SplashPage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.BuildConfig;

import ir.karcook.Login_Register_Activity;
import ir.karcook.MainActivity;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.UseCase.GetProfile_useCase;
import ir.karcook.databinding.SplashScreenBinding;

public class SplashScreen extends AppCompatActivity implements SplashScreenContract.view{
    SplashScreenBinding binding;
    boolean activityDestroyed = false;
    SplashScreenPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen);
        activityDestroyed = false;
        presenter = new SplashScreenPresenter(this , new GetProfile_useCase());

        G.getInstance().alphaAnimation(binding.logo);
        G.getInstance().alphaAnimation(binding.txt1);

        G.getInstance().animateUp(binding.version, 400, 1000);

        binding.version.setText("نسخه " + BuildConfig.VERSION_NAME);

        (findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.getInstance().refreshClickedMainProgress(SplashScreen.this);
                presenter.getProfile();
            }
        });


        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (Shared_Prefrences.getInstance(getApplicationContext()).getSp()
                            .getBoolean(getString(R.string.logged), false)) {

                        presenter.getProfile();

                    } else {
                        Intent i = new Intent(getApplicationContext(), Login_Register_Activity.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityDestroyed = true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getProfileSuccess() {
        if (!activityDestroyed){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void getProfileFailed(String error) {
        if (!activityDestroyed){
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
            G.getInstance().errorMainProgress(this);

        }
    }

    @Override
    public void tokenExpired() {
        if (!activityDestroyed){
            SharedPreferences.Editor editor = Shared_Prefrences.getInstance(getApplicationContext()).getSp().edit();
            editor.putBoolean(getString(R.string.logged), false);
            editor.apply();
            tokenExpireDialog();
        }
    }

    private void tokenExpireDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.token_expire_dialog);
        TextView txt = (TextView) dialog.findViewById(R.id.txt);
        TextView yes = (TextView) dialog.findViewById(R.id.yes);
        TextView no = (TextView) dialog.findViewById(R.id.no);
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                Intent i = new Intent(getApplicationContext(), Login_Register_Activity.class);
                startActivity(i);
                finish();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
