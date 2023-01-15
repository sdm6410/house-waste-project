package com.example.cameraproject.Login.members;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public interface membersContract {

    interface View{

    }
    interface Presenter{
        void attachView(membersContract.View view);
        void initialize(Context context);
        void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data);
    }
}
