package com.example.cameraproject.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cameraproject.Main.Apply.ApplyFragment;
import com.example.cameraproject.Main.Detail.DetailFragment;
import com.example.cameraproject.Main.Home.HomeFragment;
import com.example.cameraproject.R;

public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener, MainContract.fragmentCallback  {

    private ViewPager viewPager;
    private MainPresenter presenter;
    private Fragment home, apply, detail;
    private LinearLayout homeHolder, applyHolder, detailHolder;
    private ImageView homeImage, applyImage, detailImage;
    private TextView homeText, applyText, detailText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        viewPager = findViewById(R.id.main_viewpager);
        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);

        homeHolder = findViewById(R.id.main_home_holder);
        applyHolder = findViewById(R.id.main_apply_holder);
        detailHolder = findViewById(R.id.main_detail_holder);

        homeImage = findViewById(R.id.main_home_image);
        applyImage = findViewById(R.id.main_apply_image);
        detailImage = findViewById(R.id.main_detail_image);

        homeText = findViewById(R.id.main_home_text);
        applyText = findViewById(R.id.main_apply_text);
        detailText = findViewById(R.id.main_detail_text);

        homeHolder.setOnClickListener(this);
        applyHolder.setOnClickListener(this);
        detailHolder.setOnClickListener(this);

        home = new HomeFragment();
        apply = new ApplyFragment();
        detail = new DetailFragment();

        presenter = new MainPresenter();
        presenter.attachView(this);
        presenter.initialize(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_home_holder:
                presenter.setFragment(this, 0);
                break;
            case R.id.main_apply_holder:
                presenter.setFragment(this, 1);
                break;
            case R.id.main_detail_holder:
                presenter.setFragment(this, 2);
                break;
        }

    }

    @Override
    public void onFragmentCallback(String msg) {presenter.handleFragmentCallback(this,msg,this);

    }

    @Override
    public void setFragmentView(int index) {viewPager.setCurrentItem(index);

    }

    @Override
    public void setHolderView(int index) {
        homeText.setTextColor(getResources().getColor(R.color.grayColor));
        applyText.setTextColor(getResources().getColor(R.color.grayColor));
        detailText.setTextColor(getResources().getColor(R.color.grayColor));
        homeImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_home));
        applyImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_community));
        detailImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_service));

        switch (index){
            case 0:
                homeText.setTextColor(getResources().getColor(R.color.orangeColor));
                homeImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_home_active));
                break;
            case 1:
                applyText.setTextColor(getResources().getColor(R.color.orangeColor));
                applyImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_community_active));
                break;
            case 2:
                detailText.setTextColor(getResources().getColor(R.color.orangeColor));
                detailImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_service_active));
                break;
        }




    }

    private class pagerAdapter extends FragmentStatePagerAdapter {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return home;
                case 1:
                    return apply;
                case 2:
                    return detail;

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
