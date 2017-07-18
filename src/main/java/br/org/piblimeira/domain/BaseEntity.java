package br.org.piblimeira.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract Long getId();

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }

        BaseEntity other = (BaseEntity) object;
        return !(!Objects.equals(this.getId(), other.getId()) && (this.getId() == null || !this.getId().equals(other.getId())));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [ID=" + this.getId() + "]";
    }
}
