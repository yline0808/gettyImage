package net.ddns.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import net.ddns.myapplication.adapter.ImgAdapter;
import net.ddns.myapplication.item.RowImg;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String WEB_URL = "https://www.gettyimages.com/photos/collaboration?page=%d&phrase=collaboration&sort=mostpopular";
    private RecyclerView recyclerView;
    private ArrayList<RowImg> rowImgList = new ArrayList<>();
    private ImgAdapter imgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_img);

        new GetImgSrc().execute();

        setListener();
    }


    private void setListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){
                    new GetImgSrc(rowImgList.size()).execute();
                }
            }
        });
    }

    private class GetImgSrc extends AsyncTask<Void, Void, Void>{
        private int pageNum;
        private ProgressDialog progressDialog;

        public GetImgSrc() {
            this.pageNum = 1;
        }

        public GetImgSrc(int itemSize) {
            this.pageNum = itemSize == 0 ? 1 : itemSize / 20 + 1;
        }

        private Bitmap convStringToBitmap(String src){
            Bitmap bm = null;
            try{
                URL url = new URL(src);
                URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
            }catch(Exception e){
                Log.e("===ERROR BIT", e.toString());
                e.printStackTrace();
            }
            return bm;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("로딩중...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect(String.format(WEB_URL, pageNum)).get();
                Elements mElementDataSize = doc.select("div[class=search-content__gallery-assets]").select("img");
                for(int i = 0; i < mElementDataSize.size(); i += 3){
                    Log.d("====l" + i, mElementDataSize.get(i).attr("src"));
                    Log.d("====i" + (i+1), mElementDataSize.get(i+1).attr("src"));
                    Log.d("====s" + (i+2), mElementDataSize.get(i+2).attr("src"));
                    rowImgList.add(new RowImg(
                            convStringToBitmap(mElementDataSize.get(i).attr("src")),
                            convStringToBitmap(mElementDataSize.get(i+1).attr("src")),
                            convStringToBitmap(mElementDataSize.get(i+2).attr("src"))
                    ));
                }
            }catch (Exception e){
                Log.e("===ERROR", "doInBackground");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(pageNum==1){
                imgAdapter = new ImgAdapter(rowImgList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(imgAdapter);
            }else{
                imgAdapter.notifyDataSetChanged();
            }
            progressDialog.dismiss();
        }
    }
}