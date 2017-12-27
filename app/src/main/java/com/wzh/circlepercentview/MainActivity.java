package com.wzh.circlepercentview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
