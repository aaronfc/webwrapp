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
- Rename project and migrate github to `webwrapp`

ONGOING
=======
- Migrating project to library

TODO
====
- Review WebWrapp interface
  - `WebWrapp.getContext` may be unused
  - `Activity` argument in `WebWrapp.setupWebView` method is unused
- Add tests :)
- Allow more control over warming up procedure
- Review README.md and add more documentation
- Deploy to Maven Central
- Consider removing dagger dependency
- Consider multiple listeners to be specified

HOW TO
======
- Add dependency to `net.nebur.webwrapp`
- Inject (or instantiate) main `WebWrapp` object passing as an argument an implementation of `WebWrappListener`
- Wait for `onReady(String url)` method from your `WebWrappListener` implementation to be called
- Setup at some point your `WebView` with the method `WebWrapp.setupWebView(WebView wv)`
- When `onReady` is called, load the received url in your `WebView`

FAQ
===
No questions, yet

