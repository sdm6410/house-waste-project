package com.example.cameraproject.Login.nonMembers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public interface nonMembersContract {
    interface View{

    }
    interface Presenter{
        void attachView(nonMembersContract.View view);
        void initialize(Context context);
        void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data);
        void onClickMain(Activity activity);
    }
}
