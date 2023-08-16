package ru.skyeng.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skyeng.task.models.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
