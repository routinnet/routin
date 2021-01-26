package ir.karcook.ViewPresenter.VoiceListPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.CourseListApiModel;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchDataModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.DoSearch_courseId_useCase;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.UseCase.GetCourseListById_useCase;
import ir.karcook.ViewPresenter.ContentListPage.ContentListAdapter;
import ir.karcook.databinding.RowContentBinding;
import ir.karcook.databinding.RowVoiceListBinding;

public class VoiceListPresenter implements VoiceListContract.presenter {
    private VoiceListContract.view iv;
    private Context context;
    GetCourseListById_useCase getCourseListById_useCase;
    GetCourseDetail_useCase getCourseDetail_useCase;
    DoSearch_courseId_useCase doSearch_courseId_useCase;
    VoiceListAdapter adapter;
    CourseListApiModel model;

    public VoiceListPresenter(VoiceListContract.view iv,
                              GetCourseListById_useCase getCourseListById_useCase,
                              GetCourseDetail_useCase getCourseDetail_useCase,
                              DoSearch_courseId_useCase doSearch_courseId_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getCourseDetail_useCase = getCourseDetail_useCase;
        this.getCourseListById_useCase = getCourseListById_useCase;
        this.doSearch_courseId_useCase = doSearch_courseId_useCase;

    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public VoiceListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter() {
        this.adapter = new VoiceListAdapter(this);
    }

    public CourseListApiModel getModel() {
        return model;
    }

    public void setModel(CourseListApiModel model) {
        this.model = model;
    }

    @Override
    public VoiceListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowVoiceListBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_voice_list, parent, false);
        return new VoiceListAdapter.viewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(VoiceListAdapter.viewHolder holder, int position) {
        holder.binding.title.setText(model.getData().get(position).getTitle());
        holder.binding.description.setText(model.getData().get(position).getDescription());
        holder.binding.duration.setText(model.getData().get(position).getDuration() + "دقیقه");

        if (model.getData().get(position).getIsFree()) {
            holder.binding.isFreePic.setImageResource(R.drawable.ic_free_unlock2);

        } else {
            holder.binding.isFreePic.setImageResource(R.drawable.ic_free_lock2);

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
    public void itemClicked(int adapterPosition) {
        iv.itemClicked(adapterPosition);
    }

    @Override
    public void getCourseListById(int coursePackageId) {
        getCourseListById_useCase.execute(coursePackageId, new UseCase.CallBack<CourseListApiModel>() {
            @Override
            public void onSuccess(CourseListApiModel response) {
                model = response;
                if (response.getData().size() == 0)
                    iv.emptyList();
                else {
                    setAdapter();
                    iv.getCourseListByIdSuccess(model);
                }

            }

            @Override
            public void onError(String error) {
                iv.getCourseListByIdFailed(error);
            }
        }, context);
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

    @Override
    public void playBtnClicked(int adapterPosition) {
        iv.playBtnClicked(adapterPosition);
    }

    @Override
    public void doSearch(int coursePkgId, String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("coursePackageId", coursePkgId);
        params.put("text", text);
        doSearch_courseId_useCase.execute(params, new UseCase.CallBack<CourseListApiModel>() {
            @Override
            public void onSuccess(CourseListApiModel response) {
                model = response;
                if (response.getData().size() == 0)
                    iv.emptyList();
                else {
                    setAdapter();
                    iv.getCourseListByIdSuccess(model);
                }
            }

            @Override
            public void onError(String error) {
                iv.doSearchFailed(error);
            }
        }, context);
    }

}