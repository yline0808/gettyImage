package net.ddns.myapplication.item;

import android.graphics.Bitmap;

public class RowImg {
    Bitmap imgLeft, imgMiddle, imgRight;

    public RowImg(Bitmap imgLeft, Bitmap imgMiddle, Bitmap imgRight) {
        this.imgLeft = imgLeft;
        this.imgMiddle = imgMiddle;
        this.imgRight = imgRight;
    }

    public Bitmap getImgLeft() {
        return imgLeft;
    }

    public Bitmap getImgMiddle() {
        return imgMiddle;
    }

    public Bitmap getImgRight() {
        return imgRight;
    }
}
