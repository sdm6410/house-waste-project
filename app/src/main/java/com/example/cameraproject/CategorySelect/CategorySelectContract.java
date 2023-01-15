package com.example.cameraproject.CategorySelect;

import android.app.Activity;
import android.content.Context;

public interface CategorySelectContract {
    interface View{

    }
    interface Presenter{
        void attachView(CategorySelectContract.View view);
        void initialize(Activity activity);

    }
}
