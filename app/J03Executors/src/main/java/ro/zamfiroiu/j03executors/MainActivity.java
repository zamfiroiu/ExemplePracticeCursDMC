package ro.zamfiroiu.j03executors;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Executor executor= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    List<RataSchimb> lista=new ArrayList<>();
                    url = new URL("https://acs.ase.ro/Media/Default/documents/cts/zamfiroiu/curs2020/BNRRates.json.txt");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String linie = null;
                    StringBuilder builder = new StringBuilder();
                    while ((linie = reader.readLine()) != null) {
                        builder.append(linie);
                    }
                    JSONObject object=new JSONObject(builder.toString());
                    //JSONObject dataset=object.getJSONObject("DataSet");
                    JSONObject body=object.getJSONObject("Body");
                    JSONObject cube=body.getJSONObject("Cube");
                    JSONArray rates=cube.getJSONArray("Rate");
                    for(int i=0;i<rates.length();i++){
                        JSONObject currency=rates.getJSONObject(i);
                        RataSchimb rate=new RataSchimb(
                                currency.getString("@currency"),
                                Double.parseDouble(currency.getString("#text"))
                        );
                        lista.add(rate);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ListView listView =findViewById(R.id.listView);
                            List<String> rateSchimb=new ArrayList<>();
                            for(RataSchimb rate:lista){
                                rateSchimb.add(rate.toString());
                            }
                            ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,rateSchimb);
                            listView.setAdapter(adapter);
                        }
                    });
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}