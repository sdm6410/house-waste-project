package com.example.cameraproject.Util;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.cameraproject.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

public class PhotoManager {

    public static final int PICK_FROM_CAMERA = 0;
    public static final int PICK_FROM_ALBUM = 1;

    public static final String SAMPLE_CROPPED_IMAGE_NAME = ".png";

    public static final int RATIO_ORIGIN = 0;
    public static final int RATIO_SQUARE = 1;
    public static final int RATIO_DYNAMIC = 2;
    public static final int RATIO_CUSTOM = 3;

    public static final int FORMAT_PNG = 0;
    public static final int FORMAT_WEBP = 1;
    public static final int FORMAT_JPEG = 2;
    public static Uri mDestinationUri,mImageCaptureUri;
    public static String mCurrentPhotoPath;

    private static PhotoManager instance;

    public static PhotoManager getInstance(){

        if(instance == null)
            instance = new PhotoManager();

        return instance;
    }

    public Uri getmImageCaptureUri(){
        return mImageCaptureUri;
    }


    public void pickFromPhoto(Activity activity) // 카메라 촬영 후 이미지 가져오기
    {
        mDestinationUri = Uri.fromFile(new File(activity.getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME));

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
        mImageCaptureUri = FileProvider.getUriForFile(activity, "com.example.cameraproject.fileprovider", (new File(Environment.getExternalStorageDirectory(),url)));

        if ( Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP ) {
            intent.setClipData( ClipData.newRawUri( "", mImageCaptureUri ) );
            intent.addFlags( Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION );
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        activity.startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void pickFormAlbum(Activity activity) { // 앨범에서 이미지 가져오기

        mDestinationUri = Uri.fromFile(new File(activity.getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME));


        Intent intent = new Intent();

        if ( Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP ) {
            intent.setClipData( ClipData.newRawUri( "", mImageCaptureUri ) );
            intent.addFlags( Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION );
        }

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_ALBUM);
    }


    public void startCropActivity(Activity activity,@NonNull Uri uri) {

        UCrop uCrop = UCrop.of(uri, mDestinationUri);

        uCrop = _setRatio(uCrop, RATIO_ORIGIN, 0, 0);

        //화질
        uCrop = _setSize(uCrop, 1024, 1024);

        //파일 종류 / 퀄리티
        uCrop = _advancedConfig(activity,uCrop, FORMAT_PNG, 100);

        uCrop.start(activity);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes;
        bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title" + UUID.randomUUID().toString(), null);
        return Uri.parse(path);
    }

    public void startCropActivity(Activity activity, @NonNull Bitmap image) {
        Uri uri = getImageUri(activity, image);
        startCropActivity(activity, uri);
    }


    private UCrop _setRatio(@NonNull UCrop uCrop, int choice, float xratio, float yratio){
        switch (choice) {
            case RATIO_ORIGIN:
                uCrop = uCrop.useSourceImageAspectRatio();
                break;
            case RATIO_SQUARE:
                uCrop = uCrop.withAspectRatio(1, 1);
                break;
            case RATIO_DYNAMIC:
                // do nothing
                break;
            case RATIO_CUSTOM:
            default:
                try {
                    float ratioX = xratio;
                    float ratioY = yratio;
                    if (ratioX > 0 && ratioY > 0) {
                        uCrop = uCrop.withAspectRatio(ratioX, ratioY);
                    }
                } catch (NumberFormatException e) {
                }
                break;
        }

        return uCrop;

    }

    private UCrop _setSize(@NonNull UCrop uCrop, int maxWidth, int maxHeight){
        if(maxWidth > 0 && maxHeight > 0){
            return uCrop.withMaxResultSize(maxWidth, maxHeight);
        }
        return uCrop;
    }

    private UCrop _advancedConfig(Activity activity,@NonNull UCrop uCrop, int format, int quality) {
        UCrop.Options options = new UCrop.Options();


        switch (format) {
            case FORMAT_PNG:
                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                break;
            case FORMAT_WEBP:
                options.setCompressionFormat(Bitmap.CompressFormat.WEBP);
                break;
            case FORMAT_JPEG:
            default:
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                break;
        }

        options.setCompressionQuality(quality); // range [0-100]

        options.setAllowedGestures(0,0, UCropActivity.SCALE);
        //options.setToolbarTitle("사진 편집");

        options.setToolbarColor(ContextCompat.getColor(activity, R.color.main_color));
        options.setStatusBarColor(ContextCompat.getColor(activity, R.color.main_color));
        options.setActiveWidgetColor(ContextCompat.getColor(activity, R.color.main_color));

        return uCrop.withOptions(options);
    }
}
