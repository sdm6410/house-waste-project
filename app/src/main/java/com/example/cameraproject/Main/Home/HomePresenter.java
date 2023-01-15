package com.example.cameraproject.Main.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import com.example.cameraproject.Util.Dialog.PhotoDialog;
import com.example.cameraproject.Util.PhotoManager;
import com.example.cameraproject.Util.StartActivityManager;
import com.yalantis.ucrop.UCrop;


import androidx.annotation.Nullable;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.example.cameraproject.Util.PhotoManager.PICK_FROM_ALBUM;
import static com.example.cameraproject.Util.PhotoManager.PICK_FROM_CAMERA;
import static com.example.cameraproject.Util.PhotoManager.mImageCaptureUri;
import static com.yalantis.ucrop.UCrop.REQUEST_CROP;
import static com.yalantis.ucrop.UCrop.RESULT_ERROR;

public class HomePresenter implements HomeContract.Presenter{
    private HomeContract.View view;

    @Override
    public void attachView(HomeContract.View view) {
        this.view =view;
    }

    @Override
    public void initialize(Context context) {


    }

    @Override
    public void onClickService(Activity activity) {
        StartActivityManager.getInstance().startAddressActivity(activity);
    }


}
