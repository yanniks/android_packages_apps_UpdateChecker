package de.yanniks.cm_updatechecker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

import de.yanniks.cm_updatechecker.ServerUtilities;
import static de.yanniks.cm_updatechecker.CommonUtilities.displayMessage;

public class GCMIntentService extends GCMBaseIntentService{

	public GCMIntentService(){
		super(CommonUtilities.SENDER_ID);
	}

	@Override
	protected void onError(Context context, String regId) {
		// TODO Auto-generated method stub
		Log.e("", "error registration id : "+regId);
	}

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = getString(R.string.ready);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

	@Override
	protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
		handleRegistration(context, registrationId);
        ServerUtilities.register(context, registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		// TODO Auto-generated method stub
        if (GCMRegistrar.isRegisteredOnServer(context)) {
            ServerUtilities.unregister(context, registrationId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            Log.i(TAG, "Ignoring unregister callback");
        }
		
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private void handleMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		CommonUtilities.notiMsg = intent.getStringExtra("msg");
		CommonUtilities.notiTitle = intent.getStringExtra("title");
		CommonUtilities.notiType = intent.getStringExtra("type");
		CommonUtilities.notiUrl = intent.getStringExtra("url");
		
		int icon = R.drawable.ic_launcher;        // icon from resources
		CharSequence tickerText = CommonUtilities.notiTitle;//intent.getStringExtra("me");              // ticker-text
		
		long when = System.currentTimeMillis();         // notification time
		CharSequence contentTitle = ""+CommonUtilities.notiMsg; //intent.getStringExtra("me");  // message title

		NotificationManager notificationManager =
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, tickerText, when);
		Intent notificationIntent = new Intent(context, UpdateChecker.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, "", pendingIntent);
		notification.flags|=notification.FLAG_INSISTENT|notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(1, notification);
		CommonUtilities.notificationReceived=true;
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
		wl.acquire();
		
	}
	
	private void handleRegistration(Context context, String regId) {
		// TODO Auto-generated method stub
			CommonUtilities.registrationId = regId;
			Log.e("", "registration id : "+regId);
			
	}
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, UpdateChecker.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }
}
