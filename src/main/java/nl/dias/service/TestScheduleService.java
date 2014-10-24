package nl.dias.service;

import java.util.concurrent.TimeUnit;

import nl.dias.repository.GebruikerRepository;

import com.google.common.util.concurrent.AbstractScheduledService;

public class TestScheduleService extends AbstractScheduledService {

    private GebruikerRepository gebruikerRepository;

    @Override
    protected void startUp() throws Exception {
        System.out.println("Start me up!");
    }

    @Override
    public void runOneIteration() throws Exception {
        // gebruikerRepository = new GebruikerRepository();
        // System.out.println("a1");
        // Relatie relatie = (Relatie) gebruikerRepository.lees(3L);
        //
        // System.out.println("a2");
        // relatie.setAchternaam("Jonget");
        //
        // System.out.println("a3");
        // gebruikerRepository.opslaan(relatie);
        // System.out.println("a4");
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, 1, TimeUnit.MINUTES);

    }

}
