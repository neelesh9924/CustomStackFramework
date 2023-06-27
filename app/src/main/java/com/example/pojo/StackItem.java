package com.example.pojo;

public class StackItem {

    private final int mainLayoutId;

    private final int preLayoutId;

    private final int postLayoutId;

    private final int bgColorId;

    public StackItem(int PreLayoutId, int MainLayoutId, int PostLayoutId, int BGColorId) {
        this.preLayoutId = PreLayoutId;
        this.mainLayoutId = MainLayoutId;
        this.postLayoutId = PostLayoutId;
        this.bgColorId = BGColorId;
    }

    public int getPreLayoutId() {
        return preLayoutId;
    }

    public int getMainLayoutId() {
        return mainLayoutId;
    }

    public int getPostLayoutId() {
        return postLayoutId;
    }

    public int getBgColorId() {
        return bgColorId;
    }
}
