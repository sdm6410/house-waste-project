package com.example.cameraproject.Util.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.cameraproject.R;

public class PhotoDialog extends Dialog implements View.OnClickListener {
    private Callback callback;
    private ImageView cancel;
    private LinearLayout album,camera;


    public PhotoDialog(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setAttributes(lp);

        cancel = findViewById(R.id.dialog_photo_cancel);
        album = findViewById(R.id.dialog_photo_album);
        camera = findViewById(R.id.dialog_photo_carmera);

        cancel.setOnClickListener(this);
        album.setOnClickListener(this);
        camera.setOnClickListener(this);


    }
    public interface Callback{
        void cancelCallback();
        void albumCallback();
        void cameraCallback();
    }

    public void setCallback(Callback callback) { this.callback = callback; }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_photo_cancel :
                callback.cancelCallback();
                break;
            case R.id.dialog_photo_album :
                callback.albumCallback();
                break;
            case R.id.dialog_photo_carmera :
                callback.cameraCallback();
                break;
        }

    }
}
