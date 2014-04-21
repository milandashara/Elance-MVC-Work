package com.coe.mxcommunity.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	private static String ftpImageIP;
	private static int ftpImagePort;
	private static String ftpImageUser;
	private static String ftpImagePassword;
	private static String imageHost;
	public static final String PATH_IMAGE_TEMP = "albumtemp"; 
	
	public static String getFtpImageIP() {
		return ftpImageIP;
	}

	public static int getFtpImagePort() {
		return ftpImagePort;
	}

	public static String getFtpImageUser() {
		return ftpImageUser;
	}

	public static String getFtpImagePassword() {
		return ftpImagePassword;
	}
	
	public static String getImageHost() {
		return imageHost;
	}

	static {		
		String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		int index = path.lastIndexOf("WEB-INF/lib");
		if (index == -1) {
			index = path.lastIndexOf("WEB-INF/classes");
		}
		path = path.substring(0, index) + "WEB-INF/config.properties";
		path = path.replace("%20", " ");
		InputStream is;
        try {
            is = new FileInputStream(path);
            Properties p = new Properties();
            p.load(is);
            
            Configuration.ftpImageIP = p.getProperty("FTP_IMAGE_IP");
            Configuration.ftpImagePort = Integer.parseInt(p.getProperty("FTP_IMAGE_PORT"));
            Configuration.ftpImageUser = p.getProperty("FTP_IMAGE_USER");
            Configuration.ftpImagePassword = p.getProperty("FTP_IMAGE_PASSWORD");
            Configuration.imageHost = p.getProperty("IMAGE_HOST");
            
            is.close();
           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
