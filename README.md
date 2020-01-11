# PushPole for Android native

Simple implementation of [PushPole](http://pushe.co) SDK using Android studio and Java.

You can see other samples:

* [Unity](https://github.com/ronashco/pushe-unity-sample)
* [Basic4Android](https://github.com/ronashco/pushe-ba4-sample)
* [Eclipse](https://github.com/ronashco/pushe-eclipse-sample)

#### Run the example

* Install git if you don't have it.
* Run:
`git clone https://github.com/pusheco/android-studio-sample.git`
* Open it with Android studio and run it on your device.

#### Installation on your project
```groovy
dependencies {
   implementation 'com.pushpole.android:pushsdk:1.7.0' // Or compile for lower gradles
}

```
#### AndroidManifest.xml

Go to [PushPole console](https://console.pushe.co) and get the manifest content and add it to your project `AndroidManifest.xml`

The manifest will be a tag like this:

```xml
<meta-data android:name="com.pushpole.sdk.token"
            android:value="PUSHPOLE_76583046756" />
```

The value `PUSHPOLE_76583046756` is for demo panel. Replace it with your own token.

And if you need location-based features, add `Location permission` to the manifest as well.


#### Add the codes

In your Application or activity class, add this to `onCreate()` method:

```java
Context context = this.getApplicationContext(); // This is optional. The `initialize` needs a context. Provide it from anywhere you want.
PushPole.initialize(context, true);
```

#### More features

All features are added to the sample. You can check them out.

Now run and install your app on a device or emulator that has google-play-service installed.
PushPole needs minimum android api=15 and google-play-service version >= 3 to run.

## More Info
For detailed documentations visit https://pushe.co/docs/android-studio/


## Contribution

Feel free to add anything you think is suitable to be in this sample.<br>
It does not follow any specific code style. So just read the code a little bit and send a pull request at anytime. We'll be happy :D.

## Support 
#### Email:
If you have any problem, please contact us using this email, we will get back to you right away:
`support [at] pushe.co`


