package com.ustc.master.service;

import com.ustc.master.entity.UndoUrlsEntity;
import com.ustc.master.repository.UndoUrlsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UndoUrlsService {

    @Autowired
    private UndoUrlsRepository undoUrlsRepository;

    public UndoUrlsEntity getOneUndo() {
        Page<UndoUrlsEntity> all = undoUrlsRepository.findAll(new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 1;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        });
        List<UndoUrlsEntity> collect = all.getContent();
        if(collect.size() != 0) {
            UndoUrlsEntity undoUrlsEntity = collect.get(0);
            undoUrlsRepository.delete(undoUrlsEntity);
            return undoUrlsEntity;
        }
        return null;
    }


}
