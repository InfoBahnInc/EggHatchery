package com.hackagong.hatchery.jpa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Returns snapshot information to the UI.
 */

@Entity
public class Snapshot {

	//
	// Private members
	//

	private long mId;

	private Date mRecorded;

	private float mTemperature;

	private float mHumidity;

	private int mVibration;

	//
	// Public methods
	//

	@Id
	@GeneratedValue
	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	@NotNull
	public Date getRecorded() {
		return mRecorded;
	}

	public void setRecorded(Date recorded) {
		mRecorded = recorded;
	}

	@NotNull
	public float getTemperature() {
		return mTemperature;
	}

	public void setTemperature(float temperature) {
		mTemperature = temperature;
	}

	@NotNull
	public float getHumidity() {
		return mHumidity;
	}

	public void setHumidity(float humidity) {
		mHumidity = humidity;
	}

	@NotNull
	public int getVibration() {
		return mVibration;
	}

	public void setVibration(int vibration) {
		mVibration = vibration;
	}
}