package com.example.cameraproject.Login.nonMembers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.cameraproject.Main.MainActivity;
import com.example.cameraproject.Util.StartActivityManager;

public class nonMembersPresenter implements nonMembersContract.Presenter {

    private nonMembersContract.View view;
    @Override
    public void attachView(nonMembersContract.View view) {
        this.view =view;
    }

    @Override
    public void initialize(Context context) {

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {

    }

    @Override
    public void onClickMain(Activity activity) {
        StartActivityManager.getInstance().startMainActivity(activity);

    }
}
