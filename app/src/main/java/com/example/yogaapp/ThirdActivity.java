package com.example.yogaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class ThirdActivity extends AppCompatActivity {

    String buttonvalue;
    Button startBtn;
    private CountDownTimer countDownTimer;
    TextView mtextview;
    private boolean MTimeRunning;
    private long MTimeLeftinmillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third); // Ensure the correct layout is set

        Intent intent = getIntent();
        buttonvalue = intent.getStringExtra("value");

        int intvalue = Integer.parseInt(buttonvalue);
        boolean layoutSet = setActivityLayout(intvalue);

        if (layoutSet) {
            initializeViews();
        } else {
            // If no valid layout was set, return to the main activity or handle the error
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }

        // Handle back press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish(); // Finish the current activity
            }
        });
    }

    private boolean setActivityLayout(int intvalue) {
        int layoutResId;
        switch (intvalue) {
            case 1:
                layoutResId = R.layout.activity_bow;
                break;
            case 2:
                layoutResId = R.layout.activity_bridge;
                break;
            case 3:
                layoutResId = R.layout.activity_chair;
                break;
            case 4:
                layoutResId = R.layout.activity_child;
                break;
            case 5:
                layoutResId = R.layout.activity_cobbler;
                break;
            case 6:
                layoutResId = R.layout.activity_cow;
                break;
            case 7:
                layoutResId = R.layout.activity_playji;
                break;
            case 8:
                layoutResId = R.layout.activity_pauseji;
                break;
            case 9:
                layoutResId = R.layout.activity_plank;
                break;
            case 10:
                layoutResId = R.layout.activity_crunches;
                break;
            case 11:
                layoutResId = R.layout.activity_situp;
                break;
            case 12:
                layoutResId = R.layout.activity_rotation;
                break;
            case 13:
                layoutResId = R.layout.activity_twist;
                break;
            case 14:
                layoutResId = R.layout.activity_windmill;
                break;
            case 15:
                layoutResId = R.layout.activity_legup;
                break;
            default:
                // Invalid value, handle the error
                return false;
        }

        // Set the chosen layout into the content frame
        getLayoutInflater().inflate(layoutResId, findViewById(R.id.content_frame), true);
        return true;
    }

    private void initializeViews() {
        // Initialize the toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        startBtn = findViewById(R.id.startbutton);
        mtextview = findViewById(R.id.time);

        startBtn.setOnClickListener(v -> {
            if (MTimeRunning) {
                stoptimer();
            } else {
                startTimer();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void stoptimer() {
        countDownTimer.cancel();
        MTimeRunning = false;
        startBtn.setText("START");
    }

    private void startTimer() {
        final CharSequence value1 = mtextview.getText();
        String num1 = value1.toString();
        String num2 = num1.substring(0, 2);
        String num3 = num1.substring(3, 5);
        final int number = Integer.parseInt(num2) * 60 + Integer.parseInt(num3);
        MTimeLeftinmillis = number * 1000;
        countDownTimer = new CountDownTimer(MTimeLeftinmillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MTimeLeftinmillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                int newvalue = Integer.parseInt(buttonvalue) + 1;
                if (newvalue <= 15) {
                    Intent intent = new Intent(ThirdActivity.this, ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value", String.valueOf(newvalue));
                    startActivity(intent);
                } else {
                    newvalue = 1;
                    Intent intent = new Intent(ThirdActivity.this, ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value", String.valueOf(newvalue));
                    startActivity(intent);
                }
            }
        }.start();
        startBtn.setText("PAUSE");
        MTimeRunning = true;
    }

    private void updateTimer() {
        int minutes = (int) MTimeLeftinmillis / 60000;
        int seconds = (int) MTimeLeftinmillis % 60000 / 1000;
        String timeLeftText = "";
        if (minutes < 10)
            timeLeftText = "0";
        timeLeftText = timeLeftText + minutes + ":";
        if (seconds < 10)
            timeLeftText += "0";
        timeLeftText += seconds;
        mtextview.setText(timeLeftText);
    }
}
