package ir.karcook.ViewPresenter.SubscribePage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.Serializable;


import ir.karcook.Models.CreateFactorIdAPIModel;
import ir.karcook.Models.SubscribeAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.UseCase.CreateFactorId_useCase;
import ir.karcook.UseCase.GetSubscribeList_useCase;
import ir.karcook.databinding.SubscribePageBinding;

public class SubscribeFragment extends Fragment implements SubscribeContract.view, Serializable {

    SubscribePageBinding binding;
    SubscribePresenter presenter;
    boolean fragmentDestroyed = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.subscribe_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDestroyed = false;
        presenter = new SubscribePresenter(this, new GetSubscribeList_useCase(),
                new CreateFactorId_useCase());
        binding.list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        G.getInstance().showMainProgress(getActivity());
        presenter.getSubscribeList();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        super.onDetach();
    }

    @Override
    public void getSubscribeListSuccess(SubscribeAPIModel model) {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            binding.list.setAdapter(presenter.getAdapter());
        }
    }

    @Override
    public void getSubscribeListFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().errorMainProgress(getActivity());
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }

    @Override
    public void itemClicked(int adapterPosition) {
        if (Shared_Prefrences.getInstance(getContext()).getSp()
                .getBoolean(getString(R.string.logged), false)) {
            G.getInstance().showMainProgress(getActivity());
            presenter.createFactorId(presenter.getModel().getData().get(adapterPosition).getId());
        }else {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, getString(R.string.pleaseLogin));
        }

    }

    @Override
    public void createFactorIdSuccess(CreateFactorIdAPIModel model) {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://karcook.ir/Orders/Index/" + model.getData()));
            startActivity(i);
        }
    }

    @Override
    public void createFactorIdFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }
}