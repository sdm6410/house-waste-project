package com.example.cameraproject.Util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class Permission {

    private static Permission instance;
    private String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION};

    public static Permission getInstance(){

        if(instance == null)
            instance = new Permission();


        return instance;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermission(Activity activity) {
        activity.requestPermissions(permissions,1);
    }



    public boolean checkPermission(final Activity activity) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            for(int j=0;j<permissions.length;j++){

                if(activity.checkSelfPermission(permissions[j]) != PackageManager.PERMISSION_GRANTED){
                    //승인 거절.

                    for(int i=0;i<permissions.length;i++){
                        if(activity.shouldShowRequestPermissionRationale(permissions[i])){
                            new AlertDialog.Builder(activity).setMessage("모든 권한을 승인해주셔야 이용가능합니다.")

                                    .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            activity.finish();
                                        }
                                    })
                                    .setNegativeButton("설정", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                                    .setData(Uri.parse("package:" + activity.getPackageName()));
                                            activity.startActivity(intent);
                                        }
                                    }).setCancelable(false).show();

                            return false;
                        }

                    }

                    Permission.getInstance().requestPermission(activity);
                    return false;
                }
            }

        }

        return true;
    }
}
