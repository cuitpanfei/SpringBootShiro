package cn.pfinfo.springbootshiro.common.util.file;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件类型判断类。该文件引自
 * <a href="http://www.cnblogs.com/rainsilence/archive/2012/06/06/2538294.html">用java流方式判断文件类型</a>，
 * 修改了方法<code> isFileType()</code>
 */
public final class FileTypeJudgeUtil {

	/**
	 * Constructor
	 */
	private FileTypeJudgeUtil() {
	}

	/**
	 * 将文件头转换成16进制字符串
	 * 
	 * @param 原生byte
	 * @return 16进制字符串
	 */
	private static String bytesToHexString(byte[] src) {

		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 得到文件头
	 * @param is 文件流
	 * @return 文件头
	 * @throws IOException
	 */
	private static String getFileContent(InputStream is) throws IOException {

		byte[] b = new byte[28];

		InputStream inputStream = null;

		try {
			is.read(b, 0, 28);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
		return bytesToHexString(b);
	}

	/**
	 * 判断文件类型
	 * @param is 文件流
	 * @return 文件类型
	 */
	public static FileType getType(InputStream is) throws IOException {

		String fileHead = getFileContent(is);

		if (fileHead == null || fileHead.length() == 0) {
			return null;
		}

		fileHead = fileHead.toUpperCase();

		FileType[] fileTypes = FileType.values();

		for (FileType type : fileTypes) {
			if (fileHead.startsWith(type.getValue())) {
				return type;
			}
		}

		return null;
	}

	/**
	 * 根据枚举返回文件类型
	 * @param value
	 * @return
	 */
	public static String isFileType(FileType value) {

		FileType[] imgs = { FileType.JPEG, FileType.PNG, FileType.GIF, FileType.TIFF, FileType.BMP, FileType.DWG, FileType.PSD };

		FileType[] docs = { FileType.RTF, FileType.XML, FileType.HTML, FileType.CSS, FileType.JS, FileType.EML, FileType.DBX, FileType.PST, FileType.XLS_DOC, FileType.XLSX_DOCX, FileType.VSD,
				FileType.MDB, FileType.WPS, FileType.WPD, FileType.EPS, FileType.PDF, FileType.QDF, FileType.PWL, FileType.ZIP, FileType.RAR, FileType.JSP, FileType.JAVA, FileType.CLASS,
				FileType.JAR, FileType.MF, FileType.EXE, FileType.CHM };

		FileType[] videos = { FileType.AVI, FileType.RAM, FileType.RM, FileType.MPG, FileType.MOV, FileType.ASF, FileType.MP4, FileType.FLV, FileType.MID };

		FileType[] tottents = { FileType.TORRENT };

		FileType[] audios = { FileType.WAV, FileType.MP3 };

		// 图片
		for (FileType fileType : imgs) {
			if (fileType.equals(value)) {
				return "img";
			}
		}
		// 文档
		for (FileType fileType : docs) {
			if (fileType.equals(value)) {
				return "doc";
			}
		}
		// 视频
		for (FileType fileType : videos) {
			if (fileType.equals(value)) {
				return "video";
			}
		}
		// 种子
		for (FileType fileType : tottents) {
			if (fileType.equals(value)) {
				return "tottents";
			}
		}
		// 音乐
		for (FileType fileType : audios) {
			if (fileType.equals(value)) {
				return "audio";
			}
		}
		return "other";
	}

}