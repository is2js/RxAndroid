package com.tistory.nittaku.rx_sorting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;

public class MainActivity extends RxAppCompatActivity {
    Integer[] intList = {6, 1, 3, 5, 7, 0, -3};
    String[] strList = {"o", "T", "가나다", "a", "b", "c", "z", "h", "Q", "R", "토요일"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxView.clicks(findViewById(R.id.btn_sort))
                .subscribe( e-> doSort());
        RxView.clicks(findViewById(R.id.btn_sort2))
                .subscribe( e-> doSort2());
        RxView.clicks(findViewById(R.id.btn_sort3))
                .subscribe( e-> doSort3());
        RxView.clicks(findViewById(R.id.btn_sort4))
                .subscribe( e-> doSort4());
        RxView.clicks(findViewById(R.id.btn_sort5))
                .subscribe( e-> doSort5());
        RxView.clicks(findViewById(R.id.btn_sort6))
                .subscribe( e-> doSort6());
        RxView.clicks(findViewById(R.id.btn_map))
                .subscribe( e-> doMap());
        RxView.clicks(findViewById(R.id.btn_multimap))
                .subscribe( e-> doMultiMap());
    }



    private void doSort() {
        Observable.fromArray(intList)
                .toSortedList()
                .subscribe(list -> ((TextView) findViewById(R.id.txt_result)).setText(list.toString()) );
// int sort 1: |[-3, 0, 1, 3, 5, 6, 7]
        //toSortedList()는 옵저버블에서 받은 다수의 데이터들을 정렬해 List 로 반환한다. 알파벳 순으로 정렬된다. toSortedList가 반환하는 것은 무조건 하나이다. 옵져버블이 아니라 Single로 리턴되는 것을 알아야한다.!!
    }

    private void doSort2() {
        Observable.fromArray(intList)
                .toSortedList(  (i1, i2) -> i1.compareTo(i2)   )
                .subscribe(list -> ((TextView) findViewById(R.id.txt_result)).setText(list.toString()) );
// int sort 2: |[-3, 0, 1, 3, 5, 6, 7]
    }

    private void doSort3() {
        Observable.fromArray(intList)
                .toSortedList(  (i1, i2) -> -i1.compareTo(i2)   )
                .subscribe(list -> ((TextView) findViewById(R.id.txt_result)).setText(list.toString()) );
// int sort 3: |[7, 6, 5, 3, 1, 0, -3]
    }

    private void doSort4() {
        Observable.fromArray(strList)
                .toSortedList()
                .subscribe(list -> ((TextView) findViewById(R.id.txt_result2)).setText(list.toString()) );
    }

    private void doSort5() {
        Observable.fromArray(strList)

                .toSortedList(  (i1, i2) -> i1.compareTo(i2)   )
                .subscribe(list -> ((TextView) findViewById(R.id.txt_result2)).setText(list.toString()) );
    }

    private void doSort6() {
        Observable.fromArray(strList)
                .toSortedList(  (i1, i2) -> -i1.compareTo(i2)   )
                .subscribe(list -> ((TextView) findViewById(R.id.txt_result2)).setText(list.toString()) );
    }


    private void doMap() {
        Observable.fromArray(strList)
                .toMap( it ->it.toCharArray()[0])
                .subscribe(list -> ((TextView) findViewById(R.id.txt_result2)).setText(list.toString()) );
        //첫글자를 따로 빼내서, 첫글자를 포함시킨 리스트를 만든다. 첫글자만 나오게는 못하나?;;
    }
    private void doMultiMap() {
        Observable.fromArray(strList)
                .toMultimap(String::length)
                .subscribe(list -> ((TextView) findViewById(R.id.txt_result2)).setText(list.toString()) );
        //toMultimap은 인자로 함수를 받아--> 함수값 + 인자를 배출해준다. 각 item의 글자길이에 따른 item 배열을 구할 수 있다.
    }
}
