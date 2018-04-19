package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends Activity {

    private int seconds = 0;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
    }

    //запустить секундомер при щелчке на кнопке Start
    public void onClickStart(View view) {

        //ставим флажок что запустился секундомер
        running = true;
    }

    //остановить секундомер при щелчке на кнопке Stop
    public void onClickStop(View view) {
        //ставим флажок что остановился секундомер
        running = false;
    }

    //обнуляем секундомер при щелчке на кнопке Reset
    public void onClickReset(View view) {
        //ставим флажок что остановился секундомер
        running = false;
        //обнуляем секунды
        seconds = 0;
    }

    //
    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);

        int hours = seconds/3600;
        int minutes = (seconds%3600)/60;
        int secs = seconds%60;

        String time = String.format("%d:%02d:%02d", hours, minutes, secs);

        timeView.setText(time);

        if (running) {
            seconds++;
        }
    }
}
