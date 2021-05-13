package net.ddns.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.ddns.myapplication.adapter.ImgAdapter;
import net.ddns.myapplication.item.RowImg;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String WEB_URL = "https://www.gettyimages.com/photos/collaboration?page=%d&phrase=collaboration&sort=mostpopular";
    private RecyclerView recyclerView;
    private ImgAdapter imgAdapter;
    private ArrayList<RowImg> rowImgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_img);
        new GetImgSrc().execute();
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
        private ProgressDialog progressDialog;
        private int pageNum;

        public GetImgSrc() {
            this.pageNum = 1;
        }

        public GetImgSrc(int rowCnt) {
            this.pageNum = rowCnt / 20 + 1;
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
                    rowImgList.add(new RowImg(
                            mElementDataSize.get(i).attr("src"),
                            mElementDataSize.get(i+1).attr("src"),
                            mElementDataSize.get(i+2).attr("src")
                    ));
                }
                Log.d("===srcResult", rowImgList.toString());
            }catch (Exception e){
                Log.e("===ERROR", "doInBackground" + e.toString());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(this.pageNum==1){
                imgAdapter = new ImgAdapter(rowImgList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(imgAdapter);
            }else{
                imgAdapter.notifyDataSetChanged();
            }
            Toast.makeText(getApplicationContext(), "PAGE : " + pageNum, Toast.LENGTH_SHORT).show();
//            Log.d("===Success" + rowImgList.size(), rowImgList.get(rowImgList.size()-1).toString());
            progressDialog.dismiss();
        }
    }
}