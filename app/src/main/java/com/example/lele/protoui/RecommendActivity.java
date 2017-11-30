package com.example.lele.protoui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecommendActivity extends AppCompatActivity {

    private TextView barTitle;
    private ImageView backBtn;
    private ImageView upLoadfromCamera;
    private ImageView upLoadfromAlbum;
    private ImageView tryagain;
    private TextView recmd_txt1;
    private TextView recmd_txt2;
    private TextView recmd_txt_tryagain;

    //设置浏览本地相册用的变量
    private ImageView mImageView;
    //设置拍照用的变量
    private String mPhotoPath;
    private File mPhotoFile;
    public final static int CAMERA_RESULT = 1;
    private Camera camera;
    private Camera.Parameters parameters = null;
    private SurfaceView surfaceView;/* 显示拍摄界面 */
    Bundle bundle = null; /* 声明一个Bundle对象，用来存储数据 */
    //上传图片
    private static String requestURL_getpic = "http://192.168.1.66:8080/fashion_server/getpic";
    String pic_uri;/* 图片uri */

    //private Handler handler = new Handler() {
    //    @Override
    //    public void handleMessage(Message msg) {
    //        //输入检查
    //        if (msg.what == 204) {
    //            try {
    //                if (mPhotoFile != null) {
    //                    final Map<String, File> files = new HashMap<String, File>();
    //                    files.put("uploadfile", mPhotoFile);
    //                    final String response = UploadUtil.uploadpic(requestURL_getpic, files);
    //                    Log.v("RecommendAct",response);
    //                }
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //        }
    //    }
    //};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        barTitle = findViewById(R.id.title_bar_name);
        barTitle.setText(R.string.barTitle_recmd);
        recmd_txt1 = findViewById(R.id.recmd_txt1);
        recmd_txt2 = findViewById(R.id.recmd_txt2);
        recmd_txt_tryagain = findViewById(R.id.recmd_txt_tryagain);
        tryagain = findViewById(R.id.recmd_tryagain);

        //返回--------------------------------------------------------------------------------------
        backBtn = findViewById(R.id.title_bar_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        upLoadfromCamera = findViewById(R.id.recmd_upload1_btn);
        upLoadfromAlbum = findViewById(R.id.recmd_upload2_btn);
        tryagain = findViewById(R.id.recmd_tryagain);
        mImageView = findViewById(R.id.mImageView);
        setCameraListener();
        setAlbumListener();

        //拍摄界面----------------------------------------------------------------------------------
        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(800, 600); //设置Surface分辨率
        surfaceView.getHolder().setKeepScreenOn(true);// 屏幕常亮
        surfaceView.getHolder().addCallback(new SurfaceCallback());//为SurfaceView的句柄添加一个回调函数
    }

    protected void setCameraListener() {
        upLoadfromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    camera.takePicture(null, null, new MyPictureCallback());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void setAlbumListener() {
        upLoadfromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //开启pictures画面type设定为image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT这个Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //取得相片后返回本画面
                startActivityForResult(intent, 1);
//                //开启线程上传图片
//                new Thread((Runnable) () -> {
//                    try {
//                        File file1 = new File(Environment.getExternalStorageDirectory(), pic_uri);
//                        if (file1 != null) {
//                            String request = UploadUtil.uploadFile(file1, "UploadPicServlet");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
            }
        });
    }

    private final class MyPictureCallback implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                //获得图片
                bundle = new Bundle();
                bundle.putByteArray("bytes", data);
                // 保存图片到sd卡中
                File fileFolder = new File(getSDPath() + "/ProtoUI/");
                boolean isDirectoryCreated = fileFolder.exists();
                if (!isDirectoryCreated) {
                    isDirectoryCreated = fileFolder.mkdir();
                }
                String photo_name = getPhotoFileName();
                mPhotoFile = new File(fileFolder, photo_name);
                mPhotoPath = getSDPath() + "/ProtoUI/" + photo_name;
                FileOutputStream outputStream = new FileOutputStream(mPhotoFile);
                outputStream.write(data);
                outputStream.close();

                //Message msg = Message.obtain();
                //msg.what = 204;
                //handler.sendMessage(msg);

                //开启线程上传图片
                new Thread((Runnable) () -> {
                    try {
                        if (mPhotoFile != null) {
                            final Map<String, File> files = new HashMap<String, File>();
                            files.put("uploadfile", mPhotoFile);
                            final String response = UploadUtil.uploadpic(requestURL_getpic, files);
                            Log.v("RecommendAct",response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                Toast.makeText(getApplicationContext(), R.string.msg_takephotot, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                camera.stopPreview();
                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(mPhotoFile);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                Recommend(data);
            }
        }
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir.toString();
    }

    private String getPhotoFileName() {
        //Date date = new Date(System.currentTimeMillis());
        //SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        //return dateFormat.format(date) + ".jpg";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        return sdf.format(date) + ".jpg";
    }

    private void Recommend(byte[] data) {
        upLoadfromAlbum.setVisibility(View.GONE);
        upLoadfromCamera.setVisibility(View.GONE);
        recmd_txt1.setVisibility(View.GONE);
        recmd_txt2.setVisibility(View.GONE);
        recmd_txt_tryagain.setVisibility(View.VISIBLE);
        tryagain.setVisibility(View.VISIBLE);
        Bitmap bitmap = toBitmap(mPhotoPath);
        mImageView.setImageBitmap(bitmap);
        //设置再来一次的监听
        setTryagainListener();
    }

    protected void setTryagainListener() {
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //转回拍摄界面
                    upLoadfromAlbum.setVisibility(View.VISIBLE);
                    upLoadfromCamera.setVisibility(View.VISIBLE);
                    recmd_txt1.setVisibility(View.VISIBLE);
                    recmd_txt2.setVisibility(View.VISIBLE);
                    recmd_txt_tryagain.setVisibility(View.GONE);
                    tryagain.setVisibility(View.GONE);
                    mPhotoPath = null;
                    camera.startPreview(); // 拍完照后，重新开始预览
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //设置预览图
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            pic_uri = uri.toString();
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                mImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private final class SurfaceCallback implements Callback {
        // 拍照状态变化时调用该方法
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            parameters = camera.getParameters(); // 获取各项参数
            parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
            parameters.setPreviewSize(width, height); // 设置预览大小
            parameters.setPreviewFrameRate(5);  //设置每秒显示4帧
            parameters.setPictureSize(width, height); // 设置保存的图片尺寸
            parameters.setJpegQuality(100); // 设置照片质量
            if (parameters.getSupportedFocusModes().contains(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
            }
        }

        // 开始拍照时调用该方法
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera = Camera.open(); // 打开摄像头
                camera.setPreviewDisplay(holder); // 设置用于显示拍照影像的SurfaceHolder对象
                camera.setDisplayOrientation(getPreviewDegree(RecommendActivity.this));
                camera.cancelAutoFocus();// 打开自动对焦
                camera.startPreview(); // 开始预览
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // 停止拍照时调用该方法
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.release(); // 释放照相机
                camera = null;
            }
        }
    }

    // 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
    public static int getPreviewDegree(Activity activity) {
        // 获得手机的方向
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degree = 0;
        // 根据手机的方向计算相机预览画面应该选择的角度
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }

    private Bitmap toBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
