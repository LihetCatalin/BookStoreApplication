package service.book;

import model.Book;
import repository.book.BookRepository;
import service.book.BookService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl implements BookService {
    final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean updateStock(Book book, int decrement) {
        if(book.getStock() - decrement < 0)return false;
        else{
            book.setStock(book.getStock() - decrement);
            return bookRepository.updateStock(book);
        }
    }

    @Override
    public boolean updateBook(Long idBook, Book updatedBook) {
        return bookRepository.updateBook(idBook, updatedBook);
    }

    public boolean deleteBook(Book book){
        return bookRepository.removeById(book.getId());
    }

    @Override
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id);
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
}
