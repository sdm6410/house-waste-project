package com.example.cameraproject.ItemAdd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.cameraproject.R;
import com.example.cameraproject.Util.EventBus.Category.CategoryBusProvider;
import com.example.cameraproject.Util.PhotoManager;
import com.example.cameraproject.Util.TensorFlow.Classifier;
import com.example.cameraproject.Util.TensorFlow.TensorFlowImageClassifier;
import com.squareup.otto.Subscribe;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;
import static com.example.cameraproject.Util.PhotoManager.PICK_FROM_ALBUM;
import static com.example.cameraproject.Util.PhotoManager.PICK_FROM_CAMERA;
import static com.yalantis.ucrop.UCrop.REQUEST_CROP;
import static com.yalantis.ucrop.UCrop.RESULT_ERROR;

public class ItemAddActivity extends AppCompatActivity implements ItemAddContract.View, View.OnClickListener {

    private ItemAddPresenter presenter;
    private ImageView back, home;
    private TextView address, change;
    private ConstraintLayout itemAdd, add, selectComplete, saveContent;
    private static final int ITEM_ADD_ACTIVITY = 10001;
    private ImageView writePostImage[] = new ImageView[5];
    private Bitmap bitmaps[] = new Bitmap[5];
    private TextView plus, minus, number;
    private NestedScrollView scrollView;
    private TextView categorie;
    private int OPEN_CAMERA_FOR_CAPTURE = 0x1;
    private Uri fileUri = null;


    // tensorflow 변수 설정
    private static final String MODEL_PATH = "mobilenet_quant_v1_224.tflite";
    private static final boolean QUANT = true;
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;
    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(this,requestCode,resultCode,data);

    }





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);


        // eventBus
        CategoryBusProvider.getInstance().register(this);

        back = findViewById(R.id.item_add_back);
        home = findViewById(R.id.item_add_home);
        address = findViewById(R.id.item_add_address);
        change = findViewById(R.id.item_add_change);
        itemAdd = findViewById(R.id.item_add_item_add);
        plus = findViewById(R.id.item_add_plus_btn);
        minus = findViewById(R.id.item_add_minus_btn);
        number = findViewById(R.id.item_add_number);
        scrollView = findViewById(R.id.item_add_scrollview);
        categorie = findViewById(R.id.item_add_categorie);
        add = findViewById(R.id.item_add_add);
        selectComplete = findViewById(R.id.item_add_select_complete);
        saveContent = findViewById(R.id.item_add_save_content);

        writePostImage[0] = findViewById(R.id.item_add_item_image_1);
        writePostImage[1] = findViewById(R.id.item_add_item_image_2);
        writePostImage[2] = findViewById(R.id.item_add_item_image_3);
        writePostImage[3] = findViewById(R.id.item_add_item_image_4);
        writePostImage[4] = findViewById(R.id.item_add_item_image_5);

        back.setOnClickListener(this);
        home.setOnClickListener(this);
        change.setOnClickListener(this);
        itemAdd.setOnClickListener(this);
        for(int i = 0; i < writePostImage.length; i++){
            writePostImage[i].setOnClickListener(this);
        }

        initTensorFlowAndLoadModel();

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);




        presenter = new ItemAddPresenter();
        presenter.attachView(this);
        presenter.initialize(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CategoryBusProvider.getInstance().unregister(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_add_back:
                presenter.finishActivity(this);
                break;
            case R.id.item_add_home:

                break;
            case R.id.item_add_change:

                break;
            case R.id.item_add_item_add:
                presenter.onClickItemAddActivty(this);
                break;
            case R.id.item_add_plus_btn:
                presenter.onClickAddPlusBtn(this);
                break;
            case R.id.item_add_minus_btn:
                presenter.onClickAddMinusBtn(this);
                break;
            case R.id.item_add_item_image_1:
                presenter.onClickCameraImageClick(this);
                break;
            case R.id.item_add_item_image_2:
                presenter.onClickCameraImageClick(this);
                break;
            case R.id.item_add_item_image_3:
                presenter.onClickCameraImageClick(this);
                break;
            case R.id.item_add_item_image_4:
                presenter.onClickCameraImageClick(this);
                break;
                case R.id.item_add_item_image_5:
                presenter.onClickCameraImageClick(this);
                break;

        }

    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("LDK", "initTensorFlowAndLoadModel: ");
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE,
                            QUANT);
                    Log.d("LDK", "getAssets: "+getAssets());
                    Log.d("LDK", "getAssets: "+getAssets());
                    Log.d("LDK", "LABEL_PATH: "+LABEL_PATH);
                    Log.d("LDK", "INPUT_SIZE: "+INPUT_SIZE);
                    Log.d("LDK", "QUANT: "+QUANT);

                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }


    @Override
    public void setFragmentView(int index) {

    }

    @Override
    public void setHolderView(int index) {

    }

    @Override
    public void setAddressView(String addressView) {
        Intent intent = getIntent();
        String load = intent.getStringExtra("load");
        String detail = intent.getStringExtra("detail");
        address.setText(""+ load + detail);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void setImageView(ArrayList<Bitmap> image, int currentIndex) {
        for(int i = 0; i<image.size(); i++){
            writePostImage[i].setVisibility(View.VISIBLE);
            writePostImage[i].setClickable(false);
            writePostImage[i + 1].setVisibility(View.VISIBLE);
            writePostImage[i + 1].setClickable(true);
            writePostImage[i].setImageBitmap(image.get(i));
            bitmaps[i] = image.get(i);
            Glide.with(this)
                    .load(image.get(i))
                    .apply(new RequestOptions().transforms(new CenterCrop()))
                    .into(this.writePostImage[i]);
        }

    }

    @Override
    public void setChangeNumber(int num) {
        number.setText(""+num);
    }

    @Override
    public void setCategory(String category) {
        categorie.setText(category);
    }

    @Override
    public void setItemAddHolder() {
        itemAdd.setVisibility(View.GONE);
        saveContent.setVisibility(View.VISIBLE);
        selectComplete.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);

    }

    @Subscribe
    public void getPost(String category){
        categorie.setText(category+ "");
    }


}
