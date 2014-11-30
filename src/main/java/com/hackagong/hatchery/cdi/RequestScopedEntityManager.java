package com.hackagong.hatchery.cdi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestScopedEntityManager
	extends BaseScopedEntityManager {

	//
	// Private methods
	//

	@PostConstruct
	private void postConstruct() {

		super.start();
	}

	@PreDestroy
	private void preDestroy() {

		super.stop();
	}
}
