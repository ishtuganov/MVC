package ishtuganov.springcourse.services;
import ishtuganov.springcourse.models.Book;
import ishtuganov.springcourse.models.Person;
import ishtuganov.springcourse.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    public List<Book> findAll(int pageNumber, int booksPerPage, boolean sort_by_year){
        Pageable pageable;
        if (sort_by_year)
            pageable = PageRequest.of(pageNumber, booksPerPage, Sort.by("year"));
        else pageable = PageRequest.of(pageNumber, booksPerPage);

        return booksRepository.findAll(pageable).getContent();
    }
    public Book findOne(int id){
        return booksRepository.findById(id).orElse(null);
    }
    public List<Book> findAllByNameStartingWith(String text){
        return booksRepository.findAllByNameStartingWith(text);
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
    @Transactional
    public void releaseBookById(int bookId){
        Optional<Book> book = booksRepository.findById(bookId);
        book.ifPresent(value -> value.setPerson(null));
        book.ifPresent(value -> value.setTakenAt(null));
    }
    @Transactional
    public void assignBookOwner(Person person, int bookId){
        Book book = findOne(bookId);
        book.setTakenAt(new Date());
        book.setPerson(person);
    }
}
