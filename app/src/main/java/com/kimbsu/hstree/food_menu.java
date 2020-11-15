package com.kimbsu.hstree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kimbsu.hstree.webCrawling.FoodMenu;

import java.io.IOException;
import java.text.ParseException;

public class food_menu extends AppCompatActivity {

    private static final String CLICKED = "#FAF4E5";
    private static final String UNCLICKED = "#FBF9F1";

    private FoodMenu foodMenu;

    // 사용자가 선택한 날짜를 알려주는 TextView
    private TextView todayString;
    // 1관 식단표와 2관 식단표를 선택할 때 누르는 버튼, 버튼의 배경색 바꿔주어야 함
    private Button firstMenuBtn, secondMenuBtn;
    // 1=1관 버튼, 2=2관 버튼 둘 중에 선택된 버튼의 번호를 저장
    private int clickedBtnNum;

    // 1관 메뉴와 2관 메뉴가 표시되는 LinearLayout, 사용자가 누른 버튼에 따라 setVisibility() 설정
    private LinearLayout firstMenuLayout, secondMenuLayout;
    // 1관과 2관의 날짜, 아침, 점심, 저녁 식단표를 표시할 TextView
    private TextView[] firstDate, firstBreakfast, firstLunch, firstDinner;
    private TextView[] secondDate, secondBreakfast, secondLunch, secondDinner;

    private FoodMenuHandler foodMenuHandler;

