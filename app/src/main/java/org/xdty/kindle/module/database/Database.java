package org.xdty.kindle.module.database;

import org.xdty.kindle.module.Book;
import org.xdty.kindle.module.Node;

import java.util.List;

public interface Database {

    List<Book> getCnBooksSync();

    List<Book> getEnBooksSync();

    List<Node> getBookNodesSync(String itemId);

    Node getNodeParentSync(long nodeId);
}
