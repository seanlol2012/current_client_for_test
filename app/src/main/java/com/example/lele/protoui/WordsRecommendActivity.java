package com.example.lele.protoui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsRecommendActivity extends AppCompatActivity {

    private ImageView backBtn;
    private TextView barTitle;

    private List<CharSequence> lengthList = null;
    private ArrayAdapter<CharSequence> lengthAdapter = null;
    private Spinner lengthSpinner = null;

    private List<CharSequence> sleeveLengthList = null;
    private ArrayAdapter<CharSequence> sleeveLengthAdapter = null;
    private Spinner sleeveLengthSpinner = null;

    private List<CharSequence> collarList = null;
    private ArrayAdapter<CharSequence> collarAdapter = null;
    private Spinner collarSpinner = null;

    private List<CharSequence> modelList = null;
    private ArrayAdapter<CharSequence> modelAdapter = null;
    private Spinner modelSpinner = null;

    private List<CharSequence> patternList = null;
    private ArrayAdapter<CharSequence> patternAdapter = null;
    private Spinner patternSpinner = null;

    private TextView confirmbtn;

    private String length_value, sleeve_value, collar_value, model_value, pattern_value;
    private String user_name;

    private static String INFOPATH2 = "http://192.168.1.66:8080/fashion_server/returnClothInfo2";
    private static URL url;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 205) {
                String s = (String) msg.obj;
                String[] attr_value = s.split(",");

                Bundle bundle_toClothInfo2 = new Bundle();

                bundle_toClothInfo2.putString("user_name",user_name);
                bundle_toClothInfo2.putString("length_value",length_value);
                bundle_toClothInfo2.putString("sleeve_value",sleeve_value);
                bundle_toClothInfo2.putString("collar_value",collar_value);
                bundle_toClothInfo2.putString("model_value",model_value);
                bundle_toClothInfo2.putString("pattern_value",pattern_value);
                bundle_toClothInfo2.putStringArray("clothingInfo",attr_value);

                Intent i = new Intent(WordsRecommendActivity.this, ClothInfo2Activity.class);
                i.putExtras(bundle_toClothInfo2);
                startActivity(i);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_recommend);
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

        confirmbtn = findViewById(R.id.confirmButton);

        //string初始化
        length_value = "null";
        sleeve_value = "null";
        collar_value = "null";
        model_value = "null";
        pattern_value = "null";

        //获取上一页面传送的信息
        Bundle bundle_fromMain = this.getIntent().getExtras();
        user_name = bundle_fromMain.getString("userName");

        //处理Spinner控件
        lengthSpinner = (Spinner)super.findViewById(R.id.lengthSpinner);
        lengthSpinner.setPrompt("请选择服装长度:");
        lengthList = new ArrayList<CharSequence>();
        lengthList.add("空");
        lengthList.add("长");
        lengthList.add("中");
        lengthList.add("短");
        //建立adapter，并绑定数据源
        lengthAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, lengthList);
        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定adapter到控件
        lengthSpinner.setAdapter(lengthAdapter);
        lengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1) {
                    length_value = "length_long";
                } else if(i == 2) {
                    length_value = "length_middle";
                } else if(i == 3) {
                    length_value = "length_short";
                } else {
                    length_value = "null";
                }
                //Toast.makeText(WordsRecommendActivity.this, "你点击的是:"+length_value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing happen
            }
        });

        sleeveLengthSpinner = (Spinner)super.findViewById(R.id.sleeveLengthSpinner);
        sleeveLengthSpinner.setPrompt("请选择袖长:");
        sleeveLengthList = new ArrayList<CharSequence>();
        sleeveLengthList.add("空");
        sleeveLengthList.add("长袖");
        sleeveLengthList.add("短袖");
        sleeveLengthList.add("无袖");
        sleeveLengthAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, sleeveLengthList);
        sleeveLengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sleeveLengthSpinner.setAdapter(sleeveLengthAdapter);
        sleeveLengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1) {
                    sleeve_value = "sleeve_length_long";
                } else if(i == 2) {
                    sleeve_value = "sleeve_length_short";
                } else if(i == 3) {
                    sleeve_value = "sleeve_length_sleeveless";
                } else {
                    sleeve_value = "null";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing happen
            }
        });

        collarSpinner = (Spinner)super.findViewById(R.id.collarSpinner);
        collarSpinner.setPrompt("请选择领型:");
        collarList = new ArrayList<CharSequence>();
        collarList.add("空");
        collarList.add("立领");
        collarList.add("V领");
        collarList.add("宽领");
        collarList.add("圆领");
        collarList.add("翻领");
        collarList.add("高领");
        collarList.add("连帽衫&无领");
        collarAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, collarList);
        collarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collarSpinner.setAdapter(collarAdapter);
        collarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1) {
                    collar_value = "collar_shape_stand";
                } else if(i == 2) {
                    collar_value = "collar_shape_V";
                } else if(i == 3) {
                    collar_value = "collar_shape_bateau";
                } else if(i == 4) {
                    collar_value = "collar_shape_round";
                } else if(i == 5) {
                    collar_value = "collar_shape_lapel";
                } else if(i == 6) {
                    collar_value = "collar_shape_high";
                } else if(i == 7) {
                    collar_value = "collar_shape_hoodie";
                } else {
                    collar_value = "null";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing happen
            }
        });

        modelSpinner = (Spinner)super.findViewById(R.id.modelSpinner);
        modelSpinner.setPrompt("请选择版型:");
        modelList = new ArrayList<CharSequence>();
        modelList.add("空");
        modelList.add("修身");
        modelList.add("合身");
        modelList.add("宽松");
        modelAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, modelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpinner.setAdapter(modelAdapter);
        modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1) {
                    model_value = "model_tight";
                } else if(i == 2) {
                    model_value = "model_straight";
                } else if(i == 3) {
                    model_value = "model_loose";
                } else {
                    model_value = "null";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing happen
            }
        });

        patternSpinner = (Spinner)super.findViewById(R.id.patternSpinner);
        patternSpinner.setPrompt("请选择印花:");
        patternList = new ArrayList<CharSequence>();
        patternList.add("空");
        patternList.add("纯色");
        patternList.add("网格");
        patternList.add("圆点");
        patternList.add("花饰");
        patternList.add("横条纹");
        patternList.add("竖条纹");
        patternList.add("数字&字母");
        patternList.add("重复样式");
        patternAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, patternList);
        patternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patternSpinner.setAdapter(patternAdapter);
        patternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1) {
                    pattern_value = "pattern_pure";
                } else if(i == 2) {
                    pattern_value = "pattern_grid";
                } else if(i == 3) {
                    pattern_value = "pattern_dot";
                } else if(i == 4) {
                    pattern_value = "pattern_floral";
                } else if(i == 5) {
                    pattern_value = "pattern_cross-stripe";
                } else if(i == 6) {
                    pattern_value = "pattern_vertical-stripe";
                } else if(i == 7) {
                    pattern_value = "pattern_number&letter";
                } else if(i == 8) {
                    pattern_value = "pattern_repeat";
                } else {
                    pattern_value = "null";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing happen
            }
        });

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadclothinfo();
            }
        });
    }

    private void downloadclothinfo() {
        //开启线程，网络获取对应服装信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<String, String>();
                //post方法向服务器传入用户名与索引
                map.put("length_value", length_value);
                map.put("sleeve_value", sleeve_value);
                map.put("collar_value", collar_value);
                map.put("model_value", model_value);
                map.put("pattern_value", pattern_value);

                try{
                    url = new URL(INFOPATH2);
                } catch (MalformedURLException e) {
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
                        msg.what = 205;
                        msg.obj = changeInputStream(urlConnection.getInputStream(), "UTF-8");
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
}
