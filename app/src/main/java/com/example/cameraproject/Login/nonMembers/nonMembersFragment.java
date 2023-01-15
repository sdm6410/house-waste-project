package com.example.cameraproject.Login.nonMembers;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cameraproject.Login.LoginContract;
import com.example.cameraproject.R;

public class nonMembersFragment extends Fragment implements nonMembersContract.View, View.OnClickListener {
    private View view;
    private nonMembersContract.Presenter presenter;
    private Handler hd = new Handler();
    private boolean isVisibleToUser = false;
    private TextView login;

    public nonMembersFragment(){}
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
        view = inflater.inflate(R.layout.fragment_non_members,container,false);

        login = view.findViewById(R.id.non_members_login);
        login.setOnClickListener(this);
        presenter = new nonMembersPresenter();
        presenter.attachView(this);
        presenter.initialize(view.getContext());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.non_members_login:
                presenter.onClickMain(getActivity());
                break;
        }


    }
}
