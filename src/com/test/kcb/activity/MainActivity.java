package com.test.kcb.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.R.integer;
import android.app.ActionBar.LayoutParams;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.kcb.R;
import com.test.kcb.bean.Course;
import com.test.kcb.constant.Constant;
import com.test.kcb.database.dao.CourseDao;
import com.test.kcb.receiver.MyReceiver;
import com.test.kcb.util.MyUtils;

public class MainActivity extends ActionBarActivity {
	private String sid;
	private RelativeLayout day;
	private int currentCoursesNumber = 0;
	private int maxCoursesNumber = 0;
	private CourseDao dao;
	private int i = 0;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss E");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		sid = intent.getStringExtra("id");
		refresh();

	}

	private void refresh() {
		dao = new CourseDao(this);
		ArrayList<Course> list = dao.findAall(sid);
		Log.e("size", list.size() + "");
		for (Course course : list) {
			System.out.println(course);
			createLeftView(course);
			createCourseView(course);
			long time = getClock(course);
			sendNotify(course,time);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.toolbar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//sendNotify(1L);
		Intent intent = new Intent(this, AddActivity.class);
		intent.putExtra("sid", sid);
		startActivityForResult(intent, 1);

		return true;
	}

	private void sendNotify(Course course,long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		Log.d("setNotify", format.format(c.getTime()));
		Intent intent = new Intent(this, MyReceiver.class);
		intent.putExtra("course", course);
		intent.setAction("SEND_NOTIFICATION");
		// PendingIntent这个类用于处理即将发生的事情 
		PendingIntent sender = PendingIntent.getBroadcast(this, i++, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		// AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用相对时间
		// SystemClock.elapsedRealtime()表示手机开始到现在经过的时间
		am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000*60*60*24*7, sender);

	}

	/**
	 * 
	 */
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg0 == arg1) {
			Course course = (Course) arg2.getSerializableExtra("course");
			createLeftView(course);
			createCourseView(course);
			long time = getClock(course);
			sendNotify(course,time);
		}
	}

	// 创建课程节数视图
	private void createLeftView(Course course) {
		int len = Integer.valueOf(course.end);
		if (len > maxCoursesNumber) {
			for (int i = 0; i < len - maxCoursesNumber; i++) {
				View view = LayoutInflater.from(this).inflate(
						R.layout.left_view, null);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						110, MyUtils.dip2px(this, 100f));
				view.setLayoutParams(params);

				TextView text = (TextView) view
						.findViewById(R.id.class_number_text);
				text.setGravity(Gravity.CENTER);
				text.setText(String.valueOf(++currentCoursesNumber));
				LinearLayout leftViewLayout = (LinearLayout) findViewById(R.id.left_view_layout);
				leftViewLayout.addView(view);
			}
		}
		maxCoursesNumber = len;
	}

	// 创建课程视图
	private void createCourseView(final Course course) {
		int height =  MyUtils.dip2px(this, 100f);
		int getDay = Integer.valueOf(course.day);
		if ((getDay < 1 || getDay > 7)
				|| Integer.valueOf(course.start) > Integer.valueOf(course.end))
			Toast.makeText(this, "星期几没写对,或课程结束时间比开始时间还早~~", Toast.LENGTH_LONG)
					.show();
		else {
			switch (getDay) {
			case 1:
				day = (RelativeLayout) findViewById(R.id.monday);
				break;
			case 2:
				day = (RelativeLayout) findViewById(R.id.tuesday);
				break;
			case 3:
				day = (RelativeLayout) findViewById(R.id.wednesday);
				break;
			case 4:
				day = (RelativeLayout) findViewById(R.id.thursday);
				break;
			case 5:
				day = (RelativeLayout) findViewById(R.id.friday);
				break;
			case 6:
				day = (RelativeLayout) findViewById(R.id.saturday);
				break;
			case 7:
				day = (RelativeLayout) findViewById(R.id.weekday);
				break;
			}
			final View v = LayoutInflater.from(this).inflate(
					R.layout.course_card, null); // 加载单个课程布局
			v.setY(height * (Integer.valueOf(course.start) - 1)); // 设置开始高度,即第几节课开始
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					(Integer.valueOf(course.end)
							- Integer.valueOf(course.start) + 1)
							* height - 8); // 设置布局高度,即跨多少节课
			v.setLayoutParams(params);
			TextView text = (TextView) v.findViewById(R.id.text_view);
			text.setText(course.name + "\n" + course.teacher + "\n"
					+ course.room); // 显示课程名
			day.addView(v);
			// 长按删除课程
			v.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					v.setVisibility(View.GONE);// 先隐藏

					day.removeView(v);// 再移除课程视图 SQLiteDatabase sqLiteDatabase
					dao.removeCourse(course.id);
					MyUtils.showToast(MainActivity.this, "删除成功");

					return true;
				}
			});
		}
	}

	// 获取上课时间
	private long getClock(Course course) {
		long time = 0L;

		Calendar c = Calendar.getInstance();// 获取日期对象
		c.set(Calendar.DATE, Integer.parseInt(course.day)-1);
		c.set(Calendar.HOUR_OF_DAY,
				Constant.getHour(Integer.parseInt(course.start))); // 设置闹钟小时数
		c.set(Calendar.MINUTE,
				Constant.getMinutes(Integer.parseInt(course.start))); // 设置闹钟的分钟数
		if (c.getTimeInMillis() < System.currentTimeMillis()) {
			time = c.getTimeInMillis() + 7 * 24 * 60 * 60 * 1000;
		} else {
			time = c.getTimeInMillis();
		}
		//Log.e("time", format.format(c.getTime()));
		//System.out.println(format.format(c.getTime()));
		return time;
	}

}
