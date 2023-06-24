package com.example.pojo;

public class StackItem {

    private int layoutId;

    private int bgColorId;

    private String title;

    public StackItem(int layoutId, int bgColorId, String title) {
        this.layoutId = layoutId;
        this.bgColorId = bgColorId;
        this.title = title;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getBgColorId() {
        return bgColorId;
    }

    public void setBgColorId(int bgColorId) {
        this.bgColorId = bgColorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
