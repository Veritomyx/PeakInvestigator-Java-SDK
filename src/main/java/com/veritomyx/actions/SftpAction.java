package com.veritomyx.actions;

import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SftpAction extends BaseAction {
	private static final String action = "SFTP";

	public final static String EXAMPLE_RESPONSE_1 = "{\"Action\":\"SFTP\", \"Host\":\"peakinvestigator.veritomyx.com\", \"Port\":22022, \"Directory\":\"/files\", \"Login\":\"Vt504\", \"Password\":\"0UtnWMvzoi2jF4BQ\", \"Fingerprints\":[ {\"Signature\":\"DSA\",\"Algorithm\":\"MD5\",\"Hash\":\"96:bd:da:62:5a:53:1a:2f:82:87:65:7f:c0:45:71:94\"}, {\"Signature\":\"DSA\",\"Algorithm\":\"SHA256\",\"Hash\":\"b9SOs40umHMywBa2GtdsOhr/wgP1L6nfXWugjRrJTaM\"}, {\"Signature\":\"ECDSA\",\"Algorithm\":\"MD5\",\"Hash\":\"5c:6f:c7:c7:79:c0:76:90:4d:3a:a1:7a:81:0e:0a:57\"}, {\"Signature\":\"ECDSA\",\"Algorithm\":\"SHA256\",\"Hash\":\"d2HXgeUSmWN+gq+9V7Wad5xWaCxk+mh45F81K951MCU\"}, {\"Signature\":\"RSA\",\"Algorithm\":\"MD5\",\"Hash\":\"d2:be:b8:2e:3c:be:84:e4:a3:0a:c8:42:5c:6b:39:4e\"}, {\"Signature\":\"RSA\",\"Algorithm\":\"SHA256\",\"Hash\":\"QBsg8ejj4gZun4AWd4WBTJw89ftcLR9x/dZoG223srg\"}]}";

	private int projectID;
	private SftpFingerprints fingerprints = null;

	public SftpAction(String user, String code, int projectID) {
		super(user, code);

		this.projectID = projectID;
	}

	public String buildQuery() {
		StringBuilder builder = new StringBuilder(super.buildQuery());
		builder.append("Action=" + action + "&");
		builder.append("ID=" + projectID);

		return builder.toString();

	}

	private void preCheck() throws IllegalStateException {
		if (!isReady(action)) {
			throw new IllegalStateException("Response has not been set.");
		}
	}

	public String getHost() {
		preCheck();
		return getStringAttribute("Host");
	}

	public int getPort() {
		preCheck();
		return (int) getLongAttribute("Port");
	}

	public String getDirectory() {
		preCheck();
		return getStringAttribute("Directory");
	}

	public String getSftpUsername() {
		preCheck();
		return getStringAttribute("Login");
	}

	public String getSftpPassword() {
		preCheck();
		return getStringAttribute("Password");
	}

	public SftpFingerprints getFingerprints() {
		preCheck();

		if (fingerprints != null) {
			return fingerprints;
		}

		fingerprints = new SftpFingerprints();
		JSONArray arrayObject = (JSONArray) responseObject.get("Fingerprints");
		for (Object object : arrayObject) {
			JSONObject jsonObject = (JSONObject) object;

			StringBuilder builder = new StringBuilder();
			builder.append((String) jsonObject.get("Signature"));
			builder.append("-");
			builder.append((String) jsonObject.get("Algorithm"));
			String hash = (String) jsonObject.get("Hash");
			fingerprints.put(builder.toString(), hash);
		}

		return fingerprints;
	}

	@Override
	public String getErrorMessage() {
		preCheck();
		return super.getErrorMessage();
	}

	@Override
	public long getErrorCode() {
		preCheck();
		return super.getErrorCode();
	}

	public class SftpFingerprints extends HashMap<String, String> {

		private static final long serialVersionUID = 1L;

		public String[] getAlgorithms() {
			Set<String> keys = keySet();
			return keys.toArray(new String[keys.size()]);
		}

		public String getHash(String algorithm) {
			return get(algorithm);
		}
	}
}