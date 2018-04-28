package com.tistory.nittaku.myapplication;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.schedulers.NewThreadScheduler;

public class MainActivity extends RxAppCompatActivity {
    ArrayList<String> datas = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxView.clicks(findViewById(R.id.btn_just))
                .subscribe( e-> doJust());
        RxView.clicks(findViewById(R.id.btn_create))
                .subscribe( e-> doCreate());

        adapter = new ArrayAdapter<String>(         // 인자 전달
                this,                               // 컨택스트
                android.R.layout.simple_list_item_1,// 아이템 레이아웃
                datas                               // 데이터
        );
        ((ListView) findViewById(R.id.listview)).setAdapter(adapter);
        RxView.clicks(findViewById(R.id.btn_fromIterable))
                .subscribe( e-> dofromIterable());

        RxView.clicks(findViewById(R.id.btn_defer))
            .subscribe( e-> doDefer());

}



    //just 실행함수
    public void doJust(){
        Log.i("test","doJust");
        Observable<String> obv = Observable.just("Observable.just!", "1", "2");
        obv
                .compose(bindToLifecycle())
                .subscribe( s -> {
                                    Log.i("Create", s);
                                    ((TextView) findViewById(R.id.textView)).setText(s);
                                 }
                          );

    }



    //create 실행함수
    public void doCreate() {
        Log.i("test", "docreate");
        Observable<String> obv =  Observable.create( (ObservableEmitter<String> emitter) -> {
                                                                                                emitter.onNext("조재성");
                                                                                                emitter.onNext("김석영");
                                                                                                emitter.onNext("1000일");
                                                                                                emitter.onNext("1000일");
                                                                                                emitter.onComplete(  );
                                                                                            }
                                                    );

        obv
                .compose(bindToLifecycle())
                .subscribe(s -> {
                    Log.i("Create", s);
                    ((TextView) findViewById(R.id.textView)).setText(s);
                });
    }



    //defer 실행함수
    public void doDefer() {
        Log.i("test", "do defer");
        Observable<String> obv = Observable.defer( () -> {
                                                            return Observable.just("defer"); // defer 안에 () -> { retrun 옵져버블.operator } 를 통해 비동기로 옵져버블을 생성해준다.
                                                         }
                                                 );

        obv
                .delaySubscription(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread()) // UI 쓰레드에서.. 뭔가 비동기 작업해주면,, 메인쓰레드에서 하라고 명시해줘야한다.
                .subscribe(
                        s-> ((TextView) findViewById(R.id.textView)).setText(s),
                        e-> System.out.println("dd") ,
                        ()-> Toast.makeText(this, "defer끝!", Toast.LENGTH_SHORT).show() // .create()의 onComplete에서는 불가능했던, 메소드 작업이 subscribe의 3번재 파라미터에선 가능하다
                );
    }


    //fromIterable 실행함수
    public void dofromIterable(){
        Log.i("test","do fromIterable");

        List<String> names = new ArrayList<>();
        names.add("Cho");
        names.add("Jae");
        names.add("Seong");

        //fromIterable은  rxjava1과 다르게, new String[] { 배열 }을 바로 넣을 수 없고,, 미리 생성된 List<String> 변수만 들어가는 것 같다.
        Observable<String> obv = Observable.fromIterable(names);
        obv
                .compose(bindToLifecycle())
                .subscribe(
                        s -> {datas.add(s); datas.add("+1"); }, //subscribe시 onNext자리
                        throwable -> throwable.printStackTrace(), //subscribe시 onError? 자리
                        ()->adapter.notifyDataSetChanged() //subscribe시 onComplete자리..
                );

    }
}
