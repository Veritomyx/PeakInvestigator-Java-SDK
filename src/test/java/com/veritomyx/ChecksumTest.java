package com.veritomyx;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.veritomyx.ChecksumFileReader.MissingChecksum;

public class ChecksumTest {

	private final static String BASE_TEST_PATH = "/com/veritomyx/ChecksumTestFiles/";

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGoodFile() throws IOException, MissingChecksum {
		String path = getResourceUrl("scan_goodchksum.txt").getPath();
		File file = new File(path);

		assertTrue("File checksum is not valid", Checksum.isFileIntact(file));
	}

	@Test
	public void testBadFile() throws IOException, MissingChecksum {
		String path = getResourceUrl("scan_badchksum.txt").getPath();
		File file = new File(path);

		assertFalse("File checksum is valid, but should not be",
				Checksum.isFileIntact(file));
	}

	@Test
	public void testNoChecksum() throws IOException, MissingChecksum {
		thrown.expect(ChecksumFileReader.MissingChecksum.class);

		String path = getResourceUrl("scan_nochksum.txt").getPath();
		File file = new File(path);

		Checksum.isFileIntact(file);
	}

	@Test
	public void testReadWrite() throws IOException, MissingChecksum {
		Path path = Paths
				.get(folder.getRoot().getPath(), "test_read_write.txt");

		ChecksumFileWriter writer = new ChecksumFileWriter(path);
		writer.writeln("Mary had a little lamb,");
		writer.writeln("little lamb,");
		writer.writeln("little lamb.");
		writer.close();

		ChecksumFileReader reader = new ChecksumFileReader(path);
		while (reader.readLine() != null) {

		}
		reader.close();

		assertTrue(reader.isChecksumValid());

	}

	private static URL getResourceUrl(String filename) {
		URL resourceUrl = ChecksumTest.class
				.getResource(BASE_TEST_PATH + filename);
		return resourceUrl;
	}

}
