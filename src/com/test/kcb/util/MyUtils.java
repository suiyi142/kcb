package com.test.kcb.util;

import java.util.UUID;

import android.content.Context;
import android.widget.Toast;

/**
 * 工具类
 * 
 * @author shengyi
 * 
 */
public class MyUtils {

	/**
	 * 用于生成唯一识别码id
	 * 
	 * @return 识别码
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 弹出一个吐司
	 * 
	 * @param context
	 *            显示吐司的上下文
	 * @param text
	 *            显示的内容
	 */
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 将dp转换成ps
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
