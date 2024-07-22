package com.ikhideifidon.book_network.book;

import com.ikhideifidon.book_network.common.BaseEntity;
import com.ikhideifidon.book_network.feedback.FeedBack;
import com.ikhideifidon.book_network.history.BookTransactionHistory;
import com.ikhideifidon.book_network.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "book")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;

    // <<.........................RELATIONSHIP.................................>>
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<FeedBack> feedBacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> historyList;
}
