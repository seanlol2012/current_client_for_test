package com.example.lele.protoui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {
    //调试---------------
    private int flag = 1;

    //-------------------
    private TextView loginbtn;
    private TextView registerbtn;
    private TextView visitorbtn;
    private EditText editTextName;
    private EditText editTextPwd;
    private String uname;
    private String password;
    Map<String, String> loginData = new HashMap<String, String>();
    private static String PATH = "http://192.168.1.66:8080/fashion_server/login";
    private static URL url;
    private ProgressDialog dialog;//登陆处理
    private SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //输入检查
            if (msg.what == 197) {
                Toast.makeText(LoginActivity.this, getString(R.string.login_msg_nouser) + "," + getString(R.string.login_msg_nopwd), Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 198) {
                Toast.makeText(LoginActivity.this, R.string.login_msg_nouser, Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 199) {
                Toast.makeText(LoginActivity.this, R.string.login_msg_nopwd, Toast.LENGTH_SHORT).show();
            }
            //超时
            if (msg.what == 200) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(LoginActivity.this, R.string.login_msg_timeout, Toast.LENGTH_LONG).show();
            }
            //完成服务器响应
            if (msg.what == 201) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                String s = (String) msg.obj;
                //Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
                if (s.contains("log in")) {
                    Bundle bundle_toMain = new Bundle();
                    bundle_toMain.putString("userName",uname);
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtras(bundle_toMain);
                    startActivity(i);
                } else {
                    //密码错误
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        // 隐藏状态栏
        setContentView(R.layout.activity_login);

        loginbtn = findViewById(R.id.loginbtn);
        registerbtn = findViewById(R.id.registerbtn);
        editTextName = findViewById(R.id.login_user_name);
        editTextPwd = findViewById(R.id.login_user_pwd);
        visitorbtn = findViewById(R.id.visitor);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(i);
                if (loginCheck())//登陆字串检查
                {
                    dialog = ProgressDialog.show(LoginActivity.this, getString(R.string.login_msg_dialog_title), getString(R.string.login_msg_dialog_content)); //弹出ProgressDialog
                    new login().start();//开始后台登陆
                }
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        //visitorbtn.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent i = new Intent(LoginActivity.this, BuildModelActivity.class);
        //        startActivity(i);
        //    }
        //});
    }

    private boolean loginCheck() {
        if (TextUtils.isEmpty(editTextName.getText()) && TextUtils.isEmpty(editTextPwd.getText())) {
            Message msg = Message.obtain();
            msg.what = 197;
            handler.sendMessage(msg);
        } else if (TextUtils.isEmpty(editTextName.getText()) && !TextUtils.isEmpty(editTextPwd.getText())) {
            Message msg = Message.obtain();
            msg.what = 198;
            handler.sendMessage(msg);
        } else if (!TextUtils.isEmpty(editTextName.getText()) && TextUtils.isEmpty(editTextPwd.getText())) {
            Message msg = Message.obtain();
            msg.what = 199;
            handler.sendMessage(msg);
        } else {
            uname = editTextName.getText().toString();
            password = editTextPwd.getText().toString();
            loginData.put("username", uname);
            loginData.put("password", password);
            sharedPrefsUtil.savePreferences_login(LoginActivity.this, "DataBase", loginData);
            return true;
        }
        return false;
    }

    private class login extends Thread {
        public void run() {
            try {
                url = new URL(PATH);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : loginData.entrySet()) {
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
                    msg.what = 201;
                    msg.obj = changeInputStream(urlConnection.getInputStream(), "UTF-8");
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                //跳线
                if (flag == 1) {
                    Message msg = Message.obtain();
                    msg.what = 201;
                    msg.obj = "log in";
                    handler.sendMessage(msg);
                }
                //超时处理
                Message msg = Message.obtain();
                msg.what = 200;
                handler.sendMessage(msg);
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
}
