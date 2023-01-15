package com.example.cameraproject.ItemAdd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.example.cameraproject.Address.AddressContract;

import java.util.ArrayList;

public interface ItemAddContract {
    interface View{
        void setFragmentView(int index);
        void setHolderView(int index);
        void setAddressView(String address);
        void finishActivity();
        void setImageView(ArrayList<Bitmap> image, int currentIndex);
        void setChangeNumber(int number);
        void setCategory(String category);
        void setItemAddHolder();
    }
    interface Presenter{
        void attachView(ItemAddContract.View view);
        void initialize(Activity activity);
        void finishActivity(Context context);
        void handleFragmentCallback(Context context, String msg, Activity activity);
        void setFragment(Context context, int index);
        void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data);
        void onClickItemAddActivty(Activity activity);
        void onClickAddPlusBtn(Context context);
        void onClickAddMinusBtn(Context context);
        void onClickCameraImageClick(Activity activity);

    }

    interface fragmentCallback {
        void onFragmentCallback(String msg);
    }
}
