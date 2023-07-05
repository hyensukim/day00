package org.koreait.repositories;

import org.koreait.entities.file.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FileRepository extends JpaRepository<FileEntity,Long>,
        QuerydslPredicateExecutor<FileEntity> {
}
