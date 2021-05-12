package net.ddns.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.myapplication.MainActivity;
import net.ddns.myapplication.R;
import net.ddns.myapplication.item.RowImg;

import java.util.ArrayList;
import java.util.BitSet;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {
    private ArrayList<RowImg> rowImgList;
//    private LruCache<String, Bitmap> memoryCache;
//
//    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//    final int cacheSize = maxMemory / 8;
//    memoryCache = new LruCache<String, Bitmap>(cacheSize){
//        @Override
//        protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
//            return value.getByteCount() / 1024;
//        }
//    };
//
//    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
//        if(getBitmapFromMemCache(key) == null){
//            memoryCache.put(key, bitmap);
//        }
//    }
//
//    public Bitmap getBitmapFromMemCache(String key){
//        return memoryCache.get(key);
//    }
//
//    public void loadBitmap(int resId, ImageView imageView){
//        final String imageKey = String.valueOf(resId);
//
//        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
//        if(bitmap != null){
//            imageView.setImageBitmap(bitmap);
//        }else{
//            BitmapWorkerTask task = new BitmapWorkerTask();
//            task.equals(resId);
//            imageView.setImageBitmap(bitmap);
//        }
//    }
//
//    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // 사전에 inJustDecodeBounds를 통해 얻은 높이와 너비값을 구한다.
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//            //본래의 예제는 절반으로 나눈 높이와 너비값을 사용하나 불필요하다 생각되어 수정하였습니다.
//            while ((height / inSampleSize) >= reqHeight
//                    && (width / inSampleSize) >= reqWidth) {
//                //inSampleSize는 2의 자승값을 사용하기에 지속적으로 2를 곱한 값을 사용합니다.
//                inSampleSize *= 2;
//            }
//        }
//        return inSampleSize;
//    }
//
//    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
//
//        // 이미지 높이와 너비를 얻기위해 inJustDecodeBound를 true로 설정하여 디코딩합니다.
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // inSampleSize를 계산한 후 option에 적용합니다.
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // option을 이용해 비트맵으로 디코딩합니다.
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }
//
//    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap>{
//        @Override
//        protected Bitmap doInBackground(Integer... integers) {
//            final Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), integers[0], 100, 100);
//            addBitmapToMemoryCache(String.valueOf(integers[0]), bitmap);
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            memoryCache.put()
//        }
//    }

    public ImgAdapter(ArrayList<RowImg> rowImgList) {
        this.rowImgList = rowImgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("===onBindViewHolder===", rowImgList.get(position).toString());
//        holder.imgLeft.setImageBitmap(rowImgList.get(position).getImgLeft());
//        holder.imgMiddle.setImageBitmap(rowImgList.get(position).getImgMiddle());
//        holder.imgRight.setImageBitmap(rowImgList.get(position).getImgRight());
    }

    @Override
    public int getItemCount() {
        return rowImgList == null ? 0:rowImgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgLeft, imgMiddle, imgRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLeft = (ImageView) itemView.findViewById(R.id.img_left);
            imgMiddle = (ImageView) itemView.findViewById(R.id.img_middle);
            imgRight = (ImageView) itemView.findViewById(R.id.img_right);
        }
    }
}