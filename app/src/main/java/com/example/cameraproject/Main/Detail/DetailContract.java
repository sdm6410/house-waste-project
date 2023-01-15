package com.example.cameraproject.Main.Detail;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.cameraproject.Main.MainContract;

public interface DetailContract {
    interface View{
        void setPageView(int index);
    }
    interface Presenter{
        void attachView(DetailContract.View view);
        void initialize(Context context, MainContract.fragmentCallback callback);
        void setFragment(Context context, int index);
        void handleFragmentCallback(Context context, String msg, Fragment fragment);
    }

    interface fragmentCallback{
    }
}
