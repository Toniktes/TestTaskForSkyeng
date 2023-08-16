package ru.skyeng.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skyeng.task.models.PostalItem;

@Repository
public interface PostalItemRepository extends JpaRepository<PostalItem, Long> {
}
