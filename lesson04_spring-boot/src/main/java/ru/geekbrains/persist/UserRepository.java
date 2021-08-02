package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @EntityGraph(value = "GroupInfo.detail", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByUsername(String username);
}
