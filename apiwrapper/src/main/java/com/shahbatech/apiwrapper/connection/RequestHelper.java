package com.shahbatech.apiwrapper.connection;

import retrofit2.Call;
import retrofit2.Callback;

public class RequestHelper<T> {

    public Call<T> call;
    public Callback<T> callback;

    public RequestHelper(Callback<T> callback, Call<T> call) {
        this.call = call;
        this.callback = callback;
    }
}
