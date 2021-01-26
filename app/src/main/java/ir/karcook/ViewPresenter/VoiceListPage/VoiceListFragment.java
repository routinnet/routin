package ir.karcook.ViewPresenter.VoiceListPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.service.JcPlayerManagerListener;

import java.io.Serializable;
import java.util.ArrayList;

import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.CourseListApiModel;
import ir.karcook.Models.SearchSendModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.DoSearch_courseId_useCase;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.UseCase.GetCourseListById_useCase;
import ir.karcook.UseCase.GetSubCategory_useCase;
import ir.karcook.ViewPresenter.CourseDetailPage.CourseDetailActivity;
import ir.karcook.ViewPresenter.SubCategoryPage.SubCategoryPresenter;
import ir.karcook.databinding.VoiceListPageBinding;

public class VoiceListFragment extends Fragment implements VoiceListContract.view, Serializable, JcPlayerManagerListener {

    VoiceListPresenter presenter;
    VoiceListPageBinding binding;
    boolean fragmentDestroyed = false;
    int coursePackageId = 0;
    String toolbarTitle;
    ArrayList<JcAudio> jcAudios;
    boolean pausedFromSeekBar = false;
    boolean playerIsPlaying = false;
    InputMethodManager imm;

    public VoiceListFragment(int coursePackageId, String toolbarTitle) {
        this.coursePackageId = coursePackageId;
        this.toolbarTitle = toolbarTitle;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.voice_list_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDestroyed = false;

        try {
            imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Exception e) {

        }

        presenter = new VoiceListPresenter(this,
                new GetCourseListById_useCase(),
                new GetCourseDetail_useCase(),
                new DoSearch_courseId_useCase());
        binding.list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        G.getInstance().showMainProgress(getActivity());
        presenter.getCourseListById(coursePackageId);

        binding.toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        binding.searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (playerIsPlaying)
                        gonePlayer();
                    G.getInstance().showMainProgress(getActivity());
                    presenter.doSearch(coursePackageId, binding.searchEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onDetach() {
        fragmentDestroyed = true;
        if (playerIsPlaying())
            binding.jcplayer.pause();
        try {
            binding.jcplayer.kill();
        } catch (Exception e) {
        }
        super.onDetach();
    }

    void setToolBarTitle(String text) {
        binding.toolbarTitle.setText(text);
    }


    @Override
    public void getCourseListByIdSuccess(CourseListApiModel model) {
        if (!fragmentDestroyed) {
            imm.hideSoftInputFromWindow(binding.searchEditText.getWindowToken(), 0);
            binding.emptyText.setVisibility(View.GONE);
            G.getInstance().hideMainProgress(getActivity());
            binding.list.setAdapter(presenter.getAdapter());

            setToolBarTitle(toolbarTitle);

        }
    }

    @Override
    public void getCourseListByIdFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);
            G.getInstance().errorMainProgress(getActivity());

        }
    }

    @Override
    public void itemClicked(int adapterPosition) {
        if (playerIsPlaying())
            binding.jcplayer.pause();
        G.getInstance().showMainProgress(getActivity());
        presenter.getCourseDetail(presenter.getModel().getData().get(adapterPosition).getCoursePackageId(),
                presenter.getModel().getData().get(adapterPosition).getId());

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
    public void playBtnClicked(int adapterPosition) {
        if (presenter.getModel().getData().get(adapterPosition).isHasCredit()) {
            binding.jcplayer.setVisibility(View.VISIBLE);
            G.getInstance().animateUp(binding.jcplayer, 500, 300);
            jcAudios = new ArrayList<>();
            jcAudios.add(JcAudio.createFromURL(presenter.getModel().getData().get(adapterPosition).getTitle()
                    , presenter.getModel().getData().get(adapterPosition).getFileUrl()));
            binding.jcplayer.initPlaylist(jcAudios, this);
            SeekBar seekBar = (SeekBar) binding.jcplayer.findViewById(R.id.seekBar);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    binding.jcplayer.onProgressChanged(seekBar, progress, fromUser);

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    pausedFromSeekBar = true;
                    binding.jcplayer.pause();
                    pausedFromSeekBar = false;
                }
            });
            seekBar.setPadding(5, 20, 5, 20);
            TextView txtCurrentMusic = (TextView) binding.jcplayer.findViewById(R.id.txtCurrentMusic);


            ImageButton nextBtn = (ImageButton) binding.jcplayer.findViewById(R.id.btnNext);
            ImageButton prevBtn = (ImageButton) binding.jcplayer.findViewById(R.id.btnPrev);
            ImageButton btnRandom = (ImageButton) binding.jcplayer.findViewById(R.id.btnRandom);
            ImageButton btnRepeat = (ImageButton) binding.jcplayer.findViewById(R.id.btnRepeat);
            ImageButton btnPlay = (ImageButton) binding.jcplayer.findViewById(R.id.btnPlay);
            ImageButton btnPause = (ImageButton) binding.jcplayer.findViewById(R.id.btnPause);

            nextBtn.setVisibility(View.GONE);
            prevBtn.setVisibility(View.GONE);
            btnRandom.setVisibility(View.GONE);
            btnRepeat.setVisibility(View.GONE);

            btnPause.setPadding(5, 25, 5, 5);
            btnPlay.setPadding(5, 5, 5, 5);

            playerIsPlaying = true;
            binding.jcplayer.playAudio(jcAudios.get(0));
        } else
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, "شما هنوز اشتراکی خریداری نکرده اید");


    }

    @Override
    public void doSearchFailed(String error) {
        G.getInstance().hideMainProgress(getActivity());
        G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

    }

    @Override
    public void emptyList() {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(getActivity());
            binding.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCompletedAudio() {

    }

    @Override
    public void onContinueAudio(JcStatus jcStatus) {
        playerIsPlaying = true;
    }

    @Override
    public void onJcpError(Throwable throwable) {

    }

    @Override
    public void onPaused(JcStatus jcStatus) {
        playerIsPlaying = false;
        if (!pausedFromSeekBar) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.jcplayer.setVisibility(View.GONE);
                }
            }, 400);
            G.getInstance().animateDown(binding.jcplayer, 500, 300);

        }
    }

    @Override
    public void onPlaying(JcStatus jcStatus) {

    }

    @Override
    public void onPreparedAudio(JcStatus jcStatus) {

    }

    @Override
    public void onTimeChanged(JcStatus jcStatus) {

    }

    public boolean playerIsPlaying() {
        return playerIsPlaying;
    }

    public void setPlayerIsPlaying(boolean playerIsPlaying) {
        this.playerIsPlaying = playerIsPlaying;
    }

    public void gonePlayer() {
        playerIsPlaying = false;
        binding.jcplayer.pause();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.jcplayer.setVisibility(View.GONE);
            }
        }, 400);
        G.getInstance().animateDown(binding.jcplayer, 500, 300);
    }
}