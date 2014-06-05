package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.VerzekeringsMaatschappij;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.transaction.annotation.Transactional;

@Named
public class VerzekeringsMaatschappijRepository extends AbstractRepository<VerzekeringsMaatschappij> {
    public VerzekeringsMaatschappijRepository() {
        super(VerzekeringsMaatschappij.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    @Transactional
    public VerzekeringsMaatschappij zoekOpNaam(String naam) {
        TypedQuery<VerzekeringsMaatschappij> query = getEm().createNamedQuery("VerzekeringsMaatschappij.zoekOpNaam", VerzekeringsMaatschappij.class);
        query.setParameter("naam", naam);
        return query.getSingleResult();
    }

    @Override
    public List<VerzekeringsMaatschappij> alles() {
        TypedQuery<VerzekeringsMaatschappij> query = getEm().createNamedQuery("VerzekeringsMaatschappij.zoekAlles", VerzekeringsMaatschappij.class);
        return query.getResultList();
    }
}
