# MoneyPro - Expense Manager [BETA]
      
![Showcase](https://github.com/AndreAle94/moneywallet/raw/master/pictures/showcase.png)

MoneyWallet is an application designed to help you keep track of your expenses. This repository contains the source code of the latest version, completely rewritten from scratch.

# Table of Contents

1. [History](#history)
2. [Build](#build)

## History
I have been working behind this project for a long time, originally born as a tool to learn Android development, it has evolved over time and has recently been completely revised as a university project for the DIMA course at Politecnico di Milano.
As you may have noticed, the previous version on the PlayStore required a small in-app purchase to handle more than just one wallet. The idea was to invest the money earned to pay for my studies, unfortunately among the interest rates of the PlayStore and the local taxes of the individual countries the profit was practically derisory. I did not had the opportunity to continue the development with continuity due to the university and the little free time available. Only recently I had the opportunity to get the project in hand and I decided to make it open source. In this way, anyone who wants can contribute.


## Build
You can compile the application very simply: just clone this repository locally to your computer and Android Studio will take care of the rest.
You have four different options to build it with two choices.
The first choice is which version you want to build:

- proprietary: this build flavor is designed to integrate Google Drive and Dropbox for a better user experience. It contains proprietary libraries (not open source) and requires the inclusion of valid api-keys to use these services. These keys must be registered in the file called gradle.properties in the root folder of the project before compiling.
- floss: this build flavor is designed to contain only open source code and for this reason the integration with Google Drive and Dropbox has been removed.

The second choice is which map provider you want to use and you can choose between:

- gmap: this build flavor uses Google Map as map provider and requires you to provide a valid API-key. This key must be registered in the file called gradle.properties in the root folder of the project before compiling.
- osm: this build flavor uses OpenStreetMap as map provider.

To decide what kind of build to compile, use the appropriate menu of gradle to choose the desired combination of build flavors.
If you want to build only open source code (e.g. for the F-Droid market) you should use 'floss' and 'osm'.

The current release in Google Play Store uses 'proprietary' and 'osm' with some changes to integrate also the Crashlytics framework and the In-App billing library.
