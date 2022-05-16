package com.pweb.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "EMAIL_LOCATION", schema = "db", catalog = "db")
@IdClass(EmailLocationEntityPK.class)
public class EmailLocationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "location_id", nullable = false)
    private int locationId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailLocationEntity that = (EmailLocationEntity) o;

        if (locationId != that.locationId) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationId;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
