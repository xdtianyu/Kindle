package org.xdty.kindle.module;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

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
