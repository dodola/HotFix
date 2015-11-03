/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package dodola.hotfix;

import java.io.File;

import android.app.Application;
import android.content.Context;
import dodola.hotfixlib.HotFix;

/**
 * Created by sunpengfei on 15/11/4.
 */
public class HotfixApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        File dexPath = new File(getDir("dex", Context.MODE_PRIVATE), "hackdex_dex.jar");
        Utils.prepareDex(this.getApplicationContext(), dexPath, "hackdex_dex.jar");
        HotFix.patch(this, dexPath.getAbsolutePath(), "dodola.hackdex.AntilazyLoad");
        try {
            this.getClassLoader().loadClass("dodola.hackdex.AntilazyLoad");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
