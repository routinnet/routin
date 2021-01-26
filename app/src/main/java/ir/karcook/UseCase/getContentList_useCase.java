package ir.karcook.UseCase;

import android.content.Context;

import ir.karcook.Base.UseCase;
import ir.karcook.Models.SearchAPIModel;
import ir.karcook.Models.SearchSendModel;
import ir.karcook.Repository.Ripo;

public class getContentList_useCase implements UseCase<SearchSendModel, SearchAPIModel> {
    @Override
    public void execute(SearchSendModel parameter, CallBack<SearchAPIModel> callBack, Context context) {
        Ripo.getInstance(context).getSearchData(parameter, callBack);
    }
}
