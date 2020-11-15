package com.kimbsu.hstree;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class notice_post extends AppCompatActivity {

    private TextView postTitle, postInfo;
    private ImageView[] postImg;

    private ArrayList<String> postImgUrl;
    private String postTitleStr, postInfoStr, postUrl;
    private boolean isImgExist;

    private TextView[] downloadUrl;
    private ArrayList<String[]> download;

    private MyProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_post);

        myProgressBar = new MyProgressBar();
        myProgressBar.progressON(this, null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }).start();
    }

    public void init() {

        postTitle = findViewById(R.id.postTitle);
        postInfo = findViewById(R.id.postInfo);
//        postImg = findViewById(R.id.postImg);
        postImg = new ImageView[5];
        postImg[0] = findViewById(R.id.postImg0);
        postImg[1] = findViewById(R.id.postImg1);
        postImg[2] = findViewById(R.id.postImg2);
        postImg[3] = findViewById(R.id.postImg3);
        postImg[4] = findViewById(R.id.postImg4);

        postUrl = (String) getIntent().getStringExtra("postUrl");
        postTitleStr = (String) getIntent().getStringExtra("postTitle");
        postInfoStr = (String) getIntent().getStringExtra("postInfo");
        postImgUrl = (ArrayList<String>) getIntent().getSerializableExtra("postImg");
        isImgExist = (boolean) getIntent().getBooleanExtra("isImgExist", false);
        Log.e("notice_post_postImfoStr", postInfoStr);

        postTitle.setText(postTitleStr);
        postInfo.setText(postInfoStr);

        downloadUrl = new TextView[5];
        downloadUrl[0] = findViewById(R.id.downloadUrl0);
        downloadUrl[1] = findViewById(R.id.downloadUrl1);
        downloadUrl[2] = findViewById(R.id.downloadUrl2);
        downloadUrl[3] = findViewById(R.id.downloadUrl3);
        downloadUrl[4] = findViewById(R.id.downloadUrl4);
        download = (ArrayList<String[]>) getIntent().getSerializableExtra("download");

        if (isImgExist) {
            findViewById(R.id.postUrl).setVisibility(View.GONE);
            for (int i = 0; i < postImgUrl.size(); i++) {
                if (i == 5) break;
                postImg[i].setVisibility(View.VISIBLE);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    connectImgtoView(postImgUrl.size());
                }
            }).start();

            if ((download) != null) {
                if (download.size() > 0) {
                    findViewById(R.id.downloadLayout).setVisibility(View.VISIBLE);
                    for (int i = 0; i < download.size(); i++) {
                        if (i == downloadUrl.length) break;

                        downloadUrl[i].setText(download.get(i)[0]);
                        downloadUrl[i].setVisibility(View.VISIBLE);
                    }
                }
            }

        } else {
            myProgressBar.progressOFF();
            findViewById(R.id.postUrl).setVisibility(View.VISIBLE);
//            findViewById(R.id.postImg).setVisibility(View.GONE);
            for (int i = 0; i < postImgUrl.size(); i++) {
                if (i == 5) break;
                postImg[i].setVisibility(View.GONE);
            }
            findViewById(R.id.downloadLayout).setVisibility(View.GONE);

            Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return "";
                }
            };

            Pattern pattern1 = Pattern.compile("여기");
            Linkify.addLinks((TextView) findViewById(R.id.postUrl), pattern1, postUrl, null, mTransform);
        }

    }


    public void onBackBtnClicked(View view) {
//        postImg.setImageBitmap(null);
        for (int i = 0; i < postImgUrl.size(); i++) {
            if (i == 5) break;
            postImg[i].setImageBitmap(null);
        }
        Intent intent = new Intent();
        intent.putExtra("example_notice_post", "okay");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onDownloadUrlClicked(View view) {
        TextView textView = findViewById(view.getId());
        String str = (String) textView.getText();
        String url = "";
        for (int i = 0; i < download.size(); i++) {
            if (str.equals(download.get(i)[0])) {
                url = download.get(i)[1];
                break;
            }
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void connectImgtoView(final int max) {
        for (int i = 0; i < max; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    try {
                        Log.e("Image URL", postImgUrl.get(finalI));
                        url = new URL(postImgUrl.get(finalI));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    try {
                        final BufferedInputStream bufferedInputStream
                                = new BufferedInputStream(url.openStream());
                        Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                        bufferedInputStream.close();

                        Log.e("width", String.valueOf(bitmap.getWidth()));
                        Log.e("height", String.valueOf(bitmap.getHeight()));
                        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(
                                bitmap,
                                (int) (992),
                                (int) (1403),
                                true
                        );

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                postImg[finalI].setImageBitmap(scaledBitmap);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myProgressBar.progressOFF();
                }
            }).start();
        }
    }

}
