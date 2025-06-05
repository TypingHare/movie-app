# [movie-app@android](https://github.com/TypingHare/movie-app/tree/android)

> [!NOTE]
> This branch is the frontend (Android App) of Movie. For the backend, see the [main branch](https://github.com/TypingHare/movie-app).

## Installation

1. Clone this repository to your local machine in a proper directory.

    ```bash
    $ git clone https://github.com/TypingHare/movie-app.git
    ```

2. Install [JetBrains Toolbox](https://www.jetbrains.com/toolbox-app/).

3. Open **JetBrains Toolbox** and look for **Android Studio** in `Tools > Available`. Click the `Install` button.

4. Open **Android Studio** and Open the directory. Make sure you are opening the correct directory, which contains `build.gradle.kts`. **Android Studio** will look for this file to initialize the Android project and throw errors if it is not found.

5. Opening an Android project for the first time usually takes over five minutes. Please be patient. Once you see that the sidebar has an `app` folder and a `Gradle Scripts` item, the import process is complete.

6. In the menu bar of **Android Studio**, go to `Tools > Device Manager`. Look for `Create Virtual Device` in the plus symbol. Select `Pixel 9` (or other models if you want), and click `Next`. On the following page, keep the default settings and `Finish`. **Android Studio** will then download and install the emulator.

7. Click the green triangle button on the menu bar (top-right) to start the emulator and the application.