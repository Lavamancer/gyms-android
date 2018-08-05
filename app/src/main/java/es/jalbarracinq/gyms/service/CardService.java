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
import es.jalbarracinq.gyms.service.tool.DialogTool;
import es.jalbarracinq.gyms.service.tool.FakerTool;
import es.jalbarracinq.gyms.service.tool.ProgressBarTool;
import es.jalbarracinq.gyms.service.tool.RetrofitTool;

public class CardService {


    public static boolean validate(Activity activity, Card card) {
        if (card.getNumber().length() < 16) {
            Toast.makeText(activity, "Enter a valid card number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (card.getVerification().length() < 3) {
            Toast.makeText(activity, "Enter a valid verification number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (card.getMonth() == null || card.getYear() == null) {
            Toast.makeText(activity, "Enter the expiration date of your card", Toast.LENGTH_SHORT).show();
            return false;
        } else if (DateTime.now().getYear() > (2000 + card.getYear())) {
            Toast.makeText(activity, "Enter a valid date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (DateTime.now().getYear() == (2000 + card.getYear()) && DateTime.now().getMonthOfYear() > card.getMonth()) {
            Toast.makeText(activity, "Enter a valid date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public static void postCard(final Activity activity, final Card card) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, Card>() {
            @Override
            protected Card doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new Card(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.postCard(Session.getInstance().token.getAccessToken(), card).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Card result) {
                dialog.dismiss();
                if (result != null) {
                    String message = "Payment method registered successfully";
                    final DialogTool.DialogWidget dialogWidget = DialogTool.createDialog(activity, "New payment method", message);
                    dialogWidget.cancelButton.setVisibility(View.GONE);
                    dialogWidget.acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogWidget.dialog.dismiss();
                            activity.onBackPressed();
                        }
                    });
                    dialogWidget.dialog.show();
                    Session.getInstance().cards.add(result);
                } else {
                    Toast.makeText(activity, "The payment method could not be registered", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

}
