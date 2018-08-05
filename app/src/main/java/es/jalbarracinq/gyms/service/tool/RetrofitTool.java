/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;
import es.jalbarracinq.gyms.repository.ApiRepository;
import es.jalbarracinq.gyms.repository.deserializer.DateTimeDeserializer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTool {

    private static final String BASE_URL = "http://localhost:8080/";
    private static RetrofitTool instance;
    public ApiRepository apiRepository;


    private RetrofitTool() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeDeserializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);
        builder.client(httpClient.build());
        Retrofit retrofit = builder.addConverterFactory(GsonConverterFactory.create(gson)).build();
        apiRepository = retrofit.create(ApiRepository.class);
    }

    public static RetrofitTool getInstance() {
        if (instance == null) instance = new RetrofitTool();
        return instance;
    }

}
