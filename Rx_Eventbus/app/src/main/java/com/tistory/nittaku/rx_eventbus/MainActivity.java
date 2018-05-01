package com.tistory.nittaku.rx_eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //에디트텍스트의 스트링만 보내기 + onComplete는 안하더라도 onError처리를 해줘야 앱이 안죽는다.
        RxView.clicks(findViewById(R.id.btn_send))
                .compose(bindToLifecycle())
                .subscribe( e -> {

                    RxBus.getInstance().sendBus(((TextView)findViewById(R.id.txt_edit)).getText().toString());

                    Intent intent = new Intent(this, Main2Activity.class);
                    startActivity(intent);
                }
                , Throwable::printStackTrace) ;

        //Product라는 모델보내기 + onComplete는 안하더라도 onError처리를 해줘야 앱이 안죽는다.
        RxView.clicks(findViewById(R.id.btn_model))
                .compose(bindToLifecycle())
                .subscribe( e -> {

                    RxBus.getInstance().sendBus( new Product(1234,"한국이름", "영어이름", "#1" ));

                    Intent intent = new Intent(this, Main3Activity.class);
                    startActivity(intent);
                }
                , Throwable::printStackTrace) ;






    }


}
