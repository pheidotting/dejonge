package nl.dias.web.mapper;

import javax.inject.Named;

import nl.dias.domein.Bank;
import nl.dias.domein.json.JsonBank;

@Named
public class BankMapper extends Mapper<Bank, JsonBank> {

    @Override
    public Bank mapVanJson(JsonBank json) {
        return null;
    }

    @Override
    public JsonBank mapNaarJson(Bank bank) {
        JsonBank jsonBank = new JsonBank();

        jsonBank.setId(bank.getId());
        jsonBank.setNaam(bank.getNaam());

        return jsonBank;
    }
}
