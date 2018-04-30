package com.tistory.nittaku.rx_filtergroupby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MainActivity extends RxAppCompatActivity {
    Integer dataSet[] = { 1,2,3,1,4,5,3,6,7 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxView.clicks(findViewById(R.id.btn_filter))
                .subscribe( e-> doFilter());
        RxView.clicks(findViewById(R.id.btn_foreach))
                .subscribe( e-> doForeach());
        RxView.clicks(findViewById(R.id.btn_take))
                .subscribe( e-> doTake(3));
        RxView.clicks(findViewById(R.id.btn_first))
                .subscribe( e-> doFirst());
        RxView.clicks(findViewById(R.id.btn_last))
                .subscribe( e-> doLast());
        RxView.clicks(findViewById(R.id.btn_distinct))
                .subscribe( e-> doDistinct());
        RxView.clicks(findViewById(R.id.btn_groupby))
                .subscribe( e-> doGroupby());
        RxView.clicks(findViewById(R.id.btn_groupby2))
                .subscribe( e-> doGroupby2());

    }

    private void doGroupby2() {
            StringBuffer bf = new StringBuffer();
            Integer[] array = {1,2,3,1,4,5,3,6,7};
            Observable.fromArray(array)
                        .groupBy(  item -> item )  //추정, groupby는 groupedobservable을 반환하고, i자신을 기준으로 정렬하면, 중복을 제외하고 오름차순 시킨다. groupby는 subscribe전까지는 옵져버블로 반환되므로, 그안에 값을 받으려면 getkey()를 이용해야한다.
                        .map( grouped -> grouped.getKey() ) // 위에서 나온 자기자신을 기준으로 그룹화하면, 그 배열에서 <같은 값들끼리> 그룹이 되어있을 것이다. 아직 subscribe를 거치지 않았으므로, groupedObservable이다. 각 옵져버블에 getkey()를 달아주면, 그 배열의 고유값들이 나오게 된다.

                        .subscribe( grouedKey -> {
                                                        bf.append( "그룹화 기준값 : "+ grouedKey +"\n");
                                                        ((TextView) findViewById(R.id.txt_result)).setText(bf);
                                                    }
                );
// 고유값이라는 것은 나누는 i자신을 기준으로 groupby하여, 그룹을 나누는 기준을 말한다.
// 고유값 1
// 고유값 2
// 고유값 3
// 고유값 4
// 고유값 5
// 고유값 6
// 고유값 34


                Observable.fromArray(array)
                        .groupBy(  item -> item )
                        .map( grouped -> grouped.getKey() ) //고유값 1,2,3,4,5,6,7을 내어보낼 것이다.

                        .subscribe( unique -> {

                            Observable.fromArray(array).filter( item -> item.equals(  unique  ) ) // 다시한번 배열각 성분 중에서, unique한 고유값을 가지고 필터링 한다.
                                                        .subscribe( filterd -> {  bf.append("고유값("+unique+")으로 필터링된 값 : " + filterd + "\n");
                                                                                  ((TextView) findViewById(R.id.txt_result)).setText(bf.toString()); }
                                                                 );

                        });


// =======1==========
// filter: 1
// filter: 1
// filter: 1
// filter: 1
// =======2==========
// filter: 2
// filter: 2
// =======3==========
// filter: 3
// filter: 3
// =======4==========
// filter: 4
// filter: 4
// =======5==========
// filter: 5
// =======6==========
// filter: 6
// filter: 6
// =======34==========
// filter: 34
        }


    private void doGroupby() {
        StringBuffer bf = new StringBuffer();
        Observable.fromArray(dataSet)
                .groupBy(item -> item%2==0)
                .subscribe(
                        grouped ->  grouped.toList().subscribe(  item-> {
                                                                            bf.append(item+"리스트의 grouped.getkey():"+grouped.getKey() + "\n" );
                                                                            ((TextView) findViewById(R.id.txt_result)).setText(bf.toString());
                                                                        }
                                                              )


                );

    }

    private void doDistinct() {
        StringBuffer bf = new StringBuffer();
        Observable.fromArray(dataSet)
                .distinct()
                .map( result -> bf. append(result))
                .subscribe(
                        result -> ((TextView) findViewById(R.id.txt_result)).setText(bf.toString())
                );
    }

    private void doLast() {
        StringBuffer bf = new StringBuffer();
        Observable.fromArray(dataSet)
                .last(2)
                .map( result -> bf. append(result))
                .subscribe( result -> ((TextView) findViewById(R.id.txt_result)).setText(bf.toString()) );

    }

    private void doFirst() {
        StringBuffer bf = new StringBuffer();
        Observable.fromArray(dataSet)
                .first(3)
                .map( result -> bf. append(result))
                .subscribe( result -> ((TextView) findViewById(R.id.txt_result)).setText(bf.toString()) );;
    }

    private void doTake(int count) {
        StringBuffer bf = new StringBuffer();
        Observable.fromArray(dataSet)
                .take(count)
                .map( result -> bf. append(result))
                .subscribe( result -> ((TextView) findViewById(R.id.txt_result)).setText(bf.toString()) );
    }

    private void doForeach() {
        StringBuffer bf = new StringBuffer();
        Observable.fromArray(dataSet)
                .map( result -> bf. append(result))
                .forEach( result -> ((TextView) findViewById(R.id.txt_result)).setText(bf.toString())  );
    }



    private void doFilter() {
        StringBuffer bf = new StringBuffer();

    Observable.fromArray(dataSet)
            .filter(item -> item%2==0)
            .map( result -> bf. append(result))
            .subscribe(
                    result -> ((TextView) findViewById(R.id.txt_result)).setText(bf.toString())
            );

/*        String[] objs = {"1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON"};
        Observable.fromArray(objs)
                .filter(item -> item.endsWith("CIRCLE"))
                .map( result -> bf. append(result +"\n"))
                .subscribe(
                        result -> ((TextView) findViewById(R.id.txt_result)).setText(bf.toString())
                );*/

/*    Observable.flatMap( realm -> Observable.fromArray( Students ) ) // Realm의 Stundent라는 모델의 객체변수를 쪼개어 옵져버블에 담는다.
        .filter( students -> students.getScore > 30 ) //Studnets의 getScore필드를 조건식으로 건다. 30이 넘는 학생들만 뿌려질 것이다.
        .subscribe( mAdapter.add( students) ) // 30점이 넘는 학생들만 리스트뷰or리사아클러뷰의 어댑터에 add해준다. + onComplete에서 데이터를 갱신하면 좋을듯?   */

    }
}
