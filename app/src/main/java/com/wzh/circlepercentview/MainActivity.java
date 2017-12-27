package com.wzh.circlepercentview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


/**
* @date 创建时间：2017/12/27
* @author 开发者：WZH
* @Description：
*/
public class MainActivity extends AppCompatActivity {

    private CirclePercentView mCirclePercentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCirclePercentView = findViewById(R.id.circlePercentView);
        mCirclePercentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                float percent = (float) (Math.random() * 99 + 1);
                mCirclePercentView.setCurPercent(percent);
            }
        });

    }
}
