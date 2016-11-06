package org.xdty.kindle.module.database;

import org.xdty.kindle.module.Book;
import org.xdty.kindle.module.Node;
import org.xdty.kindle.module.Review;

import java.util.List;

public interface Database {

    List<Book> getCnBooksSync();

    List<Book> getEnBooksSync();

    List<Node> getBookNodesSync(String itemId);

    Node getNodeParentSync(long nodeId);

    List<Review> getReviewsSync(String itemId);

    Book getBookSync(String itemId);
}
