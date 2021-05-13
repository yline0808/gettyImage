package net.ddns.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import net.ddns.myapplication.R;
import net.ddns.myapplication.item.RowImg;

import java.util.ArrayList;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {
    private ArrayList<RowImg> rowImgList;

    public ImgAdapter(ArrayList<RowImg> rowImgList) {
        this.rowImgList = rowImgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setGlide(holder.itemView.getContext(), rowImgList.get(position).getImgLeft(), holder.imgLeft);
        setGlide(holder.itemView.getContext(), rowImgList.get(position).getImgMiddle(), holder.imgMiddle);
        setGlide(holder.itemView.getContext(), rowImgList.get(position).getImgRight(), holder.imgRight);
    }

    @Override
    public int getItemCount() {
        return rowImgList == null ? 0:rowImgList.size();
    }

    private void setGlide(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
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