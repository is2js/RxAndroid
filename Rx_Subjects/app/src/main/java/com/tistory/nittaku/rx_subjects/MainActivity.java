package com.tistory.nittaku.rx_subjects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxView.clicks(findViewById(R.id.btn_publish))
                .subscribe( e-> doPublish());
        RxView.clicks(findViewById(R.id.btn_behavior))
                .subscribe( e-> doBehavior());
        RxView.clicks(findViewById(R.id.btn_replay))
                .subscribe( e-> doReplay());
        RxView.clicks(findViewById(R.id.btn_async))
                .subscribe( e-> doAsync());
    }

    private void doPublish() {
        StringBuffer bf = new StringBuffer();

        PublishSubject<String> subject = PublishSubject.create();
        subject.onNext("A");
        //1번째 구독자
        subject.subscribe( item -> {
                    bf.append( "구독자1 에게 전달된 값 : "+ item +"\n");
                    ((TextView) findViewById(R.id.txt_result)).setText(bf);
                }
        );
        subject.onNext("B");
        subject.onNext("C");
        subject.onNext("D");
        //2번째 구독자
        subject.subscribe( item -> {
                                        bf.append( "구독자2 에게 전달된 값 : "+ item +"\n");
                                        ((TextView) findViewById(R.id.txt_result)).setText(bf);
                }
        );
        subject.onNext("E");
        subject.onNext("F");
        subject.onNext("G");
        subject.onComplete(); // onComplete 를 써줘야 됨.( 끝이 어딘지 알때 사용 )
    }

    private void doBehavior() {
        StringBuffer bf = new StringBuffer();
        BehaviorSubject<String> subject = BehaviorSubject.create();

        subject.onNext("A");
        //1번째 구독자
        subject.subscribe( item -> {
                    bf.append( "구독자1 에게 전달된 값 : "+ item +"\n");
                    ((TextView) findViewById(R.id.txt_result)).setText(bf);
                }
        );
        subject.onNext("B");
        subject.onNext("C");
        subject.onNext("D");
        //2번째 구독자
        subject.subscribe( item -> {
                    bf.append( "구독자2 에게 전달된 값 : "+ item +"\n");
                    ((TextView) findViewById(R.id.txt_result)).setText(bf);
                }
        );
        subject.onNext("E");
        subject.onNext("F");
        subject.onNext("G");
        subject.onComplete(); // onComplete 를 써줘야 됨.( 끝이 어딘지 알때 사용 )
    }

    private void doReplay() {
        StringBuffer bf = new StringBuffer();
        ReplaySubject<String> subject = ReplaySubject.create();

        subject.onNext("A");
        //1번째 구독자
        subject.subscribe( item -> {
                    bf.append( "구독자1 에게 전달된 값 : "+ item +"\n");
                    ((TextView) findViewById(R.id.txt_result)).setText(bf);
                }
        );
        subject.onNext("B");
        subject.onNext("C");
        subject.onNext("D");
        //2번째 구독자
        subject.subscribe( item -> {
                    bf.append( "구독자2 에게 전달된 값 : "+ item +"\n");
                    ((TextView) findViewById(R.id.txt_result)).setText(bf);
                }
        );
        subject.onNext("E");
        subject.onNext("F");
        subject.onNext("G");
        subject.onComplete(); // onComplete 를 써줘야 됨.( 끝이 어딘지 알때 사용 )
    }

    private void doAsync() {
        StringBuffer bf = new StringBuffer();
        AsyncSubject<String> subject = AsyncSubject.create();

        subject.onNext("A");
        //1번째 구독자
        subject.subscribe( item -> {
                    bf.append( "구독자1 에게 전달된 값 : "+ item +"\n");
                    ((TextView) findViewById(R.id.txt_result)).setText(bf);
                }
        );
        subject.onNext("B");
        subject.onNext("C");
        subject.onNext("D");
        //2번째 구독자
        subject.subscribe( item -> {
                    bf.append( "구독자2 에게 전달된 값 : "+ item +"\n");
                    ((TextView) findViewById(R.id.txt_result)).setText(bf);
                }
        );
        subject.onNext("E");
        subject.onNext("F");
        subject.onNext("G");
        subject.onComplete(); // onComplete 를 써줘야 됨.( 끝이 어딘지 알때 사용 )
    }
}
