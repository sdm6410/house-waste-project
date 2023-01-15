package com.example.cameraproject.Address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.cameraproject.Util.StartActivityManager;

public class AddressPresenter implements AddressContract.Presenter {

    private AddressContract.View view;

    @Override
    public void attachView(AddressContract.View view) {
        this.view = view;
    }

    @Override
    public void initialize(Activity activity) {

    }

    @Override
    public void finishActivity(Context context) {
        view.finishActivityView();
    }

    @Override
    public void onClickViewEvent(Context context) {
        if(true){
            view.setHolderVisibility(true);
        }else {
            view.setHolderVisibility(false);
        }

    }


    @Override
    public void onClickItemAddActivtivity(Context context,String load, String detail) {
        StartActivityManager.getInstance().startItemAddActivity(context,load,detail);
    }


}
