package com.hackagong.hatchery.rest;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hackagong.hatchery.cdi.RequestScopedEntityManager;
import com.hackagong.hatchery.jpa.Snapshot;
import com.hackagong.hatchery.jpa.Snapshot_;

/**
 * REST base service.
 */

@Path("snapshot")
public class SnapshotService {

	//
	// Protected members
	//

	@Inject
	private RequestScopedEntityManager mEntityManager;

	//
	// Public methods
	//

	@GET
	@Path("recent")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Snapshot> recent() {

		return list(15);
	}

	@GET
	@Path("last")
	@Produces(MediaType.APPLICATION_JSON)
	public Snapshot last() {

		return list(1).get(0);
	}

	//
	// Private methods
	//

	@SuppressWarnings("unchecked")
	public List<Snapshot> list(int maxResults) {

		EntityManager entityManager = mEntityManager.getEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Snapshot> criteriaQuery = builder
				.createQuery(Snapshot.class);
		Root<Snapshot> root = criteriaQuery.from(Snapshot.class);
		criteriaQuery = criteriaQuery.select(root).orderBy(
				builder.desc(root.get(Snapshot_.recorded)));

		Query query = entityManager.createQuery(criteriaQuery);
		query.setMaxResults(maxResults);
		return query.getResultList();
	}
}
