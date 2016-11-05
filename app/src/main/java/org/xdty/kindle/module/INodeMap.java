package org.xdty.kindle.module;

import android.os.Parcelable;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;

@Table(name = "node_map")
@Entity
public interface INodeMap extends Parcelable {

    @Key
    @Generated
    int getId();

    @Column(name = "item_id")
    String getItemId();

    @Column(name = "node_id")
    long getNodeId();
}
