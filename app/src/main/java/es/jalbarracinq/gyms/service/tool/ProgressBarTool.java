/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import es.jalbarracinq.gyms.R;

public class ProgressBarTool {


    public static Dialog create(Activity activity) {
        return create(activity, null);
    }

    public static Dialog create(Activity activity, @ColorRes Integer color) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.widget_progressbar);

        if (color != null) {
            int[][] states = new int[][] {
                    new int[] { android.R.attr.state_enabled},
                    new int[] {-android.R.attr.state_enabled},
                    new int[] {-android.R.attr.state_checked},
                    new int[] { android.R.attr.state_pressed}
            };
            int[] colors = new int[] {
                    ContextCompat.getColor(activity, color),
                    Color.BLACK,
                    Color.BLACK,
                    Color.BLACK
            };
            ColorStateList myList = new ColorStateList(states, colors);
            ((ProgressBar) dialog.findViewById(R.id.widget_progressbar)).setIndeterminateTintList(myList);
        }

        WindowManager.LayoutParams windowAttributes = dialog.getWindow().getAttributes();
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        windowAttributes.width = size.x;
        windowAttributes.height = size.y;
        return dialog;
    }

}
