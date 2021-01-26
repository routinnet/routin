package ir.karcook.ViewPresenter.CourseDetailPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.CourseModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.ViewPresenter.HomePage.Home_newListAdapter;
import ir.karcook.databinding.RowHomeNewBinding;

public class CourseDetailPresenter implements CourseDetailContract.presenter {
    private CourseDetailContract.view iv;
    private Context context;
    CourseDetailListAdapter adapter;
    CourseModel model;
    GetCourseDetail_useCase getCourseDetail_useCase;


    public CourseDetailPresenter(CourseDetailContract.view iv, GetCourseDetail_useCase getCourseDetail_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getCourseDetail_useCase = getCourseDetail_useCase;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public CourseDetailListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter() {
        this.adapter = new CourseDetailListAdapter(this);
    }

    public CourseModel getModel() {
        return model;
    }

    public void setModel(CourseModel model) {
        this.model = model;
    }

    @Override
    public CourseDetailListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowHomeNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_home_new, parent, false);
        return new CourseDetailListAdapter.viewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(CourseDetailListAdapter.viewHolder holder, int position) {
        holder.binding.name.setText(model.getRelatedCourses().get(position).getTitle());
        holder.binding.packageTitle.setText(model.getRelatedCourses().get(position).getCoursePackageTitle());
        holder.binding.fileSize.setText(model.getRelatedCourses().get(position).getFileSize() + "Mb");

        if (model.getRelatedCourses().get(position).getIsFree()) {
            holder.binding.isFreePic.setImageResource(R.drawable.ic_free_unlock2);

        } else {
            holder.binding.isFreePic.setImageResource(R.drawable.ic_free_lock2);

        }


        GlideApp.with(getContext()).load(G.BASE_DOCUMENT_URL + model.getRelatedCourses().get(position).getDocumentId())
                .error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder)
                .fitCenter()
                .into(holder.binding.pic);
    }

    @Override
    public int getItemCount() {
        return model.getRelatedCourses().size();
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