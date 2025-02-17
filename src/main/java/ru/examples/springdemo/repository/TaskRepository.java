package ru.examples.springdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.examples.springdemo.model.Task;

import java.util.Optional;

@Repository
@Transactional
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, CrudRepository<Task, Long> {

//    @Modifying
//    @Query("UPDATE Task t SET t.done = TRUE WHERE t.id = :id")
//    void markIsDone(@Param("id") Long id);

    Iterable<Task> findTasksByUserIdOrderById(Long userId);

    Optional<Task> findTasksByIdAndUserId(Long taskId, Long userId);
}
