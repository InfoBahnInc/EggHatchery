function CDevice(pID, pReadings, pName) {
	this.mID = pID;
	this.mName = pName;
	this.clear();

	for (var i = 0; i < pReadings.length; ++i)
		this.addReading(pReadings[i]);
}
CDevice.prototype.addReading = function CDevice_addReading(pReading) {
	this.Readings.push(pReading);

	if ((this.Current == null) || (pReading.Timestamp > this.Current.Timestamp))
		this.Current = pReading;
};
CDevice.prototype.clear = function CDevice_clear() {
	this.Readings = [];
	this.Current = null;
};

function CReading(pTimestamp, pTemperature, pHumidity, pVibration) {
	this.Temperature = new CReadingValue(pTemperature,
			this.getTemperatureClass, "#000000");
	this.Humidity = new CReadingValue(pHumidity, this.getHumidityClass,
			"#000000");
	this.Vibration = new CReadingValue(pVibration, this.getVibrationClass,
			"#000000");
	this.Timestamp = pTimestamp;
	this.LastUpdated = new CReadingValue(Math
			.round((new Date() - pTimestamp) / 60000),
			this.getLastUpdateClass, "#000000");
}
CReading.prototype.mClasses = ['chick-very-happy', 'chick-happy', 'chick-medium','chick-sad', 'chick-very-sad', 'chick-dead'];
CReading.prototype.getTemperatureClass = function CTemperatureReading_getBackgroundClass(
		pTemperature) {
	// Aim for 37 degrees
	var comparisonValue = Math.round(Math.abs(37 - pTemperature));
	if (comparisonValue < CReading.prototype.mClasses.length)
		return (CReading.prototype.mClasses[comparisonValue]);

	return 'chick-alert';
};
CReading.prototype.getHumidityClass = function CHumidityReading_getBackgroundClass(
		pHumidity) {
	// Aim for 55%
	var comparisonValue = Math.round(Math.abs(65 - pHumidity) / 3);
	if (comparisonValue < CReading.prototype.mClasses.length)
		return (CReading.prototype.mClasses[comparisonValue]);

	return 'chick-alert';
}
CReading.prototype.getVibrationClass = function CReading_getVibrationClass(
		pVibration) {
	if (pVibration > 0)
		return 'chick-alert';

	return 'chick-very-happy';
}

CReading.prototype.getLastUpdateClass = function CReading_getLastUpdateClass(
		pLastUpdated) {
	var comparisonValue = pLastUpdated - 5;
	if (comparisonValue < 0)
		comparisonValue = 0;
	if (comparisonValue < CReading.prototype.mClasses.length)
		return (CReading.prototype.mClasses[comparisonValue]);

	return ("#D9534F");
}

function CReadingValue(pValue, pBackgroundClass, pBorderClass) {
	this.Value = pValue;
	this.BackgroundClass = typeof (pBackgroundClass) == "function" ? pBackgroundClass(pValue)
			: pBackgroundClass;
	this.BorderClass = pBorderClass;
}
