package com.rkrjstudio.booklistingapp;


import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private String mTitle;

    private String mAuthor;

    private String mPublisher;

    private String mImage;

    private String mUrl;

    /*
    * Create a new Book object.
    * @param vTitle is the name of  book tile
    * @param vAuthor is the name of the book author
    * @param vPublisher is the publisher of the book
    * @param vImage is drawable reference ID that corresponds to the books
    * @param url is id for each book
    * */
    Book(String vTitle, String vAuthor, String vPublisher, String vImage, String url) {
        mTitle = vTitle;
        mAuthor = vAuthor;
        mPublisher = vPublisher;
        mImage = vImage;
        mUrl = url;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    /**
     * Get the book by get() method
     */
    String getmTitle() {
        return mTitle;
    }

    String getmAuthor() {
        return mAuthor;
    }

    String getmPublisher() {
        return mPublisher;
    }

    String getmImage() {
        return mImage;
    }

    String getUrl() {
        return mUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mAuthor);
        parcel.writeString(mPublisher);
        parcel.writeString(mImage);
        parcel.writeString(mUrl);
    }

    private Book(Parcel in) {
        mTitle = in.readString();
        mAuthor = in.readString();
        mPublisher = in.readString();
        mImage = in.readString();
        mUrl = in.readString();
    }
}

