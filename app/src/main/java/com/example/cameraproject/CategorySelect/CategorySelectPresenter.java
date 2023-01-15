package com.example.cameraproject.CategorySelect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.cameraproject.Util.EventBus.Category.CategoryBusEvent;
import com.example.cameraproject.Util.EventBus.Category.CategoryBusProvider;
import com.example.cameraproject.Util.StartActivityManager;
import com.squareup.otto.Subscribe;

public class CategorySelectPresenter implements CategorySelectContract.Presenter{

    private CategorySelectContract.View view;

    @Override
    public void attachView(CategorySelectContract.View view) {
        this.view = view;
    }

    @Override
    public void initialize(Activity activity) {
    }

}
