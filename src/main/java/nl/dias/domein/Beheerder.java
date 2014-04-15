package nl.dias.domein;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.lakedigital.hulpmiddelen.domein.PersistenceObject;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "B")
@AttributeOverrides({ @AttributeOverride(name = "identificatie", column = @Column(name = "EMAILADRES")) })
@Audited
public class Beheerder extends Gebruiker implements PersistenceObject, Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3403882985064145165L;

}
