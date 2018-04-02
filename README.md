# Pic It!

## Setup

Clone repo and open in Android Studio. 

## Inspiration

Have you ever gathered with a group of people with the intention of going out for a meal, but don't know where to go? Everyone seems to say that they "don't care," making choosing a place to eat almost impossible. Pic It! helps you find a place to eat without the frustration or discussion. 

## What it does

Users begin by having a host of the group create a party, and then the rest of the group simply joins that same party. Then, users are taken to a page of images of food. Simply vote on pictures of food that look good to you, and Pic It! will provide a list of restaurants around you based on the food item that was voted on the most. Alternatively, if your group wants no discussion at all, Pic It! has a switch to simply return just one restaurant based on the food item that was voted on the most. No further discussion necessary after the votes are in. Users can also submit their own images of food to our database and submit a keyword with it for usage in future food polls. 

## How we built it

The project was created in Android Studio since this was an Android app utilizing Firebase for authentication, storage, and realtime database. By using the Foursquare API, we were able to pass it a keyword and the user's location to quickly get back a complete, detailed list of the restaurants nearby fulfilling that query. This was then returned back to the host in a RecyclerView of all of the restaurants.

## Challenges we ran into

With many team members working on the project at the same time, even while working on completely individual tasks merge conflicts occasionally occurred. 

## Accomplishments that we're proud of

Users are able to quickly upload photos to be included in future food item polls and can quickly and efficiently create or join groups to vote together on food items. The host is also able to quickly see the full list of restaurants around them that fulfills the group chosen keyword based on the photo votes. 

## What we learned

We were able to learn how to link the Realtime Database and Storage features of Firebase together for quick loading and storing of images for all clients. We also learned how to work with RecyclerViews and how to link a large Android Studio with many components together. 

## What's next for Pic It!

We plan to use Google Cloud Vision for the user submitted photos to automatically generate the keyword of the food item instead of having the user submit it themselves. 

## Devpost

https://devpost.com/software/pic-it/edit
