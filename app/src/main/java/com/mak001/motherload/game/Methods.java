package com.mak001.motherload.game;

/**
 * Created by Matthew on 3/21/2017.
 */

public class Methods {

    public static boolean between(int a, int b, int x) {
        return a < x && x < b;
    }

    public static boolean between(double a, double b, double x) {
        return a < x && x < b;
    }

    // for zeroing a float that is within +/- 0.003
    public static boolean isZero(float value) {
        return isZero(value, 0.003f);
    }

    public static boolean isZero(float value, float threshold) {
        return value >= -threshold && value <= threshold;
    }

}