    private DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        init();
    }

    public void init() {
        todayString = findViewById(R.id.todayString);
        firstMenuBtn = findViewById(R.id.firstMenuBtn);
        secondMenuBtn = findViewById(R.id.secondMenuBtn);
//        맨 처음 화면에는 1관 메뉴가 먼저 표시된다.
        clickedBtnNum = 1;

        firstMenuLayout = findViewById(R.id.firstMenuLayout);
        secondMenuLayout = findViewById(R.id.secondMenuLayout);

        firstDate = new TextView[8];
        firstBreakfast = new TextView[8];
        firstLunch = new TextView[8];
        firstDinner = new TextView[8];

        secondDate = new TextView[8];
        secondBreakfast = new TextView[8];
        secondLunch = new TextView[8];
        secondDinner = new TextView[8];

        firstDate[0] = findViewById(R.id.todayDate1);
        firstDate[1] = findViewById(R.id.sunDate1);
        firstDate[2] = findViewById(R.id.monDate1);
        firstDate[3] = findViewById(R.id.tueDate1);
        firstDate[4] = findViewById(R.id.wedDate1);
        firstDate[5] = findViewById(R.id.thuDate1);
        firstDate[6] = findViewById(R.id.friDate1);
        firstDate[7] = findViewById(R.id.satDate1);
        firstBreakfast[0] = findViewById(R.id.todayBreakfast1);
        firstBreakfast[1] = findViewById(R.id.sunBreakfast1);
        firstBreakfast[2] = findViewById(R.id.monBreakfast1);
        firstBreakfast[3] = findViewById(R.id.tueBreakfast1);
        firstBreakfast[4] = findViewById(R.id.wedBreakfast1);
        firstBreakfast[5] = findViewById(R.id.thuBreakfast1);
        firstBreakfast[6] = findViewById(R.id.friBreakfast1);
        firstBreakfast[7] = findViewById(R.id.satBreakfast1);
        firstLunch[0] = findViewById(R.id.todayLunch1);
        firstLunch[1] = findViewById(R.id.sunLunch1);
        firstLunch[2] = findViewById(R.id.monLunch1);
        firstLunch[3] = findViewById(R.id.tueLunch1);
        firstLunch[4] = findViewById(R.id.wedLunch1);
        firstLunch[5] = findViewById(R.id.thuLunch1);
        firstLunch[6] = findViewById(R.id.friLunch1);
        firstLunch[7] = findViewById(R.id.satLunch1);
        firstDinner[0] = findViewById(R.id.todayDinner1);
        firstDinner[1] = findViewById(R.id.sunDinner1);
        firstDinner[2] = findViewById(R.id.monDinner1);
        firstDinner[3] = findViewById(R.id.tueDinner1);
        firstDinner[4] = findViewById(R.id.wedDinner1);
        firstDinner[5] = findViewById(R.id.thuDinner1);
        firstDinner[6] = findViewById(R.id.friDinner1);
        firstDinner[7] = findViewById(R.id.satDinner1);

        secondDate[0] = findViewById(R.id.todayDate2);
        secondDate[1] = findViewById(R.id.sunDate2);
        secondDate[2] = findViewById(R.id.monDate2);
        secondDate[3] = findViewById(R.id.tueDate2);
        secondDate[4] = findViewById(R.id.wedDate2);
        secondDate[5] = findViewById(R.id.thuDate2);
        secondDate[6] = findViewById(R.id.friDate2);
        secondDate[7] = findViewById(R.id.satDate2);
        secondBreakfast[0] = findViewById(R.id.todayBreakfast2);
        secondBreakfast[1] = findViewById(R.id.sunBreakfast2);
        secondBreakfast[2] = findViewById(R.id.monBreakfast2);
        secondBreakfast[3] = findViewById(R.id.tueBreakfast2);
        secondBreakfast[4] = findViewById(R.id.wedBreakfast2);
        secondBreakfast[5] = findViewById(R.id.thuBreakfast2);
        secondBreakfast[6] = findViewById(R.id.friBreakfast2);
        secondBreakfast[7] = findViewById(R.id.satBreakfast2);
        secondLunch[0] = findViewById(R.id.todayLunch2);
        secondLunch[1] = findViewById(R.id.sunLunch2);
        secondLunch[2] = findViewById(R.id.monLunch2);
        secondLunch[3] = findViewById(R.id.tueLunch2);
        secondLunch[4] = findViewById(R.id.wedLunch2);
        secondLunch[5] = findViewById(R.id.thuLunch2);
        secondLunch[6] = findViewById(R.id.friLunch2);
        secondLunch[7] = findViewById(R.id.satLunch2);
        secondDinner[0] = findViewById(R.id.todayDinner2);
        secondDinner[1] = findViewById(R.id.sunDinner2);
        secondDinner[2] = findViewById(R.id.monDinner2);
        secondDinner[3] = findViewById(R.id.tueDinner2);
        secondDinner[4] = findViewById(R.id.wedDinner2);
        secondDinner[5] = findViewById(R.id.thuDinner2);
        secondDinner[6] = findViewById(R.id.friDinner2);
        secondDinner[7] = findViewById(R.id.satDinner2);

        foodMenuHandler = new FoodMenuHandler();
        if (getIntent().hasExtra("FoodMenu")) {
            foodMenu = (FoodMenu) getIntent().getSerializableExtra("FoodMenu");
            setFoodMenu();
        } else {
            foodMenu = new FoodMenu();
            FoodMenuThread foodMenuThread = new FoodMenuThread();
            foodMenuThread.start();
        }

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month 는 선택한 값보다 1 작은 정수값이 전달된다.
//                ex) 11월을 선택하면 month = 10 이 된다.
                try {
                    if (foodMenu.setDate(year, month + 1, dayOfMonth)) {
                        if (clickedBtnNum == 1) {
                            firstMenuLayout.setVisibility(View.GONE);
                        } else if (clickedBtnNum == 2) {
                            secondMenuLayout.setVisibility(View.GONE);
                        }
                        findViewById(R.id.beforeCrawling1).setVisibility(View.VISIBLE);

                        FoodMenuThread foodMenuThread = new FoodMenuThread();
                        foodMenuThread.start();
                    } else {
                        Toast.makeText(getApplicationContext(), "이번년도 식단표만 볼 수 있습니다.\n날짜를 확인하여 다시 선택해주세요.", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    public void setFoodMenu() {
//        오늘 식단표 혹은 사용자가 선택한 날짜의 식단표를 의미, { 0=1관 아침, 1=1관 점심, 2=1관 저녁, 3=2관 아침, 4=2관 점심, 5=2관 저녁 }
        String[] menu_today = foodMenu.todayMenu();
//        이번주 혹은 사용자가 선택한 날짜가 포함된 주의 식단표를 의미
//        3*7 사이즈 2차원 배열, (아침 점심 저녁) * (일 월 화 수 목 금 토)
        String[][] menu_1gn = foodMenu.getMenu_1gn();
        String[][] menu_2gn = foodMenu.getMenu_2gn();

        todayString.setText(foodMenu.getTodayString());

        firstDate[0].setText(foodMenu.getTodayString() + " 식단표");
        firstBreakfast[0].setText("아침\n" + menu_today[0]);
        firstLunch[0].setText("점심\n" + menu_today[1]);
        firstDinner[0].setText("저녁\n" + menu_today[2]);
        secondDate[0].setText(foodMenu.getTodayString() + " 식단표");
        secondBreakfast[0].setText("아침\n" + menu_today[3]);
        secondLunch[0].setText("점심\n" + menu_today[4]);
        secondDinner[0].setText("저녁\n" + menu_today[5]);

        String[] menu_day = foodMenu.getMenu_day();
        for (int i = 1; i < 8; i++) {
            firstDate[i].setText(menu_day[i - 1]);
            secondDate[i].setText(menu_day[i - 1]);
        }

        for (int i = 1; i < 8; i++) {
            firstBreakfast[i].setText("아침\n" + menu_1gn[0][i - 1]);
            secondBreakfast[i].setText("아침\n" + menu_2gn[0][i - 1]);
        }
        for (int i = 1; i < 8; i++) {
            firstLunch[i].setText("점심\n" + menu_1gn[1][i - 1]);
            secondLunch[i].setText("점심\n" + menu_2gn[1][i - 1]);
        }
        for (int i = 1; i < 8; i++) {
            firstDinner[i].setText("저녁\n" + menu_1gn[2][i - 1]);
            secondDinner[i].setText("저녁\n" + menu_2gn[2][i - 1]);
        }

        findViewById(R.id.beforeCrawling1).setVisibility(View.GONE);
        if (clickedBtnNum == 1) {
            firstMenuLayout.setVisibility(View.VISIBLE);
            secondMenuLayout.setVisibility(View.GONE);
        } else if (clickedBtnNum == 2) {
            firstMenuLayout.setVisibility(View.GONE);
            secondMenuLayout.setVisibility(View.VISIBLE);
        }
    }

    // BACK 버튼을 눌렀을 때 이벤트 처리
    public void onBackBtnClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("example_food_menu", "okay");
        setResult(RESULT_OK, intent);
        finish();
    }

    // HOME 버튼을 눌렀을 때 이벤트 처리 -> 여기서는 미사용
    public void onHomeBtnClicked(View view) {
    }

    // 1관 메뉴를 클릭했을 때 이벤트 처리
    public void onFirstMenuBtnClicked(View view) {
        firstMenuLayout.setVisibility(View.VISIBLE);
        secondMenuLayout.setVisibility(View.GONE);

        firstMenuBtn.setBackgroundColor(Color.parseColor(CLICKED));
        firstMenuBtn.setTypeface(null, Typeface.BOLD);
        secondMenuBtn.setBackgroundColor(Color.parseColor(UNCLICKED));
        secondMenuBtn.setTypeface(null, Typeface.NORMAL);
    }

    // 2관 메뉴를 클릭했을 때 이벤트 처리
    public void onSecondMenuBtnClicked(View view) {
        firstMenuLayout.setVisibility(View.GONE);
        secondMenuLayout.setVisibility(View.VISIBLE);

        firstMenuBtn.setBackgroundColor(Color.parseColor(UNCLICKED));
        firstMenuBtn.setTypeface(null, Typeface.NORMAL);
        secondMenuBtn.setBackgroundColor(Color.parseColor(CLICKED));
        secondMenuBtn.setTypeface(null, Typeface.BOLD);
    }

    // 날짜변경 버튼을 클릭했을 때 이벤트 처리 -> DatePicker 객체 생성하여 날짜 입력 받자
    public void onChangeDateBtnClicked(View view) {
//        if (clickedBtnNum == 1) {
//            firstMenuLayout.setVisibility(View.GONE);
//        } else if (clickedBtnNum == 2) {
//            secondMenuLayout.setVisibility(View.GONE);
//        }
//        findViewById(R.id.beforeCrawling1).setVisibility(View.VISIBLE);

        // 날짜 설정 후에 새로운 쓰레드 생성해서 크롤링 하면 됨
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, foodMenu.getYear(), foodMenu.getMonth() - 1, foodMenu.getDay());
        dialog.show();
    }

    class FoodMenuHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            if (!(bundle.isEmpty())) {
                if (bundle.getBoolean("isFoodMenu")) {
                    setFoodMenu();
                }
            }
        }
    }

    class FoodMenuThread extends Thread {

        @Override
        public void run() {
            Message message = foodMenuHandler.obtainMessage();
            Bundle bundle = new Bundle();

            boolean while_break = false;
            while (!while_break) {
                try {
                    foodMenu.weekMenu();
                    while_break = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bundle.putBoolean("isFoodMenu", true);

            message.setData(bundle);
            foodMenuHandler.sendMessage(message);
        }
    }
}
