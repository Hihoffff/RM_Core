package com.ruinsmc.Utils;

import com.ruinsmc.RM_Core;

import java.util.Random;

public class Utils {
    public static final Random RANDOM = new Random();
    private final RM_Core plugin;

    public Utils(RM_Core plugin){
        this.plugin = plugin;
    }

    public int randomInRangeInt(int min, int max){
        if (min == max)
            return min;

        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        return RANDOM.nextInt(max - min + 1) + min;
    }


}
