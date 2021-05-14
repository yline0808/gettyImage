package net.ddns.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import net.ddns.myapplication.R;
import net.ddns.myapplication.item.RowImg;

import java.util.ArrayList;

public class ImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private ArrayList<RowImg> rowImgList;

    public ImgAdapter(ArrayList<RowImg> rowImgList) {
        this.rowImgList = rowImgList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false));
        }else{
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            addItemRows((ItemViewHolder)holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return rowImgList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return rowImgList == null ? 0 : rowImgList.size();
    }

    private void addItemRows(ItemViewHolder  holder, int position){
        holder.setItem(rowImgList.get(position));
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgLeft, imgMiddle, imgRight;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLeft = (ImageView) itemView.findViewById(R.id.img_left);
            imgMiddle = (ImageView) itemView.findViewById(R.id.img_middle);
            imgRight = (ImageView) itemView.findViewById(R.id.img_right);
        }

        public void setItem(RowImg item){
            setGlide(imgLeft.getContext(), item.getImgLeft(), imgLeft);
            setGlide(imgMiddle.getContext(), item.getImgMiddle(), imgMiddle);
            setGlide(imgRight.getContext(), item.getImgRight(), imgRight);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void setGlide(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }
}