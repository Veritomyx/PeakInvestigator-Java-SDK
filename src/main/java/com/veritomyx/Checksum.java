package com.veritomyx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.veritomyx.ChecksumFileReader.MissingChecksum;

public class Checksum {

	public final static String PREFIX = "# checksum:";
	private final static String HASH_SEED = "Hash seed!";
	private final static Logger LOGGER = LoggerFactory.getLogger(ChecksumFileReader.class);

	private MessageDigest messageDigest;
	private String checksum;

	public Checksum() {
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Serious internal error missing SHA-1 algorithm " + e);
		}
		// Start with special hash seed
		messageDigest.update(HASH_SEED.getBytes());
		checksum = toHex(messageDigest.digest());
	}

	// Append line to the cumulative checksum
	public void append(String line) {
		// calculate the hash of the last sum + the new line
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while (i < line.length()) {
			char c = line.charAt(i);
			if (c == '\r') {
				i++;
				if (i < line.length() && line.charAt(i) == '\n') {
					i++;
				}
				// Output sb
				append(sb);
				sb.setLength(0);
			} else if (c == '\n') {
				i++;
				if (i < line.length() && line.charAt(i) == '\r') {
					i++;
				}
				// Output sb
				append(sb);
				sb.setLength(0);
			} else {
				sb.append(c);
				i++;
			}
		}
		// Output sb
		append(sb);
	}

	private void append(StringBuffer sb) {
		String tmp = checksum + sb;
		messageDigest.update(tmp.getBytes());
		checksum = toHex(messageDigest.digest());
	}

	public String getChecksum() {
		return checksum;
	}

	public String getChecksumLine() {
		return PREFIX + checksum;
	}

	private static String toHex(byte[] buf) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < buf.length; i++) {
			sb.append(Integer.toString((buf[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	// Verify the hash within a file matches the computed hash for that file
	public static boolean isFileIntact(File file) throws IOException,
			MissingChecksum {
		ChecksumFileReader reader = new ChecksumFileReader(Paths.get(file
				.getPath()));
		while (reader.readLine() != null) {

		}
		reader.close();

		return reader.isChecksumValid();
	}
}
