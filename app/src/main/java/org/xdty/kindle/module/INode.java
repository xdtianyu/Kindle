package org.xdty.kindle.module;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;

@Table(name = "node")
@Entity
public interface INode {

    @Key
    @Generated
    int getId();

    Node getNode();

    long getNodeId();

    boolean isRoot();

    String getName();
}
