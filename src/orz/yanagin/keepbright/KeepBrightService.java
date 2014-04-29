package orz.yanagin.keepbright;

import orz.yanagin.commons.android.ActivityHelper;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.Toast;

public class KeepBrightService extends Service {

	private static final int NOTIFICATION_ID = 1;

	private WakeLock lock ;

	private NotificationManager notificationManager;

	private Notification notification;

	@Override
	public void onCreate() {
		super.onCreate();

		PowerManager pm = ActivityHelper.getSystemService(this, Context.POWER_SERVICE);
		lock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "KeepBright");

		notificationManager = ActivityHelper.getSystemService(this, Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
		notification = new Notification(
				R.drawable.ic_launcher,
				getString(R.string.app_name),
				System.currentTimeMillis());
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notification.setLatestEventInfo(
				this,
				getString(R.string.app_name),
				"Keep Bright",
				pendingIntent);
		notification.icon = R.drawable.ic_launcher;
		notificationManager.notify(NOTIFICATION_ID, notification);

		Toast.makeText(this, "Start keep bright", Toast.LENGTH_SHORT).show();

		try {
			lock.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (lock != null) {
			try {
				lock.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			notificationManager.cancelAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Toast.makeText(this, "Finish keep bright", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
