package com.boby.livinghelper.app.qarobot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.boby.livinghelper.R;
import com.boby.livinghelper.app.qarobot.adapter.TextAdapter;
import com.boby.livinghelper.app.qarobot.entity.ListData;
import com.boby.livinghelper.app.qarobot.util.HttpData;
import com.boby.livinghelper.app.qarobot.util.HttpGetDataListener;
import com.boby.livinghelper.base.BaseActivity;
import com.boby.livinghelper.base.BasePresenter;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 问答机器人
 *
 * @author zbobin.com
 */
public class QARobotActivity extends BaseActivity implements HttpGetDataListener,View.OnClickListener {

    private ImageButton btnBack;
    private TextView tvTitle;

    private HttpData httpData;
    private List<ListData> lists;
    private ListView lv;
    private EditText sendText;
    private Button sendBtn;
    private String content_str;
    private TextAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qarobot);
        initView();
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.public_top_bar_left_btn);
        btnBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.public_top_bar_title);
        tvTitle.setText(R.string.activity_qarobot);

        lv = (ListView) findViewById(R.id.lv);
        sendText = (EditText) findViewById(R.id.sendText);
        sendBtn = (Button) findViewById(R.id.send_btn);
        lists = new ArrayList<ListData>();
        sendBtn.setOnClickListener(this);
        adapter = new TextAdapter(lists, this);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.public_top_bar_left_btn:
                finish();
                break;

            case R.id.send_btn:
                content_str = sendText.getText().toString().trim();
                ListData listData;
                listData = new ListData(content_str, ListData.SEND);
                lists.add(listData);
                adapter.notifyDataSetChanged();
                sendText.setText("");
                httpData = (HttpData) new HttpData(
                        "http://www.tuling123.com/openapi/api?key=5ee4aad340a659ce08c39b12a098f998&info=" + content_str,
                        this).execute();
                break;
        }
    }

    @Override
    public void getDataUrl(String data) {
        System.out.println(data);
        parseText(data);
    }

    public void parseText(String str){

        try {
            JSONObject jb = new JSONObject(str);
            //System.out.println(jb.getString("code"));
            //System.out.println(jb.getString("text"));
            ListData listData;
            listData = new ListData(jb.getString("text"), ListData.RECEIVER);
            lists.add(listData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
