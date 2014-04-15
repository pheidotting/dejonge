package nl.dias.service.envers;

import nl.dias.inloggen.Sessie;


public class RevisionListener implements org.hibernate.envers.RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
        RevEntity revEntity = (RevEntity) revisionEntity;

        revEntity.setUserid(Sessie.getIngelogdeGebruiker());
	}

}
