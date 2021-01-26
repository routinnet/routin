package ir.karcook.ViewPresenter.SecondActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ir.karcook.R;
import ir.karcook.ViewPresenter.HomePage.HomeFragment;
import ir.karcook.ViewPresenter.VoiceListPage.VoiceListFragment;
import ir.karcook.databinding.SecondActivityBinding;

public class SecondActivity extends AppCompatActivity implements SecondActivityContract.view {

    SecondActivityPresenter presenter;
    SecondActivityBinding binding;
    boolean activityDestroyed = false;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.second_activity);
        activityDestroyed = false;
        presenter = new SecondActivityPresenter(this);

        fragment = (Fragment) getIntent().getExtras().getSerializable("fragment");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.container, fragment).commit();


    }


    @Override
    protected void onStop() {
        super.onStop();
        activityDestroyed = true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof VoiceListFragment && ((VoiceListFragment) fragment).playerIsPlaying()) {
            ((VoiceListFragment) fragment).gonePlayer();
        } else
            super.onBackPressed();
    }
}