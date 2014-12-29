package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Bank;
import nl.dias.domein.json.JsonBank;

import org.dozer.DozerBeanMapper;

@Named
public class BankMapper extends Mapper<Bank, JsonBank> {

    @Override
    public Bank mapVanJson(JsonBank json) {
        return null;
    }

    @Override
    public JsonBank mapNaarJson(Bank bank) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        JsonBank jsonBank = mapper.map(bank, JsonBank.class);

        return jsonBank;
    }
}