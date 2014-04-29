package orz.yanagin.keepbright;

import orz.yanagin.commons.android.ActivityHelper;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UsbConnectedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (Intent.ACTION_UMS_CONNECTED.equals(action)) {
			ActivityHelper.startService(context, KeepBrightService.class);
		} else if (Intent.ACTION_UMS_DISCONNECTED.equals(action)) {
			ActivityHelper.stopService(context, KeepBrightService.class);
		}
	}

}
