package com.kimbsu.hstree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class update_developer extends AppCompatActivity {

    private static final String CLICKED = "#FAF4E5";
    private static final String UNCLICKED = "#FBF9F1";

    private LinearLayout updateLayout, developerLayout;
    private Button updateBtn, developerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_developer);

        init();
    }

    public void init() {
        updateLayout = findViewById(R.id.updateLayout);
        developerLayout = findViewById(R.id.developerLayout);
        updateBtn = findViewById(R.id.updateBtn);
        developerBtn = findViewById(R.id.developerBtn);
    }

    public void onBackBtnClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("example_update_developer", "okay");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onHomeBtnClicked(View view) {

    }

    public void onDeveloperBtnClicked(View view) {
        updateLayout.setVisibility(View.GONE);
        developerLayout.setVisibility(View.VISIBLE);

        updateBtn.setBackgroundColor(Color.parseColor(UNCLICKED));
        updateBtn.setTypeface(null, Typeface.NORMAL);
        developerBtn.setBackgroundColor(Color.parseColor(CLICKED));
        developerBtn.setTypeface(null, Typeface.BOLD);
    }

    public void onUpdateBtnClicked(View view) {
        updateLayout.setVisibility(View.VISIBLE);
        developerLayout.setVisibility(View.GONE);

        updateBtn.setBackgroundColor(Color.parseColor(CLICKED));
        updateBtn.setTypeface(null, Typeface.BOLD);
        developerBtn.setBackgroundColor(Color.parseColor(UNCLICKED));
        developerBtn.setTypeface(null, Typeface.NORMAL);
    }
}
