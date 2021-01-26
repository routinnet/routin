package ir.karcook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.ViewPresenter.IntroFragment;
import ir.karcook.ViewPresenter.Login.LoginFragment;
import ir.karcook.databinding.LoginRegisterActivityBinding;


/**
 * Created by Adak on 23/05/2018.
 */

public class Login_Register_Activity extends AppCompatActivity {

    LoginRegisterActivityBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Shared_Prefrences.getInstance(getApplicationContext()).getSp().
                getBoolean(getString(R.string.logged), false)) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        } else {

            binding = DataBindingUtil.setContentView(this, R.layout.login_register_activity);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.container2, new IntroFragment()).commit();

        }

    }

}
