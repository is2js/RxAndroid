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
                ((TextView) findViewById(R.id.txt2)).setText(s);
                Log.i("###", "옵져버가 텍스트받아서 setText찍음");


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


        //Rxbinding을 통해  버튼 변수없이 id만 참조하여 -> 바로 텍스트뷰에 뿌려줄수 있다.
        clicks(findViewById(R.id.btn))
                .subscribe(
                        e -> ((TextView) findViewById(R.id.txt)).setText("버튼클릭을 옵져버블없이 ->subcribe-> setText ")
                );



        //Rxbinding을 통해  클릭이벤트->map->String 으로 옵져버블에 대입
        mObservable = clicks(findViewById(R.id.btn2))
                .map(e -> " 클릭시마다 옵져버블 -> 옵저버를 통해 전해주기 ");

        //옵져버가 옵져버블을 구독 -> 클릭시마다 데이터를 전해줄 것임
        mObservable
                .subscribe(mObserver);



        // Rxlifecycle 사용
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map( String::valueOf)
                .subscribe( s -> Log.i("###", s));


        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map( String::valueOf)
                .compose(bindToLifecycle())
                .subscribe( s -> Log.i("lifecycle적용한 옵져버블", s));



    }
}
