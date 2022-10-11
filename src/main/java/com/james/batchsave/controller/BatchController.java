package com.james.batchsave.controller;

import com.google.common.collect.Lists;
import com.james.batchsave.dataobject.Batch;
import com.james.batchsave.mapper.BatchMapper;
import com.james.batchsave.service.IBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class BatchController {

    @Autowired
    private IBatchService service;

    @Autowired
    private BatchMapper mapper;

    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    public String hello() {
        Batch batch = new Batch();
        batch.setK1("1");
        batch.setK1("2");
        batch.setK1("3");
        batch.setK1("4");

        batch.setB1("1");
        batch.setB2("2");
        batch.setB3("3");
        batch.setB4("4");
        batch.setB5("5");
        batch.setB6("6");
        batch.setB7("7");
        batch.setB8("8");
        batch.setB9("9");
        batch.setB10("10");
        batch.setB11("11");
        batch.setB12("12");
        batch.setB13("13");
        batch.setB14("14");
        batch.setB15("15");
        batch.setB16("16");
        batch.setB17("17");
        batch.setB18("18");
        batch.setB19("19");
        batch.setB20("20");

        List<Batch> batchList = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 500; i++) {
            Batch target = new Batch();
            BeanUtils.copyProperties(batch, target);
            target.setK1(String.valueOf(1));
            target.setK2(String.valueOf(2));
            target.setK3(String.valueOf(3));
            target.setK4(String.valueOf(4));
            target.setId(String.valueOf(i + 1));
            batchList.add(target);
        }

        List<List<Batch>> partitions = Lists.partition(batchList, 100);

        List<CompletableFuture<List<Batch>>> futures = partitions
                .stream()
                .map(partition -> CompletableFuture.supplyAsync(() -> {
                    List<Batch> partitionResult = new ArrayList<>();
                    partition.forEach(e -> partitionResult.add(mapper.selectById(e.getId())));
                    return partitionResult;}))
                .collect(Collectors.toList());

        List<Batch> allResult = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<Batch> result = new ArrayList<>();
                    futures.stream().map(future -> getFutureResult(future, new ArrayList<Batch>())).forEach(result::addAll);
                    return result;
                })
                .join();

        /*ExecutorService executorService = Executors.newFixedThreadPool(partitions.size());
        CompletableFuture[] completableFutures = partitions.stream().map(e ->
                CompletableFuture.runAsync(() -> service.batchUpdate(e), executorService)
        ).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();*/

        // TODO 多线程更新
        log.info(">> costTime: {}ms", System.currentTimeMillis() - startTime);
        return "ok";
    }

    public static <T> T getFutureResult(Future<T> future, T defaultValue) {
        try {
            return future.get();
        } catch (Exception ignored) {
            return defaultValue;
        }
    }
}
