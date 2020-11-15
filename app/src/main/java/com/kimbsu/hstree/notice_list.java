package com.kimbsu.hstree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kimbsu.hstree.webCrawling.NormalNotice;
import com.kimbsu.hstree.webCrawling.Notice;
import com.kimbsu.hstree.webCrawling.UpperNotice;

import java.io.IOException;
import java.util.Vector;

public class notice_list extends AppCompatActivity {

    //    리스트로 보여줄 일반 공지사항 10개의 제목에 대한 변수,
//    상단 고정 공지사항은 개수가 유동적이므로 동적으로 객체 생성할 예정
    private TextView[] normalNotice;
    private TextView listIndex;

    private Notice notice;
    private String selectNormalNoticeUrl;

    private NoticeListHandler noticeListHandler;

    private MyProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        init();
    }

    public void init() {
        myProgressBar = new MyProgressBar();

        normalNotice = new TextView[10];
        normalNotice[0] = findViewById(R.id.normalNotice0);
        normalNotice[1] = findViewById(R.id.normalNotice1);
        normalNotice[2] = findViewById(R.id.normalNotice2);
        normalNotice[3] = findViewById(R.id.normalNotice3);
        normalNotice[4] = findViewById(R.id.normalNotice4);
        normalNotice[5] = findViewById(R.id.normalNotice5);
        normalNotice[6] = findViewById(R.id.normalNotice6);
        normalNotice[7] = findViewById(R.id.normalNotice7);
        normalNotice[8] = findViewById(R.id.normalNotice8);
        normalNotice[9] = findViewById(R.id.normalNotice9);

        listIndex = findViewById(R.id.listIndex);

        notice = (Notice) getIntent().getSerializableExtra("Notice");
        if (notice != null) {
            setUpperNoticeList_r1(notice.getM_upperNotice());
            setNormalNoticeList_r1(notice.getNormalNotice());

            listIndex.setText(notice.getListIndex());
        } else {
            normalNotice[0].setText("System error occurs.");
            normalNotice[1].setText("System error occurs.");
            normalNotice[2].setText("System error occurs.");
            normalNotice[3].setText("System error occurs.");
            normalNotice[4].setText("System error occurs.");
            normalNotice[5].setText("System error occurs.");
            normalNotice[6].setText("System error occurs.");
            normalNotice[7].setText("System error occurs.");
            normalNotice[8].setText("System error occurs.");
            normalNotice[9].setText("System error occurs.");

            listIndex.setText(notice.getListIndex());
        }

        selectNormalNoticeUrl = "";
        noticeListHandler = new NoticeListHandler();

        if ((boolean) getIntent().getBooleanExtra("isHomeNoticeIndex", false)) {
            int index = (int) getIntent().getIntExtra("homeNoticeIndex", 0);
            switch (index) {
                case 0:
                    findViewById(R.id.normalNotice0).callOnClick();
                    break;
                case 1:
                    findViewById(R.id.normalNotice1).callOnClick();
                    break;
                case 2:
                    findViewById(R.id.normalNotice2).callOnClick();
                    break;
                case 3:
                    findViewById(R.id.normalNotice3).callOnClick();
                    break;
                default:
                    break;
            }
        }

    }

    public void setUpperNoticeList_r1(Vector<UpperNotice> notice) {
        TextView[] upperNotice = new TextView[10];
        upperNotice[0] = findViewById(R.id.text_upperNotice0);
        upperNotice[1] = findViewById(R.id.text_upperNotice1);
        upperNotice[2] = findViewById(R.id.text_upperNotice2);
        upperNotice[3] = findViewById(R.id.text_upperNotice3);
        upperNotice[4] = findViewById(R.id.text_upperNotice4);
        upperNotice[5] = findViewById(R.id.text_upperNotice5);
        upperNotice[6] = findViewById(R.id.text_upperNotice6);
        upperNotice[7] = findViewById(R.id.text_upperNotice7);
        upperNotice[8] = findViewById(R.id.text_upperNotice8);
        upperNotice[9] = findViewById(R.id.text_upperNotice9);

        LinearLayout[] goneToVisible = new LinearLayout[10];
        goneToVisible[0] = findViewById(R.id.upperNotice0);
        goneToVisible[1] = findViewById(R.id.upperNotice1);
        goneToVisible[2] = findViewById(R.id.upperNotice2);
        goneToVisible[3] = findViewById(R.id.upperNotice3);
        goneToVisible[4] = findViewById(R.id.upperNotice4);
        goneToVisible[5] = findViewById(R.id.upperNotice5);
        goneToVisible[6] = findViewById(R.id.upperNotice6);
        goneToVisible[7] = findViewById(R.id.upperNotice7);
        goneToVisible[8] = findViewById(R.id.upperNotice8);
        goneToVisible[9] = findViewById(R.id.upperNotice9);

        for (int i = 0; i < notice.size(); i++) {
            if (i == 10) break;
            upperNotice[i].setText(notice.get(i).getTitle());
            goneToVisible[i].setVisibility(View.VISIBLE);
        }
    }

    public void setNormalNoticeList_r1(Vector<NormalNotice> notice) {
        if (!(notice.isEmpty()) && (notice.size() == 10)) {
            SpannableStringBuilder sb0 = new SpannableStringBuilder();
            sb0.append(notice.get(0).getTitle() + "\n" + notice.get(0).getWriter() + " | " + notice.get(0).getWriteDay() + " | " + notice.get(0).getHits());
            sb0.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(0).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb0.setSpan(new AbsoluteSizeSpan(13, true), notice.get(0).getTitle().length(), sb0.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[0].setText(sb0);

            SpannableStringBuilder sb1 = new SpannableStringBuilder();
            sb1.append(notice.get(1).getTitle() + "\n" + notice.get(1).getWriter() + " | " + notice.get(1).getWriteDay() + " | " + notice.get(1).getHits());
            sb1.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(1).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb1.setSpan(new AbsoluteSizeSpan(13, true), notice.get(1).getTitle().length(), sb1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[1].setText(sb1);

            SpannableStringBuilder sb2 = new SpannableStringBuilder();
            sb2.append(notice.get(2).getTitle() + "\n" + notice.get(2).getWriter() + " | " + notice.get(2).getWriteDay() + " | " + notice.get(2).getHits());
            sb2.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(2).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb2.setSpan(new AbsoluteSizeSpan(13, true), notice.get(2).getTitle().length(), sb2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[2].setText(sb2);

            SpannableStringBuilder sb3 = new SpannableStringBuilder();
            sb3.append(notice.get(3).getTitle() + "\n" + notice.get(3).getWriter() + " | " + notice.get(3).getWriteDay() + " | " + notice.get(3).getHits());
            sb3.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(3).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb3.setSpan(new AbsoluteSizeSpan(13, true), notice.get(3).getTitle().length(), sb3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[3].setText(sb3);

            SpannableStringBuilder sb4 = new SpannableStringBuilder();
            sb4.append(notice.get(4).getTitle() + "\n" + notice.get(4).getWriter() + " | " + notice.get(4).getWriteDay() + " | " + notice.get(4).getHits());
            sb4.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(4).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb4.setSpan(new AbsoluteSizeSpan(13, true), notice.get(4).getTitle().length(), sb4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[4].setText(sb4);

            SpannableStringBuilder sb5 = new SpannableStringBuilder();
            sb5.append(notice.get(5).getTitle() + "\n" + notice.get(5).getWriter() + " | " + notice.get(5).getWriteDay() + " | " + notice.get(5).getHits());
            sb5.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(5).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb5.setSpan(new AbsoluteSizeSpan(13, true), notice.get(5).getTitle().length(), sb5.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[5].setText(sb5);

            SpannableStringBuilder sb6 = new SpannableStringBuilder();
            sb6.append(notice.get(6).getTitle() + "\n" + notice.get(6).getWriter() + " | " + notice.get(6).getWriteDay() + " | " + notice.get(6).getHits());
            sb6.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(6).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb6.setSpan(new AbsoluteSizeSpan(13, true), notice.get(6).getTitle().length(), sb6.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[6].setText(sb6);

            SpannableStringBuilder sb7 = new SpannableStringBuilder();
            sb7.append(notice.get(7).getTitle() + "\n" + notice.get(7).getWriter() + " | " + notice.get(7).getWriteDay() + " | " + notice.get(7).getHits());
            sb7.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(7).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb7.setSpan(new AbsoluteSizeSpan(13, true), notice.get(7).getTitle().length(), sb7.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[7].setText(sb7);

            SpannableStringBuilder sb8 = new SpannableStringBuilder();
            sb8.append(notice.get(8).getTitle() + "\n" + notice.get(8).getWriter() + " | " + notice.get(8).getWriteDay() + " | " + notice.get(8).getHits());
            sb8.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(8).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb8.setSpan(new AbsoluteSizeSpan(13, true), notice.get(8).getTitle().length(), sb8.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[8].setText(sb8);

            SpannableStringBuilder sb9 = new SpannableStringBuilder();
            sb9.append(notice.get(9).getTitle() + "\n" + notice.get(9).getWriter() + " | " + notice.get(9).getWriteDay() + " | " + notice.get(9).getHits());
            sb9.setSpan(new StyleSpan(Typeface.BOLD), 0, notice.get(9).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb9.setSpan(new AbsoluteSizeSpan(13, true), notice.get(9).getTitle().length(), sb9.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            normalNotice[9].setText(sb9);
        }
    }

    public void setListIndex(String str) {
        TextView listIndex = findViewById(R.id.listIndex);
        listIndex.setText((str));
    }

    // 메뉴 버튼 클릭시 이벤트 처리
    public void onMenuBtnClicked(View view) {

    }

    // BACK 버튼 클릭시 이벤트 처리
    public void onBackBtnClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("example_notice_list", "okay");
        setResult(RESULT_OK, intent);
        finish();
    }

    // 다음 버튼 클릭시 이벤트 처리
    public void onNextBtnClicked(View view) {
        myProgressBar.progressON(this, null);

        NoticeListThread noticeListThread = new NoticeListThread(1);
        noticeListThread.start();
    }

    // 이전 버튼 클릭시 이벤트 처리
    public void onPrevBtnClicked(View view) {
        myProgressBar.progressON(this, null);

        NoticeListThread noticeListThread = new NoticeListThread(2);
        noticeListThread.start();
    }

    // 일반 공지사항을 클릭시 이벤트 처리
    public void onNormalNoticeClicked(View view) throws InterruptedException {
        final String cutStr = "com.kimbsu.hstree:id/normalNotice";
        String viewID = getResources().getResourceName(view.getId());
        int index = Integer.parseInt(viewID.replace(cutStr, ""));

        Vector<NormalNotice> normalNotice = notice.getNormalNotice();
        this.selectNormalNoticeUrl = normalNotice.get(index).getUrl();
        String postTitle = normalNotice.get(index).getTitle();
        String postInfo = normalNotice.get(index).getWriter() + " | " + normalNotice.get(index).getWriteDay() + " | " + normalNotice.get(index).getHits();

//        선택한 공지사항 크롤링 시작
        NoticeListThread noticeListThread = new NoticeListThread(3);
        noticeListThread.start();
        noticeListThread.join();
//        선택한 공지사항 크롤링 끝

        Intent intent = new Intent(getApplicationContext(), notice_post.class);
        intent.putExtra("postUrl", selectNormalNoticeUrl);
        intent.putExtra("postTitle", postTitle);
        intent.putExtra("postInfo", postInfo);
        intent.putExtra("postImg", notice.getPostImg());
        intent.putExtra("isImgExist", notice.getIsImgExist());
        intent.putExtra("download", notice.getDownload());

        startActivityForResult(intent, 101);
    }

    public void onUpperNoticeClicked(View view) throws InterruptedException {
        final String cutStr = "com.kimbsu.hstree:id/text_upperNotice";
        String viewID = getResources().getResourceName(view.getId());
        int index = Integer.parseInt(viewID.replace(cutStr, ""));

        TextView selectTextView = findViewById(view.getId());
        String selectTitle = String.valueOf(selectTextView.getText());
//        상단 고정 공지사항의 정보를 가져오고, 선택한 제목과 일치하는 인덱스를 알아내서 url을 가져온다.
        Vector<NormalNotice> normalNotice = notice.getNormalNotice();
        String postInfo = "";

        boolean isSame = false;
        for (int i = 0; i < normalNotice.size(); i++) {
            if (normalNotice.get(i).getTitle().equals(selectTitle)) {
                postInfo = normalNotice.get(i).getWriter() + " | " + normalNotice.get(i).getWriteDay() + " | " + normalNotice.get(i).getHits();
                isSame = true;
                break;
            }
        }
        if (!isSame) {
            postInfo = "작성자 | 작성일 | 조회수";
        }

        Vector<UpperNotice> upperNotice = notice.getM_upperNotice();
        this.selectNormalNoticeUrl = upperNotice.get(index).getUrl();

//        새로운 쓰레드 생성 후, 선택한 공지사항 게시물 크롤링 해야 함
        NoticeListThread noticeListThread = new NoticeListThread(3);
        noticeListThread.start();
        noticeListThread.join();

        Intent intent = new Intent(getApplicationContext(), notice_post.class);
        intent.putExtra("postUrl", selectNormalNoticeUrl);
        intent.putExtra("postTitle", selectTitle);
        intent.putExtra("postInfo", postInfo);
        intent.putExtra("postImg", notice.getPostImg());
        intent.putExtra("isImgExist", notice.getIsImgExist());
        intent.putExtra("download", notice.getDownload());

        startActivityForResult(intent, 101);
    }

    class NoticeListHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            if (!(bundle.isEmpty())) {
//                다음 버튼을 클릭한 경우
                if (bundle.containsKey("isNext")) {
                    if (bundle.getBoolean("isNext")) {
                        setNormalNoticeList_r1(notice.getNormalNotice());
                        setListIndex(notice.getListIndex());
                    } else {
                        Toast.makeText(getApplicationContext(), "뒷 페이지가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                }
//                이전 버튼을 클릭한 경우
                else if (bundle.containsKey("isPrev")) {
                    if (bundle.getBoolean("isPrev")) {
                        setNormalNoticeList_r1(notice.getNormalNotice());
                        setListIndex(notice.getListIndex());
                    } else {
                        Toast.makeText(getApplicationContext(), "앞 페이지가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                }
                myProgressBar.progressOFF();
            }
        }
    }

    class NoticeListThread extends Thread {
        //        쓰레드로 어떤 작업을 수행할 것인지를 결정 { 1=다음버튼클릭, 2=이전버튼클릭, 3=게시물크롤링 및 액티비티전환 }
        int select;

        public NoticeListThread() {
            select = -1;
        }

        public NoticeListThread(int m_select) {
            select = m_select;
        }

        @Override
        public void run() {
            Message message = noticeListHandler.obtainMessage();
            Bundle bundle = new Bundle();
            boolean while_break = false;
//            다음 버튼 클릭시
            if (select == 1) {
                boolean isNext = false;
                if (notice.getPageNum() != notice.getMaxPageNum()) {
                    while (!while_break) {
                        try {
                            isNext = notice.nextPage();
                            while_break = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            notice.setPageNum(false);
                        }
                    }
                }
                bundle.putBoolean("isNext", isNext);
            }
//            이전 버튼 클릭시
            else if (select == 2) {
                boolean isPrev = false;
                if (notice.getPageNum() != 0) {
                    while (!while_break) {
                        try {
                            isPrev = notice.prevPage();
                            while_break = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            notice.setPageNum(true);
                        }
                    }
                }
                bundle.putBoolean("isPrev", isPrev);
            }
//            선택한 게시물 크롤링
            else if (select == 3) {
                while (!while_break) {
                    try {
                        Log.i("selectNormalNoticeUrl", selectNormalNoticeUrl);
                        notice.crawlingSelectNotice_r1(selectNormalNoticeUrl);
                        while_break = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
//            그 외의 경우 = 예외처리
            else {
                Toast.makeText(getApplicationContext(), "Non expected error is occured.", Toast.LENGTH_LONG);
            }

            message.setData(bundle);
            noticeListHandler.sendMessage(message);
        }
    }
}
