package net.ddns.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.ddns.myapplication.adapter.ImgAdapter;
import net.ddns.myapplication.item.RowImg;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String WEB_URL = "https://www.gettyimages.com/photos/collaboration?page=%d&phrase=collaboration&sort=mostpopular";
    private RecyclerView recyclerView;
    private ArrayList<RowImg> rowImgList = new ArrayList<>();
    private ImgAdapter imgAdapter = new ImgAdapter(rowImgList);
    private boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_img);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(imgAdapter);
        recyclerView.addOnScrollListener(scrollListener);

        new GetImgSrc().execute(1);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if(!isLoading){
                if(layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == rowImgList.size() - 1){
                    new GetImgSrc().execute(rowImgList.size());
                    isLoading = true;
                }
            }
        }
    };

    private class GetImgSrc extends AsyncTask<Integer, Void, Void>{
        private int pageNum = 0;
        private int scrollPosition = 0;
        private boolean isEnd = false;

        @Override
        protected void onPreExecute() {
            rowImgList.add(null);
            imgAdapter.notifyItemInserted(rowImgList.size() - 1);
            recyclerView.scrollToPosition(rowImgList.size() - 1);
        }

        @Override
        protected Void doInBackground(Integer... itemCnt) {
            try{
                this.scrollPosition = rowImgList.size();
                pageNum = itemCnt[0] <= 1 ? 1 : (itemCnt[0] / 20 + 1);

                Log.d("===pageNum", pageNum + "");
                Document doc = Jsoup.connect(String.format(WEB_URL, pageNum)).get();
                Elements mElementDataSize = doc.select("div[class=search-content__gallery-assets]").select("img");

                for(int i = 0; i < mElementDataSize.size(); i += 3){
                    rowImgList.add(new RowImg(
                            mElementDataSize.get(i).attr("src"),
                            mElementDataSize.get(i+1).attr("src"),
                            mElementDataSize.get(i+2).attr("src")
                    ));
                }
            }catch (HttpStatusException httpStatusException){
                httpStatusException.printStackTrace();
                this.isEnd = true;
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            isLoading = false;
            rowImgList.remove(scrollPosition-1);
            imgAdapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), this.isEnd ? "마지막 페이지 입니다." :("PAGE : " + this.pageNum), Toast.LENGTH_SHORT).show();
        }
    }
}