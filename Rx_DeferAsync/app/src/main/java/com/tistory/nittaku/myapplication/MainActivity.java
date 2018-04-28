package com.tistory.nittaku.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity {
    private static final String TAG = "DEFER ASYNC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doDeferAsync();

    }

    private void doDeferAsync() {
        Log.i(TAG, ":"+Thread.currentThread().getName()+": in Main");
        Observable<String> obv = Observable.defer( () -> {

            Log.i(TAG, ":"+Thread.currentThread().getName()+": defer생성시 쓰레드");
            return Observable.just("Here i am.");

        });

        obv.subscribeOn(Schedulers.computation())   // 발행자 (Observable) Thread 를 지정 - 옵져버블이 뿌려주는 쓰레드
                .observeOn(Schedulers.newThread()) // 구독자 (Observer, subscriber) Thread 를 지정 - 옵져버가 받는 곳의 쓰레드
                .subscribe(
                        s -> Log.i(TAG, ":"+Thread.currentThread().getName()+": 구독시 옵져버의 쓰레드= "+s),
                        throwable -> throwable.printStackTrace(),
                        ()-> Log.i(TAG, ":"+Thread.currentThread().getName()+": onCompleted의 쓰레드")

                );

    }
}
