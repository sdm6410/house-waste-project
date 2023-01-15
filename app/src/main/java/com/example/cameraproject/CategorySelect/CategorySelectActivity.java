package com.example.cameraproject.CategorySelect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cameraproject.R;
import com.example.cameraproject.Util.EventBus.Category.CategoryBusProvider;
import com.example.cameraproject.Util.TensorFlow.Classifier;
import com.example.cameraproject.Util.TensorFlow.TensorFlowImageClassifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategorySelectActivity extends AppCompatActivity implements CategorySelectContract.View, View.OnClickListener {

    private ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private TextView showCategory;
    private TextView confirm;
    private CategorySelectPresenter presenter;
    private String category = "";
    private TextView resultText;
    //TODO tensorflow image
    private Bitmap bitmapImage;
    private static final String MODEL_PATH = "mobilenet_quant_v1_224.tflite";
    private static final boolean QUANT = true;
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;
    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);

        resultText = findViewById(R.id.item_add_result_text);
        //TODO byte array image get
        initTensorFlowAndLoadModel();
        Intent intent = getIntent();
        byte[] arr = getIntent().getByteArrayExtra("bytearray");
        bitmapImage = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        bitmapImage = Bitmap.createScaledBitmap(bitmapImage, INPUT_SIZE, INPUT_SIZE,false);

        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmapImage);
        resultText.setText(results.toString());

        showCategory = findViewById(R.id.category_select_show_category);
        confirm = findViewById(R.id.category_select_confirm);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CategorySelectAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " List Expanded. ",Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + "List Collapsed.",Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                showCategory.setText(expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                category = expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                confirm.setClickable(true);
                confirm.setBackground(getResources().getDrawable(R.drawable.orange_round_rectangle_10));
                return false;
            }
        });

        confirm.setOnClickListener(this);

        presenter = new CategorySelectPresenter();
        presenter.attachView(this);
        presenter.initialize(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.category_select_confirm:{
                CategoryBusProvider.getInstance().post(category);
                finish();
                break;
            }

        }

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                classifier.close();
//            }
//        });
//    }


    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE,
                            QUANT);

                    Log.d("LDK", "run: "+MODEL_PATH);
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }


}
