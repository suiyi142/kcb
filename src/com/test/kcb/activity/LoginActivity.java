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
	 * �ҵ���ؿؼ�
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
	 * ��ť��ӵ���¼�
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login:
			checkLogin();// ����¼
			break;
		case R.id.bt_goto_register:
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivityForResult(intent, 1);// ��ת��ע��ҳ��
			break;
		}
	}

	/**
	 * ���ڼ���û����������Ƿ�Ϸ����жϹ���Ա�Ƿ����
	 */
	private void checkLogin() {
		String name = et_name.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			tv_login_tips.setText("�û�������Ϊ��");
		} else if (TextUtils.isEmpty(password)) {
			tv_login_tips.setText("���벻��Ϊ��");
		} else {
			tv_login_tips.setText("");
			// ���ݿ��в�ѯ�Ƿ��иù���Ա
			Cursor cursor = db.rawQuery(
					"select * from student where sid = ? and password =?",
					new String[] { name, password });
			boolean b = cursor.moveToNext();
			if (b) {
				// ���ڣ���name��װ��intent�д���ScoreActivity
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra("id", name);
				startActivity(intent);
			} else {
				// �����ڣ�������ʾ��Ϣ
				tv_login_tips.setText("�û������������");
				MyUtils.showToast(this, "�û������������");
			}
		}
	}

	/**
	 * ����RegisterActivity���ص�����
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 1) {
			if (resultCode == 1) {
				et_name.setText(intent.getStringExtra("name"));
				et_password.setText(intent.getStringExtra("password"));
				MyUtils.showToast(this, "ע��ɹ������¼");
			} else if (resultCode == 2) {
				MyUtils.showToast(this, "ע��ʧ��");
			}
		}
	}

	/**
	 * activity����ʱ�ر����ݿ�����
	 */
	@Override
	protected void onDestroy() {
		if (db != null) {
			db.close();
		}
		super.onDestroy();
	}

}
