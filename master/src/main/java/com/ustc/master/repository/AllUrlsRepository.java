package com.ustc.master.repository;

import com.ustc.master.entity.AllUrlsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllUrlsRepository extends JpaRepository<AllUrlsEntity, String> {
}
