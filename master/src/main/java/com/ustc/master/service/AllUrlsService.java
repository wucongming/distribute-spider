package com.ustc.master.service;

import com.ustc.master.entity.AllUrlsEntity;
import com.ustc.master.repository.AllUrlsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllUrlsService {

    @Autowired
    private AllUrlsRepository allUrlsRepository;


    public AllUrlsEntity find(AllUrlsEntity allUrlsEntity) {
        Optional<AllUrlsEntity> optional = allUrlsRepository.findById(allUrlsEntity.getUrl());
        if(optional.isPresent()) return optional.get();
        else return null;
    }

}
