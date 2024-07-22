package com.ikhideifidon.book_network.book;

import com.ikhideifidon.book_network.common.PageResponse;
import com.ikhideifidon.book_network.exception.OperationNotPermittedException;
import com.ikhideifidon.book_network.file.FileStorageService;
import com.ikhideifidon.book_network.history.BookTransactionHistory;
import com.ikhideifidon.book_network.history.BookTransactionHistoryRepository;
import com.ikhideifidon.book_network.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository bookTransactionHistoryRepository;
    private final FileStorageService fileStorageService;

    public Integer save(BookRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        bookRepository.save(book);
        return bookRepository.save(book).getId();
    }

    public BookResponse findById(Integer bookId) {
        return bookRepository.findById(bookId).map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No book found with the ID '%d'", bookId)));
    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> bookResponses = books
                .stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(bookResponses, books.getNumber(), books.getSize(), books.getTotalElements(), books.getTotalPages(), books.isFirst(), books.isLast());
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()), pageable);
        List<BookResponse> bookResponses = books
                .stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(bookResponses, books.getNumber(), books.getSize(), books.getTotalElements(), books.getTotalPages(), books.isFirst(), books.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks
                .stream()
                .map(bookMapper ::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(bookResponses, allBorrowedBooks.getNumber(), allBorrowedBooks.getSize(), allBorrowedBooks.getTotalElements(), allBorrowedBooks.getTotalPages(), allBorrowedBooks.isFirst(), allBorrowedBooks.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allReturnedBooks = bookTransactionHistoryRepository.findAllReturnedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponses = allReturnedBooks
                .stream()
                .map(bookMapper ::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(bookResponses, allReturnedBooks.getNumber(), allReturnedBooks.getSize(), allReturnedBooks.getTotalElements(), allReturnedBooks.getTotalPages(), allReturnedBooks.isFirst(), allReturnedBooks.isLast());
    }

    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
         Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(String.format("No book found with the ID '%d'", bookId)));
         User user = (User) connectedUser.getPrincipal();
         if (!Objects.equals(book.getOwner().getId(), user.getId()))
             throw new OperationNotPermittedException("You cannot update books shareable status.");
         book.setShareable(!book.isShareable());
         bookRepository.save(book);
         return bookId;
    }

    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(String.format("No book found with the ID '%d'", bookId)));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getOwner().getId(), user.getId()))
            throw new OperationNotPermittedException("You cannot update others' archived status.");
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = getBookIfBorrowable(bookId, user);

        final boolean isBorrowed = bookTransactionHistoryRepository.isAlreadyBorrowedByUser(bookId, user.getId());
        if (isBorrowed) {
            throw new OperationNotPermittedException("The requested book is already borrowed.");
        }

        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public Integer returnBorrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(String.format("No book found with the ID '%d'", bookId)));
        if (book.isArchived() || !book.isShareable())
            throw new OperationNotPermittedException("The Requested Book cannot be borrowed. It is archived/not borrowable.");

        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(book.getOwner().getId(), user.getId()))
            throw new OperationNotPermittedException("You cannot borrow or return your owned book.");

        BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository.findByBookIdAndUserId(bookId, user.getId()).orElseThrow(() -> new OperationNotPermittedException("You did not borrow this book."));
        bookTransactionHistory.setReturned(true);
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public Integer approveReturnBorrowedBook(Integer bookId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository.findByBookIdAndOwnerId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("The book is not returned yet. You cannot approve its return."));
        bookTransactionHistory.setReturnApproved(true);
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(String.format("No book found with the ID '%d'", bookId)));
        User user = (User) connectedUser.getPrincipal();
        String bookCover = fileStorageService.saveFile(file, user.getId());
        book.setBookCover(bookCover);
        bookRepository.save(book);
    }

    private Book getBookIfBorrowable(Integer bookId, User user) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(String.format("No book found with the ID '%d'", bookId)));
        if (book.isArchived() || !book.isShareable())
            throw new OperationNotPermittedException("The Requested Book cannot be borrowed. It is archived/not borrowable.");

        if (Objects.equals(book.getOwner().getId(), user.getId()))
            throw new OperationNotPermittedException("You cannot borrow your owned book.");
        return book;
    }
}
