package com.wgq.android.personmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kongzue.stacklabelview.StackLabel;
import com.kongzue.stacklabelview.interfaces.OnLabelClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "wgq";
    private static final String SP_LABLES = "lables";
    private static final String SP_TASKS = "tasks";

    // 标签
    private StackLabel stackLabelView;
    private List<String> labels = new ArrayList<>();
    private String selectLable = "";

    // 每日任务
    private ListView lvTask;
    private ArrayAdapter<String> myAdapter;
    private List<String> tasks = new ArrayList<>();

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
        if (getDatasFromSp(SP_LABLES) == null) {
            labels.add("技术");
            labels.add("生活");
            labels.add("健康");
            labels.add("励志");
            saveDatasToSp(SP_LABLES, labels);
        } else {
            labels = getDatasFromSp(SP_LABLES);
        }
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
                saveDatasToSp(SP_LABLES, labels);
            }
        });


        lvTask = (ListView) findViewById(R.id.lvTask);
        if (getDatasFromSp(SP_TASKS) == null) {
            tasks.add("坚持洗澡");
            tasks.add("坚持吃药");
            tasks.add("坚持记日志");
            saveDatasToSp(SP_TASKS, tasks);
        } else {
            tasks = getDatasFromSp(SP_TASKS);
        }
        myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, tasks);
        lvTask.setAdapter(myAdapter);

        lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (cbEditTask.isChecked()) {
                    tasks.remove(i);
                    myAdapter.notifyDataSetChanged();
                }
            }
        });

        cbEditTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveDatasToSp(SP_TASKS, tasks);
//                SparseBooleanArray tmpMap = lvTask.getCheckedItemPositions();
//                int mapSize = lvTask.getCount();
//                Toast.makeText(MainActivity.this, "mapSize: " + mapSize, Toast.LENGTH_SHORT).show();
//                for (int i = 0; i < mapSize; i++) {
//                    boolean res = tmpMap.get(i, false);
//                    Toast.makeText(MainActivity.this, "mapRes: " + res, Toast.LENGTH_SHORT).show();
//                }
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
                } else if (cbEditTask.isChecked()) {
                    String s = msg.getText().toString().trim();
                    if (s != null && !s.isEmpty()) {
                        tasks.add(s);
                        myAdapter.notifyDataSetChanged();
                        msg.setText("");
                    }
                } else {
                    // 如果MSG为空，则上报每日任务，否则上报分享链接
                    if (msg.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "开始上传每日任务", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "开始上传分享链接", Toast.LENGTH_SHORT).show();
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
        btDelete.setVisibility(View.INVISIBLE);

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

    public boolean saveDatasToSp(String tag, List<String> datas) {
        SharedPreferences.Editor editor = getSharedPreferences(tag, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(datas, new TypeToken<List<String>>() {
        }.getType());
        Log.i(TAG, "saved json: "+ json);
        editor.putString(tag, json);
        editor.commit();
        return true;
    }

    public List<String> getDatasFromSp(String tag) {
        SharedPreferences preferences = getSharedPreferences(tag, MODE_PRIVATE);
        String json = preferences.getString(tag, null);
        if (json != null)
        {
            Gson gson = new Gson();
            List<String> stringList = gson.fromJson(json, new TypeToken<List<String>>() {
            }.getType());
            for (String string : stringList) {
                Log.i(TAG, "from json: " + string);
            }
            return stringList;
        }
        return null;
    }
}
