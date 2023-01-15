package com.example.cameraproject.Main.Apply;

import android.content.Context;

import com.example.cameraproject.Main.MainContract;

public interface ApplyContract {
    interface View{

    }

    interface Presenter{
        void attachView(ApplyContract.View view);
        void initialize(Context context, MainContract.fragmentCallback callback);


    }
}
