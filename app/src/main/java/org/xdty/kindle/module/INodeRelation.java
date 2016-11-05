package org.xdty.kindle.module;

import android.os.Parcelable;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;

@Table(name = "node_relation")
@Entity
public interface INodeRelation extends Parcelable {
    @Key
    @Generated
    int getId();

    long getDescendant();

    long getAncestor();

}
