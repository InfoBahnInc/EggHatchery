package com.hackagong.hatchery.cdi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.net.ssl.HttpsURLConnection;

import org.metawidget.util.LogUtils;
import org.metawidget.util.LogUtils.Log;

/**
 * Application-wide configuration settings
 */

@ApplicationScoped
public class SmsBean {

	//
	// Private statics
	//

	private final static Log LOG = LogUtils.getLog(EmailBean.class);

	private final static String SMS_URL = "https://my.smscentral.com.au/api/v3.0";

	//
	// Private members
	//
	
	/**
	 * Only send one SMS per VM restart.
	 */
	
	private boolean	mSmsSent;
	
	//
	// Public methods
	//

	public void sendNotification(String subject, String messageText) {

		if ( mSmsSent ) {
			return;
		}
	
		mSmsSent = true;
		
		try {
			URL url = new URL(SMS_URL);
			StringBuilder builder = new StringBuilder();
			builder.append("USERNAME=youremail@youremail.com&");
			builder.append("PASSWORD=yourpassword&ACTION=send&");
			builder.append("ORIGINATOR=shared&");
			builder.append("RECIPIENT=0413529023&");
			builder.append("MESSAGE_TEXT=");
			builder.append(subject);

			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(builder.toString());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());

			LOG.info("SMSed {0}: {1}", subject, messageText);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
