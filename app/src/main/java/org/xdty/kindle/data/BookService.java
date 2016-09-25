package org.xdty.kindle.data;

import org.xdty.kindle.module.Books;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BookService {

    @GET("kindle.json")
    Call<Books> getBooks();

}
