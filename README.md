LocalWebApp
===========
This is an android application that wraps a local webapp into a webview and manages updates of local resources.

STATUS
======
Simple proof of concept done.

DONE
====
- Splashscreen and webview activities and flow.
- Check resources are up to date with remote.
- Download most up to date resources from remote.
- Unzip remote data into internal memory.
- Move some data to configuration files (`webapp.properties`)
- Added dependency injection
- Descriptive info added to splashscreen

ONGOING
=======
- Some code cleaning
- Stabilization

TODO
====
- Custom image for splashscreen
- Cutomization of app name and icon (at least)
- Review basic performance
- Improve UI
- Add tests
- Rename project to something cooler and rewrite description

HOW TO
======
- Download source
- Prepare your remote server
  - Setup an URL that provides last version number (as an integer), e.g., `http://my-url.com/version`
  - Setup an URL to download last version of the webapp (in a zip), e.g., `http://my-url.com/download.zip`
- Create `webapp.properties` file in `assets/` folder (check `webapp-sample.properties`)
- Copy into `assets/html` folder (or subfolder defined in `webapp.properties`) your webapp (initial version will be included in the APK).
- Make sure that your webapp entry point is `index.html`
