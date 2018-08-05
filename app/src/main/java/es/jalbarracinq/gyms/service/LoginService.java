/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.github.javafaker.Faker;

import org.joda.time.DateTime;

import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.LoginActivity;
import es.jalbarracinq.gyms.controller.MainActivity;
import es.jalbarracinq.gyms.controller.ProfileActivity;
import es.jalbarracinq.gyms.model.Login;
import es.jalbarracinq.gyms.model.User;
import es.jalbarracinq.gyms.model.response.LoginResponse;
import es.jalbarracinq.gyms.service.tool.FakerTool;
import es.jalbarracinq.gyms.service.tool.ProgressBarTool;
import es.jalbarracinq.gyms.service.tool.RetrofitTool;
import es.jalbarracinq.gyms.service.tool.StringTool;

public class LoginService {


    public static boolean validateLogin(Activity activity, String username, String password, boolean isCheckedTerms) {
        if (!StringTool.validateEmail(username)) {
            Toast.makeText(activity, "Enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password != null && password.length() < 4) {
            Toast.makeText(activity, "Enter a valid password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isCheckedTerms) {
            Toast.makeText(activity, "To continue it is necessary to accept conditions and agreements", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean validateProfile(Activity activity, User user) {
        String response = null;
        if (user.getName() == null || user.getName().length() < 2) {
            response = "The name field must have at least two characters";
        } else if (user.getLastname() == null || user.getLastname().length() < 2) {
            response = "The lastname field must have at least two characters";
        } else if (user.getNickname() == null || user.getNickname().length() < 2) {
            response = "The nickname field must have at least two characters";
        } else if (user.getPersonalId() == null || !StringTool.validatePersonalId(user.getPersonalId())) {
            response = "Enter a valid personal ID";
        } else if (user.getBirthdate() == null || user.getBirthdate().isAfter(DateTime.now().minusYears(18))) {
            response = "You must be over 18 years old";
        } else if (user.getPhone() == null || user.getPhone().isEmpty()) {
            response = "Enter a valid phone number";
        }
        if (response != null) {
            if (activity != null) {
                Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    public static void postRegister(final Activity activity, final String username) {

        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    Faker faker = FakerTool.getInstance();
                    return true;
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.postRegister(username).execute().isSuccessful();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                dialog.dismiss();
                if (result != null) {
                    if (result) {
                        Session.getInstance().login = new Login();
                        Session.getInstance().login.setUsername(username);
                        activity.onBackPressed();
                        Toast.makeText(activity, "Check your email to activate the account", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "This email is not valid or is already in use", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public static void postLogin(final Activity activity, final String username, final String password, final boolean autoLogin) {
        final Dialog dialog = ProgressBarTool.create(activity);
        if (!autoLogin) dialog.show();
        new AsyncTask<Void, Void, LoginResponse>() {
            @Override
            protected LoginResponse doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new LoginResponse(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.postLogin(username, password).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(LoginResponse result) {
                if (!autoLogin) dialog.dismiss();
                if (result != null && result.getSubscriber() != null) {
                    Session.getInstance().token = result.getToken();
                    Session.getInstance().token.setAccessToken("Bearer " + result.getToken().getAccessToken());
                    Session.getInstance().login = new Login();
                    Session.getInstance().login.setUsername(username);
                    Session.getInstance().autoLogin = new Login();
                    Session.getInstance().autoLogin.setUsername(username);
                    Session.getInstance().autoLogin.setPassword(password);
                    Session.getInstance().facilities = result.getFacilities();
                    Session.getInstance().lessonTypes = result.getLessonTypes();
                    Session.getInstance().configurations = result.getConfigurations();
                    Session.getInstance().cards = result.getCards();
                    Session.getInstance().user = result.getSubscriber();
                    if (validateProfile(null, Session.getInstance().user)) {
                        activity.startActivity(new Intent(activity, MainActivity.class));
                    } else  {
                        Toast.makeText(activity, "Enter your personal information", Toast.LENGTH_SHORT).show();
                        activity.startActivity(new Intent(activity, ProfileActivity.class));
                    }
                    activity.overridePendingTransition(0, 0);
                    activity.finish();
                } else {
                    Toast.makeText(activity, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    if (autoLogin) {
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                    }
                }
            }
        }.execute();
    }

    public static void postLogout(final Activity activity) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    Faker faker = FakerTool.getInstance();
                    return true;
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                dialog.dismiss();
                if (result != null) {
                    Session.getInstance().autoLogin = null;
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.overridePendingTransition(0, 0);
                    activity.finish();
                } else {
                    Toast.makeText(activity, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public static void patchRecovery(final Activity activity, final String username) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    Faker faker = FakerTool.getInstance();
                    return true;
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.patchRecovery(username).execute().isSuccessful();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                dialog.dismiss();
                if (result != null) {
                    Session.getInstance().login = new Login();
                    Session.getInstance().login.setUsername(username);
                    activity.onBackPressed();
                } else {
                    Toast.makeText(activity, "Username does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


}
