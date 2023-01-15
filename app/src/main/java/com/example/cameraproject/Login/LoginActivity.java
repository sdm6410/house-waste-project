package com.example.cameraproject.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cameraproject.Login.members.membersFragment;
import com.example.cameraproject.Login.nonMembers.nonMembersFragment;
import com.example.cameraproject.R;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener, LoginContract.fragmentCallback {

    private ViewPager viewPager;
    private LoginPresenter presenter;
    private Fragment members, nonMembers;
    private TextView membersText, nonMembersText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        viewPager = findViewById(R.id.login_viewpager);
        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);

        membersText = findViewById(R.id.login_members);
        nonMembersText = findViewById(R.id.login_nonMembers);

        membersText.setOnClickListener(this);
        nonMembersText.setOnClickListener(this);

        members = new membersFragment();
        nonMembers = new nonMembersFragment();

        presenter = new LoginPresenter();
        presenter.attachView(this);
        presenter.initialize(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_members:
                presenter.setFragment(this, 0);
                break;
            case R.id.login_nonMembers:
                presenter.setFragment(this,1);
                break;
        }

    }

    @Override
    public void setFragmentView(int index) {
        viewPager.setCurrentItem(index);
    }

    @Override
    public void setHolderView(int index) {
        membersText.setTextColor(getResources().getColor(R.color.grayColor));
        nonMembersText.setTextColor(getResources().getColor(R.color.grayColor));
        switch (index){
            case 0:
                membersText.setTextColor(getResources().getColor(R.color.main_color));
                break;
            case 1:
                nonMembersText.setTextColor(getResources().getColor(R.color.main_color));
                break;

        }
    }

    @Override
    public void onFragmentCallback(String msg) {
        presenter.handleFragmentCallback(this,msg,this);

    }

    private class pagerAdapter extends FragmentStatePagerAdapter {

        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return members;
                case 1:
                    return nonMembers;

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
