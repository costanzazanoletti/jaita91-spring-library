package org.learning.java.springlibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  @Size(max = 255)
  private String firstName;

  @NotBlank
  @Size(max = 255)
  private String lastName;

  @NotNull
  @PastOrPresent
  private LocalDate registrationDate;


  @OneToMany(mappedBy = "borrower")
  private List<Borrowing> borrowings;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
  }

  public List<Borrowing> getBorrowings() {
    return borrowings;
  }

  public void setBorrowings(List<Borrowing> borrowings) {
    this.borrowings = borrowings;
  }

  // metodi custom
  // nome completo
  public String getFullName() {
    return firstName + " " + lastName;
  }

  // metodo che restituisce quanti prestiti ha in corso l'utente
  public int getCurrentBorrowings() {
    int numberOfBorrowings = 0;
    for (Borrowing b : borrowings) {
      if (b.getReturnDate() == null) {
        numberOfBorrowings++;
      }
    }

    return numberOfBorrowings;
  }
}
