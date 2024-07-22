package com.ikhideifidon.book_network.role;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface RoleRepository extends ListCrudRepository<Role, Integer> {

    Optional<Role> findByName(String role);
}
