package nl.dias.domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAAKRELATIES")
public class TaakRelaties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "TAAK", referencedColumnName = "ID")
    private Taak taak;
    @Enumerated(EnumType.STRING)
    @Column(name = "SOORT", length = 1)
    private GerelateerdObjectSoort gerelateerdObjectSoort;
    @Column(name = "GERELATEERDOBJECT")
    private Long gerelateerdObject;

    public enum GerelateerdObjectSoort {
        H("Hypotheek"), P("Polis"), S("Schade"), B("Bedrijf");

        private final String omschrijving;

        private GerelateerdObjectSoort(String omschrijving) {
            this.omschrijving = omschrijving;
        }

        public String getOmschrijving() {
            return omschrijving;
        }

    }

}
