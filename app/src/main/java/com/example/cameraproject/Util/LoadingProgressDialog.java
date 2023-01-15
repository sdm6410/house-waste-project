package com.example.cameraproject.Util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.cameraproject.R;

public class LoadingProgressDialog extends DialogFragment {
    private static LoadingProgressDialog instance;

    private ImageView image;

    public static LoadingProgressDialog getInstance(){
        if(instance == null){
            instance = new LoadingProgressDialog();
            Bundle bundle = new Bundle();
            bundle.putString("","");
            instance.setArguments(bundle);
        }
        return instance;
    }

    public void showDialog(Context context){

        if(this.isAdded()){
            this.dismiss();
        }
        this.show(((AppCompatActivity)context).getSupportFragmentManager(),"");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_loading_progress, null);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        RelativeLayout relativeLayout = view.findViewById(R.id.dialog_loading_progress_holder);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return dialog;
    }
}
