package com.tzw.eq.view;

public class ContextMargin {
    private int bottom = 0;
    private int left = 0;
    private int right = 0;
    private int top = 0;

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    /**
     * 更新边距
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void updateMargin(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
}
