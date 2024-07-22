package com.ikhideifidon.book_network.user;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface TokenRepository extends ListCrudRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
}
