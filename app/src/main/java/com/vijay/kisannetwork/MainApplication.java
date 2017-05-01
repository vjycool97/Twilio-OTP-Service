package com.vijay.kisannetwork;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.orm.SugarContext;
import com.orm.util.SugarConfig;

/**
 * Created by vijay on 16/04/17.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */

public class MainApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarConfig.clearCache();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
