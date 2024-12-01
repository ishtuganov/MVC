package ishtuganov.springcourse.repositories;
import ishtuganov.springcourse.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Page<Book> findAll(Pageable req);
    List<Book> findAllByNameStartingWith(String text);
}
