package com.temp.builder;

import java.util.Random;

public class Helper {

    public static Random random = new Random();

    public static double roundMe(double rand) {
        return (double)Math.round(rand*10)/10;
    }

    public static double randTemp(float temp, float diff) {
        float rand = random.nextFloat() * diff;
        rand = (float) Math.round(rand * 10) / 10;
        float var = random.nextFloat();

        float out;

        if (var < 0.5) {
            out = temp - rand;
        }
        out = temp + rand;

        return (double) Math.round(out * 10) / 10;
    }

    public static int getRand(int from, int to) {

        return random.nextInt(to - from) + from;

    }

    public static int calcSize(double cm) {
        return (int) (cm * 100 * 13);
    }

    public static int calcSizePoints(double cm) {
        return (int)(cm*100*0.32);

    }
}
