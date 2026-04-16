package com.svalero.bstronggym.api;
import com.svalero.bstronggym.domain.Activity;
import com.svalero.bstronggym.domain.Booking;
import com.svalero.bstronggym.domain.Member;
import com.svalero.bstronggym.domain.Monitor;
import com.svalero.bstronggym.domain.Subscription;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ApiService {
    // ---- MEMBERS ----
    @GET("members")
    Call<List<Member>> getMembers();

    @GET("members")
    Call<List<Member>> getMembersByName(@Query("firstName") String firstName);

    @GET("members/{id}")
    Call<Member> getMember(@Path("id") long id);

    @POST("members")
    Call<Member> createMember(@Body Member member);

    @PUT("members/{id}")
    Call<Member> updateMember(@Path("id") long id, @Body Member member);

    @DELETE("members/{id}")
    Call<Void> deleteMember(@Path("id") long id);

    // ---- ACTIVITIES ----
    @GET("activities")
    Call<List<Activity>> getActivities();

    @GET("activities")
    Call<List<Activity>> getActivitiesByName(@Query("name") String name);

    @GET("activities/{id}")
    Call<Activity> getActivity(@Path("id") long id);

    @POST("activities")
    Call<Activity> createActivity(@Body Activity activity);

    @PUT("activities/{id}")
    Call<Activity> updateActivity(@Path("id") long id, @Body Activity activity);

    @DELETE("activities/{id}")
    Call<Void> deleteActivity(@Path("id") long id);

    // ---- BOOKINGS ----
    @GET("bookings")
    Call<List<Booking>> getBookings();

    @GET("bookings")
    Call<List<Booking>> getBookingsByMember(@Query("memberId") long memberId);

    @GET("bookings/{id}")
    Call<Booking> getBooking(@Path("id") long id);

    @POST("bookings")
    Call<Booking> createBooking(@Body Booking booking);

    @PUT("bookings/{id}")
    Call<Booking> updateBooking(@Path("id") long id, @Body Booking booking);

    @DELETE("bookings/{id}")
    Call<Void> deleteBooking(@Path("id") long id);

    // ---- MONITORS ----
    @GET("monitors")
    Call<List<Monitor>> getMonitors();

    @GET("monitors")
    Call<List<Monitor>> getMonitorsByName(@Query("name") String name);

    @GET("monitors/{id}")
    Call<Monitor> getMonitor(@Path("id") long id);

    @POST("monitors")
    Call<Monitor> createMonitor(@Body Monitor monitor);

    @PUT("monitors/{id}")
    Call<Monitor> updateMonitor(@Path("id") long id, @Body Monitor monitor);

    @DELETE("monitors/{id}")
    Call<Void> deleteMonitor(@Path("id") long id);

    // ---- SUBSCRIPTIONS ----
    @GET("subscriptions")
    Call<List<Subscription>> getSubscriptions();

    @GET("subscriptions")
    Call<List<Subscription>> getSubscriptionsByMember(@Query("memberId") long memberId);

    @GET("subscriptions")
    Call<List<Subscription>> getSubscriptionsByType(@Query("type") String type);

    @GET("subscriptions/{id}")
    Call<Subscription> getSubscription(@Path("id") long id);

    @POST("subscriptions")
    Call<Subscription> createSubscription(@Body Subscription subscription);

    @PUT("subscriptions/{id}")
    Call<Subscription> updateSubscription(@Path("id") long id, @Body Subscription subscription);

    @DELETE("subscriptions/{id}")
    Call<Void> deleteSubscription(@Path("id") long id);
}
