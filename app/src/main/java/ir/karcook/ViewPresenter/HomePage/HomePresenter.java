package ir.karcook.ViewPresenter.HomePage;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.HomeDataAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.UseCase.GetHomeData_useCase;
import ir.karcook.databinding.RowHomeNewBinding;

public class HomePresenter implements HomeContract.presenter {
    private HomeContract.view iv;
    private Context context;
    GetHomeData_useCase getHomeData_useCase;
    HomeDataAPIModel model;
    Home_newListAdapter adapter;
    Home_freeListAdapter freeListAdapter;
    GetCourseDetail_useCase getCourseDetail_useCase;

    public HomePresenter(HomeContract.view iv, GetHomeData_useCase getHomeData_useCase,
                         GetCourseDetail_useCase getCourseDetail_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getHomeData_useCase = getHomeData_useCase;
        this.getCourseDetail_useCase = getCourseDetail_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public HomeDataAPIModel getModel() {
        return model;
    }

    public void setModel(HomeDataAPIModel model) {
        this.model = model;
    }

    public Home_newListAdapter getAdapter() {
        return adapter;
    }

    public Home_freeListAdapter getFreeListAdapter() {
        return freeListAdapter;
    }

    public void setFreeListAdapter() {
        this.freeListAdapter = new Home_freeListAdapter(this);
    }

    public void setAdapter() {
        this.adapter = new Home_newListAdapter(this);
    }

    @Override
    public void getHomeData() {
        getHomeData_useCase.execute(null, new UseCase.CallBack<HomeDataAPIModel>() {
            @Override
            public void onSuccess(HomeDataAPIModel response) {
                model = response;
                setAdapter();
                setFreeListAdapter();
                iv.getHomeDataSuccess(model);
            }

            @Override
            public void onError(String error) {
                iv.getHomeDataFailed(error);
            }
        }, context);
    }

    /////////////////////Home New List
    @Override
    public Home_newListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowHomeNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_home_new, parent, false);
        return new Home_newListAdapter.viewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(Home_newListAdapter.viewHolder holder, int position) {
        holder.binding.name.setText(model.getData().getLastCourse().get(position).getTitle());
        holder.binding.packageTitle.setText(model.getData().getLastCourse().get(position).getCoursePackageTitle());
        holder.binding.fileSize.setText(model.getData().getLastCourse().get(position).getFileSize() + "Mb");

        if (model.getData().getLastCourse().get(position).getIsFree()) {
            holder.binding.isFreePic.setImageResource(R.drawable.ic_free_unlock2);

        } else {
            holder.binding.isFreePic.setImageResource(R.drawable.ic_free_lock2);

        }


        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getLastCourse().get(position).getDocumentId())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .fitCenter()
                .into(holder.binding.pic);


    }

    @Override
    public int getItemCount() {
        return model.getData().getLastCourse().size();
    }

    @Override
    public void itemClicked(int adapterPosition) {
        iv.lastItemClicked(adapterPosition);
    }

//////////////////////////////////////////


    ////////////////////////free list
    @Override
    public Home_freeListAdapter.viewHolder onCreateViewHolder_free(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowHomeNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_home_new, parent, false);
        return new Home_freeListAdapter.viewHolder(binding, this);
    }

    @Override
    public int getItemCount_free() {
        return model.getData().getLastFreeCourses().size();
    }

    @Override
    public void onBindViewHolder_free(Home_freeListAdapter.viewHolder holder, int position) {
        holder.binding.name.setText(model.getData().getLastFreeCourses().get(position).getTitle());
        holder.binding.packageTitle.setText(model.getData().getLastFreeCourses().get(position).getCoursePackageTitle());
        holder.binding.fileSize.setText(model.getData().getLastFreeCourses().get(position).getFileSize() + "Mb");

        holder.binding.isFreePic.setImageResource(R.drawable.ic_free_unlock2);

        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getData().getLastFreeCourses().get(position).getDocumentId())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .fitCenter()
                .into(holder.binding.pic);
    }

    @Override
    public void itemClicked_free(int adapterPosition) {
        iv.itemClicked_free(adapterPosition);
        getCourseDetail(model.getData().getLastFreeCourses().get(adapterPosition).getCoursePackageId(),
                model.getData().getLastFreeCourses().get(adapterPosition).getId());
    }

    /////////////////////////////////////

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