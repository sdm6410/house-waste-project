package com.example.cameraproject.Util.EventBus.Category;

public class CategoryBusEvent {
    boolean flag;

    public CategoryBusEvent(boolean flag){
        this.flag = flag;
    }

    public boolean isFlag(){
        return flag;
    }
}
