package com.gospell.aas.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	/**
	 * 
	 * 使用gzip进行压缩
	 */
	public static String gzip(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 * 
	 * <p>
	 * Description:使用gzip进行解压缩
	 * </p>
	 * 
	 * @param compressedStr
	 * @return
	 */
	public static String gunzip(String compressedStr) throws Exception{
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new sun.misc.BASE64Decoder()
					.decodeBuffer(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			throw e;
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
					throw e;
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw e;
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}

		return decompressed;
	}

	/**
	 * 使用zip进行压缩
	 * 
	 * @param str
	 *            压缩前的文本
	 * @return 返回压缩后的文本
	 */
	public static final String zip(String str) {
		if (str == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
			compressedStr = new sun.misc.BASE64Encoder()
					.encodeBuffer(compressed);
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressedStr;
	}

	/**
	 * 使用zip进行解压缩
	 * 
	 * @param compressed
	 *            压缩后的文本
	 * @return 解压后的字符串
	 */
	public static final String unzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try {
			byte[] compressed = new sun.misc.BASE64Decoder()
					.decodeBuffer(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}

	public static String exChange(String str) {
		StringBuffer sb = new StringBuffer();
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				int b = c; // 字符的ascii码值
				if (b >= 97 && b <= 122) {
					sb.append(Character.toUpperCase(c));
				} else {
					sb.append(c);
				}

			}
		}

		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
//	 String s="H4sIAAAAAAAAA1WOTQ5CMQiED2PiahZAC4+3a5sXL2W8u0M1JrIg3zD8DRFREZtDvjE8z3PaWreynkwHIpt4NPidMn5SG7VOCELLWSTz3eREKfeCoperjh4pmcfHeMBhRjIOGSwKFca10otZ832RJeMPRYHGqbZPtL99NqHJxtcbQSF+49IAAAA=";
//	 String u =ZipUtils.gunzip(s);
//	 System.out.println(u);
		String s1="H4sIAAAAAAAAA1WPSw4CQQhED2PiigXQX3Yz48RLGe8uBaMde9OvgKLCxszCrGPj623NWL2231zoC31i4rvDoCGtFLOU5V/akm94y88bUiCny5peIQOog8xY0B3VQR4OMVVRmRl9zZ9UMu4g7zaMN6q9W50jGqLUGsEk8iV+guCXTjNDzEEA/l25NajF0p3EL0qh7A3JWNSV+syLwQUTQn3U2KK28AxEis5ArNCycF94JL4/UPYKhJIBAAA=";
		String s ="@00010046@0033A786@591EB470#0001{0066,0,3&0067,0,12&0006,0,3&002A,0,5&0060,4666352,0&0046,7085648,1&0036,7085648,0&004A,7157862,0&0041,7157862,0&0034,7157814,0&0043,7157814,1&0055,7157814,1&005E,7157814,1&0042,7157814,1&0049,7157814,1&005F,7157814,0}";
System.out.println(gzip(s));
		
	}
}