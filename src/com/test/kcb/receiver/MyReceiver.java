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
			// ͨ��Notification.Builder������֪ͨ��ע��API Level
			// API16֮���֧��
			Notification notify = new Notification.Builder(context)
					.setSmallIcon(R.drawable.ic_launcher)
					.setTicker("�Ͽ�����")
					.setContentTitle("�γ̹���ϵͳ")
					.setContentText(course.day+"-"+course.start+"����ʮ���ӾͿ��ο���׼��ȥ�Ͽΰ�")
					.setContentIntent(pendingIntent).build(); // ��Ҫע��build()����API
			// level16��֮�����ӵģ�API11����ʹ��getNotificatin()�����
			notify.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL������֪ͨ���û����ʱ��֪ͨ���������
			// ��Android����֪ͨ����������Ҫ��ϵͳ������֪ͨ������NotificationManager������һ��ϵͳService��
			NotificationManager manager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.notify(NOTIFICATION_FLAG, notify);// ����4��ͨ��֪ͨ������������֪ͨ�����id��ͬ����ÿclick����status��������һ����ʾ
		}

	}

}
