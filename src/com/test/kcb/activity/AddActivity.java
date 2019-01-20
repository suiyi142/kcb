package com.test.kcb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.test.kcb.R;
import com.test.kcb.bean.Course;
import com.test.kcb.database.dao.CourseDao;
import com.test.kcb.database.helper.MyOpenHelper;
import com.test.kcb.util.MyUtils;

public class AddActivity extends Activity {
	private String sid;
	private EditText et_course_name;
	private EditText et_day;
	private EditText et_start;
	private EditText et_end;
	private EditText et_teacher;
	private EditText et_room;
	private CourseDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		dao = new CourseDao(this);
		Intent intent = getIntent();
		sid = intent.getStringExtra("sid");
		et_course_name = (EditText) findViewById(R.id.et_course_name);
		et_day = (EditText) findViewById(R.id.et_day);
		et_start = (EditText) findViewById(R.id.et_start);
		et_end = (EditText) findViewById(R.id.et_end);
		et_teacher = (EditText) findViewById(R.id.et_teacher);
		et_room = (EditText) findViewById(R.id.et_room);

	}

	/**
	 * 当完成被点击
	 * 
	 * @param view
	 */
	public void finishClick(View view) {
		String name = et_course_name.getText().toString().trim();
		String day = et_day.getText().toString().trim();
		String start = et_start.getText().toString().trim();
		String end = et_end.getText().toString().trim();
		String teacher = et_teacher.getText().toString().trim();
		String room = et_room.getText().toString().trim();
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(day)
				|| TextUtils.isEmpty(start) || TextUtils.isEmpty(end)
				|| TextUtils.isEmpty(teacher) || TextUtils.isEmpty(room)) {
			MyUtils.showToast(this, "有值为空");
			return;
		}
		String id = MyUtils.uuid();
		Course course = new Course(id, sid, name, teacher, day,
				start, end, room);
		if (dao.checkHas(course)) {
			MyUtils.showToast(this, "课程冲突");
			return;
		}
		long row = dao.addCourse(course);
		if (row == 0) {
			MyUtils.showToast(this, "添加失败");
			return;
		} else {
			MyUtils.showToast(this, "添加成功");
		}
		Intent intent = new Intent();
		intent.putExtra("course", course);
		setResult(1,intent);
		finish();

	}
	


	

}
