package com.example.mm.appquakes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private static final String USGS_REQUEST_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
    private static final String USGS_REQUEST_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=3&limit=50";

    private EarthquakeAdapter mEarthquakeAdapter;

    private ArrayList <Earthquake> mEarthquakeList;

    private RecyclerView mRecyclerView;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEarthquakeList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    public void parseJSON(){
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                USGS_REQUEST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray earthquakeArray = response.getJSONArray("features");
                    for (int i = 0; i < earthquakeArray.length(); i++) {
                        JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                        JSONObject properties = currentEarthquake.getJSONObject("properties");

                        double mag = properties.getDouble("mag");
                        String place = properties.getString("place");

                        long time =properties.getLong("time");
                        String url = properties.getString("url");

                        mEarthquakeList.add(new Earthquake(mag, place, time, url));
                    }

                    Context mContext = MainActivity.this;
                    mEarthquakeAdapter = new EarthquakeAdapter(mContext, mEarthquakeList);
                    mRecyclerView.setAdapter(mEarthquakeAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(mJsonObjectRequest);
    }
}
