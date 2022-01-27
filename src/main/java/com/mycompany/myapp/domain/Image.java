package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "image_book")
    private byte[] imageBook;

    @Column(name = "image_book_content_type")
    private String imageBookContentType;

    @Column(name = "image_libelle")
    private String imageLibelle;

    @Column(name = "image_code")
    private Integer imageCode;

    @ManyToOne
    @JsonIgnoreProperties(value = { "staff", "images", "members", "catalogues" }, allowSetters = true)
    private Book book;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Image id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageBook() {
        return this.imageBook;
    }

    public Image imageBook(byte[] imageBook) {
        this.setImageBook(imageBook);
        return this;
    }

    public void setImageBook(byte[] imageBook) {
        this.imageBook = imageBook;
    }

    public String getImageBookContentType() {
        return this.imageBookContentType;
    }

    public Image imageBookContentType(String imageBookContentType) {
        this.imageBookContentType = imageBookContentType;
        return this;
    }

    public void setImageBookContentType(String imageBookContentType) {
        this.imageBookContentType = imageBookContentType;
    }

    public String getImageLibelle() {
        return this.imageLibelle;
    }

    public Image imageLibelle(String imageLibelle) {
        this.setImageLibelle(imageLibelle);
        return this;
    }

    public void setImageLibelle(String imageLibelle) {
        this.imageLibelle = imageLibelle;
    }

    public Integer getImageCode() {
        return this.imageCode;
    }

    public Image imageCode(Integer imageCode) {
        this.setImageCode(imageCode);
        return this;
    }

    public void setImageCode(Integer imageCode) {
        this.imageCode = imageCode;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Image book(Book book) {
        this.setBook(book);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return id != null && id.equals(((Image) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", imageBook='" + getImageBook() + "'" +
            ", imageBookContentType='" + getImageBookContentType() + "'" +
            ", imageLibelle='" + getImageLibelle() + "'" +
            ", imageCode=" + getImageCode() +
            "}";
    }
}
