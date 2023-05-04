package com.manji.shardingdemo.consistencehash;

import com.manji.shardingdemo.util.ConsistenceHashUtil;
import com.manji.shardingdemo.util.SpringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;

/**
 * 自定义哈希算法 + 虚拟节点实现数据分片
 * @author manji
 * @Date 2023/5/4
 */
public class ConsistenceHashAlgorithm implements RangeShardingAlgorithm<Long>, PreciseShardingAlgorithm<Long> {

    /**
     * 范围查找时需要用到改分片算法，这里暂不完善了
     * @param collection
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {
        System.out.println(collection);
        System.out.println(rangeShardingValue);
        return collection;
    }

    /**
     * @param collection collection 配置文件中解析到的所有分片节点
     * @param preciseShardingValue 解析到的sql值
     * @return
     */
    @Override
    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
        System.out.println(collection);
        InitTableNodesToHashLoop initTableNodesToHashLoop = SpringUtils.getBean(InitTableNodesToHashLoop.class);
        if (CollectionUtils.isEmpty(collection)) {
            return preciseShardingValue.getLogicTableName();
        }

        //这里主要为了兼容当联表查询时，如果两个表非关联表则
        //当对副表分表时shardingValue这里传递进来的依然是主表的名称，
        //但availableTargetNames中确是副表名称，所有这里要从availableTargetNames中匹配真实表
        ArrayList<String> availableTargetNameList = new ArrayList<>(collection);
        String logicTableName = availableTargetNameList.get(0).replaceAll("[^(a-zA-Z_)]", "");
        SortedMap<Long, String> tableHashNode =
                initTableNodesToHashLoop .getTableVirtualNodes().get(logicTableName);

        ConsistenceHashUtil consistentHashAlgorithm = new ConsistenceHashUtil(tableHashNode,
                collection);

        return consistentHashAlgorithm.getTableNode(String.valueOf(preciseShardingValue.getValue()));
    }
}
