package com.veritomyx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChecksumFileWriter {

	private BufferedWriter bufferedWriter;
	private Checksum fileChecksum;
	private String output;

	public ChecksumFileWriter(String filename) throws IOException {
		bufferedWriter = new BufferedWriter(new FileWriter(filename));
		fileChecksum = new Checksum();
		output = "";
	}

	public ChecksumFileWriter(Path path) throws IOException {
		bufferedWriter = Files.newBufferedWriter(path, Charset.forName("UTF-8"));
		fileChecksum = new Checksum();
		output = "";
	}

	public void write(String msg) throws IOException {
		bufferedWriter.write(msg);
		if (output.length() == 0) {
			output = msg; 
		} else {
			output = output + msg;
		}
	}
	
	public void newLine() throws IOException {
		bufferedWriter.newLine();
		fileChecksum.append(output);
		output = "";
	}

	public void writeln(String msg) throws IOException {
		write(msg);
		newLine();
	}
		
	public void close() throws IOException {
		bufferedWriter.write(fileChecksum.getChecksumLine());
		bufferedWriter.newLine();
		bufferedWriter.close();
	}
}
