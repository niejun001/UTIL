package cn.itcast.mobilesafe.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtils {

	public static void unzip(InputStream is, OutputStream os) throws Exception {
		// 2.具备解压功能的输入流
		GZIPInputStream gis = new GZIPInputStream(is);
		
		int len = -1;
		byte[] buffer = new byte[1024];
		while ((len = gis.read(buffer)) != -1) {
			os.write(buffer, 0, len);
			os.flush();
		}
		os.close();
		gis.close();
	}

	public static void zip(InputStream is, OutputStream os) throws Exception {
		// 1.具备压缩功能的输出流
		// 输入流 输出流
		// InputStream is = new FileInputStream(new File("location.db"));
		// OutputStream os = new FileOutputStream(new File("location.zip"));
		// GZIPOutputStream gos = new GZIPOutputStream(os);

		// GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream(new
		// File("location.zip")));
		GZIPOutputStream gos = new GZIPOutputStream(os);

		// 对接
		int len = -1;
		byte[] buffer = new byte[1024];
		while ((len = is.read(buffer)) != -1) {
			gos.write(buffer, 0, len);
			gos.flush();
		}

		gos.close();
		is.close();
	}

}
