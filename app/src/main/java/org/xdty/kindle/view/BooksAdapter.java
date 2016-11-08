package org.xdty.kindle.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import org.xdty.kindle.R;
import org.xdty.kindle.application.Application;
import org.xdty.kindle.module.Book;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    @Inject
    RequestManager mGlideRequest;
    private List<Book> mBooks = new ArrayList<>();
    private ItemClickListener mItemClickListener;

    public BooksAdapter() {
        Application.getAppComponent().inject(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public void refresh(List<Book> books) {
        mBooks.clear();
        mBooks.addAll(books);
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(Book book);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Book mBook;

        private ImageView thumbnail;
        private TextView title;
        private TextView author;
        private TextView pages;
        private TextView price;

        ViewHolder(View view) {
            super(view);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            pages = (TextView) view.findViewById(R.id.pages);
            price = (TextView) view.findViewById(R.id.price);
            view.setOnClickListener(this);
        }

        void bind(Book book) {
            mBook = book;
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            pages.setText(book.getPages());
            price.setText(getString(R.string.price, book.getPrice()));

            mGlideRequest.load(book.getLargeImageUrl())
                    .crossFade()
                    .into(thumbnail);
        }

        String getString(int resId, Object... formatArgs) {
            return itemView.getContext().getString(resId, formatArgs);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(mBook);
            }
        }
    }
}
