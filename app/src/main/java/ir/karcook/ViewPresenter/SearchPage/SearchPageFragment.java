package ir.karcook.ViewPresenter.SearchPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchSendModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.DoSearch_useCase;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.ViewPresenter.CourseDetailPage.CourseDetailActivity;
import ir.karcook.databinding.SearchPageBinding;

public class SearchPageFragment extends Fragment implements SearchPageContract.view {

    SearchPageBinding binding;
    SearchPagePresenter presenter;
    boolean fragmentDestroyed = false;
    SearchSendModel searchSendModel;
    InputMethodManager imm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDestroyed = false;
        presenter = new SearchPagePresenter(this, new DoSearch_useCase(),new GetCourseDetail_useCase());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        try {
            imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Exception e) {

        }

        G.getInstance().showMainProgress(getActivity());
        searchSendModel = new SearchSendModel();
        searchSendModel.setSearchString("");
        searchSendModel.setLength(150);
        searchSendModel.setStart(0);
        presenter.doSearch(searchSendModel);


        binding.searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchSendModel = new SearchSendModel();
                    searchSendModel.setSearchString(binding.searchEditText.getText().toString());
                    searchSendModel.setLength(153);
                    searchSendModel.setStart(0);
                    binding.searchProgress.setVisibility(View.VISIBLE);
                    binding.searchIcon.setVisibility(View.GONE);
                    presenter.doSearch(searchSendModel);
                    return true;
                }
                return false;
            }
        });

        (getActivity().findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.getInstance().refreshClickedMainProgress(getActivity());
                searchSendModel = new SearchSendModel();
                searchSendModel.setSearchString("");
                searchSendModel.setLength(150);
                searchSendModel.setStart(0);
                presenter.doSearch(searchSendModel);
            }
        });

        (getActivity().findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.getInstance().refreshClickedMainProgress(getActivity());
                presenter.doSearch(searchSendModel);
            }
        });

    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        super.onDetach();
    }

    @Override
    public void doSearchSuccess(SearchAPIModel model) {
        if (!fragmentDestroyed) {
            binding.emptyText.setVisibility(View.GONE);
            if (searchSendModel.getLength() == 150) {
                G.getInstance().hideMainProgress(getActivity());
                binding.list.setAdapter(presenter.getSearchPageListAdapter());
            } else {
                imm.hideSoftInputFromWindow(binding.searchEditText.getWindowToken(), 0);
                binding.emptyText.setVisibility(View.GONE);
                binding.searchProgress.setVisibility(View.GONE);
                binding.list.setAdapter(presenter.getSearchPageListAdapter());
                binding.searchIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void doSearchFailed(String error) {
        if (!fragmentDestroyed) {
            if (searchSendModel.getLength() == 150) {
                G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
                G.getInstance().errorMainProgress(getActivity());
            } else {
                G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
                binding.searchProgress.setVisibility(View.GONE);
                binding.searchIcon.setVisibility(View.VISIBLE);

            }

        }
    }

    @Override
    public void emptyList() {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            binding.searchProgress.setVisibility(View.GONE);
            binding.emptyText.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void itemClicked(int adapterPosition) {
        G.getInstance().showMainProgress(getActivity());
        presenter.getCourseDetail(presenter.getModel().getData().getCourse().get(adapterPosition).getCoursePackageId(),
                presenter.getModel().getData().getCourse().get(adapterPosition).getId());

    }

    @Override
    public void getCourseDetailSuccess(CourseDetailAPIModel model) {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            Intent i = new Intent(getActivity(), CourseDetailActivity.class);
            i.putExtra("model", model.getData());
            startActivity(i);
        }
    }

    @Override
    public void getCourseDetailFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }
}