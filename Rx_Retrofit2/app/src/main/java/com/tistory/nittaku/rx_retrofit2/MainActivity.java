package com.tistory.nittaku.rx_retrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //static final String BASE_URL = "http://api.openweathermap.org";
    //static final String API_KEY = "11c7f631cd74614447645fc773e2a1a7";
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
        Observable<Bus> weatherData = service.getData( busstop_Id );

        // 4. scribeOn
        // 가. 데이터를 가져오는 대상 Observable : newTHread 로 새로운 Thread 에서 작업한다.
        // 나. 화면에 새팅하는 Observer : main Thread에서 작업한다.

        weatherData.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            String temp = "";
                            temp += "RESULTMSG:" + data.getRESULT().getRESULTMSG();
                            temp += "\nRESULTCODE:" + data.getRESULT().getRESULTCODE();
                            temp += "\nBUSTOPLIST : "+ data.getBUSSTOPLIST();
                            temp += "\nROWCOUNT:" + data.getROWCOUNT();
                            result.setText(temp);
                        }
                );

    }
}
