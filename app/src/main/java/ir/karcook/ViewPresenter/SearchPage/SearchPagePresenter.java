package ir.karcook.ViewPresenter.SearchPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchSendModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.DoSearch_useCase;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.ViewPresenter.HomePage.Home_newListAdapter;
import ir.karcook.databinding.RowContentBinding;
import ir.karcook.databinding.RowHomeNewBinding;

public class SearchPagePresenter implements SearchPageContract.presenter {
    private SearchPageContract.view iv;
    private Context context;
    DoSearch_useCase doSearch_useCase;
    SearchPageListAdapter searchPageListAdapter;
    SearchAPIModel model;
    GetCourseDetail_useCase getCourseDetail_useCase;


    public SearchPagePresenter(SearchPageContract.view iv, DoSearch_useCase doSearch_useCase,
                               GetCourseDetail_useCase getCourseDetail_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.doSearch_useCase = doSearch_useCase;
        this.getCourseDetail_useCase = getCourseDetail_useCase;

    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public SearchPageListAdapter getSearchPageListAdapter() {
        return searchPageListAdapter;
    }

    public void setSearchPageListAdapter() {
        this.searchPageListAdapter = new SearchPageListAdapter(this);
    }

    public SearchAPIModel getModel() {
        return model;
    }

    public void setModel(SearchAPIModel model) {
        this.model = model;
    }

    @Override
    public void doSearch(final SearchSendModel sendModel) {
        doSearch_useCase.execute(sendModel, new UseCase.CallBack<SearchAPIModel>() {
            @Override
            public void onSuccess(SearchAPIModel response) {
                if (response.getData().getCourse().size() == 0) {
                    iv.emptyList();
                } else {
                    model = response;
                    setSearchPageListAdapter();
                    iv.doSearchSuccess(response);
                }
            }

            @Override
            public void onError(String error) {
                iv.doSearchFailed(error);
            }
        }, context);
    }

    @Override
    public SearchPageListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowContentBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_content, parent, false);
        return new SearchPageListAdapter.viewHolder(binding, this);

    }

    @Override
    public int getItemCount() {
        return model.getData().getCourse().size();
    }

    @Override
    public void onBindViewHolder(SearchPageListAdapter.viewHolder holder, int position) {
        holder.binding.name.setText(model.getData().getCourse().get(position).getTitle());
        holder.binding.packageTitle.setText(model.getData().getCourse().get(position).getCoursePackageTitle());
        holder.binding.fileSize.setText(model.getData().getCourse().get(position).getFileSize() + "Mb");

        if (model.getData().getCourse().get(position).getIsFree()) {
            holder.binding.isFreePic.setImageResource(R.drawable.ic_free_unlock2);

        } else {
            holder.binding.isFreePic.setImageResource(R.drawable.ic_free_lock2);

        }


        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getCourse().get(position).getDocumentId())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .fitCenter()
                .into(holder.binding.pic);

    }

    @Override
    public void itemClicked(int adapterPosition) {
        iv.itemClicked(adapterPosition);
    }

    @Override
    public void getCourseDetail(int coursePackageId, int courseId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("coursePackageId", coursePackageId);
        params.put("courseId", courseId);
        getCourseDetail_useCase.execute(params, new UseCase.CallBack<CourseDetailAPIModel>() {
            @Override
            public void onSuccess(CourseDetailAPIModel response) {
                iv.getCourseDetailSuccess(response);
            }

            @Override
            public void onError(String error) {
                iv.getCourseDetailFailed(error);
            }
        }, context);
    }

}