package rob.myappcompany.stopwatchweardemo2;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;

import rob.myappcompany.stopwatchweardemo2.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private TextView mTextView;
    private ActivityMainBinding binding;

    private Button stopButton;
    private Button startButton;
    private NumberPicker numberPicker;

    private boolean timerRunning;
    public static long MILLIS_IN_FUTURE = new Long(2000);

    private long leftTimeInMillis = MILLIS_IN_FUTURE;
    private CountDownTimer countDownTimer;

    String tag = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        numberPicker = findViewById(R.id.timerNumberPicker);

        numberPicker.setMinValue(10000);
        numberPicker.setMaxValue(30000);



        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                Log.i(tag, String.valueOf(numberPicker));

                MILLIS_IN_FUTURE = new Long(numberPicker.getValue());

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning){
                    stopTimer();
                }else {
                    startTimer();

                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateTimer();
    }

    private void startTimer() {
        mTextView.setVisibility(View.VISIBLE);
        numberPicker.setVisibility(View.GONE);
        stopButton.setVisibility(View.GONE);
        startButton.setText("Pause");


        countDownTimer = new CountDownTimer(leftTimeInMillis, 1) {
            @Override
            public void onTick(long l) {
                leftTimeInMillis = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                //mTextView.setText("00:00");
                timerRunning = false;
                numberPicker.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.GONE);
            }
        }.start();
        timerRunning = true;
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        startButton.setText("Start");
        stopButton.setVisibility(View.VISIBLE);


    }

    public void resetTimer(){
        leftTimeInMillis = MILLIS_IN_FUTURE;
        updateTimer();
    }

    public void updateTimer(){
        int minutes = (int) (leftTimeInMillis / 1000) / 60;
        int seconds = (int) (leftTimeInMillis / 1000) % 60;

        String timeLeftFormated = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        mTextView.setText(timeLeftFormated);
    }


}