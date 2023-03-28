package org.hibernate.bugs;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Collection;
import java.util.LinkedList;

@Entity
public class UserEntity {
    @Id
    @Column(name="ID", length = 36)
    @Access(AccessType.PROPERTY) // we do this because relationships often fetch id, but not entity.  This avoids an extra SQL
    protected String id;

    @Nationalized
    @Column(name = "USERNAME")
    protected String username;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy="user")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 20)
    protected Collection<FederatedIdentityEntity> federatedIdentities = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<FederatedIdentityEntity> getFederatedIdentities() {
        if (federatedIdentities == null) {
            federatedIdentities = new LinkedList<>();
        }
        return federatedIdentities;
    }

    public void setFederatedIdentities(Collection<FederatedIdentityEntity> federatedIdentities) {
        this.federatedIdentities = federatedIdentities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof UserEntity)) return false;

        UserEntity that = (UserEntity) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
