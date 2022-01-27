package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_member")
    private String idMember;

    @Column(name = "password_member")
    private String passwordMember;

    /**
     * Another side of the same relationship
     */
    @Schema(description = "Another side of the same relationship")
    @ManyToOne
    @JsonIgnoreProperties(value = { "staff", "images", "members", "catalogues" }, allowSetters = true)
    private Book book;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Member id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdMember() {
        return this.idMember;
    }

    public Member idMember(String idMember) {
        this.setIdMember(idMember);
        return this;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public String getPasswordMember() {
        return this.passwordMember;
    }

    public Member passwordMember(String passwordMember) {
        this.setPasswordMember(passwordMember);
        return this;
    }

    public void setPasswordMember(String passwordMember) {
        this.passwordMember = passwordMember;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member book(Book book) {
        this.setBook(book);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        return id != null && id.equals(((Member) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", idMember='" + getIdMember() + "'" +
            ", passwordMember='" + getPasswordMember() + "'" +
            "}";
    }
}
