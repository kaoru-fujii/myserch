package com.example.broadcaster;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity
{
    String uuid  = new String("65432461-1EFE-4ADB-BC7E-9F7F8E27FDC1");

    BluetoothLeAdvertiser advertiser;
    AdvertiseSettings settings;
    public AdvertiseData advertiseData;
    //AdvertiseData.Builder dataBuilder = new AdvertiseData.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = manager.getAdapter();
        advertiser = adapter.getBluetoothLeAdvertiser();

        // 設定
        AdvertiseSettings.Builder settingBuilder = new AdvertiseSettings.Builder();
        settingBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER);
        settingBuilder.setConnectable(false);//アドバタイジングの種類を接続か非接続か選択
        settingBuilder.setTimeout(0);
        settingBuilder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_LOW);
        settings = settingBuilder.build();

        //** dataBuilder に設定するものを用意↓↓↓
        AdvertiseData.Builder dataBuilder = new AdvertiseData.Builder();

        // 1 アドバタイジングデータ
        ParcelUuid mUUID = new ParcelUuid(UUID.fromString(uuid));
        dataBuilder.addServiceUuid(mUUID);

        //2 ManufacturerSpecificDdata
        byte[] bytearray = new byte[16];
        bytearray[0] = 23;
        bytearray[13] = 13;

        dataBuilder.addManufacturerData(76,bytearray );
        dataBuilder.setIncludeDeviceName(true);
       // dataBuilder.addServiceData(new ParcelUuid(UUID.fromString("65")),bytearray); //←なぜか動かなくなってしまう

        //** dataBuilderをAdvertiseDataに格納
        advertiseData = dataBuilder.build();

        System.out.println("Oncreate      hoge1"+advertiseData);

        /*画面に表示*/
        ((TextView) findViewById(R.id.uuid)).setText( mUUID.toString() );
        ((EditText) findViewById(R.id.manudata)).setText( advertiseData.getManufacturerSpecificData().toString() );
        /*画面に表示*/


        //アドバタイズを開始
        advertiser.startAdvertising(settings, advertiseData, new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                super.onStartSuccess(settingsInEffect);
            }
            @Override
            public void onStartFailure(int errorCode) {
                super.onStartFailure(errorCode);
            }
        });

    }

    // リストビューのアイテムクリック時の処理
    public void onButtonClick(View view )
    {
        System.out.println("onButtonClick hoge3"+advertiseData);

        // クリックされたら
        //アドバタイズを開始
        advertiser.startAdvertising(settings, advertiseData, new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                super.onStartSuccess(settingsInEffect);
            }
            @Override
            public void onStartFailure(int errorCode) {
                super.onStartFailure(errorCode);
            }
        });
    }


//    // リストビューのアイテムクリック時の処理
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id )
//    {
//        System.out.println("onItemClick hoge3"+advertiseData.getManufacturerSpecificData());
//
//        // クリックされたら
//        //アドバタイズを開始
//        advertiser.startAdvertising(settings, advertiseData, new AdvertiseCallback() {
//            @Override
//            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
//                super.onStartSuccess(settingsInEffect);
//            }
//
//            @Override
//            public void onStartFailure(int errorCode) {
//                super.onStartFailure(errorCode);
//            }
//        });
//    }

}