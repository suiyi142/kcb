package com.test.kcb.receiver;

import com.test.kcb.R;
import com.test.kcb.activity.MainActivity;
import com.test.kcb.bean.Course;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
	private static final int NOTIFICATION_FLAG = 1;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("SEND_NOTIFICATION")) {
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					new Intent(context, MainActivity.class), 0);
			Course course = (Course) intent.getSerializableExtra("course");
			// 通过Notification.Builder来创建通知，注意API Level
			// API16之后才支持
			Notification notify = new Notification.Builder(context)
					.setSmallIcon(R.drawable.ic_launcher)
					.setTicker("上课提醒")
					.setContentTitle("课程管理系统")
					.setContentText(course.day+"-"+course.start+"还有十分钟就开课咯！准备去上课吧")
					.setContentIntent(pendingIntent).build(); // 需要注意build()是在API
			// level16及之后增加的，API11可以使用getNotificatin()来替代
			notify.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
			// 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
			NotificationManager manager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(NOTIFICATION_FLAG, notify);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
		}

	}

}
