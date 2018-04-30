package com.tistory.nittaku.rx_logincheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnLogin = findViewById(R.id.btn_login); //활성화 or 비활성화 옵션을 넣기 위해서, 이번에는 버튼 변수를 따로 만든다.
        btnLogin.setEnabled(false); //먼저 버튼을 비활성화 해놓는다. (조건 만족시 활성화하는 코드가 들어간다)

        // id와 pass가 적히는 에디트뷰에 대해서, RxTextView.textChangeEvents를 binding한 뒤 -> 각 옵져버블<TextViewTextChangeEvent>타입으로 할당해준다.
        Observable<TextViewTextChangeEvent> idObs
                =  RxTextView.textChangeEvents(findViewById(R.id.txt_login));
        Observable<TextViewTextChangeEvent> passObs
                =  RxTextView.textChangeEvents(findViewById(R.id.txt_pass));

        //id와 pass가 바인딩된 옵져버블에 대해서 combineLatest로 묶어준다. combineLatest는 zip과 다르게, 해당시점에서 가장 최근의 item을 각각 가져와서 합친다.
        Observable.combineLatest(idObs,passObs,                             // 2개의 옵져버블을 받고
                (idChanges,passChanges) -> {                                // 옵져버블고 무관한 2개의 인자 입력(생성이나 마찬가지)
                    boolean idCheck = idChanges.text().length() >= 8;       // 첫번째 id옵져버블의 변화. text()추출 후 , 그 길이가 8이상   일 때, boolean형의 첫번째 인자에게 true를
                    boolean passCheck = passChanges.text().length() >= 6;   // 2번째 pass옵져버블의 변화. text() 추출후 , 그 길이가 6이상   일 때, boolean형의 2번째 인자에게 true를 대입해준다.
                    return idCheck && passCheck;                            // 2개의 결과가 모두 true일 때,  true를 반환할 수 있도록,   boolean인자 2개를 && (and연산자)로 만들어 return해준다.
                })
                .subscribe(  //subscribe에 넘어오는 값은 =true 혹은 false일 것이다.
                        checkFlag -> btnLogin.setEnabled(checkFlag) // true 로 넘어온다면, 버턴 활성화 인자에 true가 들어가서 활성화가 될 것이다.
                );
    }
}
