package ir.karcook;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.ViewPresenter.CategoryPage.CategoryFragment;
import ir.karcook.ViewPresenter.CompetitionPage.CompetitionFragment;
import ir.karcook.ViewPresenter.HomePage.HomeFragment;
import ir.karcook.ViewPresenter.ProfilePage.ProfileFragment;
import ir.karcook.ViewPresenter.SearchPage.SearchPageFragment;
import ir.karcook.ViewPresenter.WebViewActivity;
import ir.karcook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    boolean activityDestroyed = false;
    private long mBackPressed;
    final int TIME_INTERVAL = 2000;
    ChipNavigationBar nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityDestroyed = false;

        nav = (ChipNavigationBar)findViewById(R.id.bottom_menu);
        nav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

            }
        });

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        if (getIntent().hasExtra("isPush"))
            fragmentTransaction.replace(R.id.container, new HomeFragment(true ,
                    getIntent().getExtras().getInt("course"),
                    getIntent().getExtras().getInt("coursePackage"))).commit();
        else
            fragmentTransaction.replace(R.id.container, new HomeFragment(false)).commit();

        binding.bottomNavigation.setSelectedItemId(R.id.home_bottom);

        if (Shared_Prefrences.getInstance(getApplicationContext()).getSp()
                .getBoolean(getString(R.string.logged), false)) {
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_bottom) {

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.container, new HomeFragment(false)).commit();


                } else if (item.getItemId() == R.id.category_bottom) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.container, new CategoryFragment()).commit();

                } else if (item.getItemId() == R.id.search) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.container, new SearchPageFragment()).commit();

                } else if (item.getItemId() == R.id.competition) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.container, new CompetitionFragment()).commit();

                } else if (item.getItemId() == R.id.profile) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.container, new ProfileFragment()).commit();

                }
                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        activityDestroyed = true;

    }

    public void aboutDialog() {
/*        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.about_dialog);
        dialog.show();*/

        Intent ii = new Intent(this, WebViewActivity.class);
        ii.putExtra("url", "https://karcook.ir/home/Aboutus");
        startActivity(ii);

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 && !(getSupportFragmentManager().findFragmentById(R.id.container) instanceof HomeFragment)) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(getBaseContext(), "برای خروج دوباره کلید برگشت را بزنید", Toast.LENGTH_SHORT).show();
            }

            mBackPressed = System.currentTimeMillis();
        }
    }
}
