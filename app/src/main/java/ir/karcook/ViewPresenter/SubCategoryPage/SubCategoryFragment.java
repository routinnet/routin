package ir.karcook.ViewPresenter.SubCategoryPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import ir.karcook.Models.SubCategoryApiModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.GetSubCategory_useCase;
import ir.karcook.ViewPresenter.ContentListPage.ContentListPageFragment;
import ir.karcook.ViewPresenter.SecondActivity.SecondActivity;
import ir.karcook.ViewPresenter.VoiceListPage.VoiceListFragment;
import ir.karcook.databinding.SubCategoryPageBinding;

public class SubCategoryFragment extends Fragment implements SubCategoryContract.view {

    SubCategoryPageBinding binding;
    SubCategoryPresenter presenter;
    boolean fragmentDestroyed = false;
    int categoryId;
    String toolbarTitle;

    public SubCategoryFragment(int categoryId , String toolbarTitle) {
        this.categoryId = categoryId;
        this.toolbarTitle = toolbarTitle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.sub_category_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDestroyed = false;
        presenter = new SubCategoryPresenter(this, new GetSubCategory_useCase());
        binding.list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        G.getInstance().showMainProgress(getActivity());
        presenter.getSubCategory(categoryId);

        binding.toolbarTitle.setText(toolbarTitle);
        binding.toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        super.onDetach();
    }

    @Override
    public void getSubCategorySuccess(SubCategoryApiModel model) {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            binding.list.setAdapter(presenter.getAdapter());
        }
    }

    @Override
    public void getSubCategoryFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
            G.getInstance().errorMainProgress(getActivity());

        }
    }

    @Override
    public void goToVideoList(int adapterPosition) {
        Intent i = new Intent(getActivity(), SecondActivity.class);
        i.putExtra("fragment", new ContentListPageFragment(G.ContentPage.byId,
                presenter.getModel().getData().get(adapterPosition).getId(),
                presenter.getModel().getData().get(adapterPosition).getTitle()));
        startActivity(i);
    }

    @Override
    public void goToVoiceList(int adapterPosition) {
        Intent i = new Intent(getActivity(), SecondActivity.class);
        i.putExtra("fragment", new VoiceListFragment(presenter.getModel().getData().get(adapterPosition).getId(),
                presenter.getModel().getData().get(adapterPosition).getTitle()));
        startActivity(i);
    }
}