package ir.karcook.ViewPresenter.Register;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.GetForgetPassCode_useCase;
import ir.karcook.UseCase.Register_useCase;
import ir.karcook.ViewPresenter.Login.LoginFragment;
import ir.karcook.ViewPresenter.RecieveCode.ReceiveCodeFragment;
import ir.karcook.ViewPresenter.ResetPassword.ResetPasswordFragment;
import ir.karcook.databinding.RegisterBinding;

/**
 * Created by Adak on 23/05/2018.
 */

public class RegisterFragment extends Fragment implements RegisterContract.view, AdapterView.OnItemClickListener {

    RegisterBinding binding;
    boolean fragmentDestroyed = false;
    RegisterPresenter p_register;
    Spinner spinner;
    ProgressBar dialogProgress;
    Dialog dialog;
    TextView dialogOk;
    String phoneNumber;
    EditText phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.register, container, false);
       // getActivity().findViewById(R.id.topLayout).setVisibility(View.VISIBLE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        p_register = new RegisterPresenter(this, new Register_useCase(),
                new GetForgetPassCode_useCase());
        fragmentDestroyed = false;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_text, G.educationList);
      //  binding.education.setAdapter(adapter);

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (G.getInstance().isNetworkAvailable(getContext())) {
                    if (binding.userName.getText().toString().equals("") || binding.password.getText().toString().equals("")
                            || binding.repeatPassword.getText().toString().equals("") );
                     //       binding.age.getText().toString().equals(""))
                   //     G.getInstance().customSnackBar(getContext(), binding.getRoot(), getString(R.string.EmptyFieldError));
                    else if (binding.password.getText().toString().length() < 6)
                        binding.password.setError(getString(R.string.passwordError));
                    else if (!binding.userName.getText().toString().startsWith("09") || binding.userName.getText().toString().length() < 11)
                        binding.userName.setError(getString(R.string.invalidNumber));
                    else if (!binding.password.getText().toString().equals(binding.repeatPassword.getText().toString())) {
                        binding.repeatPassword.setError(getString(R.string.errorRepeatePass));
                    } else {

                        binding.progress.setVisibility(View.VISIBLE);
                        binding.registerBtn.setText("");
                        binding.registerBtn.setEnabled(false);
                        Map<String, Object> params = new HashMap<>();
                        params.put("Username", G.getInstance().replaceFarsiNumber(binding.userName.getText().toString()));
                        params.put("Password", G.getInstance().replaceFarsiNumber(binding.password.getText().toString()));
                        params.put("IntroducingFriendsCode", G.getInstance().replaceFarsiNumber(binding.friendCode.getText().toString()));
                       // params.put("Age", Integer.parseInt(G.getInstance().replaceFarsiNumber(binding.age.getText().toString())));
                     //   params.put("DegreeOfEducation", binding.education.getSelectedItemPosition() + 1);
                        p_register.register(params);
                    }
                } else
                    G.getInstance().customSnackBar(getContext(), binding.getRoot(), getString(R.string.netWorkError));

            }
        });

        binding.goToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.container2, new LoginFragment()).addToBackStack("login").commit();

            }
        });

    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        super.onDetach();
    }


    @Override
    public void registerSuccess() {
        if (!fragmentDestroyed) {
            binding.progress.setVisibility(View.GONE);
            binding.registerBtn.setText("ثبت نام");
            binding.registerBtn.setEnabled(true);

            Fragment fragment = new ReceiveCodeFragment();
            Bundle b = new Bundle();
            b.putString("userName", binding.userName.getText().toString());
            b.putString("password", binding.password.getText().toString());
            fragment.setArguments(b);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.container2, fragment).addToBackStack("code").commit();


        }
    }

    @Override
    public void registerFailed(String error) {
        if (!fragmentDestroyed) {
            binding.progress.setVisibility(View.GONE);
            binding.registerBtn.setText("ثبت نام");
            binding.registerBtn.setEnabled(true);
            G.getInstance().customSnackBar(getContext(), binding.getRoot(), error);
        }
    }

    @Override
    public void userDisabled(final String error) {
        if (!fragmentDestroyed) {

            binding.progress.setVisibility(View.GONE);
            binding.registerBtn.setText("ثبت نام");
            binding.registerBtn.setEnabled(true);

            forgetPasswordDialog(error);

        }
    }

    @Override
    public void requestVerifyCodeSuccess() {
        if (!fragmentDestroyed) {
            dialog.cancel();
            Fragment fragment = new ResetPasswordFragment();
            Bundle b = new Bundle();
            b.putString("phoneNumber", phoneNumber);
            fragment.setArguments(b);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.container2, fragment).addToBackStack("reset").commit();

        }
    }

    @Override
    public void requestVerifyCodeFailed(String error) {
        if (!fragmentDestroyed) {
            dialog.cancel();
            G.getInstance().customSnackBar(getContext(), binding.getRoot(), error);

        }
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
                        p_register.requestForgetCode(params);
                    }

                } else {
                    G.getInstance().customSnackBar(getContext(), binding.linearlayoutregister, getString(R.string.netWorkError));
                }

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
