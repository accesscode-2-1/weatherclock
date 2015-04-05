# Weather Clock

## Overview

Your team will build a program that displays a visual clock with ASCII art.  More than a simple clock, the display will show lots of information about the current time, date, and weather.  

Here's a simplified example of what the display might look like.  

```
                                             ooooooo                                       
  **    ****         ***     ***           ooooooooooo                      APRIL 2015     
 ***   ******       *****   *****           ooooooooo    Light rain                        
****  **   **  **  **   ** **   **          \\  \  \ \\                Su Mo Tu We Th Fr Sa
  **      **   **  **   ** **   **            \  \ \\                            1  2  3  4
  **    ***        **   ** **   **                                      5  6  7  8  9 10 11
  **   **      **  **   ** **   **         Temperature: 64° F          12 13 14 15 16 17 18
  **  *******  **   *****   *****          Pressure:    30.1 inHg      19 20 21 22 23 24 25
  **  *******        ***     ***   PM      Humidity:    62%            26 27 28 29 30      
                                                                                           
        Friday, 2015 April 3               Sunrise:  6:04 AM                               
                                           Sunset:   7:04 PM                               
         [[ GOOD FRIDAY ]]                 DST is in effect.                               
                                                                                           
```

Your display will be more elborate, will show more information, and employ color and animation.


## Instructions

Each team should fork this repository, as always.

Before attempting this project, please:

1. Read and understand these directions in full.
2. Read the `Main` class and familiarize yourself with its contents.  This will be the starting point for your code.
3. Browse the other classes that are provided, and read the Javadoc comments that explain how to use each method.
4. Figure out what your project will look like.  Divide up the work and figure out how you will organize your code.

Please email Nathan and Alex Qin the link to your team's github repo.  The course team will evaluate your project based on the contents of the repo.  Each team will also demo its project in front of the whole group.

As always, the instructors and TAs are here to help you if you get stuck.  In general, please post your questions to one of the #channels instead of @direct messages so that other students can help you and benefit from the answers.

_Have fun!_



## Features

First, your weather clock should display **all** of the following information for New York City:

- The time, in large characters.  Use a 12-hour format, and indicate whether it's AM or PM.

- The day of week and full date.

- If today is a holiday, the holiday name.

- The monthly calendar, with today's date indiated.

- Today's sunrise and sunset time.

- Whether or not DST is in effect today.

- The current weather (sun, clouds, rain, _etc._) as a picture.

- The current weather statistics:
  - temperature in Farenheit
  - pressure in inches of mercury ("inHg")
  - humidity in percent

Second, your team should choose **three** of the following features, and implement them.

- Add an indicator for seconds.  Use your imagination for how to show this in a useful and attractive way.

- Show the wind speed and direction grapically.

- Show a graphical respresentation of whether it's day or night.

- Ask the user to select (at startup) the city to show, instead of New York City.

- Ask the user to select (at startup) Farenheit or Celcius for temperature.

- Ask the user to select (at startup) whether to use a 24-hour time format (_e.g._ 19:04 instead of 7:04 PM) for all times.

- Ask the user to select (at startup) an alarm time, and display a dramatic visual alarm at that time.

This is an open-ended project, and these requirements are not specified in detail.  Where the requirements aren't clear, it's up to you to decide what to implement to produce the best possible product.

The **Challenges** section at the end of this document gives ideas for more advanced features, which your team may choose to implement as well.


## Criteria

Your team's project will be evaluated on these criteria:

1. **Functionality:** Does the program run correctly and produce correct results?

2. **Display:** Does the program produce a neat and attractive display, making good use of colors and animation?  Does it run correctly regardless of the terminal size?  A good display requires both artisitic creativity and careful implementation.

3. **Code quality:** Is your code neat and easy to understand?  Have you included code comments that explain each section?

All code that you write for this project should be reviewed by at least one other team member.


## Code

We have provided for you the libraries you need to solve this problem.  The individual components are explained below.

