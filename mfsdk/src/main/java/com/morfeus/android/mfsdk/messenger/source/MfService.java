package com.morfeus.android.mfsdk.messenger.source;

import android.support.annotation.UiThread;

import com.morfeus.android.mfsdk.volley.Request;

/**
 * List of services provided by network
 */
public interface MfService {
    /**
     * Init endpoint returns the welcome message for SDK users.
     * <br>
     * <br>
     * <b>Response Content Type</b> : application/json
     * <br>
     * <br>
     * <b>Parameter</b>
     * <table Parameter="Parameter">
     * <tr>
     * <td><b>Parameter</b></td>
     * <td><b>Description</b></td>
     * <td><b>Data Type</b></tc>
     * </tr>
     * <tr>
     * <td>botChannelID</td>
     * <td>Unique identifier of the bot for channel</td>
     * <td>String</td>
     * </tr>
     * <tr>
     * <td>body</td>
     * <td>Request object that needs to be sent</td>
     * <td>String</td>
     * </tr>
     * </table>
     * <br>
     * <br>
     * <b>Response Message</b>
     * <p/>
     * <table Response Message="Response Message">
     * <tr>
     * <td><b>HTTP Status Code</b></td>
     * <td><b>Reason</b></td>
     * </tr>
     * <tr>
     * <td>201</td>
     * <td>Created</td>
     * </tr>
     * <tr>
     * <td>401</td>
     * <td>Unauthorized</td>
     * </tr>
     * <tr>
     * <td>403</td>
     * <td>Forbidden</td>
     * </tr>
     * <tr>
     * <td>404</td>
     * <td>Not Found</td>
     * </tr>
     * </table>
     */
    int INIT_POST_REQUEST = 0;

    /**
     * <br>
     * <br>
     * <b>Response Content Type</b> : application/json
     * <br>
     * <br>
     * <b>Parameter</b>
     * <table Parameter="Parameter">
     * <tr>
     * <td><b>Parameter</b></td>
     * <td><b>Description</b></td>
     * <td><b>Data Type</b></tc>
     * </tr>
     * <tr>
     * <td>botChannelID</td>
     * <td>Unique identifier of the bot for channel</td>
     * <td>String</td>
     * </tr>
     * <tr>
     * <td>body</td>
     * <td>Request object that needs to be sent</td>
     * <td>String</td>
     * </tr>
     * </table>
     * <br>
     * <br>
     * <b>Response Message</b>
     * <p/>
     * <table Response Message="Response Message">
     * <tr>
     * <td><b>HTTP Status Code</b></td>
     * <td><b>Reason</b></td>
     * </tr>
     * <tr>
     * <td>201</td>
     * <td>Created</td>
     * </tr>
     * <tr>
     * <td>401</td>
     * <td>Unauthorized</td>
     * </tr>
     * <tr>
     * <td>403</td>
     * <td>Forbidden</td>
     * </tr>
     * <tr>
     * <td>404</td>
     * <td>Not Found</td>
     * </tr>
     * </table>
     */
    int MESSAGE_POST_REQUEST = 1;


    int LOGIN_POST_REQUEST = 2;


    int TIMEOUT_POST_REQUEST = 3;


    /**
     * Call this method to initiate initRequest http
     * request [/v1/channels/{botChannelID}/initRequest]
     * <p/>
     * <b>Must call from UI thread only. Response will provided
     * only on UI thread</b>
     */
    @UiThread
    <T> void init(Request<T> request);

    /**
     * Call this method to send message to server
     * <p/>
     * <b>Must call from UI thread only. Response will provided
     * only on UI thread</b>
     */
    @UiThread
    <T> void sendMessage(Request<T> request);

    /**
     * Call this method to send login request to server.
     * <p/>
     * <b>Must call from UI thread only. Response will provided
     * only on UI thread</b>
     */
    <T> void sendLogin(Request<T> request);

    /**
     * Call this method to send timeout request to server.
     * <p/>
     * <b>Must call from UI thread only. Response will provided
     * only on UI thread</b>
     */
    <T> void sendTimeout(Request<T> request);
}
