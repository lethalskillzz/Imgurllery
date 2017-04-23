# Mobigur
A simple app that allows to browse Imgur gallery using http://api.imgur.com/


### Setup Requirements

- Java 8
- Latest version of Android SDK and Android Build Tools


### API Key

The app uses imgur.com API to access images. You must provide your own [API key][1] in order to build the app.

Just put your API key into `gradle.properties` file (create the file if it does not exist already):

```gradle
CLIENT_ID = "Client-ID abcd1234"
```

### Testing

This project integrates a combination of [local unit tests][2] and [instrumented tests][3].


[1]: https://api.imgur.com/endpoints
[2]: app/src/test/
[3]: app/src/androidTest/

