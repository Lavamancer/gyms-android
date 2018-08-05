/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.repository;

import org.joda.time.DateTime;

import java.util.List;

import es.jalbarracinq.gyms.model.Card;
import es.jalbarracinq.gyms.model.Evolution;
import es.jalbarracinq.gyms.model.Facility;
import es.jalbarracinq.gyms.model.News;
import es.jalbarracinq.gyms.model.Payment;
import es.jalbarracinq.gyms.model.Reservation;
import es.jalbarracinq.gyms.model.Subscriber;
import es.jalbarracinq.gyms.model.Subscription;
import es.jalbarracinq.gyms.model.SubscriptionType;
import es.jalbarracinq.gyms.model.response.CalendarResponse;
import es.jalbarracinq.gyms.model.response.EvolutionResponse;
import es.jalbarracinq.gyms.model.response.LessonResponse;
import es.jalbarracinq.gyms.model.response.LoginResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRepository {

    @POST("/public/subscribers/register")
    @FormUrlEncoded
    Call<Void> postRegister(@Field("email") String email);

    @POST("/public/subscribers/login")
    @FormUrlEncoded
    Call<LoginResponse> postLogin(@Field("username") String username,
                                  @Field("password") String password);

    @PATCH("/public/subscribers/recovery")
    @FormUrlEncoded
    Call<Void> patchRecovery(@Field("email") String email);

    @GET("/api/facilities/{facilityId}/calendar")
    Call<CalendarResponse> getCalendar(@Header("Authorization") String token, @Path("facilityId") Long facilityId);

    @GET("/api/lessons/{lessonId}/details")
    Call<LessonResponse> getLesson(@Header("Authorization") String token, @Path("lessonId") Long lessonId);

    @PATCH("/api/subscribers")
    Call<Subscriber> patchSubscribers(@Header("Authorization") String token, @Body Subscriber subscriber);

    @GET("/api/lessons/{lessonId}/reservations")
    Call<List<Reservation>> getReservations(@Header("Authorization") String token, @Path("lessonId") Long lessonId);

    @POST("/api/lessons/{lessonId}/reservations")
    @FormUrlEncoded
    Call<Reservation> postReservations(@Header("Authorization") String token, @Path("lessonId") Long lessonId, @Field("subscriptionId") Long subscriptionId, @Field("cardId") Long cardId);

    @DELETE("/api/reservations/{reservationId}")
    Call<Void> deleteReservations(@Header("Authorization") String token, @Path("reservationId") Long reservationId);

    @GET("/api/news")
    Call<List<News>> getNews(@Header("Authorization") String token);

    @GET("/api/lesson-types/{lessonTypeId}/facilities")
    Call<List<Facility>> getFacilitiesByLessonType(@Header("Authorization") String token, @Path("lessonTypeId") Long lessonTypeId);

    @GET("/api/subscribers/{subscriberId}/evolutions")
    Call<List<Evolution>> getEvolutions(@Header("Authorization") String token, @Path("subscriberId") Long subscriberId);

    @GET("/api/subscribers/{subscriberId}/evolutions/current")
    Call<EvolutionResponse> getCurrentEvolution(@Header("Authorization") String token, @Path("subscriberId") Long subscriberId);

    @GET("/api/evolutions/{evolutionId}")
    Call<EvolutionResponse> getEvolution(@Header("Authorization") String token, @Path("evolutionId") Long evolutionId);

    @GET("/api/subscriptions")
    Call<List<Subscription>> getSubscriptions(@Header("Authorization") String token, @Query("orderByStart") Boolean orderByStart, @Query("actives") Boolean actives);

    @GET("/api/subscription-types")
    Call<List<SubscriptionType>> getSubscriptionTypes(@Header("Authorization") String token);

    @PATCH("/api/subscriptions/{subscriptionId}/pause")
    @FormUrlEncoded
    Call<Subscription> patchSubscriptionPause(@Header("Authorization") String token,
                                                        @Path("subscriptionId") Long subscriptionId,
                                                        @Field("date") DateTime date);

    @POST("/api/evolutions/{evolutionId}/frontal")
    @Multipart
    Call<Evolution> postEvolutionFrontal(@Header("Authorization") String token,
                                         @Path("evolutionId") Long evolutionId,
                                         @Part MultipartBody.Part image,
                                         @Part("image") RequestBody name);

    @POST("/api/evolutions/{evolutionId}/side")
    @Multipart
    Call<Evolution> postEvolutionSide(@Header("Authorization") String token,
                                      @Path("evolutionId") Long evolutionId,
                                      @Part MultipartBody.Part image,
                                      @Part("image") RequestBody name);

    @POST("/api/evolutions/{evolutionId}/back")
    @Multipart
    Call<Evolution> postEvolutionBack(@Header("Authorization") String token,
                                      @Path("evolutionId") Long evolutionId,
                                      @Part MultipartBody.Part image,
                                      @Part("image") RequestBody name);

    @POST("/api/cards")
    Call<Card> postCard(@Header("Authorization") String token, @Body Card card);

    @POST("/api/subscription-types/{subscriptionTypeId}/subscriptions")
    @FormUrlEncoded
    Call<Payment> postSubscription(@Header("Authorization") String token,
                              @Path("subscriptionTypeId") Long subscriptionTypeId,
                              @Field("start") DateTime start,
                              @Field("cardId") Long cardId);

    @POST("/api/subscribers/image")
    @Multipart
    Call<Subscriber> postSubscriberImage(@Header("Authorization") String token,
                                         @Part MultipartBody.Part image,
                                         @Part("image") RequestBody name);

}