As in previous assignments, the classes we provide for you contain comments for each method that describe how to use the method.  Make sure you read and understand these.  (Methods marked `private` are internal to the class; you do not have to understand how these work, if you don't wish to)

The `Main` class contains sample code to get you started.  You should add your logic to this class, but feel free to add additional classes to organize your code.

As with the signboard project, your program must be run from the terminal to display correctly.  A `run` script is provided to do this for you; invoke it as `./run` from the terminal after changing directory to the top of your project.


## Technology

You'll need a number of components to build this project, most of which you have already encountered.


### Display

To draw the display, you will use code from the _singboard_ project.  Rather than using the `SignBoard` class itself, you will use the underlying `AnsiTerminal` class, which provides a more flexible interface for drawing.

Your program should create one `AnsiTerminal` instance, and call its methods throughout to draw on the screen.  Unlike the `SignBoard`, the screen is not cleared unless you request it by calling the `clear()` method.

The `Main` class includes some simple code that demonstrates how to use `AnsiTerminal` to draw a very simple clock that updates once a second.

Feel free to use unusual symbols from the [Unicode code charts](http://www.unicode.org/charts/) to enahnce your display, but make sure the symbols work correctly in your terminal.  Not every font can display each symbol.    


### Date and time

For date calculations, use code from the _calendar_ group project.  Reference solutions have been included in this repository, but you may use your own.

The `DateTools` class is now called `DateTime`.  We've added some additonal methods for formatting a `Calendar` object as a time or as a and "datetime" (a combination of date and time), which you may find useful.  We've also added a `pause()` method that halts program execution for a while; use this for animating displays.

**Important:** `DST.getDSTDates()` and `Holidays.getHolidays()` both expect to find their respective input files in the current directory, which is in the top of the project.  You will have to be in this directory when you run your program for these methods to work correctly.


### JSON parsing

You'll use a JSON library [json-simple](https://code.google.com/p/json-simple/) to parse / decode the weather data (see below).  To understand how to use this, see [this example](https://code.google.com/p/json-simple/wiki/DecodingExamples) and the [API documentation](http://alex-public-doc.s3.amazonaws.com/json_simple-1.1/index.html).

Note the following when parsing JSON:

- The JSON document doesn't specify types.  You'll have to cast objects appropriately.  For example, suppose you have a JSON object that contains a key "data".  If you know that the value should be a long value, use,

  ```java
  Long value = (Long) obj.get("data");
  ```
  
  whereas if you know it should be another JSON object, use,
  
  ```java
  JSONObject value = (JSONObject) obj.get("data");
  ```
  
- _Always_ check whether the result of `get()` is `null`.  For this reason, it's best not to unbox `long` and other types directly.

  ```java
  Long valueObj = (Long) obj.get("data");
  if (valueObj != null) {
      long value = valueObj;
      // ...
  }
  ```

This library's JAR file is included in this repo and set up int the IntelliJ project.


### Weather data

You will obtain weather data from the [OpenWeatherMap API](http://openweathermap.org/api).  This is a simple API in which you request current weather data with a single HTTP GET request. The response is a JSON document, which contains current weather data and other useful information.

**Important:** Do not try to call this API very rapidly, for instance every second.  It will slow down your program unacceptably.

For example, this URI:

    http://api.openweathermap.org/data/2.5/weather?q=New%20York,NY
    
returns this JSON document containing current New York weather:

```
{
 "clouds": {
  "all": 92
 }, 
 "name": "New York", 
 "coord": {
  "lat": 40.71, 
  "lon": -74.01
 }, 
 "sys": {
  "country": "United States of America", 
  "message": 1.2209, 
  "sunset": 1428103377, 
  "sunrise": 1428057325
 }, 
 "weather": [
  {
   "main": "Rain", 
   "id": 500, 
   "icon": "10d", 
   "description": "light rain"
  }
 ], 
 "rain": {
  "3h": 1.525
 }, 
 "base": "stations", 
 "dt": 1428094206, 
 "main": {
  "temp": 288.215, 
  "grnd_level": 1018.43, 
  "temp_max": 288.215, 
  "sea_level": 1022.14, 
  "humidity": 90, 
  "pressure": 1018.43, 
  "temp_min": 288.215
 }, 
 "id": 5128581, 
 "wind": {
  "speed": 4.31, 
  "deg": 208.003
 }, 
 "cod": 200
}
```

In order to use this, you will,

1. Construct the URL that requests the data you want.
2. Use `HTTP.get()` to retrieve the JSON document.
3. Use `JSONValue.parse()` to parse the document to a JSON object.
4. Use the `get()` method to extract the component you need.

You may have to convert units as well, for instance temperatures.  The units used in the JSON document are explained [in the weather documentation](http://openweathermap.org/weather-data#current).  The JSON document specifies times as "UNIX timestamps"; we've provided a method `DateTime.fromTimestamp()` to convert this to a calendar.  Use Google to find forumlas for converting from kelvins to degrees Farenheit or Celcius, and from hPa to inches of mercury.


## Challenges

If your team completes the above, choose one or more features from the following list.

- Draw an analog clock (round with hour, minute, second hands or indicators).

- Show the upcoming weather forecast for the next five days.  OpenWeatherMaps provides a [forecast API](http://openweathermap.org/forecast) that provides this information.

- Show an inspirational quote or joke randomly chosen from lines in a text file.  You should provide the text file.

- Show the phase of the moon.  The method for calculating this is quite complicated, but there are a number of Java implementations on the web that you can use, including [this one](https://code.google.com/p/moonphase/source/browse/trunk/Moon/MoonPhase.java?r=12).
  

