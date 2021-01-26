package ir.karcook.ViewPresenter.RecieveCode;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

import ir.karcook.MainActivity;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.SMS_broadCastReceiver;
import ir.karcook.UseCase.GetVerifyCode_useCase;
import ir.karcook.UseCase.VerifyCode_useCase;
import ir.karcook.databinding.ReceiveCodeBinding;

/**
 * Created by Adak on 23/05/2018.
 */

public class ReceiveCodeFragment extends Fragment implements ReceiveCodeContract.view {

    ReceiveCodeBinding binding;
    Timer t;
    private int h = 0, m = 1, s = 59;
    ReceiveCodePresenter p_receiveCode;
    boolean fragmentDestroyed = false;
    SMS_broadCastReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.receive_code, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        p_receiveCode = new ReceiveCodePresenter(this, new VerifyCode_useCase(),
                new GetVerifyCode_useCase());
        fragmentDestroyed = false;
        final Bundle b = getArguments();
        receiver = new SMS_broadCastReceiver();
        binding.userName.setText(b.getString("userName"));
        binding.userName.setEnabled(false);
        timer(null, binding.minute, binding.timer);

        binding.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (G.getInstance().isNetworkAvailable(getContext())) {
                    if (binding.ok.getText().toString().equals("")) {
                        G.getInstance().customSnackBar(getContext(), binding.mainLayout, getResources().getString(R.string.pleaseWait));
                    } else if (binding.ok.getText().toString().equals("تایید")) {
                        if (binding.code.getText().toString().equals("")) {
                            G.getInstance().customSnackBar(getContext(), binding.mainLayout, getResources().getString(R.string.EmptyFieldError));
                        } else {
                            binding.progress.setVisibility(View.VISIBLE);
                            binding.ok.setText("");
                            binding.ok.setEnabled(false);
                            p_receiveCode.activeAccount(binding.code.getText().toString(),
                                    binding.userName.getText().toString());
                        }
                    } else if (binding.ok.getText().toString().equals("ارسال مجدد کد")) {
                        binding.progress.setVisibility(View.VISIBLE);
                        binding.ok.setText("");
                        binding.ok.setEnabled(false);
                        p_receiveCode.requestCode(b.getString("userName"));
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
    public void resendSuccess() {
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
    public void resendFailed(String userMessage) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, userMessage);
            binding.progress.setVisibility(View.GONE);
            binding.ok.setText("ارسال مجدد کد");
            binding.ok.setEnabled(true);
        }

    }

    @Override
    public void accountActivated() {
        if (!fragmentDestroyed) {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
            getActivity().finishAffinity();
        }
    }

    @Override
    public void failedActivation(String userMessage) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, userMessage);
            binding.progress.setVisibility(View.GONE);
            binding.ok.setText("تایید");
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

}
