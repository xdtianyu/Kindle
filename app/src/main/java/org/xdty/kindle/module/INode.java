package org.xdty.kindle.module;

import android.os.Parcelable;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;
import io.requery.Transient;

@Table(name = "node")
@Entity
public interface INode extends Parcelable {

    @Key
    @Generated
    int getId();

    @Transient
    Node getNode();

    @Column(name = "node_id")
    long getNodeId();

    @Column(name = "is_root")
    boolean isRoot();

    String getName();
}
