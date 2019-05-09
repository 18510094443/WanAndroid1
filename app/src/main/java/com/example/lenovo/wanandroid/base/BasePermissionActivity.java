package com.example.lenovo.wanandroid.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lenovo.wanandroid.utils.PermissionsUtils;


public class BasePermissionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //权限检查

    protected void checkPermission(){
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //是否支持显示系统设置权限设置窗口跳转
        //这里的this不是上下文，是Activity对象
        PermissionsUtils.getInstance().checkPermissions(this, permissions, permissionsResult);
    }
    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            Toast.makeText(BasePermissionActivity.this, "权限通过，可以做其他事情!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void forbitPermissons() {
            Toast.makeText(BasePermissionActivity.this, "权限不通过!", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
