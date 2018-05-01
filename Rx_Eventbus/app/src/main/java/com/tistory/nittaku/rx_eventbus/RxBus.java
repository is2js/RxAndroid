package com.tistory.nittaku.rx_eventbus;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    private static RxBus mInstance;
    private BehaviorSubject<Object> mSubject;

    private RxBus() {
        mSubject = BehaviorSubject.create();
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            mInstance = new RxBus();
        }
        return mInstance;
    }

    public void sendBus(Object object) {
        mSubject.onNext(object);
    }

    public Observable<Object> getBus() {
        return mSubject;
    }
}