package org.hibernate.bugs;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import java.util.Map;

@Entity
public class UserFederationMapperEntity {

    @Id
    @Column(name="ID", length = 36)
    @Access(AccessType.PROPERTY) // we do this because relationships often fetch id, but not entity.  This avoids an extra SQL
    protected String id;

    @Column(name="NAME")
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEDERATION_PROVIDER_ID")
    protected UserFederationProviderEntity federationProvider;

    @Column(name = "FEDERATION_MAPPER_TYPE")
    protected String federationMapperType;

    @ElementCollection
    @MapKeyColumn(name="NAME")
    @Column(name="VALUE")
    @CollectionTable(name="USER_FEDERATION_MAPPER_CONFIG", joinColumns={ @JoinColumn(name="USER_FEDERATION_MAPPER_ID") })
    private Map<String, String> config;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REALM_ID")
    private RealmEntity realm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserFederationProviderEntity getFederationProvider() {
        return federationProvider;
    }

    public void setFederationProvider(UserFederationProviderEntity federationProvider) {
        this.federationProvider = federationProvider;
    }

    public String getFederationMapperType() {
        return federationMapperType;
    }

    public void setFederationMapperType(String federationMapperType) {
        this.federationMapperType = federationMapperType;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public RealmEntity getRealm() {
        return realm;
    }

    public void setRealm(RealmEntity realm) {
        this.realm = realm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ) return false;
        if (!(o instanceof UserFederationMapperEntity)) return false;

        UserFederationMapperEntity that = (UserFederationMapperEntity) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
