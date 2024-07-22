package com.ikhideifidon.book_network.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface BookRepository extends ListCrudRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    @Query(
            """
            SELECT book
            FROM Book AS book
            WHERE book.archived = false
            AND book.shareable = true
            AND book.owner.id != :userId
            """
    )
    Page<Book> findAllDisplayableBooks(Pageable pageable, Integer userId);
}
