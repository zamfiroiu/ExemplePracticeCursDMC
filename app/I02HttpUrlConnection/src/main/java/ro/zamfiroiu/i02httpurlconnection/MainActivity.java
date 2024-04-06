package ro.zamfiroiu.i02httpurlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        HttpsURLConnection con = null;
        try{
            URL url=new URL("https://static.playtech.ro/wp-content/uploads/2018/03/Cum-cauti-imagini-pe-Google-de-pe-mobil.jpg");
            con= (HttpsURLConnection) url.openConnection();
            if(con.getResponseCode()==HttpsURLConnection.HTTP_OK){
                InputStream is = con.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                ImageView iv=findViewById(R.id.imageView);
                iv.setImageBitmap(bitmap);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(con!=null){
                con.disconnect();
            }
        }


    }
}
