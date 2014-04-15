package nl.dias.service.envers;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@Table(name="REVINFO")
@RevisionEntity(RevisionListener.class)
public class RevEntity extends DefaultRevisionEntity {
	private static final long serialVersionUID = -1169834849124037964L;

	private Long userid;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
}
