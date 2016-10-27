package org.xdty.kindle.module;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;

@Table(name = "node_map")
@Entity
public interface INodeMap {

    @Key
    @Generated
    int getId();

    String getItemId();

    long getNodeId();


}
