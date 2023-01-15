package com.example.cameraproject.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public interface LoginContract {
    interface View{
        void setFragmentView(int index);
        void setHolderView(int index);
    }

    interface Presenter{
        void attachView(LoginContract.View view);
        void initialize(Activity activity);
        void handleFragmentCallback(Context context, String msg, Activity activity);
        void setFragment(Context context, int index);
        void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data);
    }
    interface fragmentCallback{
        void onFragmentCallback(String msg);
    }

}
