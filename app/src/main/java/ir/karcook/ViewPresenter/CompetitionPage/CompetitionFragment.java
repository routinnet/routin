package ir.karcook.ViewPresenter.CompetitionPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.karcook.MainActivity;
import ir.karcook.Models.CompetitionListAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.UseCase.GetCompetitionList_useCase;
import ir.karcook.ViewPresenter.QuestationPage.QuestionActivity;
import ir.karcook.ViewPresenter.SecondActivity.SecondActivity;
import ir.karcook.ViewPresenter.SubscribePage.SubscribeFragment;
import ir.karcook.databinding.CompetitionListPageBinding;

public class CompetitionFragment extends Fragment implements CompetitionContract.view {

    CompetitionListPageBinding binding;
    CompetitionPresenter presenter;
    boolean fragmentDestroyed = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.competition_list_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDestroyed = false;
        presenter = new CompetitionPresenter(this, new GetCompetitionList_useCase());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);


        G.getInstance().showMainProgress(getActivity());
        presenter.getCompetitionList();

        (getActivity().findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.getInstance().refreshClickedMainProgress(getActivity());
                presenter.getCompetitionList();
            }
        });

        binding.sharedToolbar.subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SecondActivity.class);
                i.putExtra("fragment", new SubscribeFragment());
                startActivity(i);
            }
        });

        binding.sharedToolbar.toolbarAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.getInstance().alphaAnimation(binding.sharedToolbar.toolbarAbout);
                ((MainActivity) getActivity()).aboutDialog();
            }
        });

    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        super.onDetach();
    }

    @Override
    public void getCompetitionListSuccess(CompetitionListAPIModel model) {
        if (!fragmentDestroyed) {
            binding.emptyText.setVisibility(View.GONE);
            G.getInstance().hideMainProgress(getActivity());
            binding.list.setAdapter(presenter.getAdapter());
        }
    }

    @Override
    public void getCompetitionListFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
            G.getInstance().errorMainProgress(getActivity());

        }
    }

    @Override
    public void itemClicked(int adapterPosition) {
        if (Shared_Prefrences.getInstance(getContext()).getSp()
                .getBoolean(getString(R.string.logged), false)) {
            Intent i = new Intent(getActivity(), QuestionActivity.class);
            i.putExtra("id", presenter.getModel().getData().getCompetition().get(adapterPosition).getId());
            startActivity(i);
        }else {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, getString(R.string.pleaseLogin));

        }
    }

    @Override
    public void emptyList() {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            binding.emptyText.setVisibility(View.VISIBLE);

        }
    }
}