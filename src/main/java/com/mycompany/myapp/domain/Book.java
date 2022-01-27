package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_of_book")
    private String nameOFBook;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "nb_of_books")
    private Integer nbOfBooks;

    @Column(name = "is_dn_nomber")
    private String isDnNomber;

    @Column(name = "subject_book")
    private String subjectBook;

    @Column(name = "lang_of_book")
    private String langOfBook;

    @OneToMany(mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "book" }, allowSetters = true)
    private Set<Staff> staff = new HashSet<>();

    @OneToMany(mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "book" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    /**
     * A relationship
     */
    @Schema(description = "A relationship")
    @OneToMany(mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "book" }, allowSetters = true)
    private Set<Member> members = new HashSet<>();

    @ManyToMany(mappedBy = "books")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "books" }, allowSetters = true)
    private Set<Catalogue> catalogues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOFBook() {
        return this.nameOFBook;
    }

    public Book nameOFBook(String nameOFBook) {
        this.setNameOFBook(nameOFBook);
        return this;
    }

    public void setNameOFBook(String nameOFBook) {
        this.nameOFBook = nameOFBook;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public Book authorName(String authorName) {
        this.setAuthorName(authorName);
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getNbOfBooks() {
        return this.nbOfBooks;
    }

    public Book nbOfBooks(Integer nbOfBooks) {
        this.setNbOfBooks(nbOfBooks);
        return this;
    }

    public void setNbOfBooks(Integer nbOfBooks) {
        this.nbOfBooks = nbOfBooks;
    }

    public String getIsDnNomber() {
        return this.isDnNomber;
    }

    public Book isDnNomber(String isDnNomber) {
        this.setIsDnNomber(isDnNomber);
        return this;
    }

    public void setIsDnNomber(String isDnNomber) {
        this.isDnNomber = isDnNomber;
    }

    public String getSubjectBook() {
        return this.subjectBook;
    }

    public Book subjectBook(String subjectBook) {
        this.setSubjectBook(subjectBook);
        return this;
    }

    public void setSubjectBook(String subjectBook) {
        this.subjectBook = subjectBook;
    }

    public String getLangOfBook() {
        return this.langOfBook;
    }

    public Book langOfBook(String langOfBook) {
        this.setLangOfBook(langOfBook);
        return this;
    }

    public void setLangOfBook(String langOfBook) {
        this.langOfBook = langOfBook;
    }

    public Set<Staff> getStaff() {
        return this.staff;
    }

    public void setStaff(Set<Staff> staff) {
        if (this.staff != null) {
            this.staff.forEach(i -> i.setBook(null));
        }
        if (staff != null) {
            staff.forEach(i -> i.setBook(this));
        }
        this.staff = staff;
    }

    public Book staff(Set<Staff> staff) {
        this.setStaff(staff);
        return this;
    }

    public Book addStaff(Staff staff) {
        this.staff.add(staff);
        staff.setBook(this);
        return this;
    }

    public Book removeStaff(Staff staff) {
        this.staff.remove(staff);
        staff.setBook(null);
        return this;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        if (this.images != null) {
            this.images.forEach(i -> i.setBook(null));
        }
        if (images != null) {
            images.forEach(i -> i.setBook(this));
        }
        this.images = images;
    }

    public Book images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Book addImage(Image image) {
        this.images.add(image);
        image.setBook(this);
        return this;
    }

    public Book removeImage(Image image) {
        this.images.remove(image);
        image.setBook(null);
        return this;
    }

    public Set<Member> getMembers() {
        return this.members;
    }

    public void setMembers(Set<Member> members) {
        if (this.members != null) {
            this.members.forEach(i -> i.setBook(null));
        }
        if (members != null) {
            members.forEach(i -> i.setBook(this));
        }
        this.members = members;
    }

    public Book members(Set<Member> members) {
        this.setMembers(members);
        return this;
    }

    public Book addMember(Member member) {
        this.members.add(member);
        member.setBook(this);
        return this;
    }

    public Book removeMember(Member member) {
        this.members.remove(member);
        member.setBook(null);
        return this;
    }

    public Set<Catalogue> getCatalogues() {
        return this.catalogues;
    }

    public void setCatalogues(Set<Catalogue> catalogues) {
        if (this.catalogues != null) {
            this.catalogues.forEach(i -> i.removeBook(this));
        }
        if (catalogues != null) {
            catalogues.forEach(i -> i.addBook(this));
        }
        this.catalogues = catalogues;
    }

    public Book catalogues(Set<Catalogue> catalogues) {
        this.setCatalogues(catalogues);
        return this;
    }

    public Book addCatalogue(Catalogue catalogue) {
        this.catalogues.add(catalogue);
        catalogue.getBooks().add(this);
        return this;
    }

    public Book removeCatalogue(Catalogue catalogue) {
        this.catalogues.remove(catalogue);
        catalogue.getBooks().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", nameOFBook='" + getNameOFBook() + "'" +
            ", authorName='" + getAuthorName() + "'" +
            ", nbOfBooks=" + getNbOfBooks() +
            ", isDnNomber='" + getIsDnNomber() + "'" +
            ", subjectBook='" + getSubjectBook() + "'" +
            ", langOfBook='" + getLangOfBook() + "'" +
            "}";
    }
}
