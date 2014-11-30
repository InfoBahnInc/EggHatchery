package com.hackagong.hatchery.cdi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

public abstract class BaseScopedEntityManager {

	//
	// Protected members
	//

	@PersistenceUnit
	private EntityManagerFactory mEntityManagerFactory;

	private EntityManager mEntityManager;

	//
	// Public methods
	//

	public EntityManager getEntityManager() {

		return mEntityManager;
	}

	//
	// Protected methods
	//

	protected void start() {

		if (mEntityManager != null) {
			throw new RuntimeException("Already created");
		}

		mEntityManager = mEntityManagerFactory.createEntityManager();
		mEntityManager.getTransaction().begin();
	}

	protected void stop() {

		if (mEntityManager == null) {
			throw new RuntimeException("Already destroyed");
		}

		EntityTransaction transaction = mEntityManager.getTransaction();
		try {
			if (transaction.isActive()) {
				if (transaction.getRollbackOnly()) {
					transaction.rollback();
				} else {
					transaction.commit();
				}
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		} finally {
			mEntityManager.close();
		}

		mEntityManager = null;
	}
}
