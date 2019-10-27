package com.ustc.master.service;

import com.ustc.master.repository.DoneUrlsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoneUrlsService {

    @Autowired
    private DoneUrlsRepository doneUrlsRepository;

}
