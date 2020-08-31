package com.common.framework.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * @描述：     @权限相关操作帮助类
 * @作者：     @ljl
 * @创建时间： @2020-05-11
 */
public final class PermissionHolder {

    private static  int mRequestCode = -1;

    private static OnPermissionListener sOnPermissionListener;

    public interface OnPermissionListener {

        void onPermissionGranted();

        void onPermissionDenied(String[] deniedPermissions);
    }

    public abstract static class RationaleHandler {
        private Context context;
        private int requestCode;
        private String[] permissions;

        protected abstract void showRationale();

        void showRationale(Context context, int requestCode, String[] permissions) {
            this.context = context;
            this.requestCode = requestCode;
            this.permissions = permissions;
            showRationale();
        }

        @TargetApi(Build.VERSION_CODES.M)
        public void requestPermissionsAgain() {
            ((Activity) context).requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static final void requestPermissions(Context context, int requestCode
            , String[] permissions, OnPermissionListener listener) {
        requestPermissions(context, requestCode, permissions, listener, null);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static final void requestPermissions(Context context, int requestCode
            , String[] permissions, OnPermissionListener listener, RationaleHandler handler) {
        if (context instanceof Activity) {
            mRequestCode          = requestCode;
            sOnPermissionListener = listener;
            String[] deniedPermissions = getDeniedPermissions(context, permissions);
            if (deniedPermissions.length > 0) {
                boolean rationale = shouldShowRequestPermissionRationale(context, deniedPermissions);
                if (rationale && handler != null) {
                    handler.showRationale(context, requestCode, deniedPermissions);
                } else {
                    ((Activity) context).requestPermissions(deniedPermissions, requestCode);
                }
            } else {
                if (sOnPermissionListener != null){
                    sOnPermissionListener.onPermissionGranted();
                }
            }
        } else {
            throw new RuntimeException("Context must be an Activity");
        }
    }

    /**
     * 请求权限结果，对应Activity中onRequestPermissionsResult()方法。
     */
    public static final void onRequestPermissionsResult(Activity context, int requestCode, String[] permissions, int[]
            grantResults) {
        if (mRequestCode != -1 && requestCode == mRequestCode) {
            if (sOnPermissionListener != null) {
                String[] deniedPermissions = getDeniedPermissions(context, permissions);
                if (deniedPermissions.length > 0) {
                    sOnPermissionListener.onPermissionDenied(deniedPermissions);
                } else {
                    sOnPermissionListener.onPermissionGranted();
                }
            }
        }
    }

    /**
     * 获取请求权限中需要授权的权限
     */
    private static final String[] getDeniedPermissions(final Context context, final String[] permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions.toArray(new String[deniedPermissions.size()]);
    }

    /**
     * 是否彻底拒绝了某项权限
     */
    public static final boolean hasAlwaysDeniedPermission(final Context context, final String... deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (!shouldShowRequestPermissionRationale(context, deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有权限需要说明提示
     */
    private static final boolean shouldShowRequestPermissionRationale(
            final Context context, final String... deniedPermissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        boolean rationale;
        for (String permission : deniedPermissions) {
            rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
            if (rationale) return true;
        }
        return false;
    }
}
