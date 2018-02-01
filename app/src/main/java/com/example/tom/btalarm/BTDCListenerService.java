package com.example.tom.btalarm;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Created by tom on 1/31/18.
 */

public class BTDCListenerService extends Service {
    public static BTDCListenerService instance = null;

    public static final String FILENAME = "BlueToothAlarmProfile";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
        new BTDCProfileData();
        loadData();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void toasty(Intent intent) {

        Bundle b = intent.getExtras();
        String disconnedAddress = ((BluetoothDevice)b.getParcelable(BluetoothDevice.EXTRA_DEVICE)).getAddress();

        if (BTDCProfileData.instance.savedAddresses.contains(disconnedAddress)) {
            Toast.makeText(instance, "YAY", Toast.LENGTH_SHORT).show();
            try {

                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }

    @Override
    public void onDestroy() {
        saveData();
        super.onDestroy();
    }

    public void endService() {
        stopSelf();
    }

    public void saveData() {

        FileOutputStream fileOut = null;
        ObjectOutputStream objectOut = null;
        try {
            fileOut = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(BTDCProfileData.instance);
            objectOut.flush();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {fileOut.close();} catch (Exception e) {}
            try {objectOut.close();} catch (Exception e) {}
        }
    }

    public void loadData() {
        File f = new File(getApplicationContext().getFilesDir(),FILENAME);
        if (f.exists()) {
            FileInputStream fin = null;
            ObjectInputStream in = null;
            try {
                fin = openFileInput(FILENAME);
                in = new ObjectInputStream(fin);
                BTDCProfileData temp = (BTDCProfileData) in.readObject();

                BTDCProfileData.updateSingleton(temp);

            } catch (Exception e) {

            } finally {
                try {in.close();} catch (Exception e) {}
                try {fin.close();} catch (Exception e) {}

            }
        } else {
            if (BTDCProfileData.instance == null) {
                new BTDCProfileData();
            }
            saveData();
        }
    }

}
