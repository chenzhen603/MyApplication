package com.example.flower.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.img)
    ImageView img;

    private Context mContext;

    /**
     * terst*******************
     * 88888888888888888888888888888888
     * 777777777777777777777
     *
     * @param savedInstanceState
     *
     * 22222222222222222222
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mContext = this;
        int a = 10;
        int b = 12;
        Log.d(TAG, "onCreate: " + a);
        AnimationSet anim = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setStartOffset(500);
        alphaAnimation.setDuration(1000);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 90, 180, 180);
        rotateAnimation.setStartOffset(500);
        rotateAnimation.setDuration(1000);

        anim.addAnimation(alphaAnimation);
        anim.addAnimation(rotateAnimation);
        img.startAnimation(anim);
       /* int cameraPermission = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.CAMERA );
        int writeExternalPermission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE );
        final List<String> permissions = new ArrayList<String>();
        if( cameraPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add( Manifest.permission.CAMERA );
        }
        if( writeExternalPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add( Manifest.permission.WRITE_EXTERNAL_STORAGE );
        }
        if( !permissions.isEmpty() ) {
            ActivityCompat.requestPermissions((Activity) mContext,permissions.toArray( new String[permissions.size()] ),123);
        }*/
        final PermissionUtils permissionUtils = new PermissionUtils();
        permissionUtils.checkPermissionFirst(mContext,123, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA});
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setPermiss();
                permissionUtils.checkPermissionSecond(mContext,123, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE});
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==123){
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

            }else{
                Toast.makeText(mContext,"请开启相关权限，否则无法进行改操作" ,Toast.LENGTH_LONG).show();
            }
        }

    }

    private void setPermiss() {
        int cameraPermission = ActivityCompat.checkSelfPermission(mContext,Manifest.permission.CAMERA );
        int writeExternalPermission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE );
        List<String> permissions = new ArrayList<String>();
        if( cameraPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add( Manifest.permission.CAMERA );
        }
        if( writeExternalPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add( Manifest.permission.WRITE_EXTERNAL_STORAGE );
        }
        if( !permissions.isEmpty() ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                //shouldShowRationale(activity, requestCode, requestPermission);
                Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
            } else {
                //shouldShowRationale(this,123,Manifest.permission.CAMERA);
                //ActivityCompat.requestPermissions((Activity) this,permissions.toArray( new String[permissions.size()] ),123);
                Toast.makeText(mContext, "2", Toast.LENGTH_SHORT).show();
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", this.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", this.getPackageName());
                }
                startActivity(localIntent);
            }
        }
    }
    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        //TODO
        String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
        showMessageOKCancel(activity, "Rationale: " + permissionsHint[requestCode], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},
                        requestCode);
                Log.d(TAG, "showMessageOKCancel requestPermissions:" + requestPermission);
            }
        });
    }
    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }
}