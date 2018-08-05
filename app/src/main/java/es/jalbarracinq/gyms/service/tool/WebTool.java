/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class WebTool {


    public static void show(Activity activity, String url) {
        try {
            Uri webpage = Uri.parse(url);
            activity.startActivity(new Intent(Intent.ACTION_VIEW, webpage));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "This action could not be performed. Please install a browser on the device.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public static void share(Activity activity, String title, String body, String url) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, title);
            String sAux = "\n" + body + "\n\n";
            sAux = sAux + url;
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            activity.startActivity(Intent.createChooser(i, "Choose an option"));
        } catch (Exception e) {
            Toast.makeText(activity, "The link could not be shared", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
