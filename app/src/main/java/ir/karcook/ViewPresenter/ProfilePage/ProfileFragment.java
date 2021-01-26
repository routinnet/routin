package ir.karcook.ViewPresenter.ProfilePage;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Login_Register_Activity;
import ir.karcook.Models.ProfileAPIModel;
import ir.karcook.Models.SearchSendModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.UseCase.ChangeAge_useCase;
import ir.karcook.UseCase.ChangePass_useCase;
import ir.karcook.UseCase.GetProfile_useCase;
import ir.karcook.UseCase.GetVerifyCode_useCase;
import ir.karcook.databinding.ProfilePageBinding;

public class ProfileFragment extends Fragment implements ProfileContract.view {

    ProfilePageBinding binding;
    ProfilePresenter presenter;
    boolean fragmentDestroyed = false;
    Dialog changePassDialog;
    Dialog avatarDialog;
    Dialog changeAge;
    TextView dialogOk;
    private ProgressBar dialogProgress;
    ProfileAPIModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDestroyed = false;
        presenter = new ProfilePresenter(this,
                new GetProfile_useCase(),
                new ChangePass_useCase(),
                new GetVerifyCode_useCase(),
                new ChangeAge_useCase());

        if (Shared_Prefrences.getInstance(getContext()).getSp()
                .getBoolean(getString(R.string.logged), false)) {
            binding.loginPage.setVisibility(View.VISIBLE);
            binding.logoutPage.setVisibility(View.GONE);


            G.getInstance().showMainProgress(getActivity());
            presenter.getProfile();

            binding.changePass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changePasswordDialog();
                }
            });

            binding.logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_signOut();
                }
            });

            (getActivity().findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    G.getInstance().refreshClickedMainProgress(getActivity());
                    presenter.getProfile();
                }
            });


            binding.ageEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    binding.changeAge.setVisibility(View.VISIBLE);

                }
            });

            binding.changeAge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.ageProgress.setVisibility(View.VISIBLE);
                    presenter.getVerifyCode(model.getUserName());
                }
            });

            binding.inviteFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = "http://karcook.ir/Home/Register#" + model.getIntroducingFriendsCode();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "کارکوک\n کودکان امروز کارآفرینان فردا\nآدرس سایت و دانلود اپلیکیشن :\n " + link);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "کارکوک");
                    startActivity(Intent.createChooser(shareIntent, "کارکوک"));

                }
            });


        } else {
            G.getInstance().hideMainProgress(getActivity());
            binding.loginPage.setVisibility(View.GONE);
            binding.logoutPage.setVisibility(View.VISIBLE);

            binding.goToLoginPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), Login_Register_Activity.class);
                    startActivity(i);
                    getActivity().finish();

                }
            });

        }

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_avatar();
            }
        });


        setProfileAvatar();

    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        super.onDetach();
    }

    private void setProfileAvatar(){

        SharedPreferences sp = Shared_Prefrences.getInstance(getContext()).getSp();
        String profile_image = sp.getString(getResources().getString(R.string.profileImage), "default");

        switch (profile_image) {
            case "educate_avatar":
                binding.profileImage.setImageResource(R.drawable.educate_avatar);
                break;
            case "doctor_avatar":
                binding.profileImage.setImageResource(R.drawable.doctor_avatar);
                break;
            case "astronaut_avatar":
                binding.profileImage.setImageResource(R.drawable.astronaut_avatar);
                break;
            case "polic_avatar":
                binding.profileImage.setImageResource(R.drawable.polic_avatar);
                break;
            case "pilot_avatar":
                binding.profileImage.setImageResource(R.drawable.pilot_avatar);
                break;
            case "engineer_avatar":
                binding.profileImage.setImageResource(R.drawable.engineer_avatar);
                break;

            default:
                binding.profileImage.setImageResource(R.drawable.person_male_icon);

        }

    }

    private void dialog_signOut() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sign_out_dialog);
        TextView txt = (TextView) dialog.findViewById(R.id.txt);
        TextView yes = (TextView) dialog.findViewById(R.id.yes);
        TextView no = (TextView) dialog.findViewById(R.id.no);
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                SharedPreferences.Editor editor = Shared_Prefrences.getInstance(getActivity()).getSp().edit();
                editor.putBoolean(getString(R.string.logged), false);
                editor.putString(getString(R.string.token), "");
                editor.apply();

                Intent i = new Intent(getActivity(), Login_Register_Activity.class);
                startActivity(i);
                getActivity().finish();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });


    }

    private void dialog_avatar() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.avatar_profile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ImageView educate_avatar = dialog.findViewById(R.id.educate_avatar);
        ImageView doctor_avatar = dialog.findViewById(R.id.doctor_avatar);
        ImageView astronaut_avatar = dialog.findViewById(R.id.astronaut_avatar);
        ImageView polic_avatar = dialog.findViewById(R.id.polic_avatar);
        ImageView pilot_avatar = dialog.findViewById(R.id.pilot_avatar);
        ImageView engineer_avatar = dialog.findViewById(R.id.engineer_avatar);
        dialog.show();


        educate_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileImage.setImageResource(R.drawable.educate_avatar);
                SharedPreferences sp = Shared_Prefrences.getInstance(getContext()).getSp();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(getResources().getString(R.string.profileImage), "educate_avatar");
                editor.apply();
                dialog.cancel();
            }
        });

        doctor_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileImage.setImageResource(R.drawable.doctor_avatar);
                SharedPreferences sp = Shared_Prefrences.getInstance(getContext()).getSp();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(getResources().getString(R.string.profileImage), "doctor_avatar");
                editor.apply();
                dialog.cancel();
            }
        });

        astronaut_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileImage.setImageResource(R.drawable.astronaut_avatar);
                SharedPreferences sp = Shared_Prefrences.getInstance(getContext()).getSp();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(getResources().getString(R.string.profileImage), "astronaut_avatar");
                editor.apply();
                dialog.cancel();
            }
        });

        polic_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileImage.setImageResource(R.drawable.polic_avatar);
                SharedPreferences sp = Shared_Prefrences.getInstance(getContext()).getSp();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(getResources().getString(R.string.profileImage), "polic_avatar");
                editor.apply();
                dialog.cancel();
            }
        });

        pilot_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileImage.setImageResource(R.drawable.pilot_avatar);
                SharedPreferences sp = Shared_Prefrences.getInstance(getContext()).getSp();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(getResources().getString(R.string.profileImage), "pilot_avatar");
                editor.apply();
                dialog.cancel();
            }
        });

        engineer_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileImage.setImageResource(R.drawable.engineer_avatar);
                SharedPreferences sp = Shared_Prefrences.getInstance(getContext()).getSp();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(getResources().getString(R.string.profileImage), "engineer_avatar");
                editor.apply();
                dialog.cancel();
            }
        });


    }


    private void changePasswordDialog() {
        changePassDialog = new Dialog(getActivity());
        changePassDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changePassDialog.setContentView(R.layout.change_password);
        changePassDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText oldPassword = (EditText) changePassDialog.findViewById(R.id.oldPassword);
        final EditText newPassword = (EditText) changePassDialog.findViewById(R.id.newPassword);
        final EditText confirmPassword = (EditText) changePassDialog.findViewById(R.id.confirmPassword);
        dialogProgress = (ProgressBar) changePassDialog.findViewById(R.id.dialogProgress);
        dialogOk = (TextView) changePassDialog.findViewById(R.id.ok);
        changePassDialog.show();

        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldPassword.getText().toString().equals("") || newPassword.getText().toString().equals("") ||
                        confirmPassword.getText().toString().equals(""))
                    G.getInstance().toast_custom(getContext(), getResources().getString(R.string.EmptyFieldError), Toast.LENGTH_LONG);
                else if (!newPassword.getText().toString().equals(confirmPassword.getText().toString()))
                    confirmPassword.setError(getResources().getString(R.string.errorRepeatePass));
                else if (newPassword.getText().toString().length() < 6) {
                    newPassword.setError(getResources().getString(R.string.passwordError));

                } else {
                    dialogProgress.setVisibility(View.VISIBLE);
                    dialogOk.setEnabled(false);
                    changePassDialog.setCancelable(false);
                    presenter.changePass(oldPassword.getText().toString(), newPassword.getText().toString());
                }
            }
        });
    }

    @Override
    public void changePassSuccess() {
        if (!fragmentDestroyed) {
            dialogProgress.setVisibility(View.GONE);
            dialogOk.setEnabled(true);
            changePassDialog.setCancelable(true);
            changePassDialog.cancel();
            G.getInstance().customSnackBar(getActivity(), binding.mainLayout, getString(R.string.successChangePass));

        }
    }

    @Override
    public void changePassFailed(String error) {
        if (!fragmentDestroyed) {
            dialogProgress.setVisibility(View.GONE);
            dialogOk.setEnabled(true);
            changePassDialog.setCancelable(true);
            G.getInstance().customSnackBar(getActivity(), changePassDialog.getWindow().getDecorView(), error);

        }
    }

    @Override
    public void getProfileSuccess(ProfileAPIModel model) {
        if (!fragmentDestroyed) {
            this.model = model;
            G.getInstance().hideMainProgress(getActivity());
            binding.phoneNumber.setText(model.getUserName());
            binding.score.setText("امتیاز شما : " + model.getScore());
            binding.subscribe.setText("اشتراک شما : " + model.getSubscribeDay());
            binding.education.setText(G.educationList[model.getDegreeOfEducation() == 0 ? 0 : model.getDegreeOfEducation() - 1]);
            binding.ageEdt.setText(model.getAge() + "");
            binding.changeAge.setVisibility(View.GONE);


        }
    }

    @Override
    public void getProfileFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().errorMainProgress(getActivity());
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }

    @Override
    public void getVerifyCodeSuccess() {
        if (!fragmentDestroyed) {
            binding.ageProgress.setVisibility(View.GONE);
            changeAgeDialog();
        }
    }

    @Override
    public void getVerifyCodeFailed(String error) {
        if (!fragmentDestroyed) {
            binding.ageProgress.setVisibility(View.GONE);
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }

    @Override
    public void doChangeAgeSuccess() {
        if (!fragmentDestroyed) {
            changeAge.cancel();
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, getString(R.string.successMessage));

        }
    }

    @Override
    public void doChangeAgeFailed(String error) {
        if (!fragmentDestroyed) {
            dialogProgress.setVisibility(View.GONE);
            dialogOk.setText("تایید");
            dialogOk.setEnabled(true);
            changeAge.setCancelable(true);
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
        }
    }

    private void changeAgeDialog() {
        changeAge = new Dialog(getActivity());
        changeAge.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changeAge.setContentView(R.layout.dialog_change_age);
        changeAge.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText codeEdt = (EditText) changeAge.findViewById(R.id.code);
        dialogProgress = (ProgressBar) changeAge.findViewById(R.id.dialogProgress);
        dialogOk = changeAge.findViewById(R.id.ok);
        changeAge.show();
        changeAge.setCancelable(true);

        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (G.getInstance().isNetworkAvailable(getActivity())) {
                    if (codeEdt.getText().toString().equals(""))
                        codeEdt.setError(getString(R.string.EmptyFieldError));
                    else {
                        dialogProgress.setVisibility(View.VISIBLE);
                        dialogOk.setText("");
                        dialogOk.setEnabled(false);
                        changeAge.setCancelable(false);
                        int code = Integer.parseInt(G.getInstance().replaceFarsiNumber(codeEdt.getText().toString()));
                        int age = Integer.parseInt(G.getInstance().replaceFarsiNumber(binding.ageEdt.getText().toString()));
                        Map<String, Integer> params = new HashMap<>();
                        params.put("Age", age);
                        params.put("ConfirmCode", code);
                        presenter.doChangeAge(params);
                    }

                } else {
                    G.getInstance().customSnackBar(getContext(), binding.mainLayout, getString(R.string.netWorkError));
                }


            }
        });
    }
}