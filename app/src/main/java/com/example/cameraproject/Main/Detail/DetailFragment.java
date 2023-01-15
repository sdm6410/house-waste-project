package com.example.cameraproject.Main.Detail;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.cameraproject.Main.MainContract;
import com.example.cameraproject.R;

import java.util.ArrayList;

public class DetailFragment extends Fragment implements DetailContract.View, View.OnClickListener {

    private View view;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private DetailPresenter presenter;
    private Handler hd = new Handler();
    private boolean isVisibleToUser = false;
//    private SwitchTagView switchTagView;


    public DetailFragment(){}
    private MainContract.fragmentCallback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainContract.fragmentCallback)
            callback = (MainContract.fragmentCallback) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;

        if(isVisibleToUser){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while(true){
                        if(getContext() != null && presenter != null){

                            hd.post(new Runnable() {
                                @Override
                                public void run() {
                                    presenter.initialize(getActivity(),callback);
                                }
                            });

                            return;
                        }

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_detail, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setPageView(int index) {

    }

}
