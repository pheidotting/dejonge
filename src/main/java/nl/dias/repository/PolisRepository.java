package nl.dias.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Relatie;
import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.domein.polis.Polis;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.transaction.annotation.Transactional;

@Named
public class PolisRepository extends AbstractRepository<Polis> {
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
        @SuppressWarnings("unchecked")
        List<Polis> ret = query.getResultList();

        return ret;
    }

    @Transactional
    public List<Polis> allePolissenBijMaatschappij(VerzekeringsMaatschappij maatschappij) {
        TypedQuery<Polis> query = getEm().createNamedQuery("Polis.allesBijMaatschappij", Polis.class);

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
    public Polis zoekOpPolisNummer(String PolisNummer) {
        TypedQuery<Polis> query = getEm().createNamedQuery("Polis.zoekOpPolisNummer", Polis.class);
        query.setParameter("polisNummer", PolisNummer);
        return query.getSingleResult();
    }

    @Transactional
    public void opslaanBijlage(Bijlage bijlage) {
        getEm().persist(bijlage);
    }

    public Bijlage leesBijlage(Long id) {
        return getEm().find(Bijlage.class, id);
    }
}
