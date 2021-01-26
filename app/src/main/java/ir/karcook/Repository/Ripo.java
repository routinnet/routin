package ir.karcook.Repository;


import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.AnswerVideoAPIModel;
import ir.karcook.Models.CategoryApiModel;
import ir.karcook.Models.CompetitionListAPIModel;
import ir.karcook.Models.CompetitionQuestionsAPIModel;
import ir.karcook.Models.CourseDetailAPIModel;
import ir.karcook.Models.CourseListApiModel;
import ir.karcook.Models.CreateFactorIdAPIModel;
import ir.karcook.Models.ForgetPassCodeApiModel;
import ir.karcook.Models.HomeDataAPIModel;
import ir.karcook.Models.LoginAPIModel;
import ir.karcook.Models.MessageAPIModel;
import ir.karcook.Models.ProfileAPIModel;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchSendModel;
import ir.karcook.Models.SubCategoryApiModel;
import ir.karcook.Models.SubscribeAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.Tools.RetrofitConnection;
import ir.karcook.Tools.RetrofitServices;
import ir.karcook.Tools.Shared_Prefrences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by John Cale on 6/5/2018.
 */


public class Ripo implements ripo_interface {

    Context context;

    private static Ripo ripo;


    public static Ripo getInstance(Context context) {
        if (ripo == null) {
            ripo = new Ripo(context);
            return ripo;
        }
        return ripo;
    }

    public Ripo(Context context) {
        this.context = context;
    }

    @Override
    public void login(Map<String, String> params, final UseCase.CallBack<LoginAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<LoginAPIModel> call = service.login(params);
        Callback<LoginAPIModel> retrofitCallback = new Callback<LoginAPIModel>() {
            @Override
            public void onResponse(Call<LoginAPIModel> call, Response<LoginAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 403) {
                    callBack.onError(context.getString(R.string.userLock));
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<LoginAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getHomeData(Void params, final UseCase.CallBack<HomeDataAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<HomeDataAPIModel> call = service.getHomeData("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""));
        Callback<HomeDataAPIModel> retrofitCallback = new Callback<HomeDataAPIModel>() {
            @Override
            public void onResponse(Call<HomeDataAPIModel> call, Response<HomeDataAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<HomeDataAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getSearchData(SearchSendModel params, final UseCase.CallBack<SearchAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<SearchAPIModel> call = service.getSearchModel("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                        .getString(context.getString(R.string.token), ""), params.getSearchString(),
                params.getLength(), params.getStart(), params.getSortBy(), params.getSortDir());
        Callback<SearchAPIModel> retrofitCallback = new Callback<SearchAPIModel>() {
            @Override
            public void onResponse(Call<SearchAPIModel> call, Response<SearchAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<SearchAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getContent(SearchSendModel params, final UseCase.CallBack<SearchAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<SearchAPIModel> call = service.getContent("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                        .getString(context.getString(R.string.token), ""), params.getSearchString(),
                params.getLength(), params.getStart(), params.getSortBy(), params.getSortDir(), params.isFree());
        Callback<SearchAPIModel> retrofitCallback = new Callback<SearchAPIModel>() {
            @Override
            public void onResponse(Call<SearchAPIModel> call, Response<SearchAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<SearchAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getCategoryList(Void params, final UseCase.CallBack<CategoryApiModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<CategoryApiModel> call = service.getCategoryList("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""));
        Callback<CategoryApiModel> retrofitCallback = new Callback<CategoryApiModel>() {
            @Override
            public void onResponse(Call<CategoryApiModel> call, Response<CategoryApiModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<CategoryApiModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getSubCategory(int id, final UseCase.CallBack<SubCategoryApiModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<SubCategoryApiModel> call = service.getSubCategory("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), id);
        Callback<SubCategoryApiModel> retrofitCallback = new Callback<SubCategoryApiModel>() {
            @Override
            public void onResponse(Call<SubCategoryApiModel> call, Response<SubCategoryApiModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<SubCategoryApiModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getCourseDetail(Map<String, Integer> params, final UseCase.CallBack<CourseDetailAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<CourseDetailAPIModel> call = service.getCourseDetail("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), params.get("coursePackageId"), params.get("courseId"));
        Callback<CourseDetailAPIModel> retrofitCallback = new Callback<CourseDetailAPIModel>() {
            @Override
            public void onResponse(Call<CourseDetailAPIModel> call, Response<CourseDetailAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<CourseDetailAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getCourseListById(int id, final UseCase.CallBack<CourseListApiModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<CourseListApiModel> call = service.getCourseListById("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), id);
        Callback<CourseListApiModel> retrofitCallback = new Callback<CourseListApiModel>() {
            @Override
            public void onResponse(Call<CourseListApiModel> call, Response<CourseListApiModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<CourseListApiModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void changePassword(Map<String, String> params, final UseCase.CallBack<MessageAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<MessageAPIModel> call = service.changePass("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), params);
        Callback<MessageAPIModel> retrofitCallback = new Callback<MessageAPIModel>() {
            @Override
            public void onResponse(Call<MessageAPIModel> call, Response<MessageAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<MessageAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getSubscribeList(Void params, final UseCase.CallBack<SubscribeAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<SubscribeAPIModel> call = service.getSubscribeList("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""));
        Callback<SubscribeAPIModel> retrofitCallback = new Callback<SubscribeAPIModel>() {
            @Override
            public void onResponse(Call<SubscribeAPIModel> call, Response<SubscribeAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<SubscribeAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getProfile(Void params, final UseCase.CallBack<ProfileAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<ProfileAPIModel> call = service.getProfile("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""));
        Callback<ProfileAPIModel> retrofitCallback = new Callback<ProfileAPIModel>() {
            @Override
            public void onResponse(Call<ProfileAPIModel> call, Response<ProfileAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else if (response.code() == 403) {
                    callBack.onError(context.getResources().getString(R.string.tokenExpired));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<ProfileAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void register(Map<String, Object> params, final UseCase.CallBack<MessageAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<MessageAPIModel> call = service.register(params);
        Callback<MessageAPIModel> retrofitCallback = new Callback<MessageAPIModel>() {
            @Override
            public void onResponse(Call<MessageAPIModel> call, Response<MessageAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 400) {
                    callBack.onError(context.getString(R.string.userLock));
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<MessageAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getVerifyCode(Map<String, String> params, final UseCase.CallBack<MessageAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<MessageAPIModel> call = service.getVerifyCode(params);
        Callback<MessageAPIModel> retrofitCallback = new Callback<MessageAPIModel>() {
            @Override
            public void onResponse(Call<MessageAPIModel> call, Response<MessageAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<MessageAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void verifyCode(Map<String, Object> params, final UseCase.CallBack<LoginAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<LoginAPIModel> call = service.verifyCode(params);
        Callback<LoginAPIModel> retrofitCallback = new Callback<LoginAPIModel>() {
            @Override
            public void onResponse(Call<LoginAPIModel> call, Response<LoginAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<LoginAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getForgetPassCode(Map<String, String> params, final UseCase.CallBack<ForgetPassCodeApiModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<ForgetPassCodeApiModel> call = service.getForgetPassCode(params);
        Callback<ForgetPassCodeApiModel> retrofitCallback = new Callback<ForgetPassCodeApiModel>() {
            @Override
            public void onResponse(Call<ForgetPassCodeApiModel> call, Response<ForgetPassCodeApiModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<ForgetPassCodeApiModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void resetPassword(Map<String, Object> params, final UseCase.CallBack<MessageAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<MessageAPIModel> call = service.resetPass(params);
        Callback<MessageAPIModel> retrofitCallback = new Callback<MessageAPIModel>() {
            @Override
            public void onResponse(Call<MessageAPIModel> call, Response<MessageAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<MessageAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getCompetitionList(Map<String, Integer> params, final UseCase.CallBack<CompetitionListAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<CompetitionListAPIModel> call = service.getCompetition("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), params.get("start"), params.get("length"));
        Callback<CompetitionListAPIModel> retrofitCallback = new Callback<CompetitionListAPIModel>() {
            @Override
            public void onResponse(Call<CompetitionListAPIModel> call, Response<CompetitionListAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<CompetitionListAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void getCompetitionQuestions(int competitionId, final UseCase.CallBack<CompetitionQuestionsAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<CompetitionQuestionsAPIModel> call = service.getCompetitionQuestions("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), competitionId);
        Callback<CompetitionQuestionsAPIModel> retrofitCallback = new Callback<CompetitionQuestionsAPIModel>() {
            @Override
            public void onResponse(Call<CompetitionQuestionsAPIModel> call, Response<CompetitionQuestionsAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<CompetitionQuestionsAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void answerQuestion(Map<String, Integer> params, final UseCase.CallBack<AnswerVideoAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<AnswerVideoAPIModel> call = service.answerQuestion("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), params);
        Callback<AnswerVideoAPIModel> retrofitCallback = new Callback<AnswerVideoAPIModel>() {
            @Override
            public void onResponse(Call<AnswerVideoAPIModel> call, Response<AnswerVideoAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<AnswerVideoAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }


    @Override
    public void searchFromTextAndCoursePackageId(Map<String, Object> params, final UseCase.CallBack<CourseListApiModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<CourseListApiModel> call = service.searchByCourseId("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                        .getString(context.getString(R.string.token), ""),
                (int) params.get("coursePackageId"), (String) params.get("text"));
        Callback<CourseListApiModel> retrofitCallback = new Callback<CourseListApiModel>() {
            @Override
            public void onResponse(Call<CourseListApiModel> call, Response<CourseListApiModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<CourseListApiModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void changeAge(Map<String, Integer> params, final UseCase.CallBack<MessageAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<MessageAPIModel> call = service.changeAge("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), params);
        Callback<MessageAPIModel> retrofitCallback = new Callback<MessageAPIModel>() {
            @Override
            public void onResponse(Call<MessageAPIModel> call, Response<MessageAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<MessageAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void answerQuestion_dialog(Map<String, Integer> params, final UseCase.CallBack<AnswerVideoAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<AnswerVideoAPIModel> call = service.answerVideo("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), params);
        Callback<AnswerVideoAPIModel> retrofitCallback = new Callback<AnswerVideoAPIModel>() {
            @Override
            public void onResponse(Call<AnswerVideoAPIModel> call, Response<AnswerVideoAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<AnswerVideoAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void saveUserCompetition(String params, final UseCase.CallBack<MessageAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<MessageAPIModel> call = service.saveCompetition("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), params);
        Callback<MessageAPIModel> retrofitCallback = new Callback<MessageAPIModel>() {
            @Override
            public void onResponse(Call<MessageAPIModel> call, Response<MessageAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<MessageAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void createFactorId(Map<String, Integer> params, final UseCase.CallBack<CreateFactorIdAPIModel> callBack) {
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<CreateFactorIdAPIModel> call = service.createFactorId("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), params);
        Callback<CreateFactorIdAPIModel> retrofitCallback = new Callback<CreateFactorIdAPIModel>() {
            @Override
            public void onResponse(Call<CreateFactorIdAPIModel> call, Response<CreateFactorIdAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<CreateFactorIdAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

    @Override
    public void sendTokenToServer(String token, final UseCase.CallBack<MessageAPIModel> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("Token", token);
        RetrofitServices service = RetrofitConnection.getInstance().create(RetrofitServices.class);
        Call<MessageAPIModel> call = service.sendTokenToServer("Bearer " + Shared_Prefrences.getInstance(context).getSp()
                .getString(context.getString(R.string.token), ""), params);
        Callback<MessageAPIModel> retrofitCallback = new Callback<MessageAPIModel>() {
            @Override
            public void onResponse(Call<MessageAPIModel> call, Response<MessageAPIModel> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else if (response.code() == 500) {
                    callBack.onError(context.getResources().getString(R.string.serverError));
                } else {
                    callBack.onError((ErrorHandling.parseError(context, response).getErrors().getErrorDetails().get(0)));

                }

            }

            @Override
            public void onFailure(Call<MessageAPIModel> call, Throwable t) {
                callBack.onError(context.getResources().getString(R.string.serverError));
            }
        };
        call.enqueue(retrofitCallback);
    }

}
