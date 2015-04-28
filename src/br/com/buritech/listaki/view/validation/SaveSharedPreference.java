package br.com.buritech.listaki.view.validation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lucas on 23/01/15.
 */
public class SaveSharedPreference {


        static final String PREF_USER_NAME= "username";

        static SharedPreferences getSharedPreferences(Context ctx) {
            return PreferenceManager.getDefaultSharedPreferences(ctx);
        }

        public static void setUserEmail(Context ctx, String userName)
        {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            editor.putString(PREF_USER_NAME, userName);
            editor.commit();
        }

        public static String getUserEmail(Context ctx)
        {
            return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
        }

}
