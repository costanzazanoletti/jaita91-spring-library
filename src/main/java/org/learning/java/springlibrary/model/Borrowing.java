package org.learning.java.springlibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "borrowings")
public class Borrowing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @NotNull
  private LocalDate startDate;
  @NotNull
  private LocalDate expireDate;
  private LocalDate returnDate;

  @ManyToOne
  @NotNull
  private Book book;

  @ManyToOne
  @NotNull
  private User borrower;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(LocalDate expireDate) {
    this.expireDate = expireDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public User getBorrower() {
    return borrower;
  }

  public void setBorrower(User borrower) {
    this.borrower = borrower;
  }

  // metodi custom
  // metodo che mi restituisce se un prestito è scaduto
  public boolean isExpired() {
    // data di scadenza è passata e non è restituito
    // data di scadenza è < data di ritorno
    return (returnDate == null && expireDate.isBefore(LocalDate.now())) || (returnDate != null
        && returnDate.isAfter(expireDate));
  }
}
