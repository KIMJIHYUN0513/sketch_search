package com.google.sample.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class Main_SketchActivity extends AppCompatActivity implements View.OnClickListener{

    // 그림 그릴 CustomView
    private DrawingView drawingView;

    // 색상 선택 버튼
    private ImageButton[] colorImageButtons;

    // 초기화 버튼, 저장 버튼
    private Button resetButton, saveButton;

    public static Context mContext;
    public Bitmap screenshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sketch);

        //변수 초기화
        drawingView = (DrawingView) findViewById(R.id.drawingView);
        colorImageButtons = new ImageButton[3];
        colorImageButtons[0] = (ImageButton) findViewById(R.id.blackColorBtn);
        colorImageButtons[1] = (ImageButton) findViewById(R.id.redColorBtn);
        colorImageButtons[2] = (ImageButton) findViewById(R.id.blueColorBtn);
        for (ImageButton colorImageButton : colorImageButtons) {
            colorImageButton.setOnClickListener(this);
        }

        resetButton = (Button) findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(this);
        saveButton = (Button) findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(this);

        mContext = this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.blackColorBtn:
                drawingView.setColor(Color.BLACK);
                break;
            case R.id.redColorBtn:
                drawingView.setColor(Color.RED);
                break;
            case R.id.blueColorBtn:
                drawingView.setColor(Color.BLUE);
                break;
            case R.id.resetBtn:
                drawingView.reset();
                break;
            case R.id.saveBtn:
                screenshot = drawingView.save(drawingView);
                Intent intent = new Intent(this, CloudVisionActivity.class);
                startActivity(intent);
                break;
        }
    }

}
