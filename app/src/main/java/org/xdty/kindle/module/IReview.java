package org.xdty.kindle.module;

import android.os.Parcelable;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;

@Table(name = "review")
@Entity
public interface IReview extends Parcelable {

    @Key
    @Generated
    int getId();

    //@ForeignKey(referencedColumn = "item_id")
    //@OneToOne
    //@Column(name = "item_id")
    //Book getBook();

    @Column(name = "item_id")
    String getItemId();

    @Column(name = "editorial_review")
    String getEditorialReview();
}
