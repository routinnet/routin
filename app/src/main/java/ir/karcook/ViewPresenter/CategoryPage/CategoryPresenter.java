package ir.karcook.ViewPresenter.CategoryPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CategoryApiModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.GetCategoryList_useCase;
import ir.karcook.ViewPresenter.HomePage.Home_newListAdapter;
import ir.karcook.databinding.RowCategoryBinding;
import ir.karcook.databinding.RowHomeNewBinding;

public class CategoryPresenter implements CategoryContract.presenter {
    private CategoryContract.view iv;
    private Context context;
    GetCategoryList_useCase getCategoryList_useCase;
    CategoryApiModel model;
    CategoryListAdapter adapter;

    public CategoryPresenter(CategoryContract.view iv, GetCategoryList_useCase getCategoryList_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getCategoryList_useCase = getCategoryList_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public CategoryApiModel getModel() {
        return model;
    }

    public void setModel(CategoryApiModel model) {
        this.model = model;
    }

    public CategoryListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter() {
        this.adapter = new CategoryListAdapter(this);
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public CategoryListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowCategoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_category, parent, false);
        return new CategoryListAdapter.viewHolder(binding, this);

    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.viewHolder holder, int position) {
        holder.binding.categoryTitle.setText(model.getData().get(position).getTitle());

        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().get(position).getMobileImage())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .into(holder.binding.pic);
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
    public void getCategoryList() {
        getCategoryList_useCase.execute(null, new UseCase.CallBack<CategoryApiModel>() {
            @Override
            public void onSuccess(CategoryApiModel response) {
                model = response;
                setAdapter();
                iv.getCategoryListSuccess(response);
            }

            @Override
            public void onError(String error) {
                iv.getCategoryListFailed(error);
            }
        }, context);
    }

}