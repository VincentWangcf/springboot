package com.vincent.springboot.node;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class BaseNodeHandle implements IBaseNodeHandle {
    private final static Logger logger = LogManager.getLogger(BaseNodeHandle.class);

//    @Autowired
//    protected ITransactionFlowService transactionFlowService;
//
//    @Autowired
//    protected IFlowNodeService flowNodeService;
//
//    @Autowired
//    protected ITransactionFlowRecordService transactionFlowRecordService;

    @Autowired
    private NodeHandleHelper nodeHandleHelper;

    public abstract String currentNode();

    public abstract String currentFlow();

    public abstract NodeCla nextExcu(String taskId, Map<String, Object> params) throws Exception;

    public abstract NodeCla backExcu(String taskId, Map<String, Object> params);

    @Override
    public String next(String flowNodesName, String tranId, Map<String, Object> params) throws Exception {
        // 调用当前节点代码逻辑，返回下一个节点对象封装
        NodeCla nodeCla = nextExcu(tranId, params);
        if (nodeCla == null) {
            String currentNodeName = this.currentNode();
            Map<String, NodeCla> nodeClaMap = nodeHandleHelper.getFlowsNodeMap().get(flowNodesName);
            NodeCla currentNodeData = nodeClaMap.get(currentNodeName);
            if (currentNodeData == null) {
                logger.error("当前节点名称不在json数据链中包含！");
                return null;
            }
            nodeCla = nodeClaMap.get(currentNodeData.getNextNode());
            if (nodeCla == null) {
                logger.error("当前节点名称所对应的下一节点不在json数据链中包含！");
                return null;
            }
        }
        // 更新并保存流程节点
       // updateAndSaveTaskFlow(tranId, nodeCla);
        // 5.返回下一节点
        return nodeCla.getCurrentNode();
    }

    /**
     * 执行下一节点,该方法已过期, 请使用重载方法next(String flowNodesName,String tranId, Map<String, Object> params) 采用配置方式,进行节点的拼接运行
     *
     * @param tranId 交易id
     * @param params map对象
     * @return
     * @throws Exception
     */
    @Override
    public String next(String tranId, Map<String, Object> params) throws Exception {
        NodeCla nodeCla = nextExcu(tranId, params);
        //updateAndSaveTaskFlow(tranId, nodeCla);
        return nodeCla.getCurrentNode();
    }

//    @Override
//    public String back(String tranId, Map<String, Object> params) throws Exception {
//        TransactionFlow taskFlow = transactionFlowService.findByTranId(tranId);
//        if (!currentNode().equals(taskFlow.getCurrentNodeCode())) {
//            throw new IllegalParameterException(String.format("当前任务[%s] 的流程节点[%s],与操作的流程节点[%s]执行不一致", tranId,
//                    taskFlow.getCurrentNodeCode(), currentNode()));
//        }
//        NodeCla nodeCla = backExcu(tranId, params);
//        if (!flowNodeService.isExist(nodeCla.getCurrentNode())) {
//            throw new IllegalParameterException(String.format("当前任务[%s] 的流程节点[%s],回退的节点[%s]不存在", tranId,
//                    taskFlow.getCurrentNodeCode(), nodeCla.getCurrentNode()));
//        }
//
//        taskFlow.setCurrentNodeCode(nodeCla.getCurrentNode());
//        taskFlow.setNextNodeCode(nodeCla.getNextNode());
//        taskFlow.setUpdateTime(DateUtil.date());
//
//        // 更新任务流程实例
//        transactionFlowService.updateById(taskFlow);
//
//        this.saveRecord(nodeCla, params);
//        return nodeCla.getCurrentNode();
//    }
//
//    @Override
//    public String end(String tranId, Map<String, Object> params) throws Exception {
//
//        TransactionFlow taskFlow = transactionFlowService.findByTranId(tranId);
//        if (!currentNode().equals(taskFlow.getCurrentNodeCode())) {
//            throw new IllegalParameterException(String.format("当前任务[%s] 的流程节点[%s],与操作的流程节点[%s]执行不一致", tranId,
//                    taskFlow.getCurrentNodeCode(), currentNode()));
//        }
//        taskFlow.setCurrentNodeCode(NodeConstants.NODE_END);
//        taskFlow.setNextNodeCode("");
//        taskFlow.setUpdateTime(DateUtil.date());
//
//        // 更新任务流程实例
//        transactionFlowService.updateById(taskFlow);
//
//        NodeCla nodeCla = new NodeCla();
//        nodeCla.setCurrentNode(NodeConstants.NODE_END);
//        nodeCla.setNextNode("");
//        this.saveRecord(nodeCla, params);
//        return NodeConstants.NODE_END;
//    }
//
//    private void updateAndSaveTaskFlow(String tranId, NodeCla nodeCla) {
//        // 根据节点对象，更新任务流程实例
//        // 根据交易表id，查出已入库的流程实例
//        TransactionFlow taskFlow = transactionFlowService.findByTranId(tranId);
//        taskFlow.setCurrentNodeCode(nodeCla.getCurrentNode());
//        taskFlow.setNextNodeCode(nodeCla.getNextNode());
//        taskFlow.setUpdateTime(DateUtil.date());
//        try {
//            transactionFlowService.updateById(taskFlow);
//        } catch (Exception e) {
//            logger.info("流程节点实例修正失败,transactionId[{}],异常信息{}", taskFlow.getTransactionId(), e);
//            throw new RuntimeException(String.format("新增流程节点实例失败,transactionId[%s]", taskFlow.getTransactionId()));
//        }
//        logger.info("流程节点实例修正成功,transactionId[{}]", taskFlow.getTransactionId());
//
//        // 4.保存流程节点记录，入库
//        TransactionFlowRecord transactionFlowRecord = new TransactionFlowRecord();
//        transactionFlowRecord.setTransactionId(tranId);
//        transactionFlowRecord.setCurrentNodeCode(currentNode());
//        transactionFlowRecord.setNextNodeCode(nodeCla.getCurrentNode());
//        try {
//            transactionFlowRecordService.saveTransactionFlowRecord(transactionFlowRecord);
//        } catch (Exception e) {
//            logger.info("流程节点记录保存失败,transactionId[{}],异常信息{}", transactionFlowRecord.getTransactionId(), e);
//            throw new RuntimeException(
//                    String.format("新增流程节点记录保存失败,transactionId[%s]", transactionFlowRecord.getTransactionId()));
//        }
//        logger.info("流程节点记录保存成功,transactionId[{}]", transactionFlowRecord.getTransactionId());
//    }
//
//    private void saveRecord(NodeCla nodeCla, Map<String, Object> params) {
//        String tranId = (String)params.get("tranId");
//        String userId = (String)params.get("userId");
//        String operName = (String)params.getOrDefault("operName", "");
//        String nodeOper = (String)params.getOrDefault("nodeOper", "");
//        String opinionConclusion = (String)params.getOrDefault("opinionConclusion", "");
//        String opinionDetail = (String)params.getOrDefault("opinionDetail", "");
//
//        TransactionFlowRecord record = new TransactionFlowRecord();
//        record.setFlowId(currentFlow());
//        record.setCreateTime(DateUtil.date());
//        record.setCreateBy(userId);
//        record.setCurrentNodeCode(nodeCla.getCurrentNode());
//        record.setEnable(1);
//        String now = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
//        String id = "tFR_" + now + OnlyCodeGenerator.distriId().substring(12, 18);
//        record.setId(id);
//        record.setNextNodeCode(nodeCla.getNextNode());
//        record.setTransactionId(tranId);
//        record.setNodeOper(nodeOper);
//        record.setOpinionConclusion(opinionConclusion);
//        record.setOpinionDetail(opinionDetail);
//        record.setOperName(operName);
//
//        transactionFlowRecordService.insert(record);
//    }

}
