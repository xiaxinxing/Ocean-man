package com.ocean.core.commons.vo;

import lombok.Data;

import java.util.List;

/**
 * ztreeNode 节点
 */
@Data
public class ZtreeNode {


    private String id;
    private String pid;
    private String name;
    private boolean isParent;
    private List<ZtreeNode> children;
    private boolean checked;
    private String icon;

}
