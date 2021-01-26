package ir.karcook.ViewPresenter.PlayVideoPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.AnswerVideoAPIModel;
import ir.karcook.Models.CourseModel;
import ir.karcook.R;
import ir.karcook.UseCase.AnswerVideo_useCase;
import ir.karcook.ViewPresenter.VoiceListPage.VoiceListAdapter;
import ir.karcook.databinding.RowAnswerBinding;
import ir.karcook.databinding.RowAnswerDialogBinding;
import ir.karcook.databinding.RowVoiceListBinding;

public class PlayVideoPresenter implements PlayVideoContract.presenter {
    private PlayVideoContract.view iv;
    private Context context;
    CourseModel model;
    PlayVideoListAdapter adapter;
    AnswerVideo_useCase answerVideo_useCase;

    public PlayVideoPresenter(PlayVideoContract.view iv, AnswerVideo_useCase answerVideo_useCas) {
        this.iv = iv;
        setContext(iv.getContext());
        this.answerVideo_useCase = answerVideo_useCas;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public CourseModel getModel() {
        return model;
    }

    public void setModel(CourseModel model) {
        this.model = model;
    }

    public PlayVideoListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter() {
        this.adapter = new PlayVideoListAdapter(this);
    }

    @Override
    public PlayVideoListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowAnswerDialogBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_answer_dialog, parent, false);
        return new PlayVideoListAdapter.viewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(PlayVideoListAdapter.viewHolder holder, int position) {
        holder.binding.answerTitle.setText(model.getQuestionsModel().getAnswerModel().get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return model.getQuestionsModel().getAnswerModel().size();
    }

    @Override
    public void itemClicked(int adapterPosition) {
        iv.itemClicked(adapterPosition);
    }

    @Override
    public void answer(int id ,int courseId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("AnswerId", id);
        params.put("courseId", courseId);
        answerVideo_useCase.execute(params, new UseCase.CallBack<AnswerVideoAPIModel>() {
            @Override
            public void onSuccess(AnswerVideoAPIModel response) {
                if (response.getData())
                    iv.answerTrue();
                else
                    iv.answerFalse();
            }

            @Override
            public void onError(String error) {
                iv.answerFailed(error);
            }
        }, context);
    }

}