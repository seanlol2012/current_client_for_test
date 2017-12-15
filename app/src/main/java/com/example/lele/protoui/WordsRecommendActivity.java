package com.example.lele.protoui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
                Intent i = new Intent(WordsRecommendActivity.this, ClothInfo2Activity.class);
                startActivity(i);
            }
        });
    }
}
