package ir.karcook.ViewPresenter.CategoryPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

import ir.karcook.MainActivity;
import ir.karcook.Models.CategoryApiModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.GetCategoryList_useCase;
import ir.karcook.ViewPresenter.HomePage.HomeFragment;
import ir.karcook.ViewPresenter.SecondActivity.SecondActivity;
import ir.karcook.ViewPresenter.SubCategoryPage.SubCategoryFragment;
import ir.karcook.ViewPresenter.SubscribePage.SubscribeFragment;
import ir.karcook.databinding.CategoryFragmentBinding;

public class CategoryFragment extends Fragment implements CategoryContract.view {

    CategoryFragmentBinding binding;
    CategoryPresenter presenter;
    boolean fragmentDestroyed = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.category_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDestroyed = false;
        presenter = new CategoryPresenter(this, new GetCategoryList_useCase());

        // Choose your own preferred column width
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3); // MAX NUMBER OF SPACES
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ((position % 5) == 0)
                    return 3;
                else if ((position % 2) == 0) {
                    return 2; // ITEMS AT POSITION 1 AND 6 OCCUPY 2 SPACES
                } else {
                    return 1; // OTHER ITEMS OCCUPY ONLY A SINGLE SPACE
                }
            }
        });

        binding.listView.setLayoutManager(manager);

        G.getInstance().showMainProgress(getActivity());
        presenter.getCategoryList();

        binding.include.subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SecondActivity.class);
                i.putExtra("fragment", new SubscribeFragment());
                startActivity(i);
            }
        });

        binding.include.toolbarAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.getInstance().alphaAnimation(binding.include.toolbarAbout);
                ((MainActivity) getActivity()).aboutDialog();
            }
        });

        (getActivity().findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.getInstance().refreshClickedMainProgress(getActivity());
                presenter.getCategoryList();
            }
        });

    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        super.onDetach();
    }

    @Override
    public void getCategoryListSuccess(CategoryApiModel model) {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            binding.listView.setAdapter(presenter.getAdapter());
        }
    }

    @Override
    public void getCategoryListFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
            G.getInstance().errorMainProgress(getActivity());

        }
    }

    @Override
    public void itemClicked(int adapterPosition) {
        SubCategoryFragment fragment = new SubCategoryFragment(presenter.getModel().getData().get(adapterPosition).getId(),
                presenter.getModel().getData().get(adapterPosition).getTitle());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("wer").commit();

    }
}