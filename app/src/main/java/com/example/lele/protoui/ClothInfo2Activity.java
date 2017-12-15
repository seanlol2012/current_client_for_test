package com.example.lele.protoui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ClothInfo2Activity extends AppCompatActivity {

    private ImageView backBtn;
    private ImageView feel_love;
    private ImageView feel_like;
    private ImageView feel_soso;
    private ImageView feel_dislike;
    private ImageView stamp;
    private ImageView clothImage;
    private TextView text;
    private TextView barTitle;
    private TextView displayinfo;
    private int score;
    private int flag;

    private String picIndex;
    private String[] attr_value;
    private String user_name;

    private static String IMAGEPATH = "http://192.168.1.66:8080/fashion_server/recommendPic";
    private static String FEEDBACKPATH = "http://192.168.1.66:8080/fashion_server/getFeedbackLevel";
    private static URL url;

    Map<String, String> FeedbackData = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_info2);
        barTitle = findViewById(R.id.title_bar_name);
        barTitle.setText(R.string.barTitle_cloth);
        //返回--------------------------------------------------------------------------------------
        backBtn = findViewById(R.id.title_bar_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
