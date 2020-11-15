package com.kimbsu.hstree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class telephone_info extends AppCompatActivity {

    private static final String HSTREE_TEL = "tel:029983630";
    private static final String HSTREE1_TEL = "tel:025841082";
    private static final String HSTREE2_TEL = "tel:029983631";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone_info);
    }

    // BACK 버튼 클릭시 이벤트 처리
    public void onBackBtnClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("example_telephone_info", "okay");
        setResult(RESULT_OK, intent);
        finish();
    }

    // MENU 버튼 클릭시 이벤트 처리 -> 사용하지 않음
    public void onMenuBtnClicked(View view) {
    }

    // CALL 버튼 클릭시 이벤트 처리
    public void onCallBtnClicked(View view) {
        switch(view.getId()) {
            case R.id.hstree_call_btn:
                startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(HSTREE_TEL)));
                break;
            case R.id.hstree1_call_btn:
                startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(HSTREE1_TEL)));
                break;
            case R.id.hstree2_call_btn:
                startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(HSTREE2_TEL)));
                break;
        }

    }

}
