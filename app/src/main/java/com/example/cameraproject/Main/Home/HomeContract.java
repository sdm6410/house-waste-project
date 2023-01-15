package com.example.cameraproject.Main.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public interface HomeContract {
    interface View{

    }

    interface Presenter{
        void attachView(HomeContract.View view);
        void initialize(Context context);
        void onClickService(Activity activity);

    }
}
