package ir.karcook.ViewPresenter.ContentListPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchSendModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.GetContent_useCase;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.UseCase.GetCourseListById_useCase;
import ir.karcook.ViewPresenter.CourseDetailPage.CourseDetailActivity;
import ir.karcook.databinding.ContentListPageBinding;

public class ContentListPageFragment extends Fragment implements ContentListPageContract.view, Serializable {

    ContentListPageBinding binding;
    ContentListPagePresenter presenter;
    boolean fragmentDestroyed = false;
    G.ContentPage page;
    int coursePackageId = 0;
    String toolbarTitle;

    public ContentListPageFragment(G.ContentPage page, int coursePackageId , String toolbarTitle) {
        this.page = page;
        this.coursePackageId = coursePackageId;
        this.toolbarTitle = toolbarTitle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.content_list_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDestroyed = false;
        presenter = new ContentListPagePresenter(this,
                new GetContent_useCase(),
                new GetCourseDetail_useCase(),
                new GetCourseListById_useCase());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);


        G.getInstance().showMainProgress(getActivity());
        final SearchSendModel searchSendModel = new SearchSendModel();
        searchSendModel.setSearchString("");
        searchSendModel.setLength(150);
        searchSendModel.setStart(0);

        (getActivity().findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.getInstance().refreshClickedMainProgress(getActivity());
                if (page == G.ContentPage.newList)
                    presenter.getNewList(searchSendModel);
                else if (page == G.ContentPage.byId)
                    presenter.getCourseListById(coursePackageId);
                //  else //todo add new web Api for free list
                //  presenter.getNewList(searchSendModel);
            }
        });

        if (page == G.ContentPage.newList)
            presenter.getNewList(searchSendModel);
        else if (page == G.ContentPage.freeList)
            presenter.getNewList(searchSendModel);
        else if (page == G.ContentPage.byId)
            presenter.getCourseListById(coursePackageId);

        binding.toolbarBack.setOnClickListener(new View.OnClickListener() {
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
    public void getNewListSuccess(SearchAPIModel model) {
        if (!fragmentDestroyed) {
            binding.emptyText.setVisibility(View.GONE);
            G.getInstance().hideMainProgress(getActivity());
            binding.list.setAdapter(presenter.getContentListAdapter());

            setToolBarTitle(toolbarTitle);
        }
    }

    void setToolBarTitle(String text){
        binding.toolbarTitle.setText(text);
    }

    @Override
    public void getNewListFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
            G.getInstance().errorMainProgress(getActivity());

        }
    }

    @Override
    public void emptyList() {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
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

    @Override
    public void getCourseListByIdSuccess(SearchAPIModel model) {
        if (!fragmentDestroyed) {
            binding.emptyText.setVisibility(View.GONE);
            G.getInstance().hideMainProgress(getActivity());
            binding.list.setAdapter(presenter.getContentListAdapter());

            setToolBarTitle(toolbarTitle);

        }
    }

    @Override
    public void getCourseListByIdFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }
}