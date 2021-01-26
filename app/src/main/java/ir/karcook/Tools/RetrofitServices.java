package ir.karcook.Tools;

import java.util.Map;

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
import ir.karcook.Models.SubCategoryApiModel;
import ir.karcook.Models.SubscribeAPIModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Adak on 07/02/2018.
 */

public interface RetrofitServices {

    @POST("V1/Account/Login")
    Call<LoginAPIModel> login(@Body Map<String, String> params);

    @POST("v1/Account/Register")
    Call<MessageAPIModel> register(@Body Map<String, Object> params);

    @POST("v1/Account/SendConfirmCode")
    Call<MessageAPIModel> getVerifyCode(@Body Map<String, String> params);

    @POST("v1/Account/Confirm")
    Call<LoginAPIModel> verifyCode(@Body Map<String, Object> params);

    @POST("v1/Account/ForgetPassword")
    Call<ForgetPassCodeApiModel> getForgetPassCode(@Body Map<String, String> params);

    @POST("v1/Account/ResetPassword")
    Call<MessageAPIModel> resetPass(@Body Map<String, Object> params);

    @GET("V1/Contents")
    Call<HomeDataAPIModel> getHomeData(@Header("Authorization") String header);

    @GET("V1/Courses")
    Call<SearchAPIModel> getSearchModel(@Header("Authorization") String header,
                                        @Query("searchString") String searchString,
                                        @Query("length") int length,
                                        @Query("start") int start,
                                        @Query("sortBy") String sortBy,
                                        @Query("sortDir") String sortDir);

    @GET("V1/Courses/CourseListByFreeOrNot")
    Call<SearchAPIModel> getContent(@Header("Authorization") String header,
                                    @Query("searchString") String searchString,
                                    @Query("length") int length,
                                    @Query("start") int start,
                                    @Query("sortBy") String sortBy,
                                    @Query("sortDir") String sortDir,
                                    @Query("isFree") boolean isFree);

    @GET("v1/Categories")
    Call<CategoryApiModel> getCategoryList(@Header("Authorization") String header);

    @GET("v1/CoursePackages/{coursePackageId}/course/{courseId}")
    Call<CourseDetailAPIModel> getCourseDetail(@Header("Authorization") String header,
                                               @Path("coursePackageId") int coursePackageId,
                                               @Path("courseId") int courseId);


    @GET("v1/Categories/{categoryId}/coursePackage")
    Call<SubCategoryApiModel> getSubCategory(@Header("Authorization") String header,
                                             @Path("categoryId") int categoryId);

    @GET("v1/CoursePackages/{coursePackageId}/course")
    Call<CourseListApiModel> getCourseListById(@Header("Authorization") String header,
                                               @Path("coursePackageId") int coursePackageId);

    @POST("v1/ChangePassword")
    Call<MessageAPIModel> changePass(@Header("Authorization") String header,
                                     @Body Map<String, String> params);

    @GET("V1/Subscribes")
    Call<SubscribeAPIModel> getSubscribeList(@Header("Authorization") String header);

    @GET("v1/Account/GetUserInfo")
    Call<ProfileAPIModel> getProfile(@Header("Authorization") String header);

    @GET("V1/Competitions")
    Call<CompetitionListAPIModel> getCompetition(@Header("Authorization") String header,
                                                 @Query("start") int start,
                                                 @Query("length") int length);
    @GET("v1/Competitions/{competitionId}")
    Call<CompetitionQuestionsAPIModel> getCompetitionQuestions(@Header("Authorization") String header,
                                                               @Path("competitionId") int comId);

    @GET("v1/CoursePackages/{coursePackageId}/searchCourse")
    Call<CourseListApiModel> searchByCourseId(@Header("Authorization") String header,
                                                               @Path("coursePackageId") int coursePackageId,
                                                               @Query("searchKey") String searchTxt);

    @POST("v1/Account/AgeConfirmAndSave")
    Call<MessageAPIModel> changeAge(@Header("Authorization") String header,
                                     @Body Map<String, Integer> params);

    @POST("v1/Courses/SaveCourseQuestionAnswer")
    Call<AnswerVideoAPIModel> answerVideo(@Header("Authorization") String header,
                                          @Body Map<String, Integer> params);

    @POST("v1/Competitions/SaveCompetitionAnswer")
    Call<AnswerVideoAPIModel> answerQuestion(@Header("Authorization") String header,
                                          @Body Map<String, Integer> params);

    @POST("v1/Competitions/{competitionId}/SaveUserCompetitionAnswer")
    Call<MessageAPIModel> saveCompetition(@Header("Authorization") String header,
                                             @Path("competitionId") String id);

    @POST("v1/Payments/createFactorWithSubscribeId")
    Call<CreateFactorIdAPIModel> createFactorId(@Header("Authorization") String header,
                                                @Body Map<String, Integer> params);

    @POST("v1/Account/SaveFcmToken")
    Call<MessageAPIModel> sendTokenToServer(@Header("Authorization") String header,
                                                @Body Map<String, String> params);
}
