package com.test.kcb.util;

import java.util.UUID;

import android.content.Context;
import android.widget.Toast;

/**
 * ������
 * 
 * @author shengyi
 * 
 */
public class MyUtils {

	/**
	 * ��������Ψһʶ����id
	 * 
	 * @return ʶ����
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * ����һ����˾
	 * 
	 * @param context
	 *            ��ʾ��˾��������
	 * @param text
	 *            ��ʾ������
	 */
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ��dpת����ps
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
