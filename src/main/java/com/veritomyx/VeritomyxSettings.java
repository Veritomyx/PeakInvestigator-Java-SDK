package com.veritomyx;

/**
 * A convenience class to keep track of the following settings for Veritomyx
 * products:
 * 
 * <ul>
 * <li>Server address</li>
 * <li>Username</li>
 * <li>Password</li>
 * <li>Project number</li>
 * </ul>
 * 
 * @author Adam Tenderholt
 *
 */
public class VeritomyxSettings {
	public final String server;
	public final String username;
	public final String password;
	public final int projectID;

	public VeritomyxSettings(String server, String username, String password,
			int projectID) {
		this.server = server;
		this.username = username;
		this.password = password;
		this.projectID = projectID;
	}
}
