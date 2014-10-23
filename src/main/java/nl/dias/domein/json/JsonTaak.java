package nl.dias.domein.json;

public class JsonTaak {
    private Long id;
    private String soortTaak;
    private String datumTijdCreatie;
    private String datumTijdOppakken;
    private String datumTijdAfgerond;
    private String eindDatum;
    private String aangemaaktDoor;
    private String opgepaktDoor;
    private String gerelateerdAan;
    private String omschrijving;
    private String status;
    private Long gerelateerdeTaak;
    private boolean mijnTaak;

    // private Set<TaakRelaties> taakRelaties;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoortTaak() {
        return soortTaak;
    }

    public void setSoortTaak(String soortTaak) {
        this.soortTaak = soortTaak;
    }

    public String getDatumTijdCreatie() {
        return datumTijdCreatie;
    }

    public void setDatumTijdCreatie(String datumTijdCreatie) {
        this.datumTijdCreatie = datumTijdCreatie;
    }

    public String getDatumTijdOppakken() {
        return datumTijdOppakken;
    }

    public void setDatumTijdOppakken(String datumTijdOppakken) {
        this.datumTijdOppakken = datumTijdOppakken;
    }

    public String getDatumTijdAfgerond() {
        return datumTijdAfgerond;
    }

    public void setDatumTijdAfgerond(String datumTijdAfgerond) {
        this.datumTijdAfgerond = datumTijdAfgerond;
    }

    public String getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(String eindDatum) {
        this.eindDatum = eindDatum;
    }

    public String getAangemaaktDoor() {
        return aangemaaktDoor;
    }

    public void setAangemaaktDoor(String aangemaaktDoor) {
        this.aangemaaktDoor = aangemaaktDoor;
    }

    public String getOpgepaktDoor() {
        return opgepaktDoor;
    }

    public void setOpgepaktDoor(String opgepaktDoor) {
        this.opgepaktDoor = opgepaktDoor;
    }

    public String getGerelateerdAan() {
        return gerelateerdAan;
    }

    public void setGerelateerdAan(String gerelateerdAan) {
        this.gerelateerdAan = gerelateerdAan;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getGerelateerdeTaak() {
        return gerelateerdeTaak;
    }

    public void setGerelateerdeTaak(Long gerelateerdeTaak) {
        this.gerelateerdeTaak = gerelateerdeTaak;
    }

    public boolean isMijnTaak() {
        return mijnTaak;
    }

    public void setMijnTaak(boolean mijnTaak) {
        this.mijnTaak = mijnTaak;
    }
}
