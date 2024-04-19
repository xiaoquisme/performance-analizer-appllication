package com.example.performanceanalizerapplication.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class PreciseShardingAlgorithmCustom implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        return preciseShardingValue.getLogicTableName() + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
    }
}
