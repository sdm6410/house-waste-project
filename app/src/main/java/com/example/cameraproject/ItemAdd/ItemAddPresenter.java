package com.example.cameraproject.ItemAdd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.cameraproject.API.List.UploadImageAPI;
import com.example.cameraproject.CategorySelect.CategorySelectActivity;
import com.example.cameraproject.Util.Dialog.PhotoDialog;
import com.example.cameraproject.Util.LoadingProgressDialog;
import com.example.cameraproject.Util.PhotoManager;
import com.example.cameraproject.Util.StartActivityManager;
import com.example.cameraproject.Util.TensorFlow.Classifier;
import com.example.cameraproject.Util.TensorFlow.TensorFlowImageClassifier;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.app.Activity.RESULT_OK;
import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;
import static com.example.cameraproject.Util.PhotoManager.PICK_FROM_ALBUM;
import static com.example.cameraproject.Util.PhotoManager.PICK_FROM_CAMERA;
import static com.yalantis.ucrop.UCrop.REQUEST_CROP;
import static com.yalantis.ucrop.UCrop.RESULT_ERROR;


public class ItemAddPresenter implements ItemAddContract.Presenter {
    private ItemAddContract.View view;
    private ArrayList<Bitmap> image = new ArrayList<>();
    private int currentBitmapPosition  =0 ;
    private int furnitureNumber = 1;

    // tensorflow 변수 설정
    private static final String MODEL_PATH = "converterMobilenetV2.tflite";
    private static final boolean QUANT = true;
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;
    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void attachView(ItemAddContract.View view) {
        this.view = view;
    }

    @Override
    public void initialize(Activity activity) {
        view.setAddressView(activity.getIntent().getStringExtra("load") + activity.getIntent().getStringExtra("detail"));
        view.setChangeNumber(furnitureNumber);
        view.setCategory(activity.getIntent().getStringExtra("categoryTitle") + activity.getIntent().getStringExtra("categoryContent"));
    }

    @Override
    public void finishActivity(Context context) {
        view.finishActivity();
    }

    @Override
    public void handleFragmentCallback(Context context, String msg, Activity activity) {

    }

    @Override
    public void setFragment(Context context, int index) {

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Log.i("WritePostPresenter", "Request code: " + requestCode);
            switch (requestCode){
                case PICK_FROM_CAMERA :
                    if(PhotoManager.getInstance().getmImageCaptureUri() != null)
                        PhotoManager.getInstance().startCropActivity(activity,PhotoManager.getInstance().getmImageCaptureUri());
                    else
                        Toast.makeText(activity, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                case PICK_FROM_ALBUM :
                    Uri selectedUri = data.getData();
                    if (selectedUri != null)
                        PhotoManager.getInstance().startCropActivity(activity,selectedUri);
                    else
                        Toast.makeText(activity, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    break;

                case REQUEST_CROP :
                    Uri result = UCrop.getOutput(data);

                    if (result != null) {
                        try {
                            UploadImageAPI.getInstance().call(activity, image, new UploadImageAPI.uploadCallback() {
                                @Override
                                public void uploadCallbackMethod(boolean isSuccessful, JSONArray jsonArray) {
                                    if(isSuccessful){
                                        final ArrayList<String> images = new ArrayList<>();

                                        try {
                                            images.add(jsonArray.getString(0));

                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                    }else {
                                        LoadingProgressDialog.getInstance().dismiss();
                                    }
                                }
                            });
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), result);
// TODO 여기서 이미지 분류 작동 후 textView 이동
//                            //TODO Bitmap -> byteArray
                            Intent intent = new Intent(activity, CategorySelectActivity.class);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 5, stream);
                            byte[] byteArray = stream.toByteArray();
                            Log.d("LDK", "byteArray: "+ byteArray);
                            intent.putExtra("bytearray", byteArray);
                            activity.startActivity(intent);
                            //
//                            Log.d("LDK", "bitmap.getHeight: "+bitmap.getHeight());
//                            int bytes = bitmap.getByteCount();
//                            ByteBuffer buffer = ByteBuffer.allocate(bytes);
//                            bitmap.copyPixelsFromBuffer(buffer);
//                            byte[] array = buffer.array();
                            try{
                                Bitmap resized = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
                                Log.d("LDK", "resized: "+resized);

                                final List<Classifier.Recognition> results = classifier.recognizeImage(resized);
                                Log.i("LDK", "onActivityResult: "+results.toString());
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }

                            //StartActivityManager.getInstance().startCategorySelectActivity(activity);
                            image.add(bitmap);


                            // bitmap 이미지 저장
                            view.setImageView(image,currentBitmapPosition);
                            view.setItemAddHolder();



                        } catch (IOException e) {
                            Toast.makeText(activity, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(activity, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case RESULT_ERROR :
                    Toast.makeText(activity, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }




    @Override
    public void onClickItemAddActivty(Activity activity) {
        showPhotoDialog(activity);
    }

    @Override
    public void onClickAddPlusBtn(Context context) {
        furnitureNumber++;
        view.setChangeNumber(furnitureNumber);

    }

    @Override
    public void onClickAddMinusBtn(Context context) {
        if(furnitureNumber <= 1){
            Toast.makeText(context,"더이상 버튼을 누를수 없습니다.",Toast.LENGTH_SHORT).show();
            furnitureNumber = 1;
        }else {
            furnitureNumber--;
            view.setChangeNumber(furnitureNumber);
        }

    }

    @Override
    public void onClickCameraImageClick(Activity activity) {
        showPhotoDialog(activity);
    }

    private void showPhotoDialog(final Activity activity){

        final PhotoDialog uploadDialog = new PhotoDialog(activity);
        uploadDialog.setCallback(new PhotoDialog.Callback() {
            @Override
            public void cameraCallback() {
                PhotoManager.getInstance().pickFromPhoto(activity);
                uploadDialog.dismiss();

            }

            @Override
            public void albumCallback() {
                PhotoManager.getInstance().pickFormAlbum(activity);
                uploadDialog.dismiss();
            }
            @Override
            public void cancelCallback() {
                uploadDialog.dismiss();
            }

        });
        uploadDialog.show();

    }


    public Bitmap getScaledBitmap(Uri fileUri){
        Bitmap scaledPhoto = null;

        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath());

            ExifInterface ei = new ExifInterface(fileUri.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
            }
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inPurgeable = true;

            scaledPhoto = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledPhoto;
    }


}
