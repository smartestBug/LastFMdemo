package dev.msemyak.lastfmdemo.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {
    private static volatile RxBus sRxBus = null;
    private PublishSubject<Object> mPublishSubject = PublishSubject.create();

    private RxBus() {    }

    public static RxBus getInstance() {
        if (sRxBus == null) {
            synchronized (RxBus.class) {
                if (sRxBus == null) {
                    sRxBus = new RxBus();
                }
            }
        }
        return sRxBus;
    }

    public <T> Observable<T> listen(Class<T> cls) {
        return mPublishSubject
                .filter(o -> o.getClass().equals(cls))
                .map(o -> (T) o);
    }

    public void post(Object obj) {
        mPublishSubject.onNext(obj);
    }
}
