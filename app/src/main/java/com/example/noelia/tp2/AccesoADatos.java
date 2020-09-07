package com.example.noelia.tp2;

import android.Manifest;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import java.util.Date;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class AccesoADatos extends Service {
    public AccesoADatos() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Uri sms = Telephony.Sms.CONTENT_URI;
        final ContentResolver cr=getContentResolver();

        Runnable leer=new Runnable(){
            @Override
            public void run() {

                while(true){
                    Cursor c = cr.query(sms, null, null, null, null);
                    if(c.getCount()>0){
                        int i=0;
                        while(c.moveToNext()&& i<5){
                            String nro=c.getString(c.getColumnIndex(Telephony.Sms.Inbox.ADDRESS));
                            String fecha=c.getString(c.getColumnIndex(Telephony.Sms.Inbox.DATE));
                            long dateLong=Long.parseLong(fecha);
                            Date date = new Date(dateLong);
                            String contenido=c.getString(c.getColumnIndex(Telephony.Sms.Inbox.BODY));
                            Log.d("salida", "Sms del Numero" + nro+"recibida el:" + date.toString()+"--" +contenido);
                            i++;
                        }
                        try{
                            Thread.sleep(9000);
                        }catch(InterruptedException e){
                            Log.e("Error",e.getMessage());

                        }
                    }
                }

            }
        };
        Thread trabajador= new Thread(leer);
        trabajador.start();
        return START_STICKY;
    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
