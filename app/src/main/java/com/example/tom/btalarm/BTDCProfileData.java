package com.example.tom.btalarm;


import java.io.Serializable;
import java.util.ArrayList;

public class BTDCProfileData implements Serializable {
    public static BTDCProfileData instance;
    public ArrayList<String> savedAddresses; //all the device addresses that have had an alarm set on them.



    public BTDCProfileData() {
        if (instance == null) {
            instance = this;
        }
        savedAddresses = new ArrayList<String>();
        savedAddresses.add("Empty");
    }

    public static void updateSingleton(BTDCProfileData source) {
        instance.savedAddresses.clear();
        instance.savedAddresses.addAll(source.savedAddresses);
        if (instance.savedAddresses.isEmpty()) {
            instance.savedAddresses.add("Empty");
        }
    }




}
