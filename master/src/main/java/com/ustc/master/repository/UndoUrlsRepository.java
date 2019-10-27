package com.ustc.master.repository;

import com.ustc.master.entity.UndoUrlsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UndoUrlsRepository extends JpaRepository<UndoUrlsEntity, String> {

}
