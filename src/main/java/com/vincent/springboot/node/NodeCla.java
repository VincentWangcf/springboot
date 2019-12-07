package com.vincent.springboot.node;

public class NodeCla {

    private String currentNode;

    private String backNode;

    private String nextNode;

    private String endNode;

    public String getBackNode() {
        return backNode;
    }

    public void setBackNode(String backNode) {
        this.backNode = backNode;
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

}
