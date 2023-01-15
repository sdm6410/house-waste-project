package com.example.cameraproject.API.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import com.example.cameraproject.API.APIClient;
import com.example.cameraproject.API.APIInterface;
import com.example.cameraproject.API.Retrofit;
import com.example.cameraproject.Data.Account.AccountDataRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class UploadImageAPI {
    private static UploadImageAPI instance;

    public static UploadImageAPI getInstance(){

        if(instance == null)
            instance = new UploadImageAPI();

        return instance;
    }

    public interface uploadCallback{
        void uploadCallbackMethod(boolean isSuccessful, JSONArray jsonArray);
    }

    public void call(final Context context, ArrayList<Bitmap> bitmap, final uploadCallback callback){

        //이미지가 없을경우 null 리턴.
        if(bitmap.size() == 0){
            callback.uploadCallbackMethod(true,new JSONArray());
            return;
        }

        try {

            MultipartBody.Part[] fileData = new MultipartBody.Part[bitmap.size()];

            //통신시작
            APIInterface apiInterface = APIClient.getBaseClient().create(APIInterface.class);
            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


            for(int i=0;i<bitmap.size();i++){

                //Bitmap -> PNG
                Random random = new Random();
                String name = System.currentTimeMillis() + random.nextInt(10000) + ".png";

                File photo = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath(), name);
                FileOutputStream fileOutputStream = new FileOutputStream(photo);
                bitmap.get(i).compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

                fileData[i] = MultipartBody.Part.createFormData("file", name, RequestBody.create(MEDIA_TYPE_PNG, photo));

            }



            final Call<String> call = apiInterface.uploadPhoto(fileData);

            Retrofit.getInstance().Enqueue(context,call, new Retrofit.callback() {
                @Override
                public void onResponseCallback(Response<String> response) {
                    if(response.isSuccessful()){

                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray jsonArray = new JSONArray(jsonObject.getString("location"));
                            callback.uploadCallbackMethod(true, jsonArray);
                        } catch (JSONException e) {
                            callback.uploadCallbackMethod(false,null);
                        }


                    }
                    else{
                        callback.uploadCallbackMethod(false,null);
                    }
                }
                @Override
                public void onFailureCallback() {
                    callback.uploadCallbackMethod(false,null);
                    Toast.makeText(context, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNetworkDisableCallback() {
                    callback.uploadCallbackMethod(false,null);
                    Toast.makeText(context, "잠시 후 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
                }


            });

        } catch (FileNotFoundException e) {
            callback.uploadCallbackMethod(false,null);
            Toast.makeText(context, "잠시 후 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
        }


    }
}

