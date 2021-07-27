package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

public final class UserSpecifications {
    public static Specification<User> usernamePrefix(String prefix) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), prefix + "%"));
    }
}
