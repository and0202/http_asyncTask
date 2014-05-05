package zhong.http.net.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.R.integer;

public class IOUtil {

	/**
	 * 读取本地中存储的String
	 * 
	 * @param path
	 *            存储路径
	 * @return
	 */
	public static String readFromFile(String path) {
		ByteArrayOutputStream out = null;
		FileInputStream inputStream = null;

		try {
			File file = new File(path);
			inputStream = new FileInputStream(file);
			out = new ByteArrayOutputStream(1024);
			byte[] bytes = new byte[1000];
			int n;
			while ((n = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, n);
			}
			out.flush();
			String result = new String(out.toByteArray());
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(inputStream);
			closeIO(out);
		}
		return null;
	}

	/**
	 * Close IO Stream
	 * 
	 * @param closeable
	 */
	public static void closeIO(Closeable closeable) {
		try {
			if (closeable != null && closeable instanceof InputStream) {
				InputStream inputStream = (InputStream) closeable;
				inputStream.close();
			} else if (closeable != null && closeable instanceof OutputStream) {
				OutputStream outputStream = (OutputStream) closeable;
				outputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
