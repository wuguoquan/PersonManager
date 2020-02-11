package com.wgq.android.personmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.kongzue.stacklabelview.StackLabel;
import com.kongzue.stacklabelview.interfaces.OnLabelClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "wgq";

    private StackLabel stackLabelView;
    private List<String> labels = new ArrayList<>();
    private CheckBox cbEditLable;
    private CheckBox cbEditTask;
    private EditText msg;
    private String selectLable = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btAdd = (Button) findViewById(R.id.btAdd);
        Button btDelete = (Button) findViewById(R.id.btDelete);
        cbEditLable = (CheckBox) findViewById(R.id.cbEditLable);
        cbEditTask = (CheckBox) findViewById(R.id.cbEditTask);
        msg = (EditText) findViewById(R.id.msg);

        stackLabelView = (StackLabel) findViewById(R.id.stackLabelView);
        labels.add("技术");
        labels.add("生活");
        labels.add("健康");
        labels.add("励志");
        stackLabelView.setLabels(labels);
        stackLabelView.setSelectMode(true);
        stackLabelView.setMaxSelectNum(1);

        stackLabelView.setOnLabelClickListener(new OnLabelClickListener() {
            @Override
            public void onClick(int index, View v, String s) {
                if (cbEditLable.isChecked()) {
                    labels.remove(index);
                    stackLabelView.setLabels(labels);
                } else {
                    Toast.makeText(MainActivity.this, "点击了：" + s, Toast.LENGTH_SHORT).show();
                    if (stackLabelView.isSelectMode()) {
                        for (int i : stackLabelView.getSelectIndexList()) {
                            Log.i(TAG, "select: " + i);
                            selectLable = s;
                        }
                    }
                }
            }
        });

        cbEditLable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                stackLabelView.setDeleteButton(isChecked);
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbEditLable.isChecked()) {
                    String s = msg.getText().toString().trim();
                    if (s != null && !s.isEmpty()) {
                        labels.add(s);
                        stackLabelView.setLabels(labels);
                        msg.setText("");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "开始上传信息", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String msgStr = msg.getText().toString();
                            String msgLabel = selectLable;
                            String msgUrl = msgStr.substring(msgStr.indexOf("\n") + 1).trim();
                            String msgTitle = msgStr.substring(0, msgStr.indexOf("\n")).trim();
                            Log.i(TAG, "msgLabel: " + msgLabel);
                            Log.i(TAG, "msgTitle: " + msgTitle);
                            Log.i(TAG, "msgUrl: " + msgUrl);

                            OkHttpUtil.postJsonParams("http://122.51.2.33:8081/personurls",
                                    "{\"url\":\""
                                            + msgUrl +
                                            "\",\"title\":\""
                                            + msgTitle +
                                            "\",\"label\":\""
                                            + msgLabel +
                                            "\"}");
                        }
                    }).start();
                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "开始测试上传", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpUtil.postJsonParams("http://122.51.2.33:8081/books",
                                "{\"name\":\"wgq4\",\"author\":\"wgq4\"}");
                    }
                }).start();
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null){
            if ("text/plain".equals(type)){
                String share = intent.getStringExtra(Intent.EXTRA_TEXT);
                msg.setText(share);
            }else if(type.startsWith("image/")){
                ShareUtil.dealPicStream(intent);
            }
        }else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null){
            if (type.startsWith("image/")){
                ShareUtil.dealMultiplePicStream(intent);
            }
        }
    }
}
