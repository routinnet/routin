package ir.karcook.ViewPresenter.ResetPassword;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.SMS_broadCastReceiver;
import ir.karcook.UseCase.GetForgetPassCode_useCase;
import ir.karcook.UseCase.GetVerifyCode_useCase;
import ir.karcook.UseCase.ResetPassword_useCase;
import ir.karcook.databinding.ResetPasswordBinding;

/**
 * Created by Adak on 28/05/2018.
 */

public class ResetPasswordFragment extends Fragment implements ResetPasswordContract.view {


    ResetPasswordBinding binding;
    ResetPasswordPresenter _resetPasswordPresenter;
    boolean fragmentDestroyed = false;
    SMS_broadCastReceiver receiver;
    private int h = 0, m = 1, s = 59;
    Timer t;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.reset_password, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _resetPasswordPresenter = new ResetPasswordPresenter(this,
                new ResetPassword_useCase(),
                new GetForgetPassCode_useCase());
        fragmentDestroyed = false;
        checkPermissions();
        receiver = new SMS_broadCastReceiver();
        timer(null, binding.minute, binding.timer);
        final Bundle b = getArguments();

        binding.userName.setText(b.getString("phoneNumber"));


        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (G.getInstance().isNetworkAvailable(getContext())) {
                    if (binding.ok.getText().toString().equals("تایید")) {
                        if (binding.code.getText().toString().equals("") || binding.userName.getText().toString().equals("") ||
                                binding.newPass.getText().toString().equals("") || binding.repeatNewPass.getText().toString().equals("")) {

                            G.getInstance().customSnackBar(getContext(), binding.mainLayout, getString(R.string.EmptyFieldError));

                        } else if (!binding.newPass.getText().toString().equals(binding.repeatNewPass.getText().toString())) {
                            binding.repeatNewPass.setError(getString(R.string.errorRepeatePass));
                        } else if (binding.newPass.getText().toString().length() < 6)
                            binding.newPass.setError(getString(R.string.passwordError));
                        else {
                            binding.progress.setVisibility(View.VISIBLE);
                            binding.ok.setText("");
                            binding.ok.setEnabled(false);
                            _resetPasswordPresenter.resetPass(binding.userName.getText().toString(),
                                    b.getString("code"), binding.code.getText().toString(),
                                    binding.newPass.getText().toString());
                        }
                    } else if (binding.ok.getText().toString().equals("ارسال مجدد کد")) {
                        binding.progress.setVisibility(View.VISIBLE);
                        binding.ok.setText("");
                        binding.ok.setEnabled(false);
                        Map<String, String> param = new HashMap<>();
                        param.put("Username", G.getInstance().replaceFarsiNumber(b.getString("phoneNumber")));
                        _resetPasswordPresenter.requestForgetCode(param);
                    } else if (binding.ok.getText().toString().equals("")) {
                        G.getInstance().customSnackBar(getContext(), binding.mainLayout, getResources().getString(R.string.pleaseWait));
                    }
                } else
                    G.getInstance().customSnackBar(getContext(), binding.mainLayout, getResources().getString(R.string.netWorkError));


            }
        });

    }


    private void timer(@Nullable final TextView H, @Nullable final TextView M, final TextView S) {
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (s > 0) {
                                if (s <= 10) {
                                    S.setText("0" + String.valueOf(--s));
                                } else
                                    S.setText(String.valueOf(--s));
                            } else if (m > 0 || h > 0) {
                                m--;
                                if (m >= 0) {
                                    if (m <= 10) {
                                        if (M != null)
                                            M.setText("0" + String.valueOf(m));
                                    } else {
                                        if (M != null)
                                            M.setText(String.valueOf(m));
                                    }
                                } else if (h > 0) {
                                    h--;
                                    if (h == 0) {
                                        h = 0;
                                    }
                                    m = 60;
                                    if (M != null)
                                        M.setText(String.valueOf(--m));

                                } else {
                                    m = 0;
                                }
                                s = 60;
                                S.setText(String.valueOf(--s));
                            } else {
                                s = 0;
                                binding.ok.setText("ارسال مجدد کد");
                                binding.progress.setVisibility(View.GONE);
                                t.cancel();
                            }

                            if (H != null)
                                H.setText(String.valueOf(h));
                            if (m <= 10) {
                                if (M != null)
                                    M.setText("0" + String.valueOf(m));
                            } else {
                                if (M != null)
                                    M.setText(String.valueOf(m));
                            }

                        }
                    });
            }
        }, 0, 1000);
    }


    @Override
    public void resetPassSuccess() {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, getString(R.string.successChangePass));
            binding.ok.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getFragmentManager().popBackStack();
                }
            }, 1000);

        }
    }

    @Override
    public void resetPassFailed(String string) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, string);
            binding.progress.setVisibility(View.GONE);
            binding.ok.setText("تایید");
            binding.ok.setEnabled(true);
        }
    }

    @Override
    public void requestVerifyCodeSuccess() {
        if (!fragmentDestroyed) {
            if (t != null) {
                s = 59;
                timer(null, binding.minute, binding.timer);
            }
            binding.progress.setVisibility(View.GONE);
            binding.ok.setText("تایید");
            binding.ok.setEnabled(true);

        }
    }

    @Override
    public void requestVerifyCodeFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
            binding.progress.setVisibility(View.GONE);
            binding.ok.setText("ارسال مجدد");
            binding.ok.setEnabled(true);
        }
    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        if (t != null)
            t.cancel();
        super.onDetach();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {

            } else
                requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }
    }
}
