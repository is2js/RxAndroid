package com.tistory.nittaku.rx_mapzip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MainActivity extends RxAppCompatActivity {
    private static final String TAG = "TEST";
    ArrayList<String> datas = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxView.clicks(findViewById(R.id.btn_lambda))
                .subscribe( e-> doLambda());
        RxView.clicks(findViewById(R.id.btn_map))
                .subscribe( e-> doMap());
        RxView.clicks(findViewById(R.id.btn_flatmap))
                .subscribe( e-> doflatMap());
        RxView.clicks(findViewById(R.id.btn_zip))
                .subscribe( e-> doZip());

        RxView.clicks(findViewById(R.id.btn_words))
                .subscribe( e-> doWords());
        RxView.clicks(findViewById(R.id.btn_gugudan))
                .subscribe( e-> doGugudan(8));


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        ((ListView) findViewById(R.id.listview)).setAdapter(adapter);
    }

    private void doGugudan(int dan) {
        Function<Integer, Observable<String>> gugudan =
                num  ->  Observable.range(1, 9).map(row -> num + " * " + row + " = " + num * row);


        Observable.just(dan).
                flatMap(gugudan).
                subscribe( item -> datas.add(item),
                        Throwable::printStackTrace,
                        () -> adapter.notifyDataSetChanged()
                );

    }

    private void doWords() {
        String str = "qwer-asdf-zxcv";
        Observable.just(str)
                .map(s -> s.split("-"))   // -를 기준으로 끊어준다.
                .flatMap(Observable::fromArray)  //끊어진 성분들을 fromArray를 통해서 옵져버블에 담아 리턴한다.
                .take(2) //각 item 중 1,2번째 item을 취한다.
                .takeLast(1) //각 item중 뒤에서 1번째(마지막) item을 취한다. ==> 앞에 2개를 선택한 뒤, 뒤에 것을 가져온다.
                .subscribe(
                        item -> datas.add(item),
                        Throwable::printStackTrace,
                        () -> adapter.notifyDataSetChanged()
                );
    }

    private void doZip() {

        Observable.zip( Observable.just("Chojaeseong"),
                        Observable.just("image.jpg"),
                        (item1,item2) -> "Name:"+item1 + ", Profile image:"+item2)
                .subscribe( zipped -> ((TextView) findViewById(R.id.textView)).setText(zipped)
                );

    }

    private void doflatMap() {

        Function<String, Observable<String>> getflatMap = item -> Observable.just(item + "[1]", item + "<2>");

        Observable.fromArray(new String[]{"aaaa","bbbbbbb","cccccccc","ddd","ee","ffffffffff"})
                //.flatMap(item -> Observable.fromArray( new String[]{"name:"+item+" Byte: "+ item.getBytes().length}))
                .flatMap( getflatMap )
                .subscribe(
                        item -> datas.add(item),
                        Throwable::printStackTrace,
                        () -> adapter.notifyDataSetChanged()
                );
    }

    private void doMap() {

        Function<String, String> getMap = item -> "<" + item + ">";

        Observable.fromArray(new String[]{"aaa","bbb","ccc","ddd","eee","fff"})
                .map( getMap )
                .subscribe(
                        item -> datas.add(item),
                        Throwable::printStackTrace,
                        () -> adapter.notifyDataSetChanged()
                );
    }

    public void doLambda() {

        Observable.just("i am Labmda")
                .subscribe(
                        item -> ((TextView) findViewById(R.id.textView)).setText(item),
                        Throwable::printStackTrace,
                        () -> Log.i(TAG, "Complieted")
                );

    }
}
