# react-native-pdf-reader



## Installation

- Run `npm install palmsnipe/react-native-pdf-reader --save` to install using npm.

- Add the following two lines to `android/settings.gradle`:

```gradle
include ':react-native-pdf-reader'
project(':react-native-pdf-reader').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-pdf-reader')
```

- Edit `android/app/build.gradle` and add the annoated lines as below:

```gradle
...

dependencies {
    compile fileTree(dir: "libs", include: ["*.jar"])
    compile "com.android.support:appcompat-v7:25.0.1"
    compile "com.facebook.react:react-native:+"
    compile project(':react-native-pdf-reader')  // <- Add this line
}
```
