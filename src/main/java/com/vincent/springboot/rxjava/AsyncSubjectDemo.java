package com.vincent.springboot.rxjava;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;

public class AsyncSubjectDemo {
    public static void main(String[] args) {
        AsyncSubject<String> subject =AsyncSubject.create();
        subject.onNext("async1");
        subject.onNext("async2");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("asyncSubject: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("AsyncSubject error");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("AsyncSubject : complete");
            }
        });
        subject.onNext("ssync3");
        subject.onNext("ssync4");
        subject.onComplete();
    }
}
