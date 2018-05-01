package com.tistory.nittaku.rx_eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class Main2Activity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

/*
        RxView.clicks(findViewById(R.id.btn_get))
                .compose(bindToLifecycle())
                .delay(2, TimeUnit.SECONDS, Schedulers.trampoline()) //2초 지연해서 받기, 3번재 인자에서 쓰레드 설정 꼭 해줘야함.
                .subscribe( e -> {
*/



                        RxBus.getInstance().getBus()
                                .compose(bindToLifecycle())
                                .subscribe(str -> {
                                                    if (str instanceof String) {  //넘어오는 자료구조확인 후 받기
                                                        ((TextView) findViewById(R.id.txt_result)).setText(str.toString());

                                                    } else Toast.makeText(this, "잘못된 선택입니다", Toast.LENGTH_SHORT).show();
                                                    });
/*                });*/




    }
}
