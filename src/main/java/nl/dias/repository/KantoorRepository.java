package nl.dias.repository;

import javax.inject.Named;

import nl.dias.domein.Kantoor;
import nl.dias.domein.RekeningNummer;
import nl.dias.domein.Relatie;
import nl.dias.exception.BsnNietGoedException;
import nl.dias.exception.IbanNietGoedException;
import nl.dias.exception.PostcodeNietGoedException;
import nl.dias.exception.TelefoonnummerNietGoedException;
import nl.dias.utils.Validatie;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import nl.lakedigital.hulpmiddelen.repository.AbstractRepository;

import org.springframework.transaction.annotation.Transactional;

@Named
public class KantoorRepository extends AbstractRepository<Kantoor> {
    public KantoorRepository() {
        super(Kantoor.class);
        zetPersistenceContext("dias");
    }

    @Override
    public void setPersistenceContext(String persistenceContext) {
        zetPersistenceContext(persistenceContext);
    }

    @Transactional
    public void opslaanKantoor(Kantoor o) throws PostcodeNietGoedException, TelefoonnummerNietGoedException, BsnNietGoedException, IbanNietGoedException {
        Validatie.valideer(o);

        for (Relatie relatie : o.getRelaties()) {
            Validatie.valideer(relatie);
        }

        for (RekeningNummer rekening : o.getRekeningnummers()) {
            rekening.setBic(rekening.getBic().toUpperCase());
            rekening.setRekeningnummer(rekening.getRekeningnummer().toUpperCase());
            rekening.setKantoor(o);
        }

        getTx().begin();
        if (((PersistenceObject) o).getId() == null) {
            getEm().persist(o);
        } else {
            getEm().merge(o);
        }
        getTx().commit();
    }

    public Kantoor getIngelogdKantoor() {
        return lees(1L);
    }

    public void wisAlles() {
        for (Kantoor kantoor : super.alles()) {
            super.verwijder(kantoor);
        }
    }
}
