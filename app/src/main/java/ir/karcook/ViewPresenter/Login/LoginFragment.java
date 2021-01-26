package ir.karcook.ViewPresenter.Login;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.MainActivity;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.GetForgetPassCode_useCase;
import ir.karcook.UseCase.Login_useCase;
import ir.karcook.ViewPresenter.ResetPassword.ResetPasswordFragment;
import ir.karcook.databinding.LoginBinding;


public class LoginFragment extends Fragment implements LoginContract.veiw {

    LoginPresenter loginPresenter;
    boolean fragmentDestroyed = false;
    ProgressBar dialogProgress;
    Dialog dialog;
    TextView dialogOk;
    String phoneNumber;
    EditText phone;
    LoginBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login, container, false);
        getActivity().findViewById(R.id.topLayout).setVisibility(View.VISIBLE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginPresenter = new LoginPresenter(this, new Login_useCase(),
                new GetForgetPassCode_useCase());
        fragmentDestroyed = false;


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (G.getInstance().isNetworkAvailable(getContext())) {
                    if (binding.userName.getText().toString().equals("") || binding.password.getText().toString().equals(""))
                        G.getInstance().customSnackBar(getContext(), binding.getRoot(), getString(R.string.EmptyFieldError));
                    else if (binding.password.getText().toString().length() < 6)
                        binding.password.setError(getString(R.string.passwordError));
                    else {
                        binding.progress.setVisibility(View.VISIBLE);
                        binding.loginBtn.setText("");
                        binding.loginBtn.setEnabled(false);
                        loginPresenter.login(binding.userName.getText().toString(), binding.password.getText().toString());
                    }
                } else
                    G.getInstance().customSnackBar(getContext(), binding.getRoot(), getString(R.string.netWorkError));
            }
        });

        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.getInstance().alphaAnimation(binding.forgetPassword);
                forgetPasswordDialog("برای بازیابی رمز عبور خود , لطفا شماره تلفن خود را در کادر زیر وارد کنید و منتظر دریافت کد تایید بمانید");
            }
        });
    }

    @Override
    public void loginFailed(String error) {
        if (!fragmentDestroyed) {
            binding.progress.setVisibility(View.GONE);
            binding.loginBtn.setText("ورود");
            binding.loginBtn.setEnabled(true);
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
        }
    }

    @Override
    public void requestVerifyCodeSuccess(String data) {
        if (!fragmentDestroyed) {
            dialog.cancel();
            Fragment fragment = new ResetPasswordFragment();
            Bundle b = new Bundle();
            b.putString("phoneNumber", phoneNumber);
            b.putString("code", data);
            fragment.setArguments(b);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.container2, fragment).addToBackStack("reset").commit();
        }
    }

    @Override
    public void requestVerifyCodeFailed(String error) {
        if (!fragmentDestroyed) {
            dialogProgress.setVisibility(View.GONE);
            dialogOk.setText("تایید");
            dialogOk.setEnabled(true);
            dialog.setCancelable(true);
            phone.setError(error);
        }
    }


    @Override
    public void loginSuccess() {
        if (!fragmentDestroyed) {
            binding.progress.setVisibility(View.GONE);
            binding.loginBtn.setText("ورود");
            binding.loginBtn.setEnabled(true);
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, getString(R.string.loginSuccess));
            binding.loginBtn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finishAffinity();
                }
            }, 1000);

        }
    }

    @Override
    public void userDisabled(String error) {
        if (!fragmentDestroyed){
            forgetPasswordDialog(error);
        }
    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        super.onDetach();
    }

    private void forgetPasswordDialog(String error) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forget_password);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        phone = (EditText) dialog.findViewById(R.id.phoneNumber);
        TextView title = dialog.findViewById(R.id.title);
        dialogProgress = (ProgressBar) dialog.findViewById(R.id.dialogProgress);
        dialogOk = dialog.findViewById(R.id.ok);
        title.setText(error);
        phone.setText(binding.userName.getText().toString());
        dialog.show();
        dialog.setCancelable(true);

        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (G.getInstance().isNetworkAvailable(getActivity())) {
                    if (phone.getText().toString().equals(""))
                        phone.setError(getString(R.string.EmptyFieldError));
                    else {
                        dialogProgress.setVisibility(View.VISIBLE);
                        dialogOk.setText("");
                        dialogOk.setEnabled(false);
                        dialog.setCancelable(false);
                        phoneNumber = phone.getText().toString();
                        Map<String, String> params = new HashMap<>();
                        params.put("Username", G.getInstance().replaceFarsiNumber(phoneNumber));
                        loginPresenter.requestForgetCode(params);
                    }

                } else {
                    G.getInstance().customSnackBar(getContext(), binding.mainLayout, getString(R.string.netWorkError));
                }


            }
        });
    }

}


