/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import es.jalbarracinq.gyms.R;

public class DialogTool {


    public static DialogWidget createDialog(Activity activity, String title, String message) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.widget_dialog);

        DialogWidget dialogWidget = new DialogWidget();
        dialogWidget.dialog = dialog;
        dialogWidget.imageView = dialog.findViewById(R.id.dialog_imageview);
        dialogWidget.titleTextView = dialog.findViewById(R.id.dialog_title_textview);
        dialogWidget.messageTextView = dialog.findViewById(R.id.dialog_message_textview);
        dialogWidget.acceptButton = dialog.findViewById(R.id.dialog_accept_button);
        dialogWidget.cancelButton = dialog.findViewById(R.id.dialog_cancel_button);

        dialogWidget.titleTextView.setText(title);
        dialogWidget.messageTextView.setText(message);
        dialogWidget.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams windowAttributes = dialog.getWindow().getAttributes();
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        windowAttributes.width = size.x;
        windowAttributes.height = size.y;
        return dialogWidget;
    }


    public static class DialogWidget {
        public Dialog dialog;
        public ImageView imageView;
        public TextView titleTextView;
        public TextView messageTextView;
        public Button acceptButton;
        public Button cancelButton;
    }

}
