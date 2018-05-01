package com.tistory.nittaku.rx_eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class Main3Activity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


/*        RxView.clicks(findViewById(R.id.btn_model))
                .compose(bindToLifecycle())
                .subscribe( e -> { */

                            RxBus.getInstance().getBus()
                            .compose(bindToLifecycle())
                            .map(object-> (Product)object)  //객체를 넘길때는, map에서 캐스팅으로 형변환해주기 ㅠㅠ 발견!
                            .subscribe( p -> {
                                if( p instanceof Product ) {  //넘어오는 자료구조확인 후 받기

                                    ((TextView) findViewById(R.id.txt_result)).setText("모델명 : " + p.getResId() + "\n" + "한국이름 : " + p.getmKorea() + "\n" + "영어이름 : " + p.getmEng() + "\n" + "넘버 : " + p.getmNumber() + "\n");

                                }else Toast.makeText(this, "잘못된 선택입니다", Toast.LENGTH_SHORT).show();
                            });

     /*           });
     */
    }
}
