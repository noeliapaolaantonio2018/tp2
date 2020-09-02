package com.example.noelia.tp2;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import static android.Manifest.permission.READ_SMS;

public class Contar extends Service {
    private int x = 0;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ContextCompat.checkSelfPermission(this, READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Conceder Permision", Toast.LENGTH_LONG).show();
        }
        Uri sms = Telephony.Sms.CONTENT_URI;
        ContentResolver cr=getContentResolver();
        Cursor c = cr.query(sms, null, null, null, null);
        while (true) {
            x++;
            if (x == 9000 || x == 18000 || x == 27000) {
                try {
                    if (c.getCount() > 0) {
                        int i = 0;
                        while (c.moveToNext() && i < 5) {
                            String nro = c.getString(c.getColumnIndex(Telephony.Sms.Inbox.ADDRESS));
                            String fecha = c.getString(c.getColumnIndex(Telephony.Sms.Inbox.DATE));
                            long dateLong = Long.parseLong(fecha);
                            Date date = new Date(dateLong);
                            String contenido = c.getString(c.getColumnIndex(Telephony.Sms.Inbox.BODY));
                            Log.d("salida", "Sms del Numero" + "recibida el:" + date.toString() + nro);

                        }
                    }
                    Thread.sleep(9000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            if (x == 30000) {
                Log.d("salida", "bucle cerradaso");
            }

        }
        return START_STICKY;
    }

}
