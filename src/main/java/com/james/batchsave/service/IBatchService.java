package com.james.batchsave.service;

import com.james.batchsave.dataobject.Batch;

import java.util.List;

public interface IBatchService {

    void batchUpdate(List<Batch> batch);
}
