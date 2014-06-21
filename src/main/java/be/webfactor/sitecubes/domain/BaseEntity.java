package be.webfactor.sitecubes.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String toString() {
		return getClass().getName() + " [id=" + id + "]";
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BaseEntity entity = (BaseEntity) o;

		if (id != null ? !id.equals(entity.id) : entity.id != null) return false;

		return true;
	}

	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

}
