package org.learning.java.springlibrary.repository;

import org.learning.java.springlibrary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
