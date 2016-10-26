package org.xdty.kindle.module;

import java.util.List;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;
import io.requery.Transient;

@Table(name = "book")
@Entity
public interface IBook {

    @Key
    @Generated
    int getId();

    @Column(name = "item_id")
    String getItemId();

    String getPages();

    String getBrand();

    String getAsin();

    @Transient
    String getBinding();

    String getEdition();

    @Transient
    String getEditorialReview();

    String getIsbn();

    @Column(name = "large_image_url")
    String getLargeImageUrl();

    @Column(name = "medium_image_url")
    String getMediumImageUrl();

    @Column(name = "small_image_url")
    String getSmallImageUrl();

    String getRegion();

    @Column(name = "release_date")
    String getReleaseDate();

    @Column(name = "publication_date")
    String getPublicationDate();

    @Transient
    String getSalesRank();

    String getLanguages();

    @Transient
    List<Node> getNodes();

    String getTitle();

    @Transient
    double getAverage();

    @Transient
    double getPrice();

    String getAuthor();

    @Transient
    double getMin();

    double getScore();

    String getUrl();

    @Transient
    String getMinDay();
}
