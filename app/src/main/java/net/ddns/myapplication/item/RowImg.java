package net.ddns.myapplication.item;

public class RowImg {
    String imgLeft, imgMiddle, imgRight;

    public RowImg(String imgLeft, String imgMiddle, String imgRight) {
        this.imgLeft = imgLeft;
        this.imgMiddle = imgMiddle;
        this.imgRight = imgRight;
    }

    public String getImgLeft() {
        return imgLeft;
    }

    public String getImgMiddle() {
        return imgMiddle;
    }

    public String getImgRight() {
        return imgRight;
    }

    @Override
    public String toString() {
        return "RowImg{" +
                "imgLeft='" + imgLeft + '\'' +
                ", imgMiddle='" + imgMiddle + '\'' +
                ", imgRight='" + imgRight + '\'' +
                '}';
    }
}
