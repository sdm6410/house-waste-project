package com.example.cameraproject.Util;

import android.content.Context;
import android.content.Intent;

import com.example.cameraproject.Address.AddressActivity;
import com.example.cameraproject.CategorySelect.CategorySelectActivity;
import com.example.cameraproject.ItemAdd.ItemAddActivity;
import com.example.cameraproject.Main.MainActivity;
import com.example.cameraproject.Util.View.DaumWebViewActivity;

public class StartActivityManager {

    private static StartActivityManager instance;

    public static StartActivityManager getInstance(){
        if(instance == null)
            instance = new StartActivityManager();
        return instance;
    }


    public void startDaumWebViewActivity(Context context){
        Intent intent = new Intent(context, DaumWebViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public void startAddressActivity(Context context){
        Intent intent = new Intent(context, AddressActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public void startMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public void startItemAddActivity(Context context,String load, String detail){
        Intent intent = new Intent(context,ItemAddActivity.class);
        intent.putExtra("load",load);
        intent.putExtra("detail",detail);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void startCategorySelectActivity(Context context){
        Intent intent = new Intent(context, CategorySelectActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
