package ro.zamfiroiu.i04asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getImages(View view) {
        GetImages getImages=new GetImages(){
            @Override
            protected void onProgressUpdate(Integer... values) {
                ProgressBar pb=findViewById(R.id.progress);
                pb.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(List<Bitmap> bitmaps) {
                LinearLayout ll=findViewById(R.id.linearLayout);
                for(Bitmap imagine:bitmaps){
                    ImageView iv=new ImageView(getApplicationContext());
                    iv.setImageBitmap(imagine);
                    ll.addView(iv);
                }
            }
        };

        List<String> links=new ArrayList<>();
        links.add("https://scontent.fotp3-3.fna.fbcdn.net/v/t39.30808-6/318123630_513403324157363_4509967294249783044_n.png?_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_ohc=0DVdNUBmRscAb6SviGf&_nc_ht=scontent.fotp3-3.fna&oh=00_AfBf3Z3Xf_EY_4ZBr8wnWHhYAptriEo0YFX-v6EwNiRq-A&oe=6616F65B");
        links.add("https://studyinromania.gov.ro/resource-c-1526-1200x720-iwh-atm_03.jpg");
        links.add("https://armed.mapn.ro/uploads//albums/20150723/9W2A2310.sized.jpg");
        links.add("https://studyinromania.gov.ro/resource-c-1528-1200x720-iwh-atm_16.jpg");
        links.add("https://armed.mapn.ro/uploads//albums/20150723/9W2A2152.sized.jpg");

        ProgressBar pb=findViewById(R.id.progress);
        pb.setIndeterminate(false);
        pb.setMax(links.size());

        getImages.execute(links);

    }
}
