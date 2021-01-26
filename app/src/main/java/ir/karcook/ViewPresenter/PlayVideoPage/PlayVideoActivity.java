package ir.karcook.ViewPresenter.PlayVideoPage;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ir.karcook.Models.CourseModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.AnswerVideo_useCase;
import ir.karcook.databinding.PlayVideoActivityBinding;

public class PlayVideoActivity extends AppCompatActivity implements PlayVideoContract.view {

    PlayVideoActivityBinding binding;
    Dialog questionDialog;
    private ProgressBar dialogProgress;
    PlayVideoPresenter presenter;
    CourseModel model;
    Timer t;
    boolean activityDestroyed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        binding = DataBindingUtil.setContentView(this, R.layout.play_video_activity);
        presenter = new PlayVideoPresenter(this,new AnswerVideo_useCase());
        activityDestroyed = false;


        model = (CourseModel) getIntent().getExtras().getSerializable("model");
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        G.getInstance().hideMainProgress(PlayVideoActivity.this);
                    }
                }, 1500);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                G.getInstance().errorMainProgress(PlayVideoActivity.this);
            }
        });

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);

        binding.webView.getSettings().setSupportZoom(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setDisplayZoomControls(false);

        binding.webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        binding.webView.setScrollbarFadingEnabled(false);
        binding.webView.loadUrl(model.getFileUrl());


        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (G.getInstance().isNetworkAvailable(getApplicationContext())) {
                    G.getInstance().showMainProgress(PlayVideoActivity.this);
                    binding.webView.reload();
                } else {
                    G.getInstance().customSnackBar(getApplicationContext(), binding.mainLayout, getString(R.string.netWorkError));

                }
            }
        });

        binding.questionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t != null)
                    t.cancel();
                questionDialog();
            }
        });

        timer((model.getDuration()*60*1000)-10000);
/*
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = "<style> .r1_iframe_embed { position: relative; overflow: hidden; width: 100%; height: auto; padding-top: 56.25%; } .r1_iframe_embed iframe { position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: 0; } </style> <div class=r1_iframe_embed> <iframe src=" +
                url +
                " frameborder=0 allow=accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture allowFullScreen=true webkitallowfullscreen=true mozallowfullscreen=true></iframe> </div>";

        binding.webView.loadDataWithBaseURL("", html, mimeType, encoding, "");
*/

    }

    private void questionDialog() {
        questionDialog = new Dialog(PlayVideoActivity.this);
        questionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        questionDialog.setContentView(R.layout.dialog_question);
        questionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView question = questionDialog.findViewById(R.id.title);
        final RecyclerView list = questionDialog.findViewById(R.id.list);
        dialogProgress = (ProgressBar) questionDialog.findViewById(R.id.dialogProgress);
        questionDialog.show();
        questionDialog.setCancelable(true);

        list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        presenter.setModel(model);
        presenter.setAdapter();
        list.setAdapter(presenter.getAdapter());

        question.setText(model.getQuestionsModel().getTitle()+"");

    }

    @Override
    public void itemClicked(int adapterPosition) {

        dialogProgress.setVisibility(View.VISIBLE);
        presenter.answer(model.getQuestionsModel().getAnswerModel().get(adapterPosition).getId(),
                model.getQuestionsModel().getId());
    }

    @Override
    public void answerTrue() {
        if (!activityDestroyed){
            dialogProgress.setVisibility(View.GONE);
            questionDialog.cancel();
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, "تبریک پاسخ شما درست بود");
        }
    }

    @Override
    public void answerFalse() {
        if (!activityDestroyed){
            dialogProgress.setVisibility(View.GONE);
            questionDialog.cancel();
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, "متاسفانه پاسخ شما نادرست بود");

        }
    }

    @Override
    public void answerFailed(String error) {
        if (!activityDestroyed){
            dialogProgress.setVisibility(View.GONE);
            questionDialog.cancel();
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityDestroyed = true;
        if (t != null)
            t.cancel();
    }

    @Override
    public Context getContext() {
        return this;
    }


    private void timer(int miliSecond) {
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        questionDialog();
                    }
                });
            }
        }, miliSecond, 400000000);
    }
}
