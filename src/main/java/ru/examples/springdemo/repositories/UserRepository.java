package ru.examples.springdemo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.examples.springdemo.models.User;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {

    Optional<User> findUserByLogin(String login);
}
