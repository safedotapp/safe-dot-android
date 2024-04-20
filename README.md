<p align="center">
 <img width="100px" src="https://github.com/kamaravichow/safe-dot-android/blob/master/fastlane/metadata/android/en-US/images/icon.png?raw=true" align="center" alt="Safe Dot" />
 <h2 align="center">Safe Dot</h2>
 <p align="center">iOS 14 Privacy Indicators for Android</p>
</p>

<p align="center">
    <a href="https://github.com/kamaravichow/safe-dot-android/actions">
      <img alt="Tests Passing" src="https://github.com/kamaravichow/safe-dot-android/workflows/Android%20CI/badge.svg" />
    </a>
    <a href="https://github.com/kamaravichow/safe-dot-android/releases">
      <img src="https://img.shields.io/github/downloads/kamaravichow/safe-dot-android/total.svg" />
    </a>
    <a href="https://github.com/kamaravichow/safe-dot-android/issues">
      <img alt="Issues" src="https://img.shields.io/github/issues/kamaravichow/safe-dot-android?color=0088ff" />
    </a>
    <a href="https://github.com/kamaravichow/safe-dot-android/pulls">
      <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/kamaravichow/safe-dot-android?color=0088ff" />
    </a>
    <br />
    <br />
    <a href="https://play.google.com/store/apps/details?id=com.aravi.dot">
      <img src="https://img.shields.io/badge/Play%20Store-5K-blueviolet?style=for-the-badge"/>
    </a>
    <a href="https://galaxy.store/dotsafe">
      <img src="https://img.shields.io/badge/Galaxy%20Store-1.2K-ff69b4?style=for-the-badge"/>
    </a>
    
  </p>

  <a href="https://polar.sh/kamaravichow/subscriptions"><picture><source media="(prefers-color-scheme: dark)" srcset="https://polar.sh/embed/tiers.svg?org=kamaravichow&darkmode"><img alt="Subscription Tiers on Polar" src="https://polar.sh/embed/tiers.svg?org=kamaravichow"></picture></a>
 

## README
When any permission is in use, my app checks for the app which is currently running in the foreground. 

In your case, I think some other app is trying to access your sensors when another app is running in foreground(which doesn't have any permissions) which makes my app think that the app in the foreground is using those sensors.

So don't completely depend on the access logs, as for now we cannot get the information about what app is exactly using your sensors.

Feel free to ask if you didn't understand in [discussions](https://github.com/kamaravichow/safe-dot-android/discussions). Thanks for using my app.

## Download Now
App is currently available for Production & Open Beta Testing on Google Play Store & Samsung's Galaxy Store. Use the below links to get redirected to the app page.

|[<img src="https://github.com/kamaravichow/safe-dot-android/raw/master/docs/google-play-badge.png" width="200">](https://play.google.com/store/apps/details?id=com.aravi.dot)| [<img src="https://github.com/kamaravichow/safe-dot-android/raw/master/docs/f-droid-badge.png" width="200">](https://f-droid.org/packages/com.aravi.dot)| [<img src="https://github.com/kamaravichow/safe-dot-android/raw/master/docs/galaxy-store-badge.png" width="200">](https://galaxystore.samsung.com/detail/com.aravi.dot)| [<img src="https://oculavis.de/img/apk_badge.png" width="200">](https://github.com/kamaravichow/safe-dot-android/releases/download/v3.1.0/app-v3.1.0.apk) |
|---|---|---|---|


## Stack :
- Minimum SDK 21
- MVVM Architecture (View - ViewModel - Model)
- View Binding
- Room Database
- Accessibility Service

## Libraries Used

- **Circle Image View** [View Source](https://github.com/hdodenhof/CircleImageView)
- ~~Gson Google [View Source](https://github.com/google/gson)~~

## Issue Tracking

Use the issues tab to report issues and request features 
[Go to Issues](https://github.com/kamaravichow/safe-dot-android/issues)

## Contributing

This project was built on Android Studio 4.0.

```
git clone https://github.com/kamaravichow/safe-dot-android.git
```

or just fork the repository.

[CONTRIBUTING.MD](https://github.com/kamaravichow/safe-dot-android/blob/master/CONTRIBUTING.md)

## Screenshots 
|![Screenshot1](https://github.com/kamaravichow/safe-dot-android/raw/master/docs/screenshot1.png)|![Screenshot2](https://github.com/kamaravichow/safe-dot-android/raw/master/docs/screenshot2.png)|![Screenshot2](https://github.com/kamaravichow/safe-dot-android/raw/master/docs/screenshot3.png)|
|---|---|---|


## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=kamaravichow/safe-dot-android&type=Date)](https://star-history.com/#kamaravichow/safe-dot-android&Date)


## Licence

```
Copyright (C) 2020-2022  Aravind Chowdary (@kamaravichow)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

```
[FULL LICENCE](https://github.com/kamaravichow/safe-dot-android/blob/master/LICENSE)
