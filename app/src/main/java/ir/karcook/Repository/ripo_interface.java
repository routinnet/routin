package ir.karcook.Repository;


import android.util.Log;

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

/**
 * Created by John Cale on 6/5/2018.
 */

public interface ripo_interface {


    void login(Map<String, String> params, UseCase.CallBack<LoginAPIModel> callBack);

    void getHomeData(Void params, UseCase.CallBack<HomeDataAPIModel> callBack);

    void getSearchData(SearchSendModel params, UseCase.CallBack<SearchAPIModel> callBack);

    void getContent(SearchSendModel params, UseCase.CallBack<SearchAPIModel> callBack);

    void getCategoryList(Void params, UseCase.CallBack<CategoryApiModel> callBack);

    void getSubCategory(int id, UseCase.CallBack<SubCategoryApiModel> callBack);

    void getCourseDetail(Map<String, Integer> params, UseCase.CallBack<CourseDetailAPIModel> callBack);

    void getCourseListById(int id, UseCase.CallBack<CourseListApiModel> callBack);

    void changePassword(Map<String, String> params, UseCase.CallBack<MessageAPIModel> callBack);

    void getSubscribeList(Void params, UseCase.CallBack<SubscribeAPIModel> callBack);

    void getProfile(Void params, UseCase.CallBack<ProfileAPIModel> callBack);

    void register(Map<String, Object> params, UseCase.CallBack<MessageAPIModel> callBack);

    void getVerifyCode(Map<String, String> params, UseCase.CallBack<MessageAPIModel> callBack);

    void verifyCode(Map<String, Object> params, UseCase.CallBack<LoginAPIModel> callBack);

    void getForgetPassCode(Map<String, String> params, UseCase.CallBack<ForgetPassCodeApiModel> callBack);

    void resetPassword(Map<String, Object> params, UseCase.CallBack<MessageAPIModel> callBack);

    void getCompetitionList(Map<String, Integer> params, UseCase.CallBack<CompetitionListAPIModel> callBack);

    void getCompetitionQuestions(int competitionId, UseCase.CallBack<CompetitionQuestionsAPIModel> callBack);

    void answerQuestion(Map<String, Integer> params, UseCase.CallBack<AnswerVideoAPIModel> callBack);

    void searchFromTextAndCoursePackageId(Map<String, Object> params, UseCase.CallBack<CourseListApiModel> callBack);

    void changeAge(Map<String, Integer> params, UseCase.CallBack<MessageAPIModel> callBack);

    void answerQuestion_dialog(Map<String, Integer> params, UseCase.CallBack<AnswerVideoAPIModel> callBack);

    void saveUserCompetition(String params, UseCase.CallBack<MessageAPIModel> callBack);

    void createFactorId(Map<String, Integer> params, UseCase.CallBack<CreateFactorIdAPIModel> callBack);

    void sendTokenToServer(String token, UseCase.CallBack<MessageAPIModel> callBack);
}
