package com.temp.builder;

import com.sun.deploy.nativesandbox.NativeSandboxBroker;

import java.util.Random;

public class Helper {

    public static Random random = new Random();

    public static double roundMe(double rand) {
        return (double)Math.round(rand*10)/10;
    }

    public static double randTemp(float temp, float diff) {
        double rand = random.nextDouble() * diff;
        rand = roundMe(rand);
        double var = random.nextDouble();

        if(var < 0.5) {
            return roundMe(temp - rand);
        }
        return roundMe(temp + rand);
    }

    public static int getRand(int from, int to) {

        return random.nextInt(to - from) + from;

    }

    public static int calcSize(double cm) {
        return (int)(cm*100*13);
    }

    public static int calcSizePoints(double cm) {
        return (int)(cm*100*0.32);
    }
}
