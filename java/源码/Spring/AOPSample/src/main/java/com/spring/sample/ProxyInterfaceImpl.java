package com.spring.sample;

import org.springframework.stereotype.Component;

@Component
public class ProxyInterfaceImpl implements ProxyTestInterface{
    @Override
    public int sum(int a, int b) {
        return a+b;
    }

    @Override
    public int sub(int a, int b) {
        return a-b;
    }

    @Override
    public int mul(int a, int b) {
        return a*b;
    }

    @Override
    public double dev(double a, double b) {
        return a/b;
    }
}
