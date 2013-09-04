package org.yuner.www.database;

public class UnicodeUtil {

	public static String utf8toGB2312(String utf8_value) {
		byte [] b;

		String GB2312_value = utf8_value;
		try {
			b = utf8_value.getBytes("8859_1");
			GB2312_value = new String(b, "GB2312");
		} catch(Exception e) {}

		return GB2312_value;
	}

	public static String GB2312toutf8(String GB2312_value) {
		byte[] b;

		String utf8_value = GB2312_value;
		try {
			b = GB2312_value.getBytes("8859_1");
	   		utf8_value = new String(b,"utf-8");
		} catch (Exception e) {}

		return utf8_value;
	}

}
