package org.learning.java.springlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

  // attributi
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "title non deve essere vuoto")
  // se validato il title non può essere una stringa vuota
  private String title;
  @NotBlank(message = "isbn non deve essere vuoto") // validazione
  @Size(min = 10, max = 13) // validazione
  @Column(length = 13, nullable = false, unique = true) // mappatura su database
  private String isbn;
  private String authors;
  private String publisher;
  private Integer year;
  @Min(0)
  @NotNull
  private Integer numberOfCopies;

  // mapped by vuole il nome dell'attributo sull'entità Borrowing dove si trova la relazione con il Book
  // cascade indica quali operazioni propagare alle istanze della entità figlia

  @JsonIgnore // indico che quando converto un oggetto Book in JSON ignoro questo campo
  @OneToMany(mappedBy = "book")
  private List<Borrowing> borrowings = new ArrayList<>();

  @ManyToMany
  private List<Category> categories = new ArrayList<>();

  // getter e setter

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getAuthors() {
    return authors;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getNumberOfCopies() {
    return numberOfCopies;
  }

  public void setNumberOfCopies(Integer numberOfCopies) {
    this.numberOfCopies = numberOfCopies;
  }

  public List<Borrowing> getBorrowings() {
    return borrowings;
  }

  public void setBorrowings(List<Borrowing> borrowings) {
    this.borrowings = borrowings;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  // metodi custom
  // metodo che mi calcola il numero di copie attualmente disponibili (non fuori in prestito)
  public int getAvailabeCopies() {
    // totale di copie - numero di copie in prestito che non sono ancora state restituite
    return numberOfCopies - getBorrowedCopies();
  }

  // metodo che calcola il numero di copie prestate non restituite
  public int getBorrowedCopies() {
    int numberOfBorrowedCopies = 0;
    // calcolo il numero di copie non restituite
    for (Borrowing b : borrowings) {
      if (b.getReturnDate() == null) {
        numberOfBorrowedCopies++;
      }
    }
    return numberOfBorrowedCopies;
  }
}
