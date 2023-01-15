package com.example.cameraproject.Util.EventBus.Category;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class CategoryBusProvider {
    public static final Bus bus = new Bus(ThreadEnforcer.ANY);

    public static Bus getInstance(){
        return bus;
    }

    public CategoryBusProvider(){

    }
}
