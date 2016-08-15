<img src="./spectaculum-logo.png" alt="Spectaculum Logo"/>

Spectaculum
===========

The MediaPlayer-Extended library (f.k.a. ITEC MediaPlayer) is a media player library for Android supporting
exact seeking to frames, playback speed adjustment, shader support, zooming & panning, frame extraction
and a lot of media source protocols and formats, including DASH. It strives to be an API-compatible
direct replacement for the Android `MediaPlayer` and `VideoView` components and builds upon the Android
`MediaExtractor` and `MediaCodec` API components. It is very lightweight, easy to use, makes native
code / NDK fiddling unnecessary, and works from Android 4.1 up.

A [demo](https://play.google.com/store/apps/details?id=at.aau.itec.android.mediaplayerdemo) is available on the Google Play Store.


Features
--------

 * Direct replacement for Android components
 * Frame-exact seeking
 * Playback speed adjustment
 * GLES shader support
 * Picture zooming/panning support
 * Frame extraction
 * Local files and network sources
 * Supports all Android [network protocols and media formats](http://developer.android.com/guide/appendix/media-formats.html)
 * DASH support
 * Lightweight (all components total to ~100kB)


Changelog
---------

* v3.1.0: add seek modes from Android's MediaPlayer, bugfixes in the DASH MPD parser
* __v3.0.0__: Library renamed from ITEC MediaPlayer to MediaPlayer-Extended
 * Package renamed from `at.aau.itec.android.mediaplayer` to `net.protyposis.android.spectaculum.mediaplayer`
 * This version is functionally equivalent to v2.2.3. It is given a new major version because the changed
   package name might be considered an incompatible API change, which requires a new major version
   according to [SemVer](http://semver.org/spec/v2.0.0.html).
* v2.2.3: fix playback speed change during pause
* v2.2.2: correctly display rotated video and report rotated dimensions (on API21+, fixes issue #6)
* v2.2.1: play back all remaining buffered audio at the end of stream
* __v2.2.0__: playback performance improvements (less CPU load), audio-only playback support, bugfixes
* v2.1.0: add setVolume() to MediaPlayer, update Grade dependencies, update project files to Android Studio 2.0
* __v2.0.0__: API changed, improved Android API compatibility, improved decoder
  * smoother playback with less CPU time
  * add prepare()/prepareAsync() to MediaPlayer (similar to Android MediaPlayer, breaking change from v1, needs to be called after setting the datasource)
  * first frame not rendered automatically anymore (similar to Android MediaPlayer)
  * add looping functionality (setLooping/isLooping) to MediaPlayer (similar to Android MediaPlayer)
* v1.4.3: bugfixes for re-setting a data source on a video view, OnSeekListener called from wrong thread, and infinite loop when seeking from end of stream (fixes GitHub issues #8 and #9)
* v1.4.2: add stopPlayback() and seek mode getters/setters to (GL)VideoView
* v1.4.1: hotfix for exact seeking in segmented DASH streams
* __v1.4.0__: DASH MPD parser improved and compatibility enhanced, MediaPlayer API improvements and minor changes, error reporting improved, increased responsivity of demo app
* v1.3.3: zooming fixed
* v1.3.2: compile and target SDK updated to 22, added to JCenter repository
* v1.3.1: support for separate audio and video sources added, lint error fix
* __v1.3__: DASH playback / representation switching greatly improved (no more screen resizing, skipped frames, and video artefacts, better segment caching), external dependencies updated, various other improvements
* v1.2.4: demo app enhanced with option to type/paste url and Crashlytics exception reporting
* v1.2.3: device compatibility improved, contrast/brightness adjustment filter added
* v1.2.2: hotfix for display aspect ratio
* v1.2.1: hotfix for video decoder crash
* __v1.2__: audio playback support, improved DASH rate based adaption, support for DASH non-square pixel aspect ratios, keep screen on during playback
* v1.0.1: do not catch up lost time after a lag, error handling for invalid URLs improved
* __v1.0__: initial release


Requirements
------------

 * Android API 16+ (Android 4.1 Jelly Bean)
 * optional: Adreno GPU
 * optional: OpenGL ES 3.0


Usage
-----

Usage is very simple because the library's aim is to be API-compatible with the default Android classes.
The `MediaPlayer` in this library is the equivalent of Android's `MediaPlayer`, the `VideoView`
and `GLVideoView` are equivalents of Android's `VideoView`.

To migrate from the Android default classes to this library, just replace the imports in your Java headers. If
there are any methods missing, fill free to open an issue on the issue tracker or submit a pull request.

### API ###

This are the most important additions to Android's default components:

| Method                       | MediaPlayer | VideoView | GLVideoView | GLCameraView | Description |
| ---------------------------- |:-----------:|:---------:|:-----------:|:------------:| ----------- |
| `setDataSource(MediaSource)` | X           | X (setVideoSource) | X  |              | Sets a `MediaSource` (e.g. `UriSource`, `FileSource`, `DashSource`), see description below. |
| `setSeekMode(SeekMode)`      | X           | X         | X           |              | Sets the seek mode (e.g. FAST, EXACT, ..., see the `SeekMode` enum). Default mode is EXACT. |
| `getSeekMode()`              | X           | X         | X           |              | Gets the current seek mode. |
| `setPlaybackSpeed(float)`    | X           | X         | X           |              | Sets the playback speed factor (e.g. 1.0 is normal speed, 0.5 is half speed, 2.0 is double speed). Audio pitch changes with the speed factor. |
| `getPlaybackSpeed()`         | X           | X         | X           |              | Gets the current playback speed factor. |
| `setZoom(float)`             |             |           | X           | X            | Sets the zoom factor into the picture (1.0 is full screen, 2.0 is 200% magnification, etc.). |
| `getZoomLevel()`             |             |           | X           | X            | Gets the current zoom factor. |
| `setPan(float, float)`       |             |           | X           | X            | Sets the panning of the zoomed picture into X and Y directions (0, 0 means center). |
| `getPanX()`                  |             |           | X           | X            | Gets the current X panning. |
| `getPanY()`                  |             |           | X           | X            | Gets the current Y panning. |
| `addEffect(Effect)`          |             |           | X           | X            | Adds a GLES shader effect that implements the `Effect` interface and usually extends the abstract `ShaderEffect` class. See the demo app for an example on how to use and parameterize effects. Add and select the `NoEffect` effect to disable an active effect. |
| `selectEffect(int)`          |             |           | X           | X            | Selects/activates a previously added GLES effect by its index (in the order of effect addition). |
| `captureFrame()`             |             |           | X           | X            | Requests the video frame to be captured, which will be returned as Bitmap through the `setOnFrameCapturedCallback(OnFrameCapturedCallback)` callback. |
| `supportsCameraSwitch()`     |             |           |             | X            | Checks if the device has multiple cameras. |
| `switchCamera()`             |             |           |             | X            | Switches to the next camera, usually front to back and back to front. |


### MediaSource ###

This library adds additional methods to set the playback source, namely
`mediaPlayer.setDataSource(MediaSource source)` and `videoView.setVideoSource(MediaSource source)`.
These methods expect an instance of an object implementing the `MediaSource` interface. A few common
implementations are provided:

 * `UriSource` expects an URI to a file or online resource, and also accepts two URIs for separate
   audio and video resources.
 * `FileSource` expects a `File` instance (e.g. `new File(String pathToFile)`), or two instances to
   separate audio and video files.
 * `DashSource` (in the MediaPlayer-DASH module) expects an URI to an MPD file/resource, or a custom
   built or parsed `MPD` object instance, and an object implementing `AdaptationLogic` (e.g. one of
   the provided `ConstantPropertyBasedLogic` or `SimpleRateBasedAdaptationLogic`).

Additional media sources can be built by implementing the `MediaSource` interface. The advantage of this
interface is that it provides a way to implement data sources above the file level, and that it can
provide and sync multiple separate media sources to the same media player.

### Gradle ###

To use this library in your own project, you can either (1) fetch the modules from the
JCenter central Maven repository, or checkout the Git repository and (2) install the modules to
your local Maven repository or (3) include the required gradle modules directly.

#### JCenter repository ####

The [JCenter](https://bintray.com/bintray/jcenter) Maven repository contains release builds of the
library, usage is similar to any other Maven dependency:

    repositories {
        ...
        jcenter()
    }

    dependencies {
        ...
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer:3.1.0'
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer-dash:3.1.0'
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer-gles:3.1.0'
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer-gles-flowabs:3.1.0'
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer-gles-qrmarker:3.1.0'
    }

#### Local Maven repository ####

Run `gradlew publishMavenPublicationToMavenLocal` to compile and install the modules to your
local Maven repository and add one or more of the following dependencies:

    repositories {
        ...
        mavenLocal()
    }

    dependencies {
        ...
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer:3.1.0-SNAPSHOT'
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer-dash:3.1.0-SNAPSHOT'
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer-gles:3.1.0-SNAPSHOT'
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer-gles-flowabs:3.1.0-SNAPSHOT'
        compile 'net.protyposis.android.spectaculum.mediaplayer:mediaplayer-gles-qrmarker:3.1.0-SNAPSHOT'
    }


### Components ###

The library is split into several logical components, comprising the base MediaPlayer and additional optional
components that extend the functionality of the base.

#### MediaPlayer ####

The base component provides the `MediaPlayer`, which can be used as a replacement for the Android
[MediaPlayer](http://developer.android.com/reference/android/media/MediaPlayer.html), and the `VideoView`,
which can be used as a replacement for the Android [VideoView](http://developer.android.com/reference/android/widget/VideoView.html).
To load a video, use either the compatibility methods known from the Android API to specify a file or URI, or supply a `UriSource`.

#### MediaPlayer-DASH ####

Extends the MediaPlayer base with DASH support. To play a DASH stream, supply the MediaPlayer or VideoView a
`DashSource` with the URI of the MPD file or an externally parsed/constructed `MPD` object, and an
`AdaptationLogic`. MPDs can be externally parsed with the `DashParser`. This component comes with
two basic AdaptationLogic implementations, `ConstantPropertyBasedLogic` which selects a specified
constant representation mode, and `SimpleRateBasedAdaptationLogic`, which monitors the bandwidth and
tries to choose the best representation accordingly. It supports MP4, fragmented MP4 and WebM
containers, with both file and byte-range requests. The DASH support does not cover the full standard,
but many common use cases.

MediaPlayer-DASH has external dependencies on [OkHttp](https://github.com/square/okhttp),
[Okio](https://github.com/square/okio), and [ISO Parser](https://github.com/sannies/mp4parser).

#### MediaPlayer-GLES ####

Extends the MediaPlayer base with a GLES surface and GLSL shader support. It provides the `GLVideoView`,
a VideoView with a GL surface and a simple interface for custom shader effects. Effects implement
the `Effect` interface and can be dynamically parameterized. It also provides the `GLCameraView`,
which is a camera preview widget with effect support. It comes with a few simple effects, e.g.
a sobel edge detector, a simple toon effect and some 9x9 kernel effects. The GLES views can be zoomed
and panned with the typical touchscreen gestures.

#### MediaPlayer-GLES-FlowAbs ####

This module adds the [FlowAbs](https://code.google.com/p/flowabs/) shader effect to the GLES component
and demonstrates the possibility to construct and use very elaborate shaders. It also offers various
sub-effects that the flowabs-effect is composed of, including (flow-based) difference of Gaussians,
color quantization and a tangent flow map.

#### MediaPlayer-GLES-QrMarker ####

This module is another example of an effect composed of multiple shaders. It is taken from
[QrMarker](https://github.com/thHube/QrMarker-ComputerVision) and provides a rather pointless and
extremely slow QR marker identification effect, and a nice Canny edge detection effect.

#### MediaPlayerDemo ####

This module is a demo app that incorporates all the main functionality of the MediaPlayer modules
and serves as an example on how they can be used and extended. It is available for download as
[MediaPlayer-Extended Demo](https://play.google.com/store/apps/details?id=at.aau.itec.android.mediaplayerdemo) on the Google Play Store.


Known Issues
------------

* MediaPlayer-DASH: MPD parser is basic and only tested with the test MPDs listed below
* MediaPlayer-DASH: representation switching can result in a short lag (this only happens with mp4/avc videos because reinitializing Android's MediaCodec takes some time; a workaround would be to prepare a second codec with a second surface, and switch them at the right frame; webm works flawlessly)
* MediaPlayer-GLES-FlowAbs: The OrientationAlignedBilateralFilterShaderProgram / FlowAbsBilateralFilterEffect does
  not work correctly for some unknown reason and is deactivated in the FlowAbs effect, making it
  slightly less fancy
* Exception handling needs to be improved

Device specific:

* MediaPlayer-GLES: GLCameraView's preview aspect ratio is slightly off on the Nexus 7 2013 back camera (seems to be a system bug)
* MediaPlayer-GLES-FlowAbs: Not working on Tegra devices because shaders contain dynamic loops

Tested and confirmed working on:

* LG Nexus 4 (Android 4.4.4/5.0/5.0.1/5.1.1, Adreno 320)
* LG Nexus 5 (Android 4.4.4/5.0/5.0.1, Adreno 330)
* ASUS Nexus 7 2012 (Android 4.4.4, Tegra 3, no FlowAbs)
* ASUS Nexus 7 2013 (Android 4.4.4/5.0/5.0.2, Adreno 320)
* ASUS Transformer TF701T (Android 4.4.2, Tegra 4, no FlowAbs)
* Samsung Galaxy SII (Android 4.1.2, ARM Mali-400MP4)
* Samsung Galaxy Note 2 (Android 4.4.4 CM, ARM Mali-400MP4)
* Samsung Galaxy Note 4 (Android 5.0.1, Snapdragon version with Adreno 420)

### DASH ###

The DASH support in this library is currently limited to the most common use cases. It supports
video and audio, and switching between multiple representations thereof (bitrates and resolutions). Segments
must be divided into separate files or explicit byte ranges and defined in a SegmentTemplate or
SegmentList. The player can also display live streams (dynamic mode), but this is just a simple hack to
demonstrate the ability. An evaluation of the DASH-IF test vectors is [available here](DASH-IF-test-vectors.md).

Currently not supported are single-segment representations, audio-only MPDs, multiple periods (it only
plays back the first period), segment index box parsing (sidx), dynamic MPD updates, and encryption.
The supported codecs are limited by their support through the Android MediaCodec.

There are two main cases when DASH fails:

* At MPD parsing time, when the parser detects an unsupported feature and throws an exception, or when
it crashes because of unexpected data. This is indicated in the demo app by a red error message and greyed
out video view buttons.
* At video view / media player initialization time, because segments cannot be downloaded, the
MediaExtractor fails reading a stream (usually because of unsupported container features), or a stream
uses a codec not supported by Android's MediaCodec. The demo app indicates this by an error toast
message and disabled playback controls.

Online Streaming Test URLs
--------------------------

These URLs have been tested and definitely work in the demo app:

* HTTP streaming:
    * http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4
    * http://www-itec.uni-klu.ac.at/dash/js/content/bunny_4000.webm
* DASH streaming:
    * all DASH MPDs on the [ITEC DASH-JS](http://www-itec.uni-klu.ac.at/dash/?page_id=746) page:
        * MP4 3 rep 50kbps to 150kbps: http://www-itec.uni-klu.ac.at/dash/js/content/bunny_ibmff_240.mpd
        * MP4 7 rep 200kbps to 700kbps: http://www-itec.uni-klu.ac.at/dash/js/content/bunny_ibmff_360.mpd
        * MP4 4 rep 900kbps to 2000kbps: http://www-itec.uni-klu.ac.at/dash/js/content/bunny_ibmff_720.mpd
        * MP4 6 rep 2500kbps to 8000kbps: http://www-itec.uni-klu.ac.at/dash/js/content/bunny_ibmff_1080.mpd
        * WebM 5 rep 200 kbps to 2000 kbps multiplexed audio: http://www-itec.uni-klu.ac.at/dash/js/content/sintel_multi_rep.mpd
        * WebM 7 rep 200 kbps to 4700 kbps multiplexed audio: http://www-itec.uni-klu.ac.at/dash/js/content/bigbuckbunny.mpd
        * WebM 7 rep 1000 kbps to 8000 kbps multiplexed audio: http://www-itec.uni-klu.ac.at/dash/js/content/bigbuckbunny_1080p.mpd
    * MP4 8 rep 250 to 6000 kbps separate audio: http://dj9wk94416cg5.cloudfront.net/sintel2/sintel.mpd
    * [DASH-IF test vectors](http://dashif.org/test-vectors/) (not all working, [evaluation protocol](DASH-IF-test-vectors.md))
    * Akamai live stream: http://24x7dash-i.akamaihd.net/dash/live/900080/elemental/dash.mpd
    * [IRT reference clips](http://av-standard.irt.de/wiki/index.php/Referenzclips) (not yet tested)


License
-------

Copyright (C) 2014, 2015, 2016 Mario Guggenberger <mg@protyposis.net>.
This project is released under the terms of the GNU General Public License. See `LICENSE` for details.
