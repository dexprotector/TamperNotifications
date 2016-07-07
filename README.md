# TamperNotifications

This sample is to demonstrate how Tamper Notifications mechanism works. 

## Description
https://dexprotector.com/docs#tamper-notifications

## Requirements
- Android Studio or Gradle
- DexProtector Enterpise with a valid license

## Configuring 
- Set path to Android SDK in local.properties
- Set path to DexProtector Enterprise in build.gradle (project’s root) instead of `/Users/developer/DexProtector`

## Building
Open the project in Android Studio and execute `Build -> Generate Signed APK..` command or execute `gradle clean assemble` from the command line. A protected APK file will be placed into `app/app-release.apk` if you use Android Studio, if you use gradle via command line a protected file will be available at `app/build/outputs/app-release.apk`. Either approach you use, we refer to the protected file as `app-release.apk` below.

## Evaluating
> Note: All the actions are to be performed from the project's root

### Original APK
Install the protected application to a device or an emulator and launch it. There should be a following message displayed: `Tamper detection result: false`

### Updating a file inside of the APK 
```
cp app-release.apk app-release-copy.apk
mkdir assets
echo 1 > assets/sample.txt
zip -g  app-release-copy.apk assets/sample.txt
zip -d  app-release-copy.apk META-INF/*
jarsigner -verbose  -digestalg SHA1 -keystore sample.keystore  app-release-copy.apk  -storepass android -keypass android android
zipalign -v -f 4  app-release-copy.apk  app-release-modified.apk
```
After installation and launching of app/app-release-modified.apk there should be a message displayed: `Tamper detection result: true`

### Adding a file into the APK
```
cp app-release.apk app-release-copy.apk
echo 1 > test.dat
zip -g app-release-copy.apk test.dat
zip -d app-release-copy.apk META-INF/*
jarsigner -verbose  -digestalg SHA1 -keystore sample.keystore app-release-copy.apk  -storepass android -keypass android android
zipalign -v -f 4  app-release-copy.apk  app-release-modified.apk
```
After installation and launching of app/app-release-modified.apk there should be a message displayed: `Tamper detection result: true`

### Deleting a file from the APK 
```
cp app-release.apk app-release-copy.apk
zip -d app-release-copy.apk assets/sample.txt
zip -d app-release-copy.apk META-INF/*
jarsigner -verbose  -digestalg SHA1 -keystore sample.keystore app-release-copy.apk  -storepass android -keypass android android
zipalign -v -f 4  app-release-copy.apk  app-release-modified.apk
```
After installation and launching of app/app-release-modified.apk there should be a message displayed: `Tamper detection result: true`

### Resigning with a different certificate

>Notifications will be fired with the following settings only (otherwise the protected application will fail to start because of the DexProtector's internal integrity checks that prevent starting of a modified APK):
```
<integrityControl>
  <checkCertificate>off</checkCertificate>
</integrityControl>
```

Prior to this step, please delete the application from a device/emulator

```
cp app-release.apk app-release-copy.apk
zip -d app-release-copy.apk META-INF/*
jarsigner -verbose  -digestalg SHA1 -keystore test.keystore app-release-copy.apk  -storepass android -keypass android android
zipalign -v -f 4  app-release-copy.apk  app-release-modified.apk
```
After installation and launching of app/app-release-modified.apk there should be a message displayed: `Tamper detection result: true`
