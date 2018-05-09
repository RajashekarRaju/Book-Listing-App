# Book-Listing-App
Book Listing app which would allow a user to get a list of published books on a given topic using the google books api in order to fetch results and display them to the user.
This project is part of udacity student submission project.

This app includes following learning components :
1. Fetching data from an API
2. Using an AsyncTask
3. Parsing a JSON response
4. Creating a list based on that data and displaying it to the user.

This app contains single layout with list of books using EditText and Search Button.

![screenshot 83](https://user-images.githubusercontent.com/25173010/39834175-c7a3ae0a-53e9-11e8-928b-bafde37d5c23.png)

Displays Result for proper search keywords, example :
![screenshot 89](https://user-images.githubusercontent.com/25173010/39834361-4e83c69e-53ea-11e8-9d6d-d167e5944554.png)

No search results will be shown for wrong keywords or books which are not available.
![screenshot 87](https://user-images.githubusercontent.com/25173010/39834402-745b3b68-53ea-11e8-81dd-37263e38644c.png)

Only works when internet connection is available, for better user freindly a text will be updated on screen when no connection of internet is established.

![screenshot 88](https://user-images.githubusercontent.com/25173010/39834530-e6e5973c-53ea-11e8-9dc2-7e7370f3765f.png)

In this project a user should be able to enter a keyword, press the search button, and recieve a list of published books which relate to that keyword. To achieve this, you'll make use of the Google Books API. This is a well-maintained API which returns information in a JSON format.
