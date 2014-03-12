uncc2014watsonsim [![Build Status](https://travis-ci.org/SeanTater/uncc2014watsonsim.png?branch=master)](https://travis-ci.org/SeanTater/uncc2014watsonsim)
======

Deep Question Answering System

## Get started
- `git clone https://github.com/SeanTater/uncc2014watsonsim.git`
- Download and unzip [gradle](http://services.gradle.org/distributions/gradle-1.11-bin.zip) somewhere convenient to call from the command line. (Most Linux distros have a packaged version that may be easier, but may also be out of date.)
- Install Java 7 or newer. Many Macs have only Java 6 by default.
- Install Indri binaries:
  - For Linux 64, pick the right library in uncc2012watsonsim/lib and copy it to libindri-jni.so
  - For Windows 64, do the same but end it in .dll
  - For others, or if the above doesn't work, compile indri on your own and copy `libindri-jni.so` or `libindri-jni.dll` to uncc2014watsonsim/lib.

- Setup UserSpecificConstants (in src/main/java)
  - Make a copy of the file without .sample at the end of the filename
  - Make your own Google cloud app in the [Google console](https://cloud.google.com/console).
    - Put the name into the source.
    - Enable the Custom Search API
    - Create a server Public API Key, put it in the source.
  - Make your own custom search engine in the [Custom Search Console](https://www.google.com/cse/create/new)
    - Search any site (but you have to pick a domain, maybe wikipedia.org would be good)
    - Edit the custom search you just made. In "Sites to search", change "Search only included sites" to "Search the entire web but emphasize included sites"
    - Get the search engine ID, put it in the source.
- Have Gradle setup gobs of other stuff
  - `/where/you/unzipped/gradle/bin/gradle cleanEclipse eclipse assemble`

## Running Queries
- To run homework Google queries
  - You do not need a indri or lucene index (yet).
  - You do need an internet connection.
  - Open and run src/java/GenerateSearchResultDataset.java with your favorite IDE
    - Eclipse classpaths are setup by default so it may be easier
- If it's not working, submit an issue.

## Start developing

- Make sure you are in the branch you want. Use (or google) `git branch` and `git checkout`
- `git pull` to get the latest code _before_ writing any code.
- Consider making a branch before making major changes (it's tougher to move the changes later)
- Get comfortable with gradle. As a 5-second tour:
  - `gradle assemble` -> update dependencies
  - `gradle test` -> run tests
  - `gradle run` -> run watsonsim (it will ask you for questions, give you results)
  - Configuration is in build.gradle
- Write code and [documentation](http://seantater.github.io/uncc2014watsonsim/)!
- [Ask to be added as a contributor](mailto:stgallag@gmail.com) or if your code is small, send a patch
- Repeat

## Troubleshoot
- Can't find libindri-jni? Make sure you enabled Java and SWIG and had the right dependencies when compiling Indri.

## Architecture {stub}
Testing setup:
- A large database of questions is run against predefined search engines.
- The results are recorded as a large JSON file, saved, and later reopened.
- The results are rescored (by an average or using hand built Logistic Regression)
- The top result becomes the candidate answer, and statistics are generated

Classes:
- Question: Holds ResultSet's, collates similar results together (using Levenshtein distance)
- ResultSet: Holds one candidate answer text (as title), and 1+ Engines.
- Engine: Represents one search result, has a rank, a score, and an engine name.

### Tools

- [Check to see if your commit broke the code](https://travis-ci.org/SeanTater/uncc2014watsonsim)
- [Examine the reference documentation](http://seantater.github.io/uncc2014watsonsim/)
- [Find out how much better your code works than the last commit](http://watsonsim.herokuapp.com/runs)
