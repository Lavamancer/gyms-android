package es.jalbarracinq.gyms.service;

import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.model.News;
import es.jalbarracinq.gyms.service.tool.FakerTool;
import es.jalbarracinq.gyms.service.tool.RetrofitTool;

public class NewsService {


    public static void getNews(final BaseAdapter adapter, final ListView listView, final List<News> list) {
        new AsyncTask<Void, Void, List<News>>() {
            @Override
            protected List<News> doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return News.mocks(FakerTool.getInstance(), 20);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.getNews(Session.getInstance().token.getAccessToken()).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<News> result) {
                if (result != null) {
                    list.clear();
                    list.addAll(result);
                    adapter.notifyDataSetChanged();
                    listView.invalidateViews();
                }
            }
        }.execute();
    }
}
