package com.example.cameraproject.Login.members;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public class membersPresenter implements membersContract.Presenter {
    private membersContract.View view;

    @Override
    public void attachView(membersContract.View view) {
        this.view = view;
    }

    @Override
    public void initialize(Context context) {

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {

    }
}
