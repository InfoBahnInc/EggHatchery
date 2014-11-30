package com.hackagong.hatchery.rest;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.metawidget.util.IOUtils;

import com.hackagong.hatchery.util.HatcheryConstants;

@Path("control")
public class ControlService {

	//
	// Public methods
	//

	@PUT
	@Path("servo")
	@Consumes(MediaType.APPLICATION_JSON)
	public void servo(Map<String, Object> parameters) throws Exception {

		float angle = ((Number) parameters.get("angle")).floatValue();

		URL url = new URL(HatcheryConstants.IOT_URL + "?sweep.angle=" + angle);
		IOUtils.streamBetween(url.openStream(), new ByteArrayOutputStream());
	}

	@PUT
	@Path("fan")
	@Consumes(MediaType.APPLICATION_JSON)
	public void fan(Map<String, Object> parameters) throws Exception {

		if (parameters.containsKey("start")) {
			URL url = new URL(HatcheryConstants.IOT_URL + "?fan.on");
			IOUtils.streamBetween(url.openStream(), new ByteArrayOutputStream());
		} else {
			URL url = new URL(HatcheryConstants.IOT_URL + "?fan.off");
			IOUtils.streamBetween(url.openStream(), new ByteArrayOutputStream());
		}
	}

	@PUT
	@Path("heater")
	@Consumes(MediaType.APPLICATION_JSON)
	public void heater(Map<String, Object> parameters) throws Exception {

		if (parameters.containsKey("start")) {
			URL url = new URL(HatcheryConstants.IOT_URL + "?heater.on");
			IOUtils.streamBetween(url.openStream(), new ByteArrayOutputStream());
		} else {
			URL url = new URL(HatcheryConstants.IOT_URL + "?heater.off");
			IOUtils.streamBetween(url.openStream(), new ByteArrayOutputStream());
		}
	}
}
