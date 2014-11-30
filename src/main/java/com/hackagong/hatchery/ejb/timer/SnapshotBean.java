package com.hackagong.hatchery.ejb.timer;

import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.metawidget.util.LogUtils;
import org.metawidget.util.LogUtils.Log;

import com.hackagong.hatchery.cdi.ManuallyScopedEntityManager;
import com.hackagong.hatchery.cdi.SmsBean;
import com.hackagong.hatchery.jpa.Snapshot;
import com.hackagong.hatchery.rest.ObjectMapperContextResolver;
import com.hackagong.hatchery.util.HatcheryConstants;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class SnapshotBean {

	//
	// Private statics
	//

	private final static Log LOG = LogUtils.getLog(SnapshotBean.class);

	//
	// Private members
	//

	@Inject
	private ManuallyScopedEntityManager mEntityManager;

	@Inject
	private ObjectMapperContextResolver mObjectMapperContextResolver;

	@Inject
	private SmsBean mSmsBean;

	//
	// Public methods
	//

	@Schedule(second = "*/3", minute = "*", hour = "*", persistent = false)
	public void doWork() {

		mEntityManager.start();

		try {
			URL url = new URL(HatcheryConstants.IOT_URL + "?temp.read");
			@SuppressWarnings("unchecked")
			Map<String, Object> data = mObjectMapperContextResolver.getContext(
					null).readValue(url, Map.class);
			LOG.info("temp.read returned {0}", data);

			Snapshot snapshot = new Snapshot();
			snapshot.setRecorded(new Date());
			float temperature = ((Number) data.get("temp")).floatValue();
			snapshot.setTemperature(temperature);
			snapshot.setHumidity(((Number) data.get("hum")).floatValue() * 100);
			snapshot.setVibration(((Number) data.get("vib")).intValue());
			mEntityManager.getEntityManager().merge(snapshot);

			// SMS warnings

			if (temperature > 40f) {
				mSmsBean.sendNotification("Your chickens are dying!",
						"Hurry up!");
			}

			// Delete old records

			mEntityManager
					.getEntityManager()
					.createNativeQuery(
							"DELETE FROM snapshot WHERE recorded < ADDDATE( NOW(), INTERVAL -10 MINUTE )")
					.executeUpdate();

		} catch (Exception e) {

			LOG.error("Unable to read IOT", e);

		} finally {
			mEntityManager.stop();
		}
	}
}