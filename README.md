WebWrapp
========
WebView wrapper library to manage a local copy of the contents of a web app and keep it updated.

STATUS
======
Basic behaviour implemented

DONE
====
- Configuration based
- Management of WebView setup
- Automatic warmup task with listener

ONGOING
=======
- Migrating project to library

TODO
====
- Rename project and migrate github to `webwrapp`
- Add tests :)
- Allow more control over warming up procedure
- Review README.md and add more documentation
- Deploy to Maven Central
- Consider removing dagger dependency
- Consider multiple listeners to be specified

HOW TO
======
- Add dependency to `net.nebur.webwrapp`
- Inject (or instantiate) main `WebWrapp` object
- Implement `WebWrappListener` and wait `onReady(String url)` method to be called
- When `onReady` is called, load the received url in your `WebView`

HELP
====
- Trouble importing project into Android Studio?
  - Open main `settings.gradle` file in the root of the project.
  - Remove the line `project(":app").name = "webwrapp"`
  - Now try to import again the project in Android Studio
  - Add the line you just removed again to the `settings.gradle`
