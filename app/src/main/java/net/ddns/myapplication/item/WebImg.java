package net.ddns.myapplication.item;

import android.graphics.Bitmap;

public class WebImg {
    Bitmap imgUrlLeft, imgUrlMiddle, imgUrlRight;

    public WebImg() {
    }

    public WebImg(Bitmap imgUrlLeft, Bitmap imgUrlMiddle, Bitmap imgUrlRight) {
        this.imgUrlLeft = imgUrlLeft;
        this.imgUrlMiddle = imgUrlMiddle;
        this.imgUrlRight = imgUrlRight;
    }

    public Bitmap getImgUrlLeft() {
        return imgUrlLeft;
    }

    public void setImgUrlLeft(Bitmap imgUrlLeft) {
        this.imgUrlLeft = imgUrlLeft;
    }

    public Bitmap getImgUrlMiddle() {
        return imgUrlMiddle;
    }

    public void setImgUrlMiddle(Bitmap imgUrlMiddle) {
        this.imgUrlMiddle = imgUrlMiddle;
    }

    public Bitmap getImgUrlRight() {
        return imgUrlRight;
    }

    public void setImgUrlRight(Bitmap imgUrlRight) {
        this.imgUrlRight = imgUrlRight;
    }

    @Override
    public String toString() {
        return "WebImg{" +
                "imgUrlLeft=" + imgUrlLeft +
                ", imgUrlMiddle=" + imgUrlMiddle +
                ", imgUrlRight=" + imgUrlRight +
                '}';
    }
}
