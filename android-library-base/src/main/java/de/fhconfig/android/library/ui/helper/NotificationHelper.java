package de.fhconfig.android.library.ui.helper;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper
{
	public static void showSimpleNotification(Context context, int id, String title, String message, int icon, Class<? extends Activity> activity)
	{
		NotificationManager manager = (NotificationManager) context.getSystemService(Application.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setContentTitle(title);
		builder.setContentText(message);
		builder.setSmallIcon(icon);
		builder.setStyle(new NotificationCompat.InboxStyle());
		builder.setAutoCancel(true);
		builder.setLights(Color.RED, 500, 500);
		builder.setVibrate(new long[] { 500 });
		builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

		Intent notificationIntent = new Intent(context, activity);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		builder.setContentIntent(contentIntent);

		manager.notify(id, builder.build());
	}
}
