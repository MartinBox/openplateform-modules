package com.study;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

public class SentinelDataSourceHandler implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(NacosConfigUtil.HOST, NacosConfigUtil.GROUP_ID, AppNameUtil.getAppName() + NacosConfigUtil.FLOW_DATA_ID_POSTFIX,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());


        ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource = new NacosDataSource<>(NacosConfigUtil.HOST, NacosConfigUtil.GROUP_ID, AppNameUtil.getAppName() + NacosConfigUtil.AUTHORITY_FLOW_DATA_ID_POSTFIX,
                source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                }));
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());


        ReadableDataSource<String, List<ParamFlowRule>> paramRuleDataSource = new NacosDataSource<>(NacosConfigUtil.HOST, NacosConfigUtil.GROUP_ID, AppNameUtil.getAppName() + NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX,
                source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                }));
        ParamFlowRuleManager.register2Property(paramRuleDataSource.getProperty());


        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(NacosConfigUtil.HOST, NacosConfigUtil.GROUP_ID, AppNameUtil.getAppName() + NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                }));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());


        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new NacosDataSource<>(NacosConfigUtil.HOST, NacosConfigUtil.GROUP_ID, AppNameUtil.getAppName() + NacosConfigUtil.SYSTEM_FLOW_DATA_ID_POSTFIX,
                source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                }));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());

    }
}
