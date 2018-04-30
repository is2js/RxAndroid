package com.tistory.nittaku.rx_binding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Random;

import io.reactivex.Observable;

import static com.jakewharton.rxbinding2.view.RxView.clicks;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        clicks(findViewById(R.id.btn_bind))
                .map(event -> new Random().nextInt())
                .subscribe(
                        rand -> {
                            ((TextView) findViewById(R.id.textView)).setText("BIND버튼 클릭시 랜덤 정수= " + rand);
                            Toast.makeText(this, "Bind버튼을 눌러 랜덤정수를 생성합니다.", Toast.LENGTH_SHORT).show();
                        }

                );

        Observable<String> leftObs = clicks(findViewById(R.id.btn_Left))
                .map(event -> "Merge를 통해 버튼2개에 토스트를 걸었으나, 먼저 온 왼쪽클릭");
        Observable<String> rightObs = clicks(findViewById(R.id.btn_Right))
                .map(event -> "Merge를 통해 버튼2개에 토스트를 걸었으나, 먼저 온 오른쪽클릭");
        Observable.merge(leftObs, rightObs) //merge를 이용해서 2개의 버튼-클릭이벤트를 Binding한 옵져버블을 한꺼번에 토스트 띄우도록 코드를 짠것임. 그러나 merge는 2개 중 먼저온것 방출시킴.
                .subscribe(
                        text -> Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                );


        RxTextView.textChangeEvents(findViewById(R.id.txt_edit))
                .map(word->word.text()) // textChangEvents의 return은 text를 포함한 옵져버블인 것 같다. 거기서 .text()를 통해 텍스트만 추출할 수 있었다.
                .subscribe(
                        word -> Toast.makeText(this, "적힌 글 = " + word ,Toast.LENGTH_SHORT).show()
                );


    }
}
