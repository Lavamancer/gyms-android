/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.joda.time.DateTime;

import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.model.Card;
import es.jalbarracinq.gyms.model.Payment;
import es.jalbarracinq.gyms.model.SubscriptionType;
import es.jalbarracinq.gyms.service.tool.DialogTool;
import es.jalbarracinq.gyms.service.tool.FakerTool;
import es.jalbarracinq.gyms.service.tool.ProgressBarTool;
import es.jalbarracinq.gyms.service.tool.RetrofitTool;

public class PaymentService {


    public static void postSubscription(final Activity activity, final SubscriptionType subscriptionType, final DateTime start, final Card card) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, Payment>() {
            @Override
            protected Payment doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new Payment(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.postSubscription(Session.getInstance().token.getAccessToken(), subscriptionType.getId(), start, card.getId()).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Payment result) {
                dialog.dismiss();
                if (result != null) {
                    System.out.println(result);
                    final DialogTool.DialogWidget dialogWidget = DialogTool.createDialog(activity, "Payment made successfully", "Payment made successfully");
                    dialogWidget.cancelButton.setVisibility(View.GONE);
                    dialogWidget.acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogWidget.dialog.dismiss();
                            activity.onBackPressed();
                        }
                    });
                    dialogWidget.dialog.show();
                } else {
                    Toast.makeText(activity, "Payment could not be made", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

}
