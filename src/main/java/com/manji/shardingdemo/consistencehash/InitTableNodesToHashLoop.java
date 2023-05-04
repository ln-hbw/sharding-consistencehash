package com.manji.shardingdemo.consistencehash;


import com.manji.shardingdemo.util.ConsistenceHashUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * 初始化hash环
 * @author manji
 * @Date 2023/5/4
 */
@Slf4j
@Component
@Lazy
public class InitTableNodesToHashLoop {

    @Resource
    private ShardingDataSource shardingDataSource;

    @Getter
    private HashMap<String, SortedMap<Long, String>> tableVirtualNodes = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            ShardingRule rule = shardingDataSource.getShardingContext().getShardingRule();
            Collection<TableRule> tableRules = rule.getTableRules();
            ConsistenceHashUtil consistenceHashUtil = new ConsistenceHashUtil();
            for (TableRule tableRule : tableRules) {
                String logicTable = tableRule.getLogicTable();

                tableVirtualNodes.put(logicTable,
                        consistenceHashUtil.initNodesToHashLoop(
                                tableRule.getActualDataNodes()
                                        .stream()
                                        .map(DataNode::getTableName)
                                        .collect(Collectors.toList()))
                );
            }
        } catch (Exception e) {
            log.error("分表节点初始化失败 {}", e);
        }
    }
}

