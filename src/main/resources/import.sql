INSERT INTO books (authors, isbn, number_of_copies, publisher, title, `year`) VALUES('J.K. Rowling', '8234567123098', 10, 'Salani', 'Harry Potter and the chamber of secrets', 2000);
INSERT INTO books (authors, isbn, number_of_copies, publisher, title, `year`) VALUES('Frank Herbert', '9234567123097', 3, 'Mondadori', 'Dune', 1953);
INSERT INTO users (first_name, last_name, registration_date) VALUES ('John', 'Doe', '2023-01-01');
INSERT INTO users (first_name, last_name, registration_date) VALUES ('Jane', 'Smith', '2023-05-05');
INSERT INTO borrowings (expire_date, return_date, start_date, book_id, borrower_id) VALUES('2023-10-26', null, '2023-09-26', 1, 1);
INSERT INTO borrowings (expire_date, return_date, start_date, book_id, borrower_id) VALUES('2023-08-05', '2023-08-31', '2023-09-05', 1, 2);
INSERT INTO categories (name) VALUES('fiction');
INSERT INTO categories (name) VALUES('teenagers');
INSERT INTO categories (name) VALUES('not fiction');
INSERT INTO categories (name) VALUES('science');
INSERT INTO categories (name) VALUES('fantasy');