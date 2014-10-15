package nl.dias.service;

import java.util.List;

import javax.inject.Named;

import nl.dias.domein.Bank;
import nl.dias.repository.BankRepository;

import com.sun.jersey.api.core.InjectParam;

@Named
public class BankService {
    @InjectParam
    private BankRepository bankRepository;

    public List<Bank> alles() {
        return bankRepository.alles();
    }

    public Bank lees(Long id) {
        return bankRepository.lees(id);
    }

    public void setBankRepository(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }
}
