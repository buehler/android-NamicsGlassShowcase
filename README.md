Google Glass Showcase Apps by Namics AG
=======================================

### Why

We became owners of a Glass :)

### What

We decided to implement some basic hello world applications and share our know-how about the cases we thought about. Here on GitHub I'll share some learnings and thoughts about the technical aspects of Glass Development.

### Prerequisities

The following things are required for google glass development:
- Android capable IDE (Eclipse, IntelliJ). My personal favourite: Android Studio (Beta). Actually, the demo code is implemented with Android Studio.
- Android SDK (API 15; Android 4.0.3)
- Google Glass GDK (downloadable over Android SDK Manager under API 15)
- A fairly good knowledge about Android development
- Basic project setup (new project with android sdk linked to project, git, whatever...)

### Showcases

#### NamicsLiveWordmark

This was the first approach to develop an application for the google glass. It's basically a hello world app which does nothing more than downloading a Namics AG wordmark and showing these on a live card. These wordmarks can be downloaded over http://gdw.namics.com and are fetched via backgroundtask. As the mark is fetched, the backgroundtask starts callback which receives the wordmark and updates the UI on the main thread. My personal approach was to start a service over a glass voice trigger and then update the livetile as long as the service runs. If you want to stop the service, you can tap the glass and open the menu on the livetile. There is a stop menu item which disables the live service and hides the card.

```java
uiHandler.post(new Runnable() {
                                
    @Override
    public void run() {
        remoteViews.setProgressBar(R.id.wordmark_showtime, 10, updateCounter, false);
        remoteViews.setTextViewText(R.id.wordmark_text, mark);
        liveCard.setViews(remoteViews); 
    }

});
```
The uihandler represents a looper object, which updates the views and runs in the main thread.

#### ColorRecognition

This is actually an android phone project. It's a little "proof of concept" to use a camera preview and recognize the color below the crosshair. The most interessting thing about this showcase is the "CameraPreview.java" class, which extends View and handles the preview of the android camera.

#### NespressoCapsuleInformation



### Learnings

### Summary

### Questions?

Ask me :)
Over GitHub or [christoph.buehler(at)namics.com](mailto:christoph.buehler@namics.com)