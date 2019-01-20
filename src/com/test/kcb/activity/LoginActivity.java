package com.test.kcb.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.test.kcb.R;
import com.test.kcb.database.helper.MyOpenHelper;
import com.test.kcb.util.MyUtils;


public class LoginActivity extends ActionBarActivity implements OnClickListener {

	private Button bt_login;
	private TextView tv_login_tips;
	private EditText et_password;
	private EditText et_name;
	private Button bt_goto_register;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		MyOpenHelper helper = new MyOpenHelper(this);
		db = helper.getReadableDatabase();
	}

	/**
	 * 找到相关控件
	 */
	private void initView() {
		et_name = (EditText) findViewById(R.id.et_name);
		et_password = (EditText) findViewById(R.id.et_password);
		tv_login_tips = (TextView) findViewById(R.id.tv_login_tips);
		bt_login = (Button) findViewById(R.id.bt_login);
		bt_goto_register = (Button) findViewById(R.id.bt_goto_register);

		tv_login_tips.setText("");
		bt_login.setOnClickListener(this);
		bt_goto_register.setOnClickListener(this);
	}

	/**
	 * 按钮添加点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login:
			checkLogin();// 检查登录
			break;
		case R.id.bt_goto_register:
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivityForResult(intent, 1);// 跳转到注册页面
			break;
		}
	}

	/**
	 * 用于检查用户名和密码是否合法并判断管理员是否存在
	 */
	private void checkLogin() {
		String name = et_name.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			tv_login_tips.setText("用户名不可为空");
		} else if (TextUtils.isEmpty(password)) {
			tv_login_tips.setText("密码不可为空");
		} else {
			tv_login_tips.setText("");
			// 数据库中查询是否有该管理员
			Cursor cursor = db.rawQuery(
					"select * from student where sid = ? and password =?",
					new String[] { name, password });
			boolean b = cursor.moveToNext();
			if (b) {
				// 存在，将name封装到intent中传入ScoreActivity
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra("id", name);
				startActivity(intent);
			} else {
				// 不存在，给出提示信息
				tv_login_tips.setText("用户名或密码错误");
				MyUtils.showToast(this, "用户名或密码错误");
			}
		}
	}

	/**
	 * 接收RegisterActivity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 1) {
			if (resultCode == 1) {
				et_name.setText(intent.getStringExtra("name"));
				et_password.setText(intent.getStringExtra("password"));
				MyUtils.showToast(this, "注册成功，请登录");
			} else if (resultCode == 2) {
				MyUtils.showToast(this, "注册失败");
			}
		}
	}

	/**
	 * activity销毁时关闭数据库连接
	 */
	@Override
	protected void onDestroy() {
		if (db != null) {
			db.close();
		}
		super.onDestroy();
	}

}
