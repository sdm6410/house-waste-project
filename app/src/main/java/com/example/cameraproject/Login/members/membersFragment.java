package com.example.cameraproject.Login.members;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cameraproject.Login.LoginContract;
import com.example.cameraproject.Main.Home.HomePresenter;
import com.example.cameraproject.R;

public class membersFragment extends Fragment implements membersContract.View, View.OnClickListener {
    private View view;
    private membersContract.Presenter presenter;
    private Handler hd = new Handler();
    private boolean isVisibleToUser = false;

    public membersFragment(){}
    private LoginContract.fragmentCallback callback;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;

        if(isVisibleToUser){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(getContext() != null && presenter != null){
                        hd.post(new Runnable() {
                            @Override
                            public void run() {
                                presenter.initialize(getActivity());

                            }
                        });
                        return;
                    }
                    try {
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_members,container,false);


        presenter = new membersPresenter();
        presenter.attachView(this);
        presenter.initialize(view.getContext());
        return view;

    }

    @Override
    public void onClick(View view) {

    }
}
