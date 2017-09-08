package com.example.flower.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by FLOWER on 2017/9/7.
 */

public class PermissionUtils {

    private int requestCode;
    private Context mContext;

    public boolean checkPermissionFirst(Context context , int requestCode, String[] permission){
        this.requestCode=requestCode;
        mContext = context;
        List<String> permissions = new ArrayList<String>();
        for (String per : permission) {
            int permissionCode = ActivityCompat.checkSelfPermission(context, per );
            if( permissionCode != PackageManager.PERMISSION_GRANTED ) {
                permissions.add(per);
            }
        }
        if( !permissions.isEmpty() ) {
            ActivityCompat.requestPermissions((Activity) context,permissions.toArray( new String[permissions.size()] ), requestCode);
            return  false;
        }else{
            return  true;
        }
    /*    int cameraPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA );
        int writeExternalPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE );
        List<String> permissions = new ArrayList<String>();
        if( cameraPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add( Manifest.permission.CAMERA );
        }
        if( writeExternalPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add( Manifest.permission.WRITE_EXTERNAL_STORAGE );
        }
        if( !permissions.isEmpty() ) {
            ActivityCompat.requestPermissions((Activity) context,permissions.toArray( new String[permissions.size()] ), requestCode);
            return  false;
        }else{
            return  true;
        }*/
    }
    public boolean checkPermissionSecond (Context context , int requestCode, String[] permission){
        this.requestCode=requestCode;
        mContext = context;
        List<String> permissions = new ArrayList<String>();
        for (String per : permission) {
            int permissionCode = ActivityCompat.checkSelfPermission(context, per );
            if( permissionCode != PackageManager.PERMISSION_GRANTED ) {
                permissions.add(per);
            }
        }
        if( !permissions.isEmpty() ) {
            Toast.makeText(mContext,"1111111" ,Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions((Activity) context,permissions.toArray( new String[permissions.size()] ), requestCode);
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
            }
            mContext.startActivity(localIntent);
            return  false;
        }else{
            /*Toast.makeText(mContext,"222222222" ,Toast.LENGTH_LONG).show();
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
            }
            mContext.startActivity(localIntent);*/
            return  true;
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==this.requestCode){
            boolean isPermissions=true;
            for( int i = 0; i < permissions.length; i++ ) {
                if( grantResults[i] == PackageManager.PERMISSION_GRANTED ) {
                    Toast.makeText(mContext,"权限开启" ,Toast.LENGTH_LONG).show();
                } else if( grantResults[i] == PackageManager.PERMISSION_DENIED ) {
                    //Toast.makeText(mContext,"开启权限失败" ,Toast.LENGTH_LONG).show();
                    isPermissions=false;
                }
            }
            if (isPermissions){
                return true;
            }else{
                Toast.makeText(mContext,"请开启相关权限，否则无法进行改操作" ,Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
}
