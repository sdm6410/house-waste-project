package com.example.cameraproject.Address;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

public interface AddressContract {
    interface View{
        void finishActivityView();
        void setHolderVisibility(boolean visibility);

    }
    interface Presenter{
        void attachView(AddressContract.View view);
        void initialize(Activity activity);
        void finishActivity(Context context);
        void onClickViewEvent(Context context);
        void onClickItemAddActivtivity(Context context,String load, String detail);


    }

}
