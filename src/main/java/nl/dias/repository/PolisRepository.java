package nl.dias.repository;

import nl.dias.domein.*;
import nl.dias.domein.polis.Polis;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Named
public class PolisRepository extends AbstractRepository<Polis> {
    private final static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PolisRepository.class);

    public PolisRepository() {
        super(Polis.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    @Transactional
    public List<Polis> zoekPolissenOpSoort(Class<?> soort) {
        Query query = getEm().createQuery("select e from " + soort.getSimpleName() + " e");
        @SuppressWarnings("unchecked") List<Polis> ret = query.getResultList();

        return ret;
    }

    @Transactional
    public List<Polis> allePolissenBijMaatschappij(VerzekeringsMaatschappij maatschappij) {
        TypedQuery<Polis> query = getEm().createNamedQuery("Polis.allesBijMaatschappij", Polis.class);
        query.setParameter("maatschappij", maatschappij);

        return query.getResultList();
    }

    public List<Polis> allePolissenBijRelatie(Relatie relatie) {
        TypedQuery<Polis> query = getEm().createNamedQuery("Polis.allesVanRelatie", Polis.class);
        query.setParameter("relatie", relatie);

        return query.getResultList();
    }

    @Transactional
    public List<Polis> allePolissenVanRelatieEnZijnBedrijf(Relatie relatie) {
        relatie = getEm().find(Relatie.class, relatie.getId());

        List<Polis> poli = new ArrayList<>();
        poli.addAll(relatie.getPolissen());

        for (Bedrijf bedrijf : relatie.getBedrijven()) {
            poli.addAll(bedrijf.getPolissen());
        }

        return poli;
    }

    @Transactional
    public Polis zoekOpPolisNummer(String PolisNummer, Kantoor kantoor) {
        TypedQuery<Polis> query = getEm().createNamedQuery("Polis.zoekOpPolisNummer", Polis.class);
        query.setParameter("polisNummer", PolisNummer);
        query.setParameter("kantoor", kantoor);
        return query.getSingleResult();
    }

    public List<Bijlage> zoekBijlagesBijPolis(Polis polis) {
        TypedQuery<Bijlage> query = getEm().createNamedQuery("Bijlage.zoekBijlagesBijPolis", Bijlage.class);
        query.setParameter("polis", polis);

        return query.getResultList();

    }

    @Transactional
    public void opslaanBijlage(Bijlage bijlage) {
        LOGGER.debug("opslaan " + bijlage);

        getEm().getTransaction().begin();
        if (bijlage.getId() == null) {
            getEm().persist(bijlage);
        } else {
            getEm().merge(bijlage);
        }
        getEm().getTransaction().commit();
    }

    public Bijlage leesBijlage(Long id) {
        return getEm().find(Bijlage.class, id);
    }

    public Bijlage leesBijlage(String s3) {
        TypedQuery<Bijlage> query = getEm().createNamedQuery("Bijlage.zoekBijlagesBijS3", Bijlage.class);
        query.setParameter("s3", s3);
        return query.getSingleResult();
    }

}
