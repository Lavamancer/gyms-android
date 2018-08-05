/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.widget.ProgressBar;

import es.jalbarracinq.gyms.R;

public class ColorTool {

    public static void colorMenu(Menu menu, Context context) {
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    public static void colorProgressBar(ProgressBar progressBar, Context context) {
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.MULTIPLY);
    }

    public static String mock(int position) {
        switch (position % 6) {
            case 0: return "#ffc28a";
            case 1: return "#fbf18d";
            case 2: return "#89ffd6";
            case 3: return "#899fff";
            case 4: return "#b600ce";
            default: return "#f4a122";
        }
    }

}
