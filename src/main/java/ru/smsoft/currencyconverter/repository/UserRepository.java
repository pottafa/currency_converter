package ru.smsoft.currencyconverter.repository;

import org.springframework.data.repository.CrudRepository;
import ru.smsoft.currencyconverter.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
