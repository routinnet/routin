package ir.karcook.Repository;

import android.content.Context;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import ir.karcook.Models.ErrorAPIModel;
import ir.karcook.R;
import ir.karcook.Tools.RetrofitConnection;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorHandling {

    public static ErrorAPIModel parseError(Context context, Response<?> response) {
        ErrorAPIModel error;
        try {
        Converter<ResponseBody, ErrorAPIModel> converter =
                RetrofitConnection.getInstance()
                        .responseBodyConverter(ErrorAPIModel.class, new Annotation[0]);

            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            ErrorAPIModel errorAPIModel = new ErrorAPIModel();
            ErrorAPIModel.Errors errors = new ErrorAPIModel.Errors();
            List<String> list = new ArrayList<>();
            list.add(context.getResources().getString(R.string.serverError));
            errors.setErrorDetails(list);
            errorAPIModel.setErrors(errors);
            return errorAPIModel;
        }

        return error;
    }
}
