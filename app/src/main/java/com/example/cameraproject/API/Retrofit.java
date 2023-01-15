package com.example.cameraproject.API;

import android.content.Context;

import com.example.cameraproject.Util.CheckNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Retrofit {

    private static Retrofit instance;

    public interface callback{
        void onResponseCallback(Response<String> response);
        void onFailureCallback();
        void onNetworkDisableCallback();
    }

    public static Retrofit getInstance(){
        if(instance == null)
            instance = new Retrofit();

        return instance;
    }

    public void Enqueue(Context context, Call<String> call, final callback callback){

        if(CheckNetwork.getInstance().isNetworkAvailable(context)){
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    callback.onResponseCallback(response);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    callback.onFailureCallback();
                }
            });

        }
        else
            callback.onNetworkDisableCallback();
    }
}
