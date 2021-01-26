package ir.karcook.ViewPresenter.SubCategoryPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.SubCategoryApiModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.GetSubCategory_useCase;
import ir.karcook.ViewPresenter.ContentListPage.ContentListAdapter;
import ir.karcook.databinding.RowContentBinding;
import ir.karcook.databinding.RowSubCategoryBinding;

public class SubCategoryPresenter implements SubCategoryContract.presenter {
    private SubCategoryContract.view iv;
    private Context context;
    GetSubCategory_useCase getSubCategory_useCase;
    SubCategoryListAdapter adapter;
    SubCategoryApiModel model;

    public SubCategoryPresenter(SubCategoryContract.view iv, GetSubCategory_useCase getSubCategory_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getSubCategory_useCase = getSubCategory_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public SubCategoryListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter() {
        this.adapter = new SubCategoryListAdapter(this);
    }

    public SubCategoryApiModel getModel() {
        return model;
    }

    public void setModel(SubCategoryApiModel model) {
        this.model = model;
    }

    @Override
    public void onBindViewHolder(SubCategoryListAdapter.viewHolder holder, int position) {

        holder.binding.title.setText(model.getData().get(position).getTitle() + "");
        holder.binding.description.setText(model.getData().get(position).getDescription() + "");
        holder.binding.fileCount.setText(model.getData().get(position).getCourseCount() + "");

        if (model.getData().get(position).getCourseType() == 1) {
            holder.binding.videoOrVoice.setImageResource(R.drawable.ic_video);
        } else {
            holder.binding.videoOrVoice.setImageResource(R.drawable.ic_music);

        }


        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().get(position).getDocumentId())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .fitCenter()
                .into(holder.binding.pic);
    }

    @Override
    public int getItemCount() {
        return model.getData().size();
    }

    @Override
    public SubCategoryListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowSubCategoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_sub_category, parent, false);
        return new SubCategoryListAdapter.viewHolder(binding, this);
    }

    @Override
    public void itemClicked(int adapterPosition) {
        if (model.getData().get(adapterPosition).getCourseType() == 1)
            iv.goToVideoList(adapterPosition);
        else
            iv.goToVoiceList(adapterPosition);

    }

    @Override
    public void getSubCategory(int id) {
        getSubCategory_useCase.execute(id, new UseCase.CallBack<SubCategoryApiModel>() {
            @Override
            public void onSuccess(SubCategoryApiModel response) {
                model = response;
                setAdapter();
                iv.getSubCategorySuccess(response);
            }

            @Override
            public void onError(String error) {
                iv.getSubCategoryFailed(error);
            }
        }, context);
    }

}