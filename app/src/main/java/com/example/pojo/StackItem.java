package com.example.pojo;

public class StackItem {

    private int mainLayoutId;

    private int preLayoutId;

    private int postLayoutId;

    private int bgColorId;

    public StackItem(int mainLayoutId, int preLayoutId, int postLayoutId, int bgColorId) {
        this.mainLayoutId = mainLayoutId;
        this.preLayoutId = preLayoutId;
        this.postLayoutId = postLayoutId;
        this.bgColorId = bgColorId;
    }

    public int getMainLayoutId() {
        return mainLayoutId;
    }

    public void setMainLayoutId(int mainLayoutId) {
        this.mainLayoutId = mainLayoutId;
    }

    public int getBgColorId() {
        return bgColorId;
    }

    public void setBgColorId(int bgColorId) {
        this.bgColorId = bgColorId;
    }

    public int getPreLayoutId() {
        return preLayoutId;
    }

    public void setPreLayoutId(int preLayoutId) {
        this.preLayoutId = preLayoutId;
    }

    public int getPostLayoutId() {
        return postLayoutId;
    }

    public void setPostLayoutId(int postLayoutId) {
        this.postLayoutId = postLayoutId;
    }
}
