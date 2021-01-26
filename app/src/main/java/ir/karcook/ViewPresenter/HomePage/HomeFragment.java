package ir.karcook.ViewPresenter.HomePage;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.auth0.android.jwt.JWT;

import java.util.HashMap;

import ir.karcook.MainActivity;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.HomeDataAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.CognitoJWTParser;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.UseCase.GetHomeData_useCase;
import ir.karcook.ViewPresenter.ContentListPage.ContentListPageFragment;
import ir.karcook.ViewPresenter.CourseDetailPage.CourseDetailActivity;
import ir.karcook.ViewPresenter.SecondActivity.SecondActivity;
import ir.karcook.ViewPresenter.SubscribePage.SubscribeFragment;
import ir.karcook.ViewPresenter.WebViewActivity;
import ir.karcook.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment implements HomeContract.view {

    HomeFragmentBinding binding;
    HomePresenter presenter;
    boolean fragmentDestroyed = false;

    boolean isPush = false;
    int course;
    int coursePackage;

    public HomeFragment(boolean isPush, int course, int coursePackage) {
        this.isPush = isPush;
        this.course = course;
        this.coursePackage = coursePackage;
    }

    public HomeFragment(boolean isPush) {
        this.isPush = isPush;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDestroyed = false;
        presenter = new HomePresenter(this, new GetHomeData_useCase(), new GetCourseDetail_useCase());
        binding.newList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        binding.freeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        G.getInstance().showMainProgress(getActivity());
        presenter.getHomeData();

        (getActivity().findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.getInstance().refreshClickedMainProgress(getActivity());
                presenter.getHomeData();
            }
        });

        binding.newContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SecondActivity.class);
                i.putExtra("fragment", new ContentListPageFragment(G.ContentPage.newList, 0, "تازه ها"));
                startActivity(i);
            }
        });

        binding.freeContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SecondActivity.class);
                i.putExtra("fragment", new ContentListPageFragment(G.ContentPage.freeList, 0, "تازه های رایگان"));
                startActivity(i);
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


        binding.homeUnderSliderL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBanner(presenter.getModel(), 1);
            }
        });

        binding.homeUnderSliderL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBanner(presenter.getModel(), 2);
            }
        });

        binding.homeOverRightL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBanner(presenter.getModel(), 3);
            }
        });

        binding.homeOverRightL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBanner(presenter.getModel(), 4);
            }
        });

        binding.homeBottomL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBanner(presenter.getModel(), 5);
            }
        });

        binding.homeBottomL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBanner(presenter.getModel(), 6);

            }
        });

    }

    void clickBanner(HomeDataAPIModel model, int bannerPosition) {
        for (int i = 0; i < model.getData().getAdsSlide().size(); i++) {
            if (model.getData().getAdsSlide().get(i).getPriority() == bannerPosition) {
                Intent ii = new Intent(getActivity(), WebViewActivity.class);
                ii.putExtra("url", model.getData().getAdsSlide().get(i).getLink());
                startActivity(ii);

            }
        }
    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        super.onDetach();
    }

    @Override
    public void getHomeDataSuccess(HomeDataAPIModel model) {
        if (!fragmentDestroyed) {
            binding.newList.setAdapter(presenter.getAdapter());
            binding.freeList.setAdapter(presenter.getFreeListAdapter());
            SliderAdapter sliderAdapter = new SliderAdapter(getContext(), model);
            binding.homeSlider.setSliderAdapter(sliderAdapter);

            for (int i = 0; i < model.getData().getAdsSlide().size(); i++) {
                switch (model.getData().getAdsSlide().get(i).getPriority()) {
                    case 1:
                        binding.homeUnderSliderL.setVisibility(View.VISIBLE);
                        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getAdsSlide().get(i).getPictureId())
                                .error(R.drawable.place_holder)
                                .placeholder(R.drawable.place_holder)
                                .fitCenter()
                                .into(binding.homeUnderSlider);
                        break;
                    case 2:
                        binding.homeUnderSliderL2.setVisibility(View.VISIBLE);
                        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getAdsSlide().get(i).getPictureId())
                                .error(R.drawable.place_holder)
                                .placeholder(R.drawable.place_holder)
                                .fitCenter()
                                .into(binding.homeUnderSlider2);
                        break;
                    case 3:
                        binding.homeOverRightL.setVisibility(View.VISIBLE);
                        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getAdsSlide().get(i).getPictureId())
                                .error(R.drawable.place_holder)
                                .placeholder(R.drawable.place_holder)
                                .fitCenter()
                                .into(binding.homeOverRight);
                        break;
                    case 4:
                        binding.homeOverRightL2.setVisibility(View.VISIBLE);
                        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getAdsSlide().get(i).getPictureId())
                                .error(R.drawable.place_holder)
                                .placeholder(R.drawable.place_holder)
                                .fitCenter()
                                .into(binding.homeOverRight2);
                        break;
                    case 5:
                        binding.homeBottomL.setVisibility(View.VISIBLE);
                        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getAdsSlide().get(i).getPictureId())
                                .error(R.drawable.place_holder)
                                .placeholder(R.drawable.place_holder)
                                .fitCenter()
                                .into(binding.homeBottomSlider);
                        break;
                    case 6:
                        binding.homeBottomL2.setVisibility(View.VISIBLE);
                        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getAdsSlide().get(i).getPictureId())
                                .error(R.drawable.place_holder)
                                .placeholder(R.drawable.place_holder)
                                .fitCenter()
                                .into(binding.homeBottomSlider2);
                        break;
                }
            }

            if (isPush) {
                G.getInstance().showMainProgress(getActivity());
                presenter.getCourseDetail(coursePackage,
                        course);
            } else
                G.getInstance().hideMainProgress(getActivity());


        }
    }

    @Override
    public void getHomeDataFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
            G.getInstance().errorMainProgress(getActivity());
        }
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
    public void lastItemClicked(int adapterPosition) {
        G.getInstance().showMainProgress(getActivity());
        presenter.getCourseDetail(presenter.getModel().getData().getLastCourse().get(adapterPosition).getCoursePackageId(),
                presenter.getModel().getData().getLastCourse().get(adapterPosition).getId());

    }

    @Override
    public void itemClicked_free(int adapterPosition) {
        G.getInstance().showMainProgress(getActivity());
        presenter.getCourseDetail(presenter.getModel().getData().getLastFreeCourses().get(adapterPosition).getCoursePackageId(),
                presenter.getModel().getData().getLastFreeCourses().get(adapterPosition).getId());

    }

}