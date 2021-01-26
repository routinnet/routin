package ir.karcook.ViewPresenter.QuestationPage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Timer;
import java.util.TimerTask;

import ir.karcook.Models.CompetitionQuestionsAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.AnswerQuestionCompetition_useCase;
import ir.karcook.UseCase.GetCompetitionQuestions_useCase;
import ir.karcook.UseCase.SaveUserCompetition_useCase;
import ir.karcook.databinding.QuestationPageBinding;

public class QuestionActivity extends AppCompatActivity implements QuestionContract.view {

    QuestationPageBinding binding;
    QuestionPresenter presenter;
    boolean fragmentDestroyed = false;
    int competitionId;
    private int h = 0, m = 0, s = 59;
    Timer t;

    int true_count = 0;
    int false_count = 0;
    int score = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.questation_page);

        fragmentDestroyed = false;
        presenter = new QuestionPresenter(this, new GetCompetitionQuestions_useCase(),
                new AnswerQuestionCompetition_useCase(), new SaveUserCompetition_useCase());
        binding.list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        competitionId = getIntent().getExtras().getInt("id");

        binding.progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        G.getInstance().showMainProgress(this);
        presenter.getQuestions(competitionId);

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progress.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.progress.setVisibility(View.GONE);
                        nextQuestionOrEndCompetition();
                    }
                }, 1000);
            }
        });

        binding.exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_exit();
            }
        });

    }

    private void dialog_exit() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.exit_competition_dialog);
        TextView txt = (TextView) dialog.findViewById(R.id.txt);
        TextView yes = (TextView) dialog.findViewById(R.id.yes);
        TextView no = (TextView) dialog.findViewById(R.id.no);
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                finish();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    @Override
    protected void onDestroy() {
        fragmentDestroyed = true;
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getQuestionsSuccess(CompetitionQuestionsAPIModel model) {
        if (!fragmentDestroyed) {
            G.getInstance().hideMainProgress(this);
            setQuestion(model);

        }
    }

    @Override
    public void getQuestionsFailed(String error) {
        if (!fragmentDestroyed) {
            G.getInstance().errorMainProgress(this);
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }

    private void setQuestion(CompetitionQuestionsAPIModel model) {
        presenter.setPositionOfQuestion(presenter.getPositionOfQuestion() + 1);
        presenter.setAdapter(presenter.getPositionOfQuestion());
        binding.list.setAdapter(presenter.getAdapter());
        binding.questionScore.setText("سوال " + G.getInstance().replaceFarsiNumber(model.getData()
                .getQuestionsModel().get(presenter.getPositionOfQuestion()).getScore() + "") + " امتیازی");
        binding.questionTitle.setText(model.getData()
                .getQuestionsModel().get(presenter.getPositionOfQuestion()).getTitle());
        if (presenter.getPositionOfQuestion() == presenter.getModel().getData().getQuestionsModel().size() - 1)
            binding.nextBtn.setText("پایان");
        if (model.getData()
                .getQuestionsModel().get(presenter.getPositionOfQuestion()).getDocumentId().equals("")) {
            binding.questionPic.setVisibility(View.GONE);
        } else {
            binding.questionPic.setVisibility(View.VISIBLE);


            GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData()
                    .getQuestionsModel().get(presenter.getPositionOfQuestion()).getDocumentId())
                    .error(R.drawable.place_holder)
                    .placeholder(R.drawable.place_holder)
                    .fitCenter()
                    .into(binding.questionPic);
        }
        s = model.getData().getQuestionsModel().get(presenter.getPositionOfQuestion()).getTimeToAnswer();
        if (t != null)
            t.cancel();
        timer(null, null, binding.timer);

    }

    @Override
    public void itemClicked(int adapterPosition) {
        binding.progress.setVisibility(View.VISIBLE);
        presenter.answerQuestion(presenter.getModel().getData()
                        .getQuestionsModel().get(presenter.positionOfQuestion).getAnswerModel().get(adapterPosition).getId(),
                presenter.model.getData().getCompetition().getId());
    }

    @Override
    public void answerQuestionTrue() {
        if (!fragmentDestroyed) {
            true_count++;
            score += presenter.getModel().getData().getQuestionsModel().get(presenter.getPositionOfQuestion()).getScore();
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, "تبریک پاسخ شما درست بود");
            binding.progress.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestionOrEndCompetition();
                }
            }, 600);
        }
    }

    @Override
    public void answerQuestionFalse() {
        if (!fragmentDestroyed) {
            false_count++;
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, "متاسفانه پاسخ شما نادرست بود");
            binding.progress.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestionOrEndCompetition();
                }
            }, 600);

        }
    }

    @Override
    public void answerQuestionFailed(String error) {
        if (!fragmentDestroyed) {
            binding.progress.setVisibility(View.GONE);
            G.getInstance().customSnackBar(getContext(), binding.mainLayout, error);

        }
    }

    @Override
    public void saveCompetitionSuccess() {
        if (!fragmentDestroyed) {
            binding.progress.setVisibility(View.GONE);
            dialog_competitionEnd();
        }
    }

    @Override
    public void saveCompetitionFailed(String error) {
        if (!fragmentDestroyed) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presenter.saveCompetition(competitionId);
                }
            }, 3000);
        }
    }

    private void dialog_competitionEnd() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.competition_end_dialog);
        TextView trueCount = (TextView) dialog.findViewById(R.id.trueCount);
        TextView falseCount = (TextView) dialog.findViewById(R.id.falseCount);
        TextView dontAnswerCount = (TextView) dialog.findViewById(R.id.dontAnswerCount);
        TextView scoreText = (TextView) dialog.findViewById(R.id.score);
        TextView yes = (TextView) dialog.findViewById(R.id.ok);
        dialog.show();

        trueCount.setText(true_count + "");
        falseCount.setText(false_count + "");
        dontAnswerCount.setText((presenter.getModel().getData().getQuestionsModel().size() - (true_count + false_count)) + "");
        scoreText.setText(score+"");

        if (t != null)
            t.cancel();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                finish();

            }
        });

    }

    private void timer(@Nullable final TextView H, @Nullable final TextView M, final TextView S) {
        S.setTextColor(Color.parseColor("#ffffff"));
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getContext() != null)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (s > 0) {
                                if (s <= 10) {
                                    S.setTextColor(Color.parseColor("#ff0000"));
                                    S.setText("0" + String.valueOf(--s));
                                } else
                                    S.setText(String.valueOf(--s));
                            } else if (m > 0 || h > 0) {
                                m--;
                                if (m >= 0) {
                                    if (m <= 10) {
                                        if (M != null)
                                            M.setText("0" + String.valueOf(m));
                                    } else {
                                        if (M != null)
                                            M.setText(String.valueOf(m));
                                    }
                                } else if (h > 0) {
                                    h--;
                                    if (h == 0) {
                                        h = 0;
                                    }
                                    m = 60;
                                    if (M != null)
                                        M.setText(String.valueOf(--m));

                                } else {
                                    m = 0;
                                }
                                s = 60;
                                S.setText(String.valueOf(--s));
                            } else {
                                s = 0;
                                t.cancel();
                                binding.progress.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.progress.setVisibility(View.GONE);
                                        nextQuestionOrEndCompetition();
                                    }
                                }, 1000);
                            }

                            if (H != null)
                                H.setText(String.valueOf(h));
                            if (m <= 10) {
                                if (M != null)
                                    M.setText("0" + String.valueOf(m));
                            } else {
                                if (M != null)
                                    M.setText(String.valueOf(m));
                            }

                        }
                    });
            }
        }, 0, 1000);
    }

    private void nextQuestionOrEndCompetition() {
        if (presenter.getPositionOfQuestion() == (presenter.getModel().getData().getQuestionsModel().size() - 1)) {
            binding.progress.setVisibility(View.VISIBLE);
            presenter.saveCompetition(competitionId);
        } else
            setQuestion(presenter.getModel());
    }

    @Override
    public void onBackPressed() {

        dialog_exit();
    }
}