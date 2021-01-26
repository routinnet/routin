package ir.karcook.ViewPresenter.CompetitionPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CompetitionListAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.GetCompetitionList_useCase;
import ir.karcook.ViewPresenter.ContentListPage.ContentListAdapter;
import ir.karcook.databinding.RowCompetitionBinding;
import ir.karcook.databinding.RowContentBinding;

public class CompetitionPresenter implements CompetitionContract.presenter {
    private CompetitionContract.view iv;
    private Context context;
    GetCompetitionList_useCase getCompetitionList_useCase;
    CompetitionListAPIModel model;
    CompetitionListAdapter adapter;

    public CompetitionPresenter(CompetitionContract.view iv, GetCompetitionList_useCase getCompetitionList_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getCompetitionList_useCase = getCompetitionList_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public CompetitionListAPIModel getModel() {
        return model;
    }

    public void setModel(CompetitionListAPIModel model) {
        this.model = model;
    }

    public CompetitionListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter() {
        this.adapter = new CompetitionListAdapter(this);
    }

    @Override
    public CompetitionListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowCompetitionBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_competition, parent, false);
        return new CompetitionListAdapter.viewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(CompetitionListAdapter.viewHolder holder, int position) {

        holder.binding.compTitle.setText(model.getData().getCompetition().get(position).getTitle());

        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getCompetition().get(position).getDocumentId())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .fitCenter()
                .into(holder.binding.pic);

    }

    @Override
    public int getItemCount() {
        return model.getData().getCompetition().size();
    }

    @Override
    public void getCompetitionList() {
        Map<String, Integer> params = new HashMap<>();
        params.put("start", 0);
        params.put("length", 200);
        getCompetitionList_useCase.execute(params, new UseCase.CallBack<CompetitionListAPIModel>() {
            @Override
            public void onSuccess(CompetitionListAPIModel response) {
                model = response;
                if (response.getData().getCompetition().size() == 0)
                    iv.emptyList();
                else {
                    setAdapter();
                    iv.getCompetitionListSuccess(response);

                }
            }

            @Override
            public void onError(String error) {
                iv.getCompetitionListFailed(error);
            }
        }, context);
    }

    @Override
    public void itemClicked(int adapterPosition) {
        iv.itemClicked(adapterPosition);
    }

}