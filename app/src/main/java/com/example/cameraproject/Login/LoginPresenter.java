package com.example.cameraproject.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void initialize(Activity activity) {

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
