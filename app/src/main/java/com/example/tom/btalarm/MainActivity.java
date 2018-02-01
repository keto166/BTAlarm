package com.example.tom.btalarm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //TODO make and implement device list fragment
    //TODO make and implement alarm notification choice fragment
    //TODO make and implement notification when service is on

    public static MainActivity instance = null;
    public BluetoothAdapter myBluetooth = null;
    public HashMap<String,String> deviceMap; //<Device Name,Address>
    public ArrayList<String> deviceNames;

    Button buttonDeviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniVars();

        if (BTDCListenerService.instance == null) {
            Intent i = new Intent(this.getApplicationContext(), BTDCListenerService.class);
            startService(i);
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBTDevices();
    }

    public void iniVars() {
        if (instance == null) {
            instance = this;
        } else {
            //ERROR, not singleton
        }
        buttonDeviceList = (Button) findViewById(R.id.d_bDeviceList);
        buttonDeviceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBTDevices();
                try {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    DeviceListFragment listFrag = new DeviceListFragment();
                    ft.replace(R.id.d_listFragment, listFrag);
                    ft.commit();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Starting the fragment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        deviceMap = new HashMap<>();
        deviceNames = new ArrayList<>();
    }

    public void updateBTDevices() {

        if (myBluetooth == null) {
            //TODO bluetooth not found
        }

        if (!myBluetooth.isEnabled()) {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(i,1);
        }
        try {
            Set<BluetoothDevice> deviceSet = myBluetooth.getBondedDevices();
            String s;
            int x;
            deviceMap.clear();
            for (BluetoothDevice device : deviceSet) {
                if (deviceMap.containsKey(device.getName())) {
                    s = device.getName();
                    x = 2;

                    while (deviceMap.containsKey(s + " - " + x)) {
                        x++;
                    }
                    deviceMap.put(s + " - " + x, device.getAddress());
                } else {
                    deviceMap.put(device.getName(), device.getAddress());
                }
            }
            deviceNames.clear();
            deviceNames.addAll(deviceMap.values());
        } catch (Exception e) {
            Toast.makeText(instance, e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(instance, "Setting the map and stuff", Toast.LENGTH_SHORT).show();
        }



    }
}
