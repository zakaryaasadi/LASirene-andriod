package com.shahbatech.apiwrapper.connection;


import com.shahbatech.apiwrapper.model.Booking;
import com.shahbatech.apiwrapper.model.BookingsList;
import com.shahbatech.apiwrapper.model.Customer;
import com.shahbatech.apiwrapper.model.NewsList;
import com.shahbatech.apiwrapper.model.ProductList;
import com.shahbatech.apiwrapper.model.ServiceList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.GET;

public interface ApiClient {

    @GET("services/gents/{ln}/{page}")
    Call<ServiceList> getGentsServices(@Path("ln") int language, @Path("page") int page);

    @GET("services/ladies/{ln}/{page}")
    Call<ServiceList> getLadiesServices(@Path("ln") int language, @Path("page") int page);

    @GET("products/{service_id}/{ln}/{page}")
    Call<ProductList> getProducts(@Path("service_id") int serviceId, @Path("ln") int language, @Path("page") int page);

    @GET("news/{ln}/{page}")
    Call<NewsList> getNews(@Path("ln") int language, @Path("page") int page);

    @GET("customers/login/{email}/{password}")
    Call<Customer> login(@Path("email") String email, @Path("password") String password);

    @GET("customers/change/{id}/{email}/{password}/{full_name}/{phone}")
    Call<Customer> change(@Path("id") int id,
                          @Path("email") String email, @Path("password") String password,
                          @Path("full_name") String fullName, @Path("phone") String phone);

    @GET("customers/signup/{full_name}/{email}/{password}/{phone}/{gender}/{date_of_birth}")
    Call<Customer> signUp(@Path("full_name") String fullName,
                          @Path("email") String email, @Path("password") String password,
                          @Path("phone") String phone, @Path("gender") int gender,
                          @Path("date_of_birth") String dateOfBirth);

    @POST("booking")
    Call<Booking> addBooking(@Body Booking booking);


    @GET("booking/{customer_id}/{page}")
    Call<BookingsList> getBookings(@Path("customer_id") int customerId, @Path("page") int page);

}
