package com.vincent.springboot.node;

import java.util.Map;

public interface IBaseNodeHandle {

    public String next(String flowNodesName, String tranId, Map<String, Object> params) throws Exception;

    @Deprecated
    public String next(String tranId, Map<String, Object> params) throws Exception;

    public String back(String tranId, Map<String, Object> params) throws Exception;

    public String end(String tranId, Map<String, Object> params) throws Exception;
}
