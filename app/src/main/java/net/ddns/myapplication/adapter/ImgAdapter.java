package net.ddns.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_web_img, false);
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_img, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgLeft.setImageBitmap(rowImgList.get(position).getImgLeft());
        holder.imgMiddle.setImageBitmap(rowImgList.get(position).getImgMiddle());
        holder.imgRight.setImageBitmap(rowImgList.get(position).getImgRight());
    }

    @Override
    public int getItemCount() {
        return rowImgList.size();
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