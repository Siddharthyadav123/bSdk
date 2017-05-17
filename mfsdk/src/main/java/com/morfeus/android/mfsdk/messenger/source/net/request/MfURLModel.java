package com.morfeus.android.mfsdk.messenger.source.net.request;

import java.util.HashMap;

/**
 * URLModel calls used to build urls.
 */

class MfURLModel {
    private HashMap<String, String> mapHeader = new HashMap<>();
    private HashMap<String, String> mapPath = new HashMap<>();

    String getPath(String key) {
        return mapPath.get(key);
    }

    void addPath(String k, String v) {
        mapPath.put(k, v);
    }

    String getHeader(String key) {
        return mapHeader.get(key);
    }

    void addHeader(String k, String v) {
        mapHeader.put(k, v);
    }

}
