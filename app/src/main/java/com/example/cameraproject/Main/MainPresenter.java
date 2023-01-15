package com.example.cameraproject.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;

import com.example.cameraproject.Util.Permission;

public class MainPresenter implements  MainContract.Presenter{
    private MainContract.View view;


    @Override
    public void attachView(MainContract.View view) { this.view = view;
    }

    @Override
    public void initialize(Activity activity) {
        // permission check
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(Permission.getInstance().checkPermission(activity)){

            }
        }
        else{

        }


    }

    @Override
    public void handleFragmentCallback(Context context, String msg, Activity activity) {

    }


    @Override
    public void setFragment(Context context, int index) {
        view.setHolderView(index);
        view.setFragmentView(index);

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {

    }
}
