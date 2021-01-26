package ir.karcook.ViewPresenter.SubscribePage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CreateFactorIdAPIModel;
import ir.karcook.Models.SubscribeAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.UseCase.CreateFactorId_useCase;
import ir.karcook.UseCase.GetSubscribeList_useCase;
import ir.karcook.ViewPresenter.VoiceListPage.VoiceListAdapter;
import ir.karcook.databinding.RowSubscribeBinding;
import ir.karcook.databinding.RowVoiceListBinding;

public class SubscribePresenter implements SubscribeContract.presenter {
    private SubscribeContract.view iv;
    private Context context;
    GetSubscribeList_useCase getSubscribeList_useCase;
    SubscribeAPIModel model;
    SubscribeListAdapter adapter;
    CreateFactorId_useCase createFactorId_useCase;

    public SubscribePresenter(SubscribeContract.view iv,
                              GetSubscribeList_useCase getSubscribeList_useCase,
                              CreateFactorId_useCase createFactorId_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getSubscribeList_useCase = getSubscribeList_useCase;
        this.createFactorId_useCase = createFactorId_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public SubscribeAPIModel getModel() {
        return model;
    }

    public void setModel(SubscribeAPIModel model) {
        this.model = model;
    }

    public SubscribeListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter() {
        this.adapter = new SubscribeListAdapter(this);
    }

    @Override
    public SubscribeListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowSubscribeBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_subscribe, parent, false);
        return new SubscribeListAdapter.viewHolder(binding, this);

    }

    @Override
    public void onBindViewHolder(SubscribeListAdapter.viewHolder holder, int position) {
        holder.binding.title.setText(model.getData().get(position).getTitle());

        if (model.getData().get(position).getOff() > 0) {
            holder.binding.price.setTextColor(context.getResources().getColor(R.color.gray));
            holder.binding.price.setPaintFlags(holder.binding.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.binding.price.setText(G.getInstance().replaceEnglishNumber(model.getData().get(position).getAmountString()) + " ریال");
            holder.binding.priceOff.setVisibility(View.VISIBLE);
            holder.binding.priceOff.setText(G.getInstance().replaceEnglishNumber(NumberFormat.getInstance(Locale.getDefault()).format(calculateOff(
                    model.getData().get(position).getAmount(),
                    model.getData().get(position).getOff()
            ))) + " ریال");
        } else {
            holder.binding.price.setText(G.getInstance().replaceEnglishNumber(model.getData().get(position).getAmountString()) + " ریال");
            holder.binding.priceOff.setVisibility(View.GONE);
            holder.binding.price.setTextColor(Color.parseColor("#555555"));


        }


    }

    Integer calculateOff(int amount, int off) {
        return amount - (amount * off / 100);
    }

    @Override
    public int getItemCount() {
        return model.getData().size();
    }

    @Override
    public void itemClicked(int adapterPosition) {
        iv.itemClicked(adapterPosition);
    }

    @Override
    public void getSubscribeList() {
        getSubscribeList_useCase.execute(null, new UseCase.CallBack<SubscribeAPIModel>() {
            @Override
            public void onSuccess(SubscribeAPIModel response) {
                model = response;
                setAdapter();
                iv.getSubscribeListSuccess(response);
            }

            @Override
            public void onError(String error) {
                iv.getSubscribeListFailed(error);
            }
        }, context);
    }

    @Override
    public void createFactorId(int id) {
        Map<String, Integer> params = new HashMap<>();
        params.put("subscribeId", id);
        createFactorId_useCase.execute(params, new UseCase.CallBack<CreateFactorIdAPIModel>() {
            @Override
            public void onSuccess(CreateFactorIdAPIModel response) {
                iv.createFactorIdSuccess(response);
            }

            @Override
            public void onError(String error) {
                iv.createFactorIdFailed(error);
            }
        }, context);
    }

}