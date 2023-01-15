package com.example.cameraproject.Data.Account;

import android.content.Context;
import android.content.SharedPreferences;

public class AccountDataRepo {

    private static AccountDataRepo instance;
    private AccountDataModel data = new AccountDataModel();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static AccountDataRepo getInstance(){
        if(instance == null)
            instance = new AccountDataRepo();

        return instance;
    }

    public AccountDataModel getData() {
        return this.data;
    }

    public void setData(AccountDataModel data) {
        this.data = data;
    }

    public void setToken(Context context, String token){
        sharedPreferences = context.getSharedPreferences("token",0);
        editor = sharedPreferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public String getToken(Context context){
        sharedPreferences = context.getSharedPreferences("token",0);
        return   sharedPreferences.getString("token","");
    }




    public void setLatitude(Context context,float latitude){
        sharedPreferences = context.getSharedPreferences("location",0);
        editor = sharedPreferences.edit();
        editor.putFloat("latitude",latitude);
        editor.commit();
    }

    public float getLatitude(Context context){
        sharedPreferences = context.getSharedPreferences("location",0);
        return   sharedPreferences.getFloat("latitude",37);
    }

    public void setLongitude(Context context,float longitude){
        sharedPreferences = context.getSharedPreferences("location",0);
        editor = sharedPreferences.edit();
        editor.putFloat("longitude",longitude);
        editor.commit();
    }

    public float getLongitude(Context context){
        sharedPreferences = context.getSharedPreferences("location",0);
        return   sharedPreferences.getFloat("longitude",127);
    }

}
