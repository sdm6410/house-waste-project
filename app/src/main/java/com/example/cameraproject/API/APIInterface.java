package com.example.cameraproject.API;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {


    //--> POST http://192.168.0.34:8001/api/upload
    // 이미지 업로드
    @Multipart
    @POST("upload")
    Call<String> uploadPhoto(@Part MultipartBody.Part[] file);


}
