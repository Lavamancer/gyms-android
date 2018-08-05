/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import es.jalbarracinq.gyms.service.tool.RetrofitTool;

public class Application extends android.app.Application {

    Session session;
    public static Application application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Session.init(this);
        Iconify.with(new FontAwesomeModule());
        RetrofitTool.getInstance();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Session.store(this, session);
    }

}
