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
import android.widget.TextView;

import net.ddns.myapplication.adapter.WebImgAdapter;
import net.ddns.myapplication.item.WebImg;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyViewWebImg;
    private ArrayList<WebImg> webImgs = new ArrayList<>();
    private boolean isLoading = false;
    private int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetImgSrc(pageNumber++).execute();

        findId();
        setListener();
    }

    private void findId(){
        recyViewWebImg = (RecyclerView)findViewById(R.id.recyViewWebImg);
    }

    private void setListener(){
        recyViewWebImg.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("===scroll", !recyclerView.canScrollVertically(1) + "");
                if(!recyclerView.canScrollVertically(1)){
                    Log.d("===END scroll", !recyclerView.canScrollVertically(1) + "");
                    new GetImgSrc(pageNumber++).execute();
                }
            }
        });
    }

    private class GetImgSrc extends AsyncTask<Void, Void, Void>{
        private final String WEB_URL = "https://www.gettyimages.com/photos/collaboration?page=%d&phrase=collaboration&sort=mostpopular";
        private int pageNum;
        private ProgressDialog progressDialog;

        public GetImgSrc(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
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
            isLoading = true;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Log.d("===page", pageNum + "");
                Document doc = Jsoup.connect(String.format(WEB_URL, pageNum)).get();
                Elements mElementDataSize = doc.select("div[class=search-content__gallery-assets]").select("img");
                Log.d("===totcnt", mElementDataSize.size()+"");
                for(int i = 0; i < mElementDataSize.size(); i += 3){
                    Log.d("===1=list" + i, mElementDataSize.get(i).attr("src"));
                    Log.d("===2=list" + (i+1), mElementDataSize.get(i+1).attr("src"));
                    Log.d("===3=list" + (i+2), mElementDataSize.get(i+2).attr("src"));
                    webImgs.add(new WebImg(
                            convStringToBitmap(mElementDataSize.get(i).attr("src")),
                            convStringToBitmap(mElementDataSize.get(i+1).attr("src")),
                            convStringToBitmap(mElementDataSize.get(i+2).attr("src"))
                    ));
                }
                Log.d("===result", webImgs.toString());

            }catch (Exception e){
                Log.e("===ERROR", "doInBackground");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            WebImgAdapter webImgAdapter = new WebImgAdapter(webImgs);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyViewWebImg.setLayoutManager(layoutManager);
            recyViewWebImg.setAdapter(webImgAdapter);

            progressDialog.dismiss();
            isLoading = false;
        }
    }
}