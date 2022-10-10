package com.james.batchsave.service.impl;

import com.james.batchsave.dataobject.Batch;
import com.james.batchsave.mapper.BatchMapper;
import com.james.batchsave.service.IBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
@Slf4j
public class BatchService implements IBatchService {

    @Autowired
    private BatchMapper mapper;

    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    @Transactional
    public void batchUpdate(List<Batch> batchList) {
        mapper.batchUpdate(batchList);
    }
}
