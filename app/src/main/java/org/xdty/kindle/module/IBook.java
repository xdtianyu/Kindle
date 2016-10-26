package org.xdty.kindle.module;

import java.util.List;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

@Entity
public interface IBook {

    @Key
    @Generated
    int getId();

    String getItemId();

    String getPages();

    String getBrand();

    String getAsin();

    String getBinding();

    String getEdition();

    String getEditorialReview();

    String getIsbn();

    String getLargeImageUrl();

    String getMediumImageUrl();

    String getSmallImageUrl();

    String getRegion();

    String getReleaseDate();

    String getPublicationDate();

    String getSalesRank();

    String getLanguages();

    List<Node> getNodes();

    String getTitle();

    double getAverage();

    double getPrice();

    String getAuthor();

    double getMin();

    double getScore();

    String getUrl();

    String getMinDay();
}
