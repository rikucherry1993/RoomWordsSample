package com.codelab.roomwordssample.util;

import androidx.test.espresso.idling.CountingIdlingResource;

public class EspressoIdlingResource {

    private static String RESOURCE = "GLOBAL";

    public static CountingIdlingResource idlingResource = new CountingIdlingResource(RESOURCE);

    public static void increment(){
        idlingResource.increment();
    }

    public static void decrement(){
        if(!idlingResource.isIdleNow()){
        idlingResource.decrement();}
    }

}
