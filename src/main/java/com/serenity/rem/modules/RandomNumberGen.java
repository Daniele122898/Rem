package com.serenity.rem.modules;

import java.util.Random;

public class RandomNumberGen {


    private static Random rand = new Random(System.currentTimeMillis());

    public static int getRandIntBetween(int lowerBound, int upperBound){
        return rand.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static int getRandint(int upperBound){

        return rand.nextInt(upperBound);
    }

    public static int randomSign(){
        return 1 | (rand.nextInt() >> 31);
    }

}
