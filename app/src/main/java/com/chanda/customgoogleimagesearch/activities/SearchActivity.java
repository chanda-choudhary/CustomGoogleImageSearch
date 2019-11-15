package com.chanda.customgoogleimagesearch.activities;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chanda.customgoogleimagesearch.R;

public class SearchActivity extends AppCompatActivity {


    EditText etQuery;
    Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupViews();
    }

    public void setupViews(){
        etQuery = (EditText) findViewById(R.id.etQuery);

        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                Intent intent=new Intent(SearchActivity.this, ImageSearchResultActivity.class);
                intent.putExtra("query",etQuery.getText().toString());
                startActivity(intent);
            }
        });


    }


    public static void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}