package com.test.kcb.database.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.test.kcb.bean.Course;
import com.test.kcb.database.helper.MyOpenHelper;

public class CourseDao {
	private MyOpenHelper helper;

	public CourseDao(Context context) {
		helper = new MyOpenHelper(context);
	}

	public long addCourse(Course course) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("cid", course.id);
		values.put("sid", course.sid);
		values.put("name", course.name);
		values.put("teacher", course.teacher);
		values.put("day", course.day);
		values.put("start", course.start);
		values.put("end", course.end);
		values.put("room", course.room);
		long row = db.insert("course", null, values);
		db.close();
		return row;
		/*
		 * db.execSQL("insert into course(cid,sid,name,teacher,day,start,end,room)"
		 * + " values(?, ? ,? ,? ,? ,? ,? ,?)",new String[]{
		 * course.id,course.sid
		 * ,course.name,course.teacher,course.day,course.start
		 * ,course.end,course.room });
		 */
	}

	public ArrayList<Course> findAall(String sid1) {
		Log.e("sid", sid1);
		ArrayList<Course> list = new ArrayList<>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from course where sid = ?;",
				new String[] { sid1 });
		Log.e("cursor", cursor.getColumnCount() + "");

		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("cid"));
			String sid = cursor.getString(cursor.getColumnIndex("sid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
			String day = cursor.getString(cursor.getColumnIndex("day"));
			String start = cursor.getString(cursor.getColumnIndex("start"));
			String end = cursor.getString(cursor.getColumnIndex("end"));
			String room = cursor.getString(cursor.getColumnIndex("room"));
			Course course = new Course(id, sid, name, teacher, day, start, end,
					room);
			list.add(course);
		}
		cursor.close();
		db.close();
		return list;
	}

	public void removeCourse(String id) {
		Log.e("remove cid", id);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("course", "cid=?", new String[] { id });
	}

	/*
	 * 检查课程是否存在
	 */
	public boolean checkHas(Course course) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from course where sid = ? and day=? and start=?;",
				new String[] { course.sid, course.day, course.start });
		if (cursor.moveToNext()) {
			return true;
		} else {
			return false;
		}
	}
}
