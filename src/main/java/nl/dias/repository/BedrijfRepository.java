package nl.dias.repository;

import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

@Named
public class BedrijfRepository extends AbstractRepository<Bedrijf> {
    public BedrijfRepository() {
        super(Bedrijf.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<Bedrijf> alleBedrijvenBijRelatie(Relatie relatie) {
        TypedQuery<Bedrijf> query = getEm().createNamedQuery("Bedrijf.allesBijRelatie", Bedrijf.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

}
