package com.ustc.master.repository;

import com.ustc.master.entity.DoneUrlsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoneUrlsRepository extends JpaRepository<DoneUrlsEntity, String> {
}
