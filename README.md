# Celestial Photos App

This README contains information about our final project, the requirements we were able to complete, and our  presentation slides.

## Requirements Completed

Below is a list of the requirements we completed for the project throughout the semester:

1. As a user, I would like to receive notifications for the astronomy picture of the day (APOD API) - Cody Price
2. As a user, I would like to put in my location and/or date and get a photo (EARTH API) - Emily Vogel
3. As a user, I would like to be able to save my favorite NASA photos of the day (SQLite) - Cody Price
4. As a user, I would like to be able to see a list of my favorite NASA photos of the day (SQLite) - Cody Price
5. As a user, I would like to be able to see how many days in a row I’ve used the app (SQLite) - Emily Vogel
6. As a user, I would like to be able to see astronomy notifications on app startup (DONKI API) - Emily Vogel
7. As a user, I would like to be able to see a current photo of the Earth (EPIC API)\* - Eniola Akinola
8. As a user, I would like to be able to search for videos and images (NASA Image and Video Library API) - Eniola Akinola

\* For some reason the git history is completely different for story #7 and has not been merged into master.  You can see this functionality on the [epic branch](https://bitbucket.org/evogel1999/celestial-photos-app/src/epic/).

## Requirements Uncompleted

Below is a list of the requirements that we were not able to get to during the semester:

1. As a user, I would like to see a statistic on how many notifications I’ve received (SQLite)
2. As a user, I would like to see statistics on the total number of photos and videos I view (SQLite)

## Presentation

Slides for the final presentation are included in this repo but if you would like the google slides link you can find it [here](https://docs.google.com/presentation/d/1Ehb7Q5dc0CFdxWhqV9epb1qVGMwyKu9ZloBu9rhFud4/edit?usp=sharing).  The slides can also be found locally in this repository under the 'slides' folder at the root of this repository.

## Testing

We opted to mainly do non-instrumental tests (i.e. non-UI tests).

## Project Structure

The project is split into five folders, all serving a different purpose.  The project mainly uses fragments and fragment navigation to navigate between different parts of the app.

### ui

The UI related fragments are located in the UI folder.  Each fragment has a subfolder which contains the fragment(s) involved and any recycler adapters needed to display a list.

### service

Services like notifications and other background jobs are located here.

### networking

Anything relating to networks and API calls are done here.  They generally follow the IService interface, which helps structure API requests.

Please note that the API Key property in the requests is needed to increase the limit we can access the API.  If you use the DEMO_KEY, you have the following API call rate limit:

>In documentation examples, the special DEMO_KEY api key is used. This API key can be used for initially exploring APIs prior to signing up, but it has much lower rate limits, so you’re encouraged to signup for your own API key if you plan to use the API (signup is quick and easy). The rate limits for the DEMO_KEY are:
>* Hourly Limit: 30 requests per IP address per hour
>* Daily Limit: 50 requests per IP address per day

The API key present (not the DEMO_KEY value) increases the rate limit to 1,000 requests per hour.  More about this and the APIs used can be found on the [NASA APIs](https://api.nasa.gov/) website.

There are also helper methods located under 'helper'.

### model

This is where all of the data classes and entities are located.

### database

Anything related to SQLite databases and repositories are located here.