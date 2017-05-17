package com.morfeus.android.mfsdk.push;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.morfeus.android.mfsdk.log.LogManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Notification service to receive notification
 */
public class FirebaseServiceImpl extends FirebaseMessagingService {

    public static final String EXTRA_DATA = "extra_data";

    //    public static final String EXTRA_PUSHID = "extra_pushid";
    private static final String TAG = FirebaseServiceImpl.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (!remoteMessage.getData().isEmpty()) {
            HashMap<String, String> dataMap = new HashMap<>();
            Map<String, String> remoteDataMap = remoteMessage.getData();
            dataMap.put("title", remoteDataMap.get("title"));
            dataMap.put("description", remoteDataMap.get("description"));
            dataMap.put("card", remoteDataMap.get("card"));
            dataMap.put("pushId", remoteDataMap.get("pushId"));
            broadcastData(dataMap);
        } else {
            LogManager.d(TAG, "Failed to get push push id");
        }
        LogManager.d(TAG, "onMessageReceived: " + remoteMessage.toString());
    }

    private void broadcastData(HashMap<String, String> mapData) {
        if (mapData != null && !mapData.isEmpty()) {
            LogManager.d(TAG, "broadcastData: ");
            Intent intent = new Intent(NotificationServiceHelper.NOTIFICATION_INTENT_FILTER);
            intent.putExtra(EXTRA_DATA, mapData);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }
}
