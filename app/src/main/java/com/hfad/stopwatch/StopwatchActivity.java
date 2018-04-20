package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends Activity {

    //количество секунд на секундомере
    private int seconds = 0;

    //проверка флага на запущенный секундомер (true/false)
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        //Для обновления секундомера
        //используется отдельный метод.
        //Он запускается при создании
        //активности.
        runTimer();
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

    //обновления показаний таймера
    private void runTimer() {
        //получаем ссылку на поле вывода текста. В нашем случае на вывод цифр
        final TextView timeView = (TextView)findViewById(R.id.time_view);

        //создаем обьект Handler для планирования запуска
        final Handler handler = new Handler();

        //Используем Handler для передачи кода на выполнение.
        //Вызов метода post() с передачей нового объекта Runnable.
        //Метод post() обеспечивает выполнение без задержки, так что код в Runnable будет
        //выполнен практически немедленно.
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                //Вывести секудомер в поле вывода текста
                timeView.setText(time);

                //Если флажок равен true тогда увеличиваем переменную seconds
                if (running) {
                    seconds++;
                }

                //Код из объекта Runnable передается на повторное выполнение после истечения задержки в 1000 миллисекунд (1 секунда).
                //Так как эта строка кода включена в метод run() объекта Runnable, код будет вызываться снова и снова.
                //Таким образом как я понял, будет тикать наши секундны, если running true
                handler.postDelayed(this, 1000);
            }
        });
    }
}
