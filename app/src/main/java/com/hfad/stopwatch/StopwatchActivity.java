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

    //флаг
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        //Сохранение значений перед уничтожением Activity.
        //если savedInstanceState получает не null (а это возможно только при втором запуске его,
        // которое и запускается только при повороте экрана в нашем случае), тогда запускаем возврат
        //значений что были сохранены в методе onSaveInstanceState
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            //Получить значения
            //переменных seconds
            //и running из Bundle.
        }

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

    //создаем метод для сохранения значений в обьект Bundle, что бы не потерять их при смене
    //ориентации экрана.
    //После того как значения переменных были сохранены
    //в Bundle, их можно будет использовать в методе onCreate().
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    //Реализация метода onStart() (onResume).
    //Если секундомер работал, то отсчет времени возобновляется.
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        //если работал секундомер, тогда запускаем его и продолжаем его счет, если же нет,
//        //тогда не запускаем.
//        if (wasRunning) {
//            running = true;
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();

        //если работал секундомер, тогда запускаем его и продолжаем его счет, если же нет,
        //тогда не запускаем.
        if (wasRunning) {
            running = true;
        }
    }

    //Переопределяем метод onStop(onPause). Когда приложение не видимое, наша программа должна остановиться.
    //В переопределениях
    //методов жизненного
    //цикла активности
    //должен вызываться
    //метод суперкласса.
    //Если этого не сделать,
    //произойдет исключение.
//    @Override
//    protected void onStop() {
//        //Каждый раз, когда вы переопределяете один из методов жиз-
//        //ненного цикла Android, важно начать с вызова версии метода
//        //из суперкласса:
//        super.onStop();
//
//        //Сохранить информацию о том, работал ли секундомер на момент вызова метода onStop().
//        wasRunning = running;
//
//        //ставим флажок что секундомер остановлен
//        running = false;
//    }

    @Override
    protected void onPause() {
        //Каждый раз, когда вы переопределяете один из методов жизненного цикла Android,
        //важно начать с вызова версии метода
        //из суперкласса:
        super.onPause();

        //Сохранить информацию о том, работал ли секундомер на момент вызова метода onPause().
        wasRunning = running;

        //ставим флажок что секундомер остановлен
        running = false;
    }
}
