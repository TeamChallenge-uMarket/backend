package com.example.securityumarket.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class DelayedMethodInvoker {

    private final long DELAY_MS = 5 * 60 * 1000; // 5 хвилин

    public void invokeDelayedMethod(Runnable method) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                method.run();
                timer.cancel();
            }
        }, DELAY_MS);
    }
}
