package com.tistory.nittaku.rx_lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import static com.jakewharton.rxbinding2.view.RxView.clicks;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class MainActivity extends RxAppCompatActivity{

    private static final String TAG = "MainActivity";
    private TextView textView;
    private Observable<String> mObservable;
    private Observer<String> mObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                ((TextView) findViewById(R.id.txt)).setText(s);
                Log.i("###", "로그찍자");


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


        clicks(findViewById(R.id.btn))
                .subscribe(
                        e -> ((TextView) findViewById(R.id.txt)).setText("RxView.clicks를 옵져버블없이 subscribe시켜 setText해버리기")
                );

        mObservable = clicks(findViewById(R.id.btn2))
                .map(e -> "RxView.clicks를 옵져버블에 담아놓고 .map()으로  e->텍스트로 전환.\n"+" 차후 옵져버가 받을 때, setText함. ");

        mObservable
                .subscribe(mObserver);

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map( String::valueOf)
                .subscribe( s -> Log.i("###", s));


        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map( String::valueOf)
                .compose(bindToLifecycle())
                .subscribe( s -> Log.i("lifecycle적용한 옵져버블", s));



    }
}
