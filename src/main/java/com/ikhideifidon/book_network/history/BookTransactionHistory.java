package com.ikhideifidon.book_network.history;

import com.ikhideifidon.book_network.book.Book;
import com.ikhideifidon.book_network.common.BaseEntity;
import com.ikhideifidon.book_network.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "book_transaction_history")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTransactionHistory extends BaseEntity {

    // <<.........................RELATIONSHIP.................................>>
    // User Relationship
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    // Book Relationship

    private boolean returned;
    private boolean returnApproved;
}
