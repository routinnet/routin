package ir.karcook.ViewPresenter.CourseDetailPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.service.JcPlayerManagerListener;

import java.util.ArrayList;

import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.CourseModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.ViewPresenter.PlayVideoPage.PlayVideoActivity;
import ir.karcook.databinding.CorseDetailPageBinding;

public class CourseDetailActivity extends AppCompatActivity implements CourseDetailContract.view, JcPlayerManagerListener {

    CourseDetailPresenter presenter;
    CorseDetailPageBinding binding;
    boolean activityDestroyed = false;
    ArrayList<JcAudio> jcAudios;
    SeekBar seekBar;
    CourseModel model;
    boolean playerIsPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.corse_detail_page);
        activityDestroyed = false;
        presenter = new CourseDetailPresenter(this, new GetCourseDetail_useCase());
        binding.list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));


        model = (CourseModel) getIntent().getExtras().getSerializable("model");

        presenter.setModel(model);
        presenter.setAdapter();
        binding.list.setAdapter(presenter.getAdapter());

        binding.category.setText(model.getCoursePackageTitle() + " > " + model.getTitle());
        binding.title.setText(model.getTitle());
        binding.duration.setText(model.getDuration() + " دقیقه");
        binding.size.setText(model.getFileSize() + " مگابایت");
        binding.isFree.setText(model.getIsFree() == false ? "قفل" : "رایگان");
        binding.description.setText(model.getDescription() + "");

        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getDocumentId())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .fitCenter()
                .into(binding.pic);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (model.getFileType() == 2) {
            binding.playTopBtn.setVisibility(View.GONE);
            binding.jcplayer.setVisibility(View.VISIBLE);

            jcAudios = new ArrayList<>();
            jcAudios.add(JcAudio.createFromURL(model.getTitle(), model.getFileUrl()));
            try {
                binding.jcplayer.initPlaylist(jcAudios, this);

            } catch (Exception e) {
            }

            seekBar = (SeekBar) binding.jcplayer.findViewById(R.id.seekBar);
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
                    binding.jcplayer.pause();
                }
            });
            seekBar.setPadding(5, 20, 5, 20);
            TextView txtCurrentMusic = (TextView) binding.jcplayer.findViewById(R.id.txtCurrentMusic);
            txtCurrentMusic.setTextSize(0);

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


            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.isHasCredit()) {
                        binding.jcplayer.playAudio(jcAudios.get(0));
                        playerIsPlaying = true;
                    } else
                        G.getInstance().customSnackBar(getContext(), binding.mainLayout, "شما هنوز اشتراکی خریداری نکرده اید");

                }
            });

        } else {

            binding.playLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.isHasCredit()) {
                        Intent i = new Intent(getApplicationContext(), PlayVideoActivity.class);
                        i.putExtra("model", model);
                        startActivity(i);
                    } else
                        G.getInstance().customSnackBar(getContext(), binding.mainLayout, "شما هنوز اشتراکی خریداری نکرده اید");

                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        activityDestroyed = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityDestroyed = true;
        if (playerIsPlaying)
            binding.jcplayer.pause();

        try {
            binding.jcplayer.kill();
        } catch (Exception e) {
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void itemClicked(int adapterPosition) {
        G.getInstance().showMainProgress(this);
        presenter.getCourseDetail(presenter.getModel().getRelatedCourses().get(adapterPosition).getCoursePackageId(),
                presenter.getModel().getRelatedCourses().get(adapterPosition).getId());

    }

    @Override
    public void getCourseDetailSuccess(CourseDetailAPIModel model) {
        if (!activityDestroyed) {
            G.getInstance().hideMainProgress(this);
            Intent i = new Intent(this, CourseDetailActivity.class);
            i.putExtra("model", model.getData());
            startActivity(i);
        }
    }

    @Override
    public void getCourseDetailFailed(String error) {
        if (!activityDestroyed) {
            G.getInstance().hideMainProgress(this);
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }

    @Override
    public void onCompletedAudio() {
        binding.jcplayer.pause();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(0);
                binding.jcplayer.continueAudio();
            }
        }, 1000);
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
}