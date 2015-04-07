package nl.dias.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import nl.dias.domein.DIASTelefoonNummer;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.stereotype.Repository;

@Repository
public class DIASTelefoonNummerRepository extends AbstractRepository<DIASTelefoonNummer> {
    public DIASTelefoonNummerRepository() {
        super(DIASTelefoonNummer.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    public List<DIASTelefoonNummer> zoekOpRegistratieNummer(String registratieNummer) {
        TypedQuery<DIASTelefoonNummer> query = getEm().createNamedQuery("DIASTelefoonNummer.zoekOpRegistratieNummer", DIASTelefoonNummer.class);
        query.setParameter("registratieNummer", registratieNummer);

        return query.getResultList();
    }

}
