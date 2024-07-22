package com.ikhideifidon.book_network.feedback;

import com.ikhideifidon.book_network.book.Book;
import com.ikhideifidon.book_network.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "feedback")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedBack extends BaseEntity {

    private Double note;
    private String comment;

    // <<.........................RELATIONSHIP.................................>>
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
