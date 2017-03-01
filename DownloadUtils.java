package cn.itcast.mobilesafe.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import cn.itcast.mobilesafe.activity.SettingCenterActivity;
import cn.itcast.mobilesafe.activity.SplashActivity;
import cn.itcast.mobilesafe.constants.HttpConstants;
import cn.itcast.mobilesafe.interf.OnDownloadListener;

public class Utils {

	protected static final int DOWNLOAD_FAILED = 0;
	protected static final int DOWNLOAD_SUCCESS = 1;

	/**
	 * 检查网络连接状态 android.Manifest.permission.ACCESS_NETWORK_STATE.
	 * 
	 * @param context
	 * @return 当现在处于数据网络或WiFi网络时 判定为有网
	 */
	public static boolean checkNetwork(Context context) {
		// ConnectivityManager 获取连接状态的管理器
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// 获取当前激活的网络信息
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo == null) {
			return false;
		}

		// 获取网络类型
		int type = networkInfo.getType();

		// if (type == ConnectivityManager.TYPE_WIFI) {
		// return true;
		// } else if (type == ConnectivityManager.TYPE_MOBILE) {
		// return true;
		// }

		if (type == ConnectivityManager.TYPE_WIFI
				|| type == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}

		return false;
	}

	// 17接口回调
	/*
	 * 接口回调
	 * 
	 * 解耦 提高程序扩展性
	 * 
	 * Splash: 不需要关注download如何执行,只需要让download方法下载成功或失败时 告知一声即可 流程:
	 * 调用download方法时,传入一个OnDownloadListener的实现类对象,必然会重写成功和失败的两个方法
	 * 下载成功和失败的方法什么时候调用,Splash不知道
	 * 
	 * Utils.download: 不需要关注是谁调用的我,只需要知道,调用我,必须给我一个OnDownloadListener的实现类对象 流程:
	 * 执行下载操作 当下载成功时,执行实现类对象的成功方法 当出现异常时,执行实现类对象的失败方法
	 * 
	 * 接口: 可以多实现, 定义规范: 实现我的接口,必须重写我的方法
	 * 
	 * 回调: 当下载完成时,回调到SplashActivity
	 */

	/**
	 * 万能的文件下载工具
	 * 
	 * @param url
	 * @param destFile
	 */
	// 16.使用接口实现下载工具类的通用性(解耦)
	public static void download(final String url, final File destFile,
			final OnDownloadListener listener) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case DOWNLOAD_SUCCESS:
					if (listener != null) {
						listener.onDownloadSuccess(destFile);
					}
					// 安装APK
					break;
				case DOWNLOAD_FAILED:
					if (listener != null) {
						listener.onDownloadFailed();
					}
					break;

				default:
					break;
				}
			}
		};

		new Thread() {
			public void run() {
				try {
					FileOutputStream fos = new FileOutputStream(destFile);
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);
					HttpParams params = get.getParams();
					params.setParameter(
							HttpConnectionParams.CONNECTION_TIMEOUT,
							HttpConstants.TIME_OUT);
					params.setParameter(HttpConnectionParams.SO_TIMEOUT,
							HttpConstants.TIME_OUT);
					get.setParams(params);
					HttpResponse response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == 200) {
						InputStream is = response.getEntity().getContent();
						int len = -1;
						byte[] buffer = new byte[1024];
						while ((len = is.read(buffer)) != -1) {
							fos.write(buffer, 0, len);
							fos.flush();
						}
						fos.close();
						// 不是自己new出来的流 都可以不用关闭
						is.close();
						handler.sendEmptyMessage(DOWNLOAD_SUCCESS);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(DOWNLOAD_FAILED);
				}
			};
		}.start();
	}

	/**
	 * 启动activity
	 * @param context
	 * @param clazz
	 */
	public static void startActivity(Context context, Class clazz) {
		context.startActivity(new Intent(context, clazz));
	}

}
