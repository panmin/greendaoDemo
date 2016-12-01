package com.panmin.greendaodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.panmin.model.TaskCall;

import java.util.Date;

public class MainActivity extends AppCompatActivity{

    private Button btn_save_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //保存数据
        btn_save_data = (Button) findViewById(R.id.btn_save_data);
        btn_save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData(){
        TaskCall taskCall = new TaskCall();
        taskCall.setId("idididididididid");
        taskCall.setCreateTime(new Date().getTime());
        taskCall.setRemarks("oldRemark");

    }
}
