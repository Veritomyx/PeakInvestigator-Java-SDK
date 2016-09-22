package com.veritomyx;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChecksumFileReader implements AutoCloseable {

	private final static Logger LOGGER = LoggerFactory.getLogger(ChecksumFileReader.class);
	private Path path;
	private BufferedReader bufferedReader;
	private Checksum fileChecksum;
	private String hashFromFile;

	public ChecksumFileReader(Path path) throws IOException {
		this.path = path;
		bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset());
		fileChecksum = new Checksum();
	}

	public String readLine() throws IOException {
		String line = bufferedReader.readLine();
		if (line != null) {
			if (line.startsWith(Checksum.PREFIX)) {
				hashFromFile = line.substring(Checksum.PREFIX.length());
			} else {
				// calculate the hash of the last sum + the new line
				fileChecksum.append(line); 
			}
		}
		return line;
	}

	public void close() throws IOException {
		// Read to end of file to get the checksum before closing 
		while (bufferedReader.ready()) {
			readLine();
		}
		if (fileChecksum.getChecksum().equals(hashFromFile)) {
			LOGGER.info(path.toFile().getName() + " checksum OK");
		} else if (hashFromFile == null) {
			LOGGER.warn(path + " missing checksum");
		} else {
			LOGGER.warn(path + " checksum mismatch");
		}
		bufferedReader.close();
	}

	public boolean hasChecksum() {
		return hashFromFile != null;
	}

	public boolean isChecksumValid() throws MissingChecksum {
		if (!hasChecksum()) {
			throw new MissingChecksum(path + " does not have a checksum");
		}

		return fileChecksum.getChecksum().equals(hashFromFile);
	}

	public class MissingChecksum extends Exception {
		private static final long serialVersionUID = 1L;

		MissingChecksum(String msg) {
			super(msg);
		}
	}
}
