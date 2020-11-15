package com.kimbsu.hstree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kimbsu.hstree.webCrawling.MainSchedule;

import java.io.IOException;
import java.util.Vector;

public class schedule_month_year extends AppCompatActivity {

    private static final String RED = "#FF5722";
    private static final String BLUE = "#2196F3";
    private static final String BLACK = "#000000";

    private static final String CLICKUNABLE = "#BCB5B5";
    private static final String CLICKABLE = "#000000";

    private static final int SCHEDULENUM = 20;

    private MainSchedule mainSchedule;

    private Button menuBtn;
    private TextView listIndex;
    private LinearLayout layout_monthSchedule, monthUpperMenu;
    private LinearLayout[] monthLinearLayout;
    private TextView[] monthDay, monthSchedule;
    private ImageView layout_yearSchedule1;

    private ScheduleHandler scheduleHandler;

    private MyProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_month_year);

        init();
    }

    public void init() {
        myProgressBar = new MyProgressBar();
//        mainSchedule = (MainSchedule) getIntent().getSerializableExtra("MainSchedule");

        menuBtn = findViewById(R.id.menuBtn);
        listIndex = findViewById(R.id.listIndex);
        layout_monthSchedule = findViewById(R.id.monthSchedule);
        monthUpperMenu = findViewById(R.id.monthUpperMenu);
        monthLinearLayout = new LinearLayout[SCHEDULENUM];
        monthDay = new TextView[SCHEDULENUM];
        monthSchedule = new TextView[SCHEDULENUM];
        layout_yearSchedule1 = findViewById(R.id.yearSchedule);

        monthLinearLayout[0] = findViewById(R.id.monthLinearLayout0);
        monthLinearLayout[1] = findViewById(R.id.monthLinearLayout1);
        monthLinearLayout[2] = findViewById(R.id.monthLinearLayout2);
        monthLinearLayout[3] = findViewById(R.id.monthLinearLayout3);
        monthLinearLayout[4] = findViewById(R.id.monthLinearLayout4);
        monthLinearLayout[5] = findViewById(R.id.monthLinearLayout5);
        monthLinearLayout[6] = findViewById(R.id.monthLinearLayout6);
        monthLinearLayout[7] = findViewById(R.id.monthLinearLayout7);
        monthLinearLayout[8] = findViewById(R.id.monthLinearLayout8);
        monthLinearLayout[9] = findViewById(R.id.monthLinearLayout9);
        monthLinearLayout[10] = findViewById(R.id.monthLinearLayout10);
        monthLinearLayout[11] = findViewById(R.id.monthLinearLayout11);
        monthLinearLayout[12] = findViewById(R.id.monthLinearLayout12);
        monthLinearLayout[13] = findViewById(R.id.monthLinearLayout13);
        monthLinearLayout[14] = findViewById(R.id.monthLinearLayout14);
        monthLinearLayout[15] = findViewById(R.id.monthLinearLayout15);
        monthLinearLayout[16] = findViewById(R.id.monthLinearLayout16);
        monthLinearLayout[17] = findViewById(R.id.monthLinearLayout17);
        monthLinearLayout[18] = findViewById(R.id.monthLinearLayout18);
        monthLinearLayout[19] = findViewById(R.id.monthLinearLayout19);
        monthDay[0] = findViewById(R.id.monthDay0);
        monthDay[1] = findViewById(R.id.monthDay1);
        monthDay[2] = findViewById(R.id.monthDay2);
        monthDay[3] = findViewById(R.id.monthDay3);
        monthDay[4] = findViewById(R.id.monthDay4);
        monthDay[5] = findViewById(R.id.monthDay5);
        monthDay[6] = findViewById(R.id.monthDay6);
        monthDay[7] = findViewById(R.id.monthDay7);
        monthDay[8] = findViewById(R.id.monthDay8);
        monthDay[9] = findViewById(R.id.monthDay9);
        monthDay[10] = findViewById(R.id.monthDay10);
        monthDay[11] = findViewById(R.id.monthDay11);
        monthDay[12] = findViewById(R.id.monthDay12);
        monthDay[13] = findViewById(R.id.monthDay13);
        monthDay[14] = findViewById(R.id.monthDay14);
        monthDay[15] = findViewById(R.id.monthDay15);
        monthDay[16] = findViewById(R.id.monthDay16);
        monthDay[17] = findViewById(R.id.monthDay17);
        monthDay[18] = findViewById(R.id.monthDay18);
        monthDay[19] = findViewById(R.id.monthDay19);
        monthSchedule[0] = findViewById(R.id.monthSchedule0);
        monthSchedule[1] = findViewById(R.id.monthSchedule1);
        monthSchedule[2] = findViewById(R.id.monthSchedule2);
        monthSchedule[3] = findViewById(R.id.monthSchedule3);
        monthSchedule[4] = findViewById(R.id.monthSchedule4);
        monthSchedule[5] = findViewById(R.id.monthSchedule5);
        monthSchedule[6] = findViewById(R.id.monthSchedule6);
        monthSchedule[7] = findViewById(R.id.monthSchedule7);
        monthSchedule[8] = findViewById(R.id.monthSchedule8);
        monthSchedule[9] = findViewById(R.id.monthSchedule9);
        monthSchedule[10] = findViewById(R.id.monthSchedule10);
        monthSchedule[11] = findViewById(R.id.monthSchedule11);
        monthSchedule[12] = findViewById(R.id.monthSchedule12);
        monthSchedule[13] = findViewById(R.id.monthSchedule13);
        monthSchedule[14] = findViewById(R.id.monthSchedule14);
        monthSchedule[15] = findViewById(R.id.monthSchedule15);
        monthSchedule[16] = findViewById(R.id.monthSchedule16);
        monthSchedule[17] = findViewById(R.id.monthSchedule17);
        monthSchedule[18] = findViewById(R.id.monthSchedule18);
        monthSchedule[19] = findViewById(R.id.monthSchedule19);

        scheduleHandler = new ScheduleHandler();
        if (getIntent().hasExtra("MainSchedule")) {
            mainSchedule = (MainSchedule) getIntent().getSerializableExtra("MainSchedule");
            setMonthSchedule();
        } else {
            mainSchedule = new MainSchedule();
            ScheduleThread scheduleThread = new ScheduleThread();
            scheduleThread.start();
        }
    }

    // 월간일정 or 연간일정 으로 텍스트 변환 및 visiblity 설정
    public void onMenuBtnClicked(View view) {
        String str = (String) menuBtn.getText();
//        월간일정을 보여주는 경우
        if (str.equals("월간일정")) {
            menuBtn.setText("연간일정");
            monthUpperMenu.setVisibility(View.VISIBLE);
            layout_monthSchedule.setVisibility(View.VISIBLE);
            layout_yearSchedule1.setVisibility(View.GONE);
//            findViewById(R.id.yearScheduleText).setVisibility(View.GONE);
        }
//        연간일정을 보여주는 경우
        else {
            menuBtn.setText("월간일정");
            monthUpperMenu.setVisibility(View.GONE);
            layout_monthSchedule.setVisibility(View.GONE);
            layout_yearSchedule1.setVisibility(View.VISIBLE);
        }
    }

    public void setMonthSchedule() {
        listIndex.setText(mainSchedule.getYear() + "년 " + mainSchedule.getMonth() + "월 주요일정");

        Vector<String[]> monthContent = this.mainSchedule.getMonthContent();

        for (int i = 1; i < SCHEDULENUM; i++) {
            monthLinearLayout[i].setVisibility(View.GONE);
        }

        Log.e("monthContent.size()", String.valueOf(monthContent.size()));
        int[] weekDay = mainSchedule.getWeekDay();
        for (int i = 0; i < monthContent.size(); i++) {
//            monthDay[i].setText(monthContent.get(i)[0]);
            int num = Integer.parseInt(monthContent.get(i)[0]) % 7;
            switch (weekDay[num]) {
                case 0:
                    monthDay[i].setText(monthContent.get(i)[0] + " (일)");
                    monthDay[i].setTextColor(Color.parseColor(RED));
                    break;
                case 1:
                    monthDay[i].setText(monthContent.get(i)[0] + " (월)");
                    monthDay[i].setTextColor(Color.parseColor(BLACK));
                    break;
                case 2:
                    monthDay[i].setText(monthContent.get(i)[0] + " (화)");
                    monthDay[i].setTextColor(Color.parseColor(BLACK));
                    break;
                case 3:
                    monthDay[i].setText(monthContent.get(i)[0] + " (수)");
                    monthDay[i].setTextColor(Color.parseColor(BLACK));
                    break;
                case 4:
                    monthDay[i].setText(monthContent.get(i)[0] + " (목)");
                    monthDay[i].setTextColor(Color.parseColor(BLACK));
                    break;
                case 5:
                    monthDay[i].setText(monthContent.get(i)[0] + " (금)");
                    monthDay[i].setTextColor(Color.parseColor(BLACK));
                    break;
                case 6:
                    monthDay[i].setText(monthContent.get(i)[0] + " (토)");
                    monthDay[i].setTextColor(Color.parseColor(BLUE));
                    break;
                default:
                    monthDay[i].setTextColor(Color.parseColor(BLACK));
                    break;
            }
            monthSchedule[i].setText(monthContent.get(i)[1]);
            monthLinearLayout[i].setVisibility(View.VISIBLE);
            Log.e(monthContent.get(i)[0], monthContent.get(i)[1]);
        }

        if (monthContent.isEmpty()) {
            monthDay[0].setText("");
            monthSchedule[0].setText("이번 달은 예정된 주요일정이 존재하지 않습니다.");
        }
    }

    public void onBackBtnClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("MainSchedule", mainSchedule);
        intent.putExtra("example_schedule", "okay");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onPrevBtnClicked(View view) {
//        for (int i = 1; i < SCHEDULENUM; i++)
//            monthLinearLayout[i].setVisibility(View.GONE);
//        monthSchedule[0].setText("데이터를 가져오고 있습니다.\n잠시만 기다려주세요.");
//        monthDay[0].setText("");
        myProgressBar.progressON(this, null);

        findViewById(R.id.nextBtn).setClickable(false);
        ((Button) findViewById(R.id.nextBtn)).setTextColor(Color.parseColor(CLICKUNABLE));
        findViewById(R.id.prevBtn).setClickable(false);
        ((Button) findViewById(R.id.prevBtn)).setTextColor(Color.parseColor(CLICKUNABLE));

        int year = mainSchedule.getYear();
        int month = mainSchedule.getMonth();
        if (month == 1) {
            mainSchedule.setDate(year - 1, 12);
        } else {
            mainSchedule.setDate(year, month - 1);
        }

        ScheduleThread scheduleThread = new ScheduleThread(1);
        scheduleThread.start();
    }

    public void onNextBtnClicked(View view) {
//        for (int i = 1; i < SCHEDULENUM; i++)
//            monthLinearLayout[i].setVisibility(View.GONE);
//        monthSchedule[0].setText("데이터를 가져오고 있습니다.\n잠시만 기다려주세요.");
//        monthDay[0].setText("");
        myProgressBar.progressON(this, null);

        findViewById(R.id.nextBtn).setClickable(false);
        ((Button) findViewById(R.id.nextBtn)).setTextColor(Color.parseColor(CLICKUNABLE));
        findViewById(R.id.prevBtn).setClickable(false);
        ((Button) findViewById(R.id.prevBtn)).setTextColor(Color.parseColor(CLICKUNABLE));

        int year = mainSchedule.getYear();
        int month = mainSchedule.getMonth();
        if (month == 12) {
            mainSchedule.setDate(year + 1, 1);
        } else {
            mainSchedule.setDate(year, month + 1);
        }

        ScheduleThread scheduleThread = new ScheduleThread(2);
        scheduleThread.start();
    }

    class ScheduleHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            if (!(bundle.isEmpty())) {
                if (bundle.getBoolean("isMainSchedule")) {
                    setMonthSchedule();
                }

                if (bundle.getBoolean("isPrevBtnClicked") || bundle.getBoolean("isNextBtnClicked")) {
                    findViewById(R.id.nextBtn).setClickable(true);
                    ((Button) findViewById(R.id.nextBtn)).setTextColor(Color.parseColor(CLICKABLE));
                    findViewById(R.id.prevBtn).setClickable(true);
                    ((Button) findViewById(R.id.prevBtn)).setTextColor(Color.parseColor(CLICKABLE));
                    myProgressBar.progressOFF();
                }
            }

        }
    }

    class ScheduleThread extends Thread {

        int select;

        public ScheduleThread() {
            select = -1;
        }

        public ScheduleThread(int m_select) {
            select = m_select;
        }

        @Override
        public void run() {
            Message message = scheduleHandler.obtainMessage();
            Bundle bundle = new Bundle();

            boolean while_break = false;
            while (!while_break) {
                try {
                    mainSchedule.crawlingSchedule();
                    while_break = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (select == 1) {
                bundle.putBoolean("isPrevBtnClicked", true);
            } else if (select == 2) {
                bundle.putBoolean("isNextBtnClicked", true);
            }
            bundle.putBoolean("isMainSchedule", true);

            message.setData(bundle);
            scheduleHandler.sendMessage(message);
        }
    }
}
