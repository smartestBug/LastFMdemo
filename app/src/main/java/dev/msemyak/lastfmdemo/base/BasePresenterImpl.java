package dev.msemyak.lastfmdemo.base;

public class BasePresenterImpl<T> {

    protected T myView;

    protected BasePresenterImpl(T myView) {
        this.myView = myView;
    }

}
