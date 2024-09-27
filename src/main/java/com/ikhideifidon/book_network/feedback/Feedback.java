package com.ikhideifidon.book_network.feedback;

import com.ikhideifidon.book_network.book.Book;
import com.ikhideifidon.book_network.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "feedback")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback extends BaseEntity {

    private Double note;
    private String comment;

    // <<.........................RELATIONSHIP.................................>>
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
