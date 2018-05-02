package com.tistory.nittaku.rx_retrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends RxAppCompatActivity {
//    http://bis.naju.go.kr:8080/json/arriveAppInfo?BUSSTOP_ID=1439
    static final String BASE_URL = "http://bis.naju.go.kr:8080/";
    String bus;

    EditText busnumber;
    TextView result;
    Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        busnumber = (EditText) findViewById(R.id.txt_bus);
        result = (TextView) findViewById(R.id.txt_result);
        btn_search = (Button) findViewById(R.id.btn_search);
        RxView.clicks(btn_search)
                .subscribe(e -> getBus() );
    }

    public void getBus(){
        String busstop_Id = busnumber.getText().toString();  //숫자여도, string에 담아야함. 주소에 붙기 때문에

        // 1. Retrofit 클라이언트 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // 2. RestAPi 서비스 생성
        Retrofit2 service = client.create(Retrofit2.class);

        // 3. 데이터 Observable 생성


        // 4. scribeOn
        // 가. 데이터를 가져오는 대상 Observable : newTHread 로 새로운 Thread 에서 작업한다.
        // 나. 화면에 새팅하는 Observer : main Thread에서 작업한다.
        Observable<Bus> BusData = service.getData( busstop_Id );



        BusData
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(
                        data -> {
                            String temp = "";
                            temp += "RESULTMSG:" + data.getRESULT().getRESULTMSG();
                            temp += "\nRESULTCODE:" + data.getRESULT().getRESULTCODE();
                            temp += "\nROWCOUNT:" + data.getROWCOUNT();
                            result.setText(temp);
                        }
                        ,e-> Toast.makeText(this, "인터넷이 연결되지 않았어요", Toast.LENGTH_SHORT).show()
                        ,()->{Toast.makeText(this, "정상적으로 버스정보를 가져왔습니다.", Toast.LENGTH_SHORT).show(); }
                );

        Observable.interval(0,5, TimeUnit.SECONDS)
                .flatMap(n-> BusData) //5초마다 갱신해주는 인터벌을 넣고 리턴은 숫자니까 -> 미리 만들어준 BusData 옵져버블로 형변환
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe( item->
                        {
                            if (item.getRESULT().getRESULTCODE().equals("SUCCESS")) {
                                Toast.makeText(this, "버스도착정보가 있음", Toast.LENGTH_SHORT).show();
                                StringBuffer bf = new StringBuffer();


                                Observable.fromIterable( item.getBUSSTOPLIST() ) //json속 배열을 가진 놈은 fromIterable로 쪼개어서 쓴다!!
                                        .subscribe(list ->
                                                {
                                                    bf. append("<< "+list.getLINENAME().toString()+" >> 버스가 \n" );
                                                    bf. append("도착까지" + list.getREMAINMIN().toString()+"분 ("+list.getREMAINSTOP().toString()+"개 정류장) 남았어요\n" );
                                                    bf. append("현재버스 위치는 " + list.getBUSSTOPNAME().toString()+"정류장 이에요.\n" );

                                                    ((TextView) findViewById(R.id.txt_result2)).setText(bf);
                                                }
                                        );
                            } else ((TextView) findViewById(R.id.txt_result2)).setText("버스도착정보가 없음");
                        }
                        , e-> Toast.makeText(this, "인터넷이 연결되지 않았어요", Toast.LENGTH_SHORT).show()
                        , ()->{ Toast.makeText(this, "정상적으로 버스정보를 가져왔습니다.", Toast.LENGTH_SHORT).show(); }
                        );




    }
}
