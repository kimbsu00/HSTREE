package com.kimbsu.hstree;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kimbsu.hstree.webCrawling.FoodMenu;
import com.kimbsu.hstree.webCrawling.MainSchedule;
import com.kimbsu.hstree.webCrawling.NormalNotice;
import com.kimbsu.hstree.webCrawling.Notice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    public static int maxTextureHeight;
    public static int maxTextureWidth;

    //    공지사항 관련 변수 시작
    public Notice notice;

    private NoticeHomeHandler noticeHandler;
    private TextView homeNotice0, homeNotice1, homeNotice2, homeNotice3;
    private String selectHomeNoticeUrl;     // 홈 화면에 표시된 공지사항 중에서 사용자가 선택한 공지사항의 url을 의미
    //    공지사항 관련 변수 끝

    //    월간 및 연간 주요일정 관련 변수 시작
    private MainSchedule mainSchedule;
    //    월간 및 연간 주요일정 관련 변수 끝

    //    식단표 관련 변수 시작
    private FoodMenu foodMenu;
    //    식단표 관련 변수 끝

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, loading.class);
        startActivity(intent);

        init();
    }

    public void init() {

//        공지사항 관련 변수 초기화 시작
        homeNotice0 = findViewById(R.id.homeNotice0);
        homeNotice1 = findViewById(R.id.homeNotice1);
        homeNotice2 = findViewById(R.id.homeNotice2);
        homeNotice3 = findViewById(R.id.homeNotice3);

        selectHomeNoticeUrl = "";

        noticeHandler = new NoticeHomeHandler();
        NoticeThread noticeThread = new NoticeThread();
//        noticeThread.start();
//        공지사항 관련 변수 초기화 끝

//        월간 및 연간 주요일정 관련 변수 초기화 시작
        mainSchedule = null;
//        월간 및 연간 주요일정 관련 변수 초기화 끝

//        식단표 관련 변수 초기화 시작
        foodMenu = null;
//        식단표 관련 변수 초기화 끝

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.updateDeveloperBtn).callOnClick();
            }
        }, 2000);
    }

    // 메뉴 버튼 클릭시 이벤트 처리
    public void onMenuBtnClicked(View view) {

    }

    // 로그인 버튼 클릭시 이벤트 처리
    public void onLoginBtnClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hstree.org/member/login.php"));
        startActivity(intent);
    }

    // 공지시항 버튼 클릭시 이벤트 처리
    public void onNoticeBtnClicked(View view) {
        noService(view);
//        Intent intent = new Intent(getApplicationContext(), notice_list.class);
//        intent.putExtra("Notice", notice);
//        startActivityForResult(intent, 100);
    }

    // 주요일정 버튼 클릭시 이벤트 처리
    public void onScheduleBtnClicked(View view) throws InterruptedException {
        noService(view);
//        Intent intent = new Intent(getApplicationContext(), schedule_month_year.class);
//        if (mainSchedule != null)
//            intent.putExtra("MainSchedule", mainSchedule);
//        startActivityForResult(intent, 102);
    }

    // 업데이트 소식 & 개발자 버튼 클릭시 이벤트 처리
    public void onUpdateDeveloperBtnClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), update_developer.class);
        startActivityForResult(intent, 103);
    }

    // 식단표 버튼 클릭시 이벤트 처리
    public void onFoodMenuBtnClicked(View view) {
        noService(view);
//        Intent intent = new Intent(getApplicationContext(), food_menu.class);
//        if (foodMenu != null)
//            intent.putExtra("FoodMenu", foodMenu);
//        startActivityForResult(intent, 104);
    }

    // 전화번호 안내 버튼 클릭시 이벤트 처리
    public void ontelephoneInfoBtnClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), telephone_info.class);
        startActivityForResult(intent, 105);
    }

    // 서비스 준비중 버튼 클릭시 이벤트 처리
    public void noService(View view) {
        Toast.makeText(getApplicationContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
    }

    // 홈 화면에 표시된 공지사항 클릭시 이벤트 처리
    public void onHomeNoticeClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), notice_list.class);
        intent.putExtra("Notice", this.notice);

        if (view.getId() == R.id.homeNotice0) {
            intent.putExtra("homeNoticeIndex", 0);
        } else if (view.getId() == R.id.homeNotice1) {
            intent.putExtra("homeNoticeIndex", 1);
        } else if (view.getId() == R.id.homeNotice2) {
            intent.putExtra("homeNoticeIndex", 2);
        } else if (view.getId() == R.id.homeNotice3) {
            intent.putExtra("homeNoticeIndex", 3);
        }
        intent.putExtra("isHomeNoticeIndex", true);
        Log.e("homeNoticeClicked", String.valueOf(view.getId()));

        startActivityForResult(intent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 공지사항 리스트 액티비티 생성 후 반환된 경우
        if (requestCode == 100) {

        }
        // 공지사항 게시글 액티비티 생성 후 반환된 경우 -> 여기서는 필요없다.
        else if (requestCode == 101) {

        }
        // 장학관 주요일정 액티비티 생성 후 반환된 경우
        else if (requestCode == 102) {
            this.mainSchedule = (MainSchedule) getIntent().getSerializableExtra("MainSchedule");
        }
        // 업데이트 소식 및 개발자 액티비티 생성 후 반환된 경우
        else if (requestCode == 103) {

        }
        // 식단표 액티비티 생성 후 반환된 경우
        else if (requestCode == 104) {
            this.foodMenu = (FoodMenu) getIntent().getSerializableExtra("FoodMenu");
        }
        // 전화번호 안내 액티비티 생성 후 반환된 경우
        else if (requestCode == 105) {

        }
    }

    class NoticeHomeHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            if (!(bundle.isEmpty())) {
                if (bundle.getBoolean("isHomeNotice")) {
                    String[] homeNotice = new String[4];
                    homeNotice[0] = bundle.getString("homeNotice0");
                    homeNotice[1] = bundle.getString("homeNotice1");
                    homeNotice[2] = bundle.getString("homeNotice2");
                    homeNotice[3] = bundle.getString("homeNotice3");

                    homeNotice0.setText(homeNotice[0]);
                    homeNotice1.setText(homeNotice[1]);
                    homeNotice2.setText(homeNotice[2]);
                    homeNotice3.setText(homeNotice[3]);

                    findViewById(R.id.noticeBtn).setClickable(true);
                    ((Button) findViewById(R.id.noticeBtn)).setTextColor(Color.parseColor("#000000"));
                }

            }
        }
    }

    class NoticeThread extends Thread {
        //        쓰레드로 어떤 작업을 수행할 것인지를 결정 { 1=공지사항 목록 크롤링, 2=선택한 공지사항 크롤링 }
        int select;

        public NoticeThread() {
            select = 1;
            notice = new Notice();
        }

        public NoticeThread(int m_select) {
            select = m_select;
            notice = new Notice();
        }

        @Override
        public void run() {
            Message message = noticeHandler.obtainMessage();
            Bundle bundle = new Bundle();

            boolean while_break = false;
            if (select == 1) {
                while (!while_break) {
                    try {
                        notice.crawlingNotice();
                        while_break = true;
                        Vector<NormalNotice> normal = notice.getNormalNotice();
                        if (!(normal.isEmpty())) {
                            bundle.putString("homeNotice0", normal.get(0).getTitle());
                            bundle.putString("homeNotice1", normal.get(1).getTitle());
                            bundle.putString("homeNotice2", normal.get(2).getTitle());
                            bundle.putString("homeNotice3", normal.get(3).getTitle());
                            bundle.putBoolean("isHomeNotice", true);
                        }
//                        Vector<String[]> normal = notice.getNotice();
//                        if (!(normal.isEmpty())) {
//                            bundle.putString("homeNotice0", normal.get(0)[0]);
//                            bundle.putString("homeNotice1", normal.get(1)[0]);
//                            bundle.putString("homeNotice2", normal.get(2)[0]);
//                            bundle.putString("homeNotice3", normal.get(3)[0]);
//                            bundle.putBoolean("isHomeNotice", true);
//                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (select == 2) {
                while (!while_break) {
                    try {
                        notice.crawlingSelectNotice_r1(selectHomeNoticeUrl);
                        while_break = true;
                        bundle.putBoolean("isHomeNotice", false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Non expected error is occured.", Toast.LENGTH_LONG).show();
            }


            message.setData(bundle);
            noticeHandler.sendMessage(message);
        }
    }

}
