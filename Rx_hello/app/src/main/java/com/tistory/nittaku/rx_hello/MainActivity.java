package com.tistory.nittaku.rx_hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView Txt;
    private Observable<String> mObservable;
    private Observer<String> mObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Txt = (TextView) findViewById(R.id.txt);

        //데이터 스트림을 생성하는 옵져버블 만들기
        mObservable = Observable.just("New RxAndroid World");

        //데이터를 받아서 처리하는 옵져버 객체 만들기 -> onNext에서 처리하기
        mObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Txt.setText(s);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    //이미 만들어놓은, 옵져버블을 -> 옵져버로 subscribe해주는 버튼
    public void subscribeNow(View view){
        mObservable.subscribe(mObserver);

        mObservable.just("옵져버블 변수에 = Observable.just 등으로 대입 안하고 바로 just().subscribe()하면\n 옵져버블 변수에는 데이터가  안남아있다.")
                .subscribe( s-> Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show() );


    }
}
