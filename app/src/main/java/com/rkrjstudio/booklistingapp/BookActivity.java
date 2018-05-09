package com.rkrjstudio.booklistingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    /**
     * EditText to display the list of books
     */
    private EditText editText;

    /**
     * TextView to diaplay no books were found in the list of books
     */
    TextView NoBooksWereFound;

    /**
     * Adapter for the list of books
     */
    private BookAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * Requesting the API
     */
    private static final String REQUEST_API =
            "https://www.googleapis.com/books/v1/volumes?q=search+";


    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        final ListView bookListView = findViewById(R.id.listview_book);
        mEmptyStateTextView = findViewById(R.id.empty);
        bookListView.setEmptyView(mEmptyStateTextView);


        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);


        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current book that was clicked on
                Book currentBook = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                assert currentBook != null;
                Uri bookUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        /*
         EditText to input search the list of books
        */
        editText = findViewById(R.id.editText);

        /*
         ImageButton to search the list of books
        */
        ImageButton imageButton = findViewById(R.id.imageButton);
        NoBooksWereFound = findViewById(R.id.text_no_data_found);

        // Set an click listener on the ImageButton which gives search results
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Initializing ConnectivityManager for network operations
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                assert connectivityManager != null;
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {

                    // Update the state with with information
                    BookAsyncTask task = new BookAsyncTask();
                    task.execute(getUrlForHttpRequest());

                    // Hide keyboard after searching books
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                } else {

                    // To avoid previous search results in list of books
                    mAdapter.clear();

                    // Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_net);
                }
            }
        });

    }

    private void BookUpdate(List<Book> books) {
        if (books.isEmpty()) {
            // if no books found, show a message
            NoBooksWereFound.setVisibility(View.VISIBLE);
        } else {
            NoBooksWereFound.setVisibility(View.GONE);
        }
        mAdapter.clear();
        mAdapter.addAll(books);
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the list of books in the response.
     */
    @SuppressLint("StaticFieldLeak")
    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link Book}s as the result.
         */
        @Override
        protected List<Book> doInBackground(String... urls) {
            URL url = QueryUtils.createUrl(getUrlForHttpRequest());
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            return QueryUtils.fetchBookData(urls[0]);
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method.
         */
        @Override
        protected void onPostExecute(List<Book> books) {


            // If there is a valid list of {@link Books}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (books == null) {
                return;
            }
            BookUpdate(books);
        }
    }

    private String getUserInput() {
        return editText.getText().toString();
    }

    private String getUrlForHttpRequest() {
        String formatUserInput = getUserInput().trim().replaceAll("\\r+", "+");
        return REQUEST_API + formatUserInput;
    }

}