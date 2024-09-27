package com.ikhideifidon.book_network.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface FeedbackRepository extends ListCrudRepository<Feedback, Integer> {

    @Query("""
        SELECT feedback
        FROM Feedback AS feedback
        WHERE feedback.book.id = :bookId
    """)
    Page<Feedback> findAllByBookId(Integer bookId, Pageable pageable);

}
