package com.rkrjstudio.booklistingapp;


import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {


    BookAdapter(Activity context, ArrayList<Book> bookList) {

        super(context, 0, bookList);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        // Storing current position of the items in the list
        Book currentBook = getItem(position);

        // Mapping title TextView into adapter which parsed from json
        assert currentBook != null;
        String Title = currentBook.getmTitle();
        TextView TitleView = listItemView.findViewById(R.id.title);
        TitleView.setText(Title);

        // Mapping author TextView into adapter which parsed from json
        String Author = currentBook.getmAuthor();
        TextView authorView = listItemView.findViewById(R.id.author);
        authorView.setText(Author);

        // Mapping publisher TextView into adapter which parsed from json
        String Publisher = currentBook.getmPublisher();
        TextView PublisherView = listItemView.findViewById(R.id.publisher);
        PublisherView.setText(Publisher);

        // Mapping images to list of ImageView into adapter which parsed from json
        ImageView imageView = listItemView.findViewById(R.id.image_thumbnail);
        Picasso.with(getContext()).load(currentBook.getmImage()).into(imageView);

        // storing and returning items to populate into ListView
        return listItemView;
    }
}

