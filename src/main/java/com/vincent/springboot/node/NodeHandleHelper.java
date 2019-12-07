package com.vincent.springboot.node;

import com.vincent.springboot.NodeConstants.CollectionUtil;
import com.vincent.springboot.NodeConstants.NodeConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Component
//@Resource
//@Repository
public class NodeHandleHelper implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LogManager.getLogger(NodeHandleHelper.class);

    @Resource
//    private ITransactionFlowService transactionFlowService;

    private Map<String, IBaseNodeHandle> nodeHandles;

    private ApplicationContext applicationContext;

    private Map<String, Map<String, NodeCla>> flowsNodeMap;

    public Map<String, Map<String, NodeCla>> getFlowsNodeMap() {
        return flowsNodeMap;
    }

    public IBaseNodeHandle nodeHandle(String nodeCode) {
        return nodeHandles.get(nodeCode);
    }

    public void startFlow(String flowNodeName, Map<String, Object> params) throws Exception {
        Map<String, NodeCla> flowNodeMap = flowsNodeMap.get(flowNodeName);
        if (flowNodeMap == null) {
            throw new RuntimeException("不存在该流程! 请检查流程名称是否有误");
        }
        NodeCla start = flowNodeMap.get(NodeConstants.NODE_START);
        String nextNode = start.getNextNode();
        String taskId = (String) params.getOrDefault("taskId", "");
        // 新建流程
        //  transactionFlowService.saveTransactionFlow(taskId, flowNodeName, start.getCurrentNode(), start.getNextNode());
        do {
            nextNode = nodeHandles.get(nextNode).next(flowNodeName, taskId, params);
        } while (!NodeConstants.NODE_END.equals(nextNode));
    }

    public void continueFlow(String flowNodeName, String nodeName, String tranId, Map<String, Object> params)
            throws Exception {
        Map<String, NodeCla> flowNodeMap = flowsNodeMap.get(flowNodeName);
        if (flowNodeMap == null) {
            throw new RuntimeException("不存在该流程! 请检查流程名称是否有误:" + flowNodeName);
        }
        NodeCla continueNode = flowNodeMap.get(nodeName);
        if (continueNode == null) {
            throw new RuntimeException("不存在该流程节点,请检查流程节点名称是否有误:" + nodeName);
        }
        String nextNode = continueNode.getCurrentNode();
        if (NodeConstants.NODE_START.equals(nextNode)) {
            nextNode = continueNode.getNextNode();
        }
        do {
            nextNode = nodeHandles.get(nextNode).next(flowNodeName, tranId, params);
        } while (!NodeConstants.NODE_END.equals(nextNode));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.nodeHandles = applicationContext.getBeansOfType(IBaseNodeHandle.class);
        this.flowsNodeMap = CollectionUtil.newHashMap();
        Properties properties = new Properties();


//        File file = new File("classpath*:properties/flow.properties");
        File file = new File("F:\\VincentCode\\src\\main\\resources\\properties\\flow.properties");

        InputStream is = new FileInputStream(file);

//        InputStream is = applicationContext.getClassLoader().getResourceAsStream("F:\\VincentCode\\src\\main\\resources\\properties.flow.properties");
//        InputStream is = applicationContext.getClassLoader().getResourceAsStream("classpath:properties/flow.properties");






        if (is == null) {
            logger.warn("项目中不包含流程节点运转配置文件,只能采用传统手动拼接方式进行节点运转!");
            return;
        }
        properties.load(is);
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            String property = properties.getProperty(propertyName);
            String[] nodes = property.split(",");
            if (nodes.length <= 0) {
                throw new RuntimeException("结点流程不能为空!");
            }
            if (!NodeConstants.NODE_START.equals(nodes[0])) {
                throw new RuntimeException("结点流程必须以START开头!");
            }
            if (!NodeConstants.NODE_END.equals(nodes[nodes.length - 1])) {
                throw new RuntimeException("结点流程必须以END结尾!");
            }
            HashMap<String, NodeCla> nodeMap = new HashMap<>(20);
            for (int i = 0; i < nodes.length; i++) {
                NodeCla nodeCla = new NodeCla();
                if (i == 0) {
                    nodeCla.setCurrentNode(nodes[i]);
                    nodeCla.setNextNode(nodes[i + 1]);
                    nodeMap.put(nodes[i], nodeCla);
                    continue;
                }
                if (i == nodes.length - 1) {
                    nodeCla.setCurrentNode(nodes[i]);
                    nodeCla.setBackNode(nodes[i - 1]);
                    nodeMap.put(nodes[i], nodeCla);
                    continue;
                }
                nodeCla.setCurrentNode(nodes[i]);
                nodeCla.setNextNode(nodes[i + 1]);
                nodeCla.setBackNode(nodes[i - 1]);
                nodeMap.put(nodes[i], nodeCla);
            }
            flowsNodeMap.put(propertyName, nodeMap);
        }
    }

    private NodeHandleHelper() {
        super();
    }
}
