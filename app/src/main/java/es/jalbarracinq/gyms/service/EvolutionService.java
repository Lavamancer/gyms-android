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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.fragment.EvolutionFragment;
import es.jalbarracinq.gyms.model.Evolution;
import es.jalbarracinq.gyms.model.response.EvolutionResponse;
import es.jalbarracinq.gyms.service.tool.FakerTool;
import es.jalbarracinq.gyms.service.tool.ProgressBarTool;
import es.jalbarracinq.gyms.service.tool.RetrofitTool;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EvolutionService {


    public static void getEvolution(final EvolutionFragment evolutionFragment, final Long evolutionId) {
        new AsyncTask<Void, Void, EvolutionResponse>() {
            @Override
            protected EvolutionResponse doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new EvolutionResponse(FakerTool.getInstance(), 1);
                } else {
                    try {
                        if (evolutionId != null) {
                            return RetrofitTool.getInstance().apiRepository.getEvolution(Session.getInstance().token.getAccessToken(), evolutionId).execute().body();
                        } else {
                            return RetrofitTool.getInstance().apiRepository.getCurrentEvolution(Session.getInstance().token.getAccessToken(), Session.getInstance().user.getId()).execute().body();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(EvolutionResponse result) {
                if (result != null ) {
                    evolutionFragment.updateEvolution(result.getEvolution(), result.getGoal());
                }
            }
        }.execute();
    }

    public static void getEvolutions(final Activity activity, final BaseAdapter adapter, final ListView listView, final List<Evolution> list) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, List<Evolution>>() {
            @Override
            protected List<Evolution> doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return Evolution.mocks(FakerTool.getInstance(), 10);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.getEvolutions(Session.getInstance().token.getAccessToken(), Session.getInstance().user.getId()).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Evolution> result) {
                dialog.dismiss();
                if (result != null ) {
                    list.clear();
                    list.addAll(result);
                    adapter.notifyDataSetChanged();
                    listView.invalidateViews();
                }
            }
        }.execute();
    }


    public static void updateImageFrontal(final Activity activity, final byte[] image, final Long evolutionId, final ImageView imageView) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), image);
        final MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestBody);
        final RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image");

        new AsyncTask<Void, Void, Evolution>() {
            @Override
            protected Evolution doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new Evolution(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.postEvolutionFrontal(Session.getInstance().token.getAccessToken(), evolutionId, body, name).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Evolution result) {
                dialog.dismiss();
                if (result != null) {
                    Glide.with(activity).load(result.getFrontal().getUrl()).into(imageView);
                } else {
                    Toast.makeText(activity, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public static void updateImageSide(final Activity activity, final byte[] image, final Long evolutionId, final ImageView imageView) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), image);
        final MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestBody);
        final RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image");

        new AsyncTask<Void, Void, Evolution>() {
            @Override
            protected Evolution doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    new Evolution(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.postEvolutionSide(Session.getInstance().token.getAccessToken(), evolutionId, body, name).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Evolution result) {
                dialog.dismiss();
                if (result != null) {
                    Glide.with(activity).load(result.getFrontal().getUrl()).into(imageView);
                } else {
                    Toast.makeText(activity, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public static void updateImageBack(final Activity activity, final byte[] image, final Long evolutionId, final ImageView imageView) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), image);
        final MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestBody);
        final RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image");

        new AsyncTask<Void, Void, Evolution>() {
            @Override
            protected Evolution doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new Evolution(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.postEvolutionBack(Session.getInstance().token.getAccessToken(), evolutionId, body, name).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Evolution result) {
                dialog.dismiss();
                if (result != null) {
                    Glide.with(activity).load(result.getFrontal().getUrl()).into(imageView);
                } else {
                    Toast.makeText(activity, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
