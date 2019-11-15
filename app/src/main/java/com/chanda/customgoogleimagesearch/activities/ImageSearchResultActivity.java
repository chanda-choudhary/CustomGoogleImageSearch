package com.chanda.customgoogleimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanda.customgoogleimagesearch.R;
import com.chanda.customgoogleimagesearch.adapters.ImageResultAdapter;
import com.chanda.customgoogleimagesearch.models.ImageResult;
import com.chanda.customgoogleimagesearch.net.SearchClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ImageSearchResultActivity extends AppCompatActivity {
    RecyclerView rvResults;
    List <ImageResult> imageResults;
    ImageResultAdapter imageAdapter;
    int startPage = 1;
    SearchClient client;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search_result);
        rvResults = findViewById(R.id.rv_results);

        imageResults = new ArrayList<>();
        imageAdapter = new ImageResultAdapter(this,imageResults);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvResults.setLayoutManager(mLayoutManager);
        rvResults.setAdapter(imageAdapter);

        Intent i=getIntent();
        query=i.getStringExtra("query");
        onImageSearch(1);
    }

    public void onImageSearch(int start) {

        if (isNetworkAvailable()) {
            client = new SearchClient();
            startPage = start;
            if (startPage == 1)
                imageAdapter.clear();

            if (!query.equals(""))
                client.getSearch(query, startPage, this, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    JSONArray imageJsonResults;
                                    if (response != null) {
                                        imageJsonResults = response.getJSONArray("items");
                                        imageAdapter.addItems(ImageResult.fromJSONArray(imageJsonResults));
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), R.string.invalid_data, Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                Toast.makeText(getApplicationContext(), R.string.service_unavailable, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            else {
                Toast.makeText(this, R.string.invalid_query, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
