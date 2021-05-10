package net.ddns.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.myapplication.R;
import net.ddns.myapplication.item.WebImg;

import java.util.ArrayList;

public class WebImgAdapter extends RecyclerView.Adapter<WebImgAdapter.ViewHolder> {
    private ArrayList<WebImg> webImgs;

    public WebImgAdapter(ArrayList<WebImg> webImgs) {
        this.webImgs = webImgs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_web_img, false);
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_web_img, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgViewLeft.setImageBitmap(webImgs.get(position).getImgUrlLeft());
        holder.imgViewMiddle.setImageBitmap(webImgs.get(position).getImgUrlMiddle());
        holder.imgViewRight.setImageBitmap(webImgs.get(position).getImgUrlRight());
    }

    @Override
    public int getItemCount() {
        return webImgs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgViewLeft, imgViewMiddle, imgViewRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgViewLeft = (ImageView) itemView.findViewById(R.id.imgViewLeft);
            imgViewMiddle = (ImageView) itemView.findViewById(R.id.imgViewMiddle);
            imgViewRight = (ImageView) itemView.findViewById(R.id.imgViewRight);
        }
    }
}
