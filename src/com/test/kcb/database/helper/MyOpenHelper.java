package com.test.kcb.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * �̳���SQLiteOpenHelper�����ڽ������ݿ���ز���
 * 
 */
public class MyOpenHelper extends SQLiteOpenHelper {


	public MyOpenHelper(Context context) {
		super(context, "kcb.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table student(sid varchar primary key,name varchar not null,password varchar(20) not null)");
		db.execSQL("create table course(" + "cid varchar(32) primary key,"
				+ "sid varchar,"
				+ "name varchar(20) not null,"
				+ "teacher varchar(20) not null,"
				+ "day varchar,"
				+ "start varchar,"
				+ "end varchar,"
				+ "room varchar )");
		/*db.execSQL("insert into student values('1','hehe','1')");
		db.execSQL("insert into course values('8','1','����','����ʦ','1','1','1','N301')");
		db.execSQL("insert into course values('16','1','����','����ʦ','2','1','1','N301')");
		db.execSQL("insert into course values('17','1','����','����ʦ','3','1','1','N301')");
		db.execSQL("insert into course values('18','1','����','����ʦ','4','1','1','N301')");
		db.execSQL("insert into course values('19','1','����','����ʦ','5','1','1','N301')");
		db.execSQL("insert into course values('20','1','����','����ʦ','7','1','1','N301')");
		
		db.execSQL("insert into course values('9','1','����','����ʦ','6','1','1','N301')");
		db.execSQL("insert into course values('10','1','����','����ʦ','6','2','2','N301')");
		db.execSQL("insert into course values('11','1','����','����ʦ','6','3','3','N301')");
		db.execSQL("insert into course values('12','1','����','����ʦ','6','4','4','N301')");
		db.execSQL("insert into course values('13','1','����','����ʦ','6','5','5','N301')");
		db.execSQL("insert into course values('14','1','����','����ʦ','6','6','6','N301')");
		db.execSQL("insert into course values('15','1','����','����ʦ','6','7','7','N301')");
		
		db.execSQL("insert into course values('1','1','����','����ʦ','6','8','8','N301')");
		db.execSQL("insert into course values('2','1','��ѧ','����ʦ','6','9','9','s301')");
		db.execSQL("insert into course values('3','1','Ӣ��','����ʦ','6','10','10','e301')");
		db.execSQL("insert into course values('4','1','����','����ʦ','6','11','11','N301')");
		db.execSQL("insert into course values('5','1','����','����ʦ','6','12','12','N301')");
		db.execSQL("insert into course values('6','1','����','����ʦ','6','13','13','N301')");
		db.execSQL("insert into course values('7','1','����','����ʦ','6','14','14','N301')");*/
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
