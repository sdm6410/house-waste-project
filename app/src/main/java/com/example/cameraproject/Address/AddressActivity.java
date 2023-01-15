package com.example.cameraproject.Address;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cameraproject.R;
import com.example.cameraproject.Util.View.AddressWebViewActivity;

public class AddressActivity extends AppCompatActivity implements AddressContract.View, View.OnClickListener {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private EditText editText, inputDetail;
    private AddressPresenter presenter;
    private ImageView back;
    private View detailLine;
    private LinearLayout textHolder, holder, detailHolder, headHolder;
    private TextView loadName, Name,title, throwTresh, down, buy, confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        headHolder = findViewById(R.id.address_head_holder);
        editText = findViewById(R.id.address_search);
        back = findViewById(R.id.address_back);
        textHolder = findViewById(R.id.address_text_holder);
        holder = findViewById(R.id.address_holder);
        detailHolder = findViewById(R.id.address_detail_holder);
        loadName = findViewById(R.id.address_load_name);
        Name = findViewById(R.id.address_name);
        title = findViewById(R.id.address_title);
        throwTresh = findViewById(R.id.address_throw);
        down = findViewById(R.id.address_down);
        buy = findViewById(R.id.address_buy);
        detailLine = findViewById(R.id.address_detail_line);
        confirm = findViewById(R.id.address_confirm);
        inputDetail = findViewById(R.id.address_input_detail);
        inputDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0){
                    confirm.setClickable(true);
                    confirm.setBackground(getResources().getDrawable(R.drawable.orange_round_rectangle_10));
                    detailLine.setBackgroundColor(getResources().getColor(R.color.main_color));
                }else {
                    confirm.setClickable(false);
                    confirm.setBackground(getResources().getDrawable(R.drawable.gray_round_rectangle_10));
                    detailLine.setBackgroundColor(getResources().getColor(R.color.grayColor));
                }

            }
        });


        throwTresh.setOnClickListener(this);
        down.setOnClickListener(this);
        buy.setOnClickListener(this);
        editText.setOnClickListener(this);
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);



        presenter = new AddressPresenter();
        presenter.attachView(this);
        presenter.initialize(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.address_search:
                Intent i = new Intent(AddressActivity.this, AddressWebViewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                break;
            case R.id.address_back:
                presenter.finishActivity(this);
                break;
            case R.id.address_throw:
                presenter.onClickViewEvent(this);
                break;
            case R.id.address_down:
                presenter.onClickViewEvent(this);
                break;
            case R.id.address_buy:
                presenter.onClickViewEvent(this);
                break;
            case R.id.address_confirm:
                presenter.onClickItemAddActivtivity(this,loadName.getText().toString(),inputDetail.getText().toString());
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    textHolder.setVisibility(View.GONE);
                    holder.setVisibility(View.VISIBLE);
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        editText.setText(data);
                        loadName.setText(data);
                        Name.setText(data);
                    }
                }
                break;
        }
    }

    @Override
    public void finishActivityView() {
        finish();
    }

    @Override
    public void setHolderVisibility(boolean visibility) {
        if(!visibility) {
            headHolder.setVisibility(View.VISIBLE);
            detailHolder.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
        }
        else {
            headHolder.setVisibility(View.GONE);
            detailHolder.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            title.setText("상세주소입력");
        }
    }
}
