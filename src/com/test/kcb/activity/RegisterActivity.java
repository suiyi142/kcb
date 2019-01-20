package com.test.kcb.activity;

import com.test.kcb.R;
import com.test.kcb.database.helper.MyOpenHelper;

import android.content.ContentValues;
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


public class RegisterActivity extends ActionBarActivity implements
		OnClickListener {

	private EditText et_reg_name;
	private EditText et_reg_password;
	private EditText et_reg_repassword;
	private TextView tv_register_tips;
	private Button bt_register;
	private Button bt_back;
	private SQLiteDatabase readDb;
	private SQLiteDatabase writeDb;
	private EditText et_reg_name1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initView();
		MyOpenHelper helper = new MyOpenHelper(this);
		readDb = helper.getReadableDatabase();
		writeDb = helper.getWritableDatabase();
	}

	/**
	 * 找到相关控件
	 */
	private void initView() {
		et_reg_name = (EditText) findViewById(R.id.et_reg_name);
		et_reg_name1 = (EditText) findViewById(R.id.et_reg_name_1);
		et_reg_repassword = (EditText) findViewById(R.id.et_reg_repassword);
		tv_register_tips = (TextView) findViewById(R.id.tv_register_tips);
		bt_register = (Button) findViewById(R.id.bt_register);
		bt_back = (Button) findViewById(R.id.bt_back);

		tv_register_tips.setText("");
		bt_register.setOnClickListener(this);
		bt_back.setOnClickListener(this);
	}

	/**
	 * 按钮添加点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_register:
			checkRegister();// 检查登录
			break;

		case R.id.bt_back:
			setResult(2);
			this.finish();
			break;
		}
	}

	/**
	 * 用于检查用户名和密码是否合法并判断管理员是否已经存在
	 */
	private void checkRegister() {
		String name = et_reg_name.getText().toString().trim();
		String name1 = et_reg_name1.getText().toString().trim();
		String repassword = et_reg_repassword.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			tv_register_tips.setText("学号不可为空");
		} else if (TextUtils.isEmpty(name1)) {
			tv_register_tips.setText("姓名不可为空");
		} else if (TextUtils.isEmpty(repassword)) {
			tv_register_tips.setText("密码不可为空");
		}  else {
			tv_register_tips.setText("");
			Cursor cursor = readDb
					.rawQuery("select * from student where sid = ?",
							new String[] { name });
			boolean b = cursor.moveToNext();
			if (b) {
				tv_register_tips.setText("该用户已存在");
			} else {
				tv_register_tips.setText("");
				ContentValues values = new ContentValues();
				values.put("sid", name);
				values.put("name", name1);
				values.put("password", repassword);
				long id = writeDb.insert("student", null, values);
				Intent intent = new Intent();
				intent.putExtra("name", name);
				setResult(1, intent);
				this.finish();
			}
		}
	}

	/**
	 * activity销毁时关闭数据库连接
	 */
	@Override
	protected void onDestroy() {
		if (readDb != null) {
			readDb.close();
		}
		if (writeDb != null) {
			writeDb.close();
		}
		super.onDestroy();
	}
}
