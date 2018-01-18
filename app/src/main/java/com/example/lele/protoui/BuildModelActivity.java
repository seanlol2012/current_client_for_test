package com.example.lele.protoui;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BuildModelActivity extends AppCompatActivity {

    private EditText question_index;
    private EditText option_index;
    private EditText username;
    private String question_index_str;
    private String option_index_str;
    private Button upload_information;
    private Button get_recommend;

    private String pic_name;
    private String user_name;

    private static String FEEDBACKPATH = "http://192.168.1.66:8080/fashion_server/getFeedbackLevel2";
    private static String FEEDBACKPATH2 = "http://192.168.1.66:8080/fashion_server/getPicFromPersonalModel";
    private static URL url;

    Map<String, String> FeedbackData = new HashMap<String, String>();
    Map<String, String> FeedbackData2 = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_model);

        username = findViewById(R.id.username);
        upload_information = findViewById(R.id.modifybtn);
        get_recommend = findViewById(R.id.getRecommend);
        question_index = findViewById(R.id.question_index);
        option_index = findViewById(R.id.option_index);

        upload_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_index_str = question_index.getText().toString();
                option_index_str = option_index.getText().toString();
                user_name = username.getText().toString();

                if(question_index_str.equals("1")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Ace_&_Jig/1.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Ace_&_Jig/3.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Ace_&_Jig/11.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Ace_&_Jig/17.jpg";
                    }
                }
                if(question_index_str.equals("2")){
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Band_of_Outsiders/0.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Band_of_Outsiders/3.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Band_of_Outsiders/17.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Band_of_Outsiders/20.jpg";
                    }
                }
                if(question_index_str.equals("3")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Central_Saint_Martins/41.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Central_Saint_Martins/98.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Central_Saint_Martins/121.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Central_Saint_Martins/129.jpg";
                    }
                }
                if(question_index_str.equals("4")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Daks/0.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Daks/9.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Daks/22.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2015-ready-to-wear/Daks/25.jpg";
                    }
                }
                if(question_index_str.equals("5")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Chanel/0.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Chanel/2.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Chanel/10.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Chanel/59.jpg";
                    }
                }
                if(question_index_str.equals("6")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Aquascutum/2.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Aquascutum/11.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Aquascutum/32.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Aquascutum/49.jpg";
                    }
                }
                if(question_index_str.equals("7")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/spring-2010-ready-to-wear/Chanel/6.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/spring-2010-ready-to-wear/Chanel/34.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/spring-2010-ready-to-wear/Chanel/36.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/spring-2010-ready-to-wear/Chanel/40.jpg";
                    }
                }
                if(question_index_str.equals("8")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/spring-2005-ready-to-wear/Ralph_Lauren/19.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/spring-2005-ready-to-wear/Ralph_Lauren/20.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/spring-2005-ready-to-wear/Ralph_Lauren/23.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/spring-2005-ready-to-wear/Ralph_Lauren/31.jpg";
                    }
                }
                if(question_index_str.equals("9")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/spring-2009-ready-to-wear/Chanel/18.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/spring-2009-ready-to-wear/Chanel/46.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/spring-2009-ready-to-wear/Chanel/49.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/spring-2009-ready-to-wear/Chanel/55.jpg";
                    }
                }
                if(question_index_str.equals("10")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/spring-2009-ready-to-wear/Derercuny/5.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/spring-2009-ready-to-wear/Derercuny/9.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/spring-2009-ready-to-wear/Derercuny/11.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/spring-2009-ready-to-wear/Derercuny/19.jpg";
                    }
                }
                if(question_index_str.equals("11")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Aquascutum/22.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Aquascutum/25.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Aquascutum/52.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2005-ready-to-wear/Aquascutum/55.jpg";
                    }
                }
                if(question_index_str.equals("12")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/3.1_Phillip_Lim/2.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/3.1_Phillip_Lim/6.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/3.1_Phillip_Lim/16.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/3.1_Phillip_Lim/19.jpg";
                    }
                }
                if(question_index_str.equals("13")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/Sophia_Kokosalaki/4.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/Sophia_Kokosalaki/8.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/Temperley_London/2.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/Temperley_London/15.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/Temperley_London/16.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/Temperley_London/17.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2006-ready-to-wear/Temperley_London/19.jpg";
                    }
                }
                if(question_index_str.equals("14")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2009-ready-to-wear/Aquascutum/4.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2009-ready-to-wear/Aquascutum/5.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2009-ready-to-wear/Aquascutum/31.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2009-ready-to-wear/Aquascutum/35.jpg";
                    }
                }
                if(question_index_str.equals("15")) {
                    if (option_index_str.equals("1")) {
                        pic_name = "/home/gzx/women_style/fall-2010-ready-to-wear/Vanessa_Bruno/22.jpg";
                    } else if (option_index_str.equals("2")) {
                        pic_name = "/home/gzx/women_style/fall-2010-ready-to-wear/Vanessa_Bruno/23.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2010-ready-to-wear/Vanessa_Bruno/39.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2010-ready-to-wear/Vivienne_Westwood_Red_Label/2.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2010-ready-to-wear/Vivienne_Westwood_Red_Label/10.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2010-ready-to-wear/Vivienne_Westwood_Red_Label/13.jpg";
                    } else if (option_index_str.equals("3")) {
                        pic_name = "/home/gzx/women_style/fall-2010-ready-to-wear/Vivienne_Westwood_Red_Label/15.jpg";
                    } else {
                        pic_name = "/home/gzx/women_style/fall-2010-ready-to-wear/Vivienne_Westwood_Red_Label/20.jpg";
                    }
                }

                FeedbackData.put("username", user_name);
                FeedbackData.put("picname",pic_name);
                FeedbackData.put("feedbackLevel", "level2");
                new BuildModelActivity.feedback2().start();
            }
        });

        get_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = username.getText().toString();

                FeedbackData2.put("username", user_name);
                new BuildModelActivity.feedback3().start();
            }
        });
    }

    private class feedback2 extends Thread {
        public void run() {
            try {
                url = new URL(FEEDBACKPATH);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : FeedbackData.entrySet()) {
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
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length",
                        String.valueOf(myData.length));
                // 获得输出流，向服务器输出内容
                OutputStream outputStream = urlConnection.getOutputStream();
                // 写入数据
                outputStream.write(myData, 0, myData.length);
                outputStream.close();
                // 获得服务器响应结果和状态码
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    // 取回响应的结果
                    Message msg = Message.obtain();
                    msg.what = 204;
                    msg.obj = changeInputStream(urlConnection.getInputStream(), "UTF-8");
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class feedback3 extends Thread {
        public void run() {
            try {
                url = new URL(FEEDBACKPATH2);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : FeedbackData2.entrySet()) {
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
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length",
                        String.valueOf(myData.length));
                // 获得输出流，向服务器输出内容
                OutputStream outputStream = urlConnection.getOutputStream();
                // 写入数据
                outputStream.write(myData, 0, myData.length);
                outputStream.close();
                // 获得服务器响应结果和状态码
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    // 取回响应的结果
                    Message msg = Message.obtain();
                    msg.what = 204;
                    msg.obj = changeInputStream(urlConnection.getInputStream(), "UTF-8");
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String changeInputStream(InputStream inputStream, String encode) {
        // 内存流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = null;
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
                result = new String(byteArrayOutputStream.toByteArray(), encode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //将下载的图片显示
            if (msg.what == 204) {
                String s = (String) msg.obj;
                //Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
                if (s.contains("server: update database successfully")) {
                    Toast.makeText(BuildModelActivity.this, s, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(BuildModelActivity.this, s, Toast.LENGTH_LONG).show();
                }
            }
        }
    };
}
