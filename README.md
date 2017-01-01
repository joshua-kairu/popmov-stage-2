# Popular Movies: Stage One

## Introduction :point_up:

This is a repository for Stage One of the Popular Movies assignment found in [Udacity]'s [Android Nanodegree] course. 

The Android app made here fetches movie data from [The Movie Database website] and displays it. 

## How To Use :wrench:

:zero: Build and run. :smile:<br/>
:one: Select a movie poster to see the movie's details. Below is a video to that effect. Please note that to move from the details to the posters a *Back* button press is needed. This is not shown in the video. :grin:<br/>

![Movie poster to movie details](screen-records/default-behavior.gif) 

:two: In settings, choose between seeing movie posters based on the movies' popularity or user rating. By default, movies are sorted based on how popular they are. (Very subjective, right? :grin:) Below is a video of how to change settings. In it, settings are changed from **Most Popular** to **Highest Rated**. Here too the *Back* button to shift from Settings to movie posters is not shown. (Maybe I should have pressed *Up*.)<br/>

![Changing settings](screen-records/settings-change.gif) 

## How It Works

Here are the steps:

1. Fetch movie JSON from [The Movie Database]()(or TMDB) using an [AsyncTask] based on the user's preference as stored in settings

2. Display movie data using posters from TMDB.

3. For the transition between the various posters to a specific movie detail, pass the movie data as a [Parcelable].

## Abilities :muscle:

* Fetches movie JSON.
* Sorts movies based on user choice.

## Limitations :worried:

* Posters do not scroll to where the user was when the user comes back from details. This can be seen in the first video.
* I haven't tested it yet but I think this app might not look very good on wide screens. :grin:

## Possible Future Work :fast_forward:

- [ ] Posters should know where the user was!

- [ ] Add a favorites section for the user's favorite flicks.

- [ ] TMDB provides trailers. The user might want to watch (some of) them.

## Other things :books:

* For this app to run, one needs to have an API key from TMDB. To get one:
	1. Register with TMBD [here] and get the API key. API keys for non-commerical use are free as of when I got mine.
	2. For Linux users, please do the following:
		1. Open ~/.gradle/gradle.properties. (Create a new file if there is no such file there.)
		2. In the file add a line containing the name of the API key you want to store followed by an `=` and then the key itself. The name will be the way you will refer to the API key in your gradle code. An example line is:

			`MyAwesomeAPIKey=TH1sIsanAWESOMEAP1K3y`
		
		3. Go to your Android Studio code.
		4. Go the module's `build.gradle` file (for example `app/build.gradle`).
		5. type the following inside the `android{...} section`.
			```
			buildTypes.each {
        			it.buildConfigField 'String', 'REFERENCE_TO_API_KEY_IN_CODE', NameOfAPIKey
			}
			```
		    for example:	
			```
			buildTypes.each {
        			it.buildConfigField 'String', 'AWESOME_API_KEY_IN_CODE', MyAwesomeAPIKey
			}
			```
		6. In Android code, reference the key using:
			`BuildConfig.REFERENCE_TO_API_KEY_IN_CODE`
		    for example:
			`BuildConfig.AWESOME_API_KEY_IN_CODE`

* A lot of gratitude to TMBD for the Movie API.

[image of TMBD]

## License :lock_with_ink_pen:

This repository is licensed under the [GNU General Public License Version 3](http://www.gnu.org/licenses/gpl-3.0.en.html).