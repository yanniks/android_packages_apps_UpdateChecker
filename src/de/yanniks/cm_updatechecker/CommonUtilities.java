package de.yanniks.cm_updatechecker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class CommonUtilities extends Application{

	public static String SENDER_ID = "490265773669";
    static final String SERVER_URL = "http://yanniks-updatechecker.rhcloud.com/gcm-demo/";
	public static boolean notificationReceived;
	public static String notiTitle="",notiType="",notiMsg="",notiUrl="",registrationId = "";
    static final String TAG = "UpdateChecker";

    /**
     * Intent used to display a message in the screen.
     */
    static final String DISPLAY_MESSAGE_ACTION =
            "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    static final String EXTRA_MESSAGE = "message";
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
