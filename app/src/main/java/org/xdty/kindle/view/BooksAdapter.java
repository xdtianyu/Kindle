package org.xdty.kindle.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xdty.kindle.R;
import org.xdty.kindle.module.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private List<Book> mBooks = new ArrayList<>();

    private ItemClickListener mItemClickListener;

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
        private TextView title;
        private TextView author;
        private TextView pages;

        ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            pages = (TextView) view.findViewById(R.id.pages);
            view.setOnClickListener(this);
        }

        void bind(Book book) {
            mBook = book;
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            pages.setText(book.getPages());
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(mBook);
            }
        }
    }
}
