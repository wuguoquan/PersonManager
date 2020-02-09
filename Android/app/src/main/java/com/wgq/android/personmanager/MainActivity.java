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
        labels.add("花哪儿记账");
        labels.add("给未来写封信");
        labels.add("密码键盘");
        labels.add("抬手唤醒");
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
                Toast.makeText(MainActivity.this, "haha", Toast.LENGTH_SHORT).show();
                if (cbEditLable.isChecked()) {
                    String s = msg.getText().toString().trim();
                    if (s != null && !s.isEmpty()) {
                        labels.add(s);
                        stackLabelView.setLabels(labels);
                        msg.setText("");
                    }
                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "world", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpUtil.postJsonParams("http://192.168.137.1:8081/books",
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
