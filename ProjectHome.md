### EasyADK Overview ###

This is a convenience library for connecting and communicating with an Android accessory over the ADK protocol.

See examples for both Android and Arduino in the Source tab. Use the normal version if you're running Android 4.0.3, or the backport version if you're running on 2.3.4 + Google APIs.

See my [Mover-bot](http://code.google.com/p/mover-bot/) project for a complete implementation using this library.

### Notes: ###

This library is designed to be used in an activity that launches automatically when its partner accessory is connected, and closes when the accessory is disconnected.

If you want to make an always-on app that detects and interacts with accessories, I suggest using a separate activity or a custom application class to manage the process.