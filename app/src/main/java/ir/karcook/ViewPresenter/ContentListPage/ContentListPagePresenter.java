package ir.karcook.ViewPresenter.ContentListPage;

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
import ir.karcook.Models.SearchSendModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.GlideApp;
import ir.karcook.UseCase.GetContent_useCase;
import ir.karcook.UseCase.GetCourseDetail_useCase;
import ir.karcook.UseCase.GetCourseListById_useCase;
import ir.karcook.ViewPresenter.SearchPage.SearchPageListAdapter;
import ir.karcook.databinding.RowContentBinding;

public class ContentListPagePresenter implements ContentListPageContract.presenter {
    private ContentListPageContract.view iv;
    private Context context;
    GetContent_useCase getContent_useCase;
    SearchAPIModel model;
    ContentListAdapter contentListAdapter;
    GetCourseDetail_useCase getCourseDetail_useCase;
    GetCourseListById_useCase getCourseListById_useCase;

    public ContentListPagePresenter(ContentListPageContract.view iv, GetContent_useCase getContent_useCase,
                                    GetCourseDetail_useCase getCourseDetail_useCase,
                                    GetCourseListById_useCase getCourseListById_useCase) {
        this.iv = iv;
        setContext(iv.getContext());
        this.getContent_useCase = getContent_useCase;
        this.getCourseDetail_useCase = getCourseDetail_useCase;
        this.getCourseListById_useCase = getCourseListById_useCase;

    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    public SearchAPIModel getModel() {
        return model;
    }

    public void setModel(SearchAPIModel model) {
        this.model = model;
    }

    public ContentListAdapter getContentListAdapter() {
        return contentListAdapter;
    }

    public void setContentListAdapter() {
        this.contentListAdapter = new ContentListAdapter(this);
    }

    @Override
    public void getNewList(SearchSendModel sendModel) {
        getContent_useCase.execute(sendModel, new UseCase.CallBack<SearchAPIModel>() {
            @Override
            public void onSuccess(SearchAPIModel response) {
                if (response.getData().getCourse().size() == 0) {
                    iv.emptyList();
                } else {
                    model = response;
                    setContentListAdapter();
                    iv.getNewListSuccess(response);
                }
            }

            @Override
            public void onError(String error) {
                iv.getNewListFailed(error);
            }
        }, context);
    }

    @Override
    public ContentListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        RowContentBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_content, parent, false);
        return new ContentListAdapter.viewHolder(binding, this);

    }

    @Override
    public void onBindViewHolder(ContentListAdapter.viewHolder holder, int position) {
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
    public int getItemCount() {
        return model.getData().getCourse().size();
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

    @Override
    public void getCourseListById(int coursePackageId) {
        getCourseListById_useCase.execute(coursePackageId, new UseCase.CallBack<CourseListApiModel>() {
            @Override
            public void onSuccess(CourseListApiModel response) {
                if (response.getData().size() == 0) {
                    iv.emptyList();
                } else {
                    model = new SearchAPIModel();
                    SearchDataModel dataModel = new SearchDataModel();
                    dataModel.setCourse(response.getData());
                    dataModel.setCourseCount(response.getData().size());
                    model.setData(dataModel);
                    setContentListAdapter();
                    iv.getCourseListByIdSuccess(model);
                }
            }

            @Override
            public void onError(String error) {
                iv.getCourseListByIdFailed(error);
            }
        }, context);
    }

}