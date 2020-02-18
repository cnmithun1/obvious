package task.com.obvious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NasaAdapter nasaAdapter;
    public static List<NasaModel> nasaModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        getJsonFileFromLocally();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = MainActivity.this.getAssets().open("nasa.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void getJsonFileFromLocally() {
        try {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            Log.e("mithun", "m_jArry -->" + jsonArray.length());
            nasaModelArrayList = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                NasaModel nm = gson.fromJson(jsonArray.getString(i), NasaModel.class);
                nasaModelArrayList.add(nm);
                sortArray(nasaModelArrayList);
            }

            setUpAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUpAdapter() {

        nasaAdapter = new NasaAdapter(MainActivity.this, nasaModelArrayList);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        recyclerView.setAdapter(nasaAdapter);

    }

    private void sortArray(List<NasaModel> arraylist) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //your own date format
            Collections.sort(arraylist, new Comparator<NasaModel>() {
                @Override
                public int compare(NasaModel o1, NasaModel o2) {
                    try {
                        return simpleDateFormat.parse(o2.getDate()).compareTo(simpleDateFormat.parse(o1.getDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
    }
}
