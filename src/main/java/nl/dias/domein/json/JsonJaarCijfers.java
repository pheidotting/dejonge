package nl.dias.domein.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Bijlage;
import nl.dias.domein.Opmerking;
import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class JsonJaarCijfers implements ObjectMetJsonBijlages, ObjectMetJsonOpmerkingen {
    private Long id;
    private Long jaar;
    private Long bedrijf;
    private List<JsonBijlage> bijlages;
    private List<JsonOpmerking> opmerkingen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJaar() {
        return jaar;
    }

    public void setJaar(Long jaar) {
        this.jaar = jaar;
    }

    public Long getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Long bedrijf) {
        this.bedrijf = bedrijf;
    }

    @Override
    public List<JsonBijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = Lists.newArrayList();
        }
        return bijlages;
    }

    @Override
    public void setBijlages(List<JsonBijlage> bijlages) {
        this.bijlages = bijlages;
    }

    @Override
    public List<JsonOpmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = Lists.newArrayList();
        }
        return opmerkingen;
    }

    @Override
    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }
}
