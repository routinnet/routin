package ir.karcook.Tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Adak on 13/05/2018.
 */

public class Shared_Prefrences {

    public static Shared_Prefrences shared_prefrences;
    public static SharedPreferences sp;
    public static Shared_Prefrences getInstance(Context context){
        if (shared_prefrences == null) {
            shared_prefrences = new Shared_Prefrences();
            sp = context.getSharedPreferences("content", 0);
        }
            return shared_prefrences;
    }

    public SharedPreferences getSp(){
        return sp;
    }

}
