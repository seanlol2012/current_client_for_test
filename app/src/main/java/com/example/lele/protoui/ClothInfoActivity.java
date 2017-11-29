package com.example.lele.protoui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.example.lele.protoui.R.id;
import static com.example.lele.protoui.R.layout;

public class ClothInfoActivity extends AppCompatActivity {

    private ImageView backBtn;
    private ImageView feel_love;
    private ImageView feel_like;
    private ImageView feel_soso;
    private ImageView feel_dislike;
    private ImageView stamp;
    private ImageView clothImage;
    private TextView text;
    private TextView barTitle;
    private int score;
    private int flag;

    private String picIndex;
    private String[] attr_value;
    private String user_name;

    private static String IMAGEPATH = "http://192.168.1.66:8080/fashion_server/recommendPic";
    private static URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_cloth_info);
        barTitle = findViewById(id.title_bar_name);
        barTitle.setText(R.string.barTitle_cloth);
        //返回--------------------------------------------------------------------------------------
        backBtn = findViewById(id.title_bar_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取上一页面传递的信息
        Bundle bundle = this.getIntent().getExtras();
        attr_value = bundle.getStringArray("clothingInfo");
        user_name = bundle.getString("userName");
        picIndex = bundle.getString("picIndex");

        //显示对应服装图像
        clothImage = findViewById(id.clothImage);
        downloadphoto();

        //用户评分----------------------------------------------------------------------------------
        score = UserScore();
    }

    private void downloadphoto() {
        //开启线程，网络下载图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                //post方法向服务器传入用户名与索引，取对应的图像
                map.put("username", user_name);
                map.put("index",picIndex);
                Message msg = Message.obtain();
                Bitmap[] bm = new Bitmap[1];

                try {
                    url = new URL(IMAGEPATH);
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                StringBuilder stringBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    try {
                        stringBuilder
                                .append(entry.getKey())
                                .append("=")
                                .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                                .append("&");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(3000);
                    urlConnection.setRequestMethod("POST"); // 以post请求方式提交
                    urlConnection.setDoInput(true); // 读取数据
                    urlConnection.setDoOutput(true); // 向服务器写数据
                    // 获取上传信息的大小和长度
                    byte[] myData = stringBuilder.toString().getBytes();
                    // 设置请求体的类型是文本类型,表示当前提交的是文本数据
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Content-Length", String.valueOf(myData.length));
                    // 获得输出流，向服务器输出内容
                    OutputStream outputStream = urlConnection.getOutputStream();
                    // 写入数据
                    outputStream.write(myData, 0, myData.length);
                    outputStream.close();
                    // 获得服务器响应结果和状态码
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == 200) {
                        // 取回响应的结果
                        Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
                        bm[0]=bitmap;
                        //clothImage.setImageBitmap(bitmap);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }

                msg.what=202;
                msg.obj = bm;
                handler.sendMessage(msg);
            }
        }

        ).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //将下载的图片显示
            if(msg.what == 202){
                Bitmap[] bm=(Bitmap[])msg.obj;

                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemImage",bm[0]);
                clothImage.setImageBitmap(bm[0]);

                //mSimpleAdapter.notifyDataSetChanged();
            }
        }
    };

    private int UserScore() {
        text = findViewById(id.textView4);
        feel_love = findViewById(id.feel_love);
        feel_like = findViewById(id.feel_like);
        feel_soso = findViewById(id.feel_soso);
        feel_dislike = findViewById(id.feel_dislike);
        stamp = findViewById(id.stamp);
        score = 0;
        //监听
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case id.feel_love:
                        score = 0;
                        text.setText("Love");
                        stamp.setVisibility(View.VISIBLE);
                        feel_love.setBackgroundResource(R.drawable.heart_2a2a2a);
                        if (flag == 1) {
                            score = -1;
                            text.setText("Unset");
                            feel_love.setBackgroundResource(R.drawable.heart2_2a2a2a);
                            stamp.setVisibility(View.INVISIBLE);
                            flag = 0;
                        } else flag = 1;
                        feel_like.setBackgroundResource(R.drawable.smile_2a2a2a);
                        feel_soso.setBackgroundResource(R.drawable.meh_2a2a2a);
                        feel_dislike.setBackgroundResource(R.drawable.frown_2a2a2a);
                        stamp.setBackgroundResource(R.drawable.stamp_love);
                        break;
                    case id.feel_like:
                        score = 1;
                        text.setText("Like");
                        stamp.setVisibility(View.VISIBLE);
                        feel_like.setBackgroundResource(R.drawable.smile2_2a2a2a);
                        if (flag == 2) {
                            score = -1;
                            text.setText("Unset");
                            feel_like.setBackgroundResource(R.drawable.smile_2a2a2a);
                            stamp.setVisibility(View.INVISIBLE);
                            flag = 0;
                        } else flag = 2;
                        feel_love.setBackgroundResource(R.drawable.heart2_2a2a2a);
                        feel_soso.setBackgroundResource(R.drawable.meh_2a2a2a);
                        feel_dislike.setBackgroundResource(R.drawable.frown_2a2a2a);
                        stamp.setBackgroundResource(R.drawable.stamp_like);
                        break;
                    case id.feel_soso:
                        score = 2;
                        text.setText("Soso");
                        stamp.setVisibility(View.VISIBLE);
                        feel_soso.setBackgroundResource(R.drawable.meh2_2a2a2a);
                        if (flag == 3) {
                            score = -1;
                            text.setText("Unset");
                            feel_soso.setBackgroundResource(R.drawable.meh_2a2a2a);
                            stamp.setVisibility(View.INVISIBLE);
                            flag = 0;
                        } else flag = 3;
                        feel_love.setBackgroundResource(R.drawable.heart2_2a2a2a);
                        feel_like.setBackgroundResource(R.drawable.smile_2a2a2a);
                        feel_dislike.setBackgroundResource(R.drawable.frown_2a2a2a);
                        stamp.setBackgroundResource(R.drawable.stamp_soso);
                        break;
                    case id.feel_dislike:
                        score = 3;
                        text.setText("Dislike");
                        feel_dislike.setBackgroundResource(R.drawable.frown2_2a2a2a);
                        stamp.setVisibility(View.VISIBLE);
                        if (flag == 4) {
                            score = -1;
                            text.setText("Unset");
                            feel_dislike.setBackgroundResource(R.drawable.frown_2a2a2a);
                            stamp.setVisibility(View.INVISIBLE);
                            flag = 0;
                        } else flag = 4;
                        feel_love.setBackgroundResource(R.drawable.heart2_2a2a2a);
                        feel_like.setBackgroundResource(R.drawable.smile_2a2a2a);
                        feel_soso.setBackgroundResource(R.drawable.meh_2a2a2a);
                        stamp.setBackgroundResource(R.drawable.stamp_dislike);
                        break;
                }
            }
        };
        feel_love.setOnClickListener(listener);
        feel_like.setOnClickListener(listener);
        feel_soso.setOnClickListener(listener);
        feel_dislike.setOnClickListener(listener);
        //返回分数
        return score;
    }
}
