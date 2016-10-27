package org.xdty.kindle.module;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;

@Table(name = "node_relation")
@Entity
public interface INodeRelation {
    @Key
    @Generated
    int getId();

    long getDescendant();

    long getAncestor();

}
