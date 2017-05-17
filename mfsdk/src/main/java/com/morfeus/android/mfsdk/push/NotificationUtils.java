package com.morfeus.android.mfsdk.push;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import com.morfeus.android.R;
import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.push.model.PushModel;
import com.morfeus.android.mfsdk.ui.screen.chat.ChatActivity;

import java.util.List;

/**
 * Utility for Notification service.
 */
public final class NotificationUtils {

    private static final String TAG = NotificationUtils.class.getSimpleName();

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (isFromSDKPackage(context, componentInfo)) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    /**
     * True if {@link ChatActivity} on top of the stack and application is in background.
     */
    public static boolean isChatOnTopOfStack(Context context) {
        boolean isOnTop = false;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        if (isChatActivityOnTopOfTask(context, componentInfo)) {
            isOnTop = true;
        }
        return isOnTop;
    }

    public static void showHeadsUpNotification(Context context, PushModel pushModel) {
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(context);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentTitle(pushModel.getTitle());
        builder.setContentText(pushModel.getDescription());
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setAutoCancel(true);

        Intent chatIntent =
                new Intent(context, ChatActivity.class);
        chatIntent.putExtra("extra_push_model", pushModel);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                101,
                chatIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager notificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notification);
    }

    public static void showNotification() {

    }

    private static boolean isChatActivityOnTopOfTask(Context context, ComponentName componentInfo) {
        return isFromSDKPackage(context, componentInfo)
                && isChatActivityClass(componentInfo);
    }

    private static boolean isChatActivityClass(ComponentName componentInfo) {
        LogManager.d(TAG, "ComponentClassName: " + componentInfo.getClassName());
        return componentInfo.getClassName().equals(ChatActivity.class.getName());
    }

    private static boolean isFromSDKPackage(Context context, ComponentName componentInfo) {
        return componentInfo.getPackageName().equals(context.getPackageName());
    }


}
