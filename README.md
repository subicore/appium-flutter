
# My flutter Mobile App

## Table of Contents
1. [Quick Start Guide](#quick-start-guide)
2. [Getting Started](#getting-started)
3. [Prerequisites](#prerequisites)
4. [Installation](#installation)
   - [Java](#java)
   - [Maven](#maven)
   - [Android Studio](#android-studio)
   - [Node JS](#node-js)
   - [Setup SDK and tools needed in Environment variables](#setup-sdk-and-tools-needed-in-environment-variables)
   - [Setup Node.js Environment variables](#setup-nodejs-environment-variables)
   - [Install Appium](#install-appium)
   - [Install IntelliJ Community Edition](#install-intellij-community-edition)
5. [Running the tests](#running-the-tests)
   - [From IntelliJ IDE](#from-intellij-ide)
6. [Repo guidelines](#repo-guidelines)
7. [Contributing](#contributing)
8. [Common Issues and Troubleshooting](#common-issues-and-troubleshooting)
9. [Project Structure](#project-structure)

## Quick Start Guide

1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-repo/my-exp-mobile.git
   cd my-exp-mobile
   v-22\bin"
     setx JAVA_HOME "C:\Program Files\Java\jdk-22"
     ```

3. **Install Maven:**
   - Download and extract [Maven 3.9.8](https://dlcdn.apache.org/maven/maven-3/3.9.8/binaries/apache-maven-3.9.8-bin.zip) to `C:\QAPrograms\Maven`.
   - Set up environment variables:
     ```sh
     setx Maven "C:\QAPrograms\Maven\apache-maven-3.9.8\bin"
     setx Maven_HOME "C:\QAPrograms\Maven\apache-maven-3.9.8"
     ```

4. **Install Node.js:**
   - Download and install [Node.js 20.15.0](https://nodejs.org/dist/v20.15.0/node-v20.15.0-x64.msi).

5. **Install Appium:**
   ```sh
   npm install -g appium
   appium driver install uiautomator2
   ```

6. **Set up Android Studio:**
   - Download and install [Android Studio](https://redirector.gvt1.com/edgedl/android/studio/install/2024.1.1.11/android-studio-2024.1.1.11-windows.exe).
   - Configure the Android SDK.

7. **Open the project in IntelliJ:**
   - Download and install [IntelliJ Community Edition](https://www.jetbrains.com/idea/download/?section=windows).
   - Open the project in IntelliJ and configure the project SDK to use JDK 22.

8. **Run the tests:**
   - Start the Appium server:
     ```sh
     appium
     ```
   - Run the tests from IntelliJ or using Maven:
     ```sh
     mvn test -PprofileName
     ```

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See Import/Setup notes on how to import the project on your system.

### Prerequisites
* Java
* Maven
* NodeJS
* IntelliJ IDE
  * Create TestNG XML plugin, by autonavi
* Android Studio

## Installation
A step-by-step series of examples that tell you how to get the package installed and running

### Java

#### Download and Install Java
1. Download [Java 22](https://download.oracle.com/java/22/archive/jdk-22_windows-x64_bin.exe) and install it in the default location.

#### Set Up Environment Variables for Java
2. Set up Environment variables for Java on your machine:
   * Right-click your Computer icon -> Properties -> Advanced system settings.
   * Click the *Environment Variables* button in the *System Properties* dialog.
   * Add the following new variables:
     - **JAVA**: `C:\Program Files\Java\jdk-22\bin`
     - **JAVA_HOME**: `C:\Program Files\Java\jdk-22`
   * Add `%JAVA%` to the `Path` variable.

#### Verify Java Installation
3. Open the command prompt, type `java -version` and press Enter to verify the installation.
   * Expected output:
     ```sh
     java version "22.0.1" 2024-04-16
     Java(TM) SE Runtime Environment (build 22.0.1+8-16)
     Java HotSpot(TM) 64-Bit Server VM (build 22.0.1+8-16, mixed mode, sharing)
     ```

### Maven

#### Download and Install Maven
1. Download [Maven](https://dlcdn.apache.org/maven/maven-3/3.9.8/binaries/apache-maven-3.9.8-bin.zip), extract it, and subordinates it in a folder on the C: drive, e.g., `C:\QAPrograms\Maven`.

#### Set Up Environment Variables for Maven
2. Set up Environment variables for Maven on your machine:
   * Right-click your Computer icon -> Properties -> Advanced system settings.
   * Click the *Environment Variables* button in the *System Properties* dialog.
   * Add the following new variables:
     - **Maven**: `C:\QA-Programs\Maven\apache-maven-3.9.8\bin`
     - **Maven_HOME**: `C:\QA-Programs\Maven\apache-maven-3.9.8`
   * Add `%Maven%` to the `Path` variable.

#### Verify Maven Installation
3. Open the command prompt, type `mvn -v` and press Enter to verify the installation.
   * Expected output:
     ```sh
     Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
     Maven home: C:\QA-Programs\apache-maven-3.9.6
     Java version: 22.0.1, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-22
     Default locale: en_US, platform encoding: UTF-8
     OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
     ```

### Android Studio

#### Download and Install Android Studio
1. Download and install [Android Studio](https://redirector.gvt1.com/edgedl/android/studio/install/2024.1.1.11/android-studio-2024.1.1.11-windows.exe).
2. Choose Standard setup and complete the installation.
3. Open Android Studio -> New -> Empty Activity -> Finish.
4. Wait for the build process and indexing to complete, allowing any requested permissions.
5. Click on Tools -> SDK Manager -> SDK Tools and uncheck 'Hide Obsolete packages'.

### Node JS

#### Download and Install Node JS
1. Download [Node JS](https://nodejs.org/dist/v20.15.0/node-v20.15.0-x64.msi) and install it.
2. Install without Chocolatey, as prompted by the installer.

### Setup SDK and Tools Needed in Environment Variables

#### Set Up Environment Variables for SDK
1. Create a new variable under User/System variables and name it `ANDROID_HOME` with the value `C:\Users\yourUsername\AppData\Local\Android\Sdk`.
2. Add the following to the `Path` variable:
   - `C:\Users\yourUsername\AppData\Local\Android\Sdk\tools\bin`
   - `C:\Users\yourUsername\AppData\Local\Android\Sdk\tools`
   - `C:\Users\yourUsername\AppData\Local\Android\Sdk\platform-tools` (optional).

### Setup Node.js Environment Variables

#### Set Up Environment Variables for Node.js
1. Create a new variable under User/System variables and name it `NODE_HOME` with the value `C:\Program Files\nodejs`.
2. Add \`C:\Program Files\nodejs\node_modules\npm\bin\` to the `Path` variable.

### Install Appium

#### Install and Verify Appium
1. Type `npm install -g Appium` and wait for the Appium module to get installed.
2. Verify the installation by typing `appium --version` - this will return the version number.
3. Press `Ctrl+C` and type `appium driver list` to list the installed drivers.
4. Type `appium driver install uiautomator2` and wait for the driver installation. Verify again by repeating step 3.

### Install IntelliJ Community Edition

### Install IntelliJ Community Edition

#### Download and Install IntelliJ
1. Download and install the IDE from [here](https://www.jetbrains.com/idea/download/?section=windows).
2. Open the IDE and go to `File -> Open`.
3. Select the cloned project's root directory in File Explorer.
    * Let the dependencies and build complete.

#### Set Up IntelliJ
4. Accept/Acknowledge any plugins suggested by the IDE.
5. Go to `File -> Project Structure -> Project Settings` and under `Project` select the installed JDK 22 version and apply.
6. Ensure Java 22 is set under `SDK` in the left pane.
7. Right-click on the project -> `Rebuild/Build Module` and wait for the build to complete.
8. Enable rendering of documentation comments for better readability:
    * Go to `File -> Settings -> Editor -> Appearance`.
    * Check the option `Render documentation comments`.


## Running the tests
### From IntelliJ IDE
#### Pre-conditions:
Go to the command prompt - type `appium` and press Enter to start the Appium Server.

#### Running Tests
1. With TestNG suites (This is the effective method for debugging):
   * Go to `/src/test/java/resources/suites/` right-click on the suite that you want to execute and click -> `Run` / `Debug`.
   * This will generate the report in the root directory of the project `/testReports/` for both Run and Debug operations.
2. With Maven commands:
   * Right-click on the project and go to `Open In -> Terminal`.
   * This will open a terminal inside IntelliJ.
   * Enter `mvn test -PprofileName` replace the profileName here with the TestNG groups that we have. Example - sanity, regression, etc.

### Repo guidelines
1. Create a branch with the name of the Jira card ID that has been assigned.
2. Checkout and fetch.
3. Write your code.
4. Commit messages should be clear on what changes are done.
5. Sync with master and clear conflicts if any.
6. Submit a pull request with default reviewers.
7. Do not commit code to the `master` branch.

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Common Issues and Troubleshooting

### Java Installation Issues
**Problem:** `'java' is not recognized as an internal or external command, operable program or batch file.`
**Solution:** 
1. Ensure that the **JAVA** and **JAVA_HOME** environment variables are correctly set.
2. Reboot your computer and try running \`java -version\` again.

### Maven Installation Issues
**Problem:** `'mvn' is not recognized as an internal or external command, operable program or batch file.`
**Solution:**
1. Ensure that the **Maven** and **Maven_HOME** environment variables are correctly set.
2. Add `%Maven%` to the `Path` variable.
3. Reboot your computer and try running `mvn -v` again.

### Android Studio Setup Issues
**Problem:** Emulator does not start.
**Solution:**
1. Make sure your system meets the minimum requirements for running the Android emulator.
2. Ensure that the Android SDK is properly installed and configured.
3. Check for any error messages in the Event Log and resolve any issues.

### Appium Installation Issues
**Problem:** `'appium' is not recognized as an internal or external command, operable program or batch file.`
**Solution:**
1. Ensure that Appium is installed globally by running `npm install -g Appium`.
2. Verify the installation by running `appium --version`.
3. Add the Appium installation directory to the `Path` variable if necessary.

## Project Structure

The project follows a standard structure for a Java-based application with TestNG and Maven. Below is an overview of the main directories and files:

```
my-exp-mobile/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── exp/
│   │   │           └── mobile/
│   │   │               └── Main.java
│   │   └── resources/
│   │       └── application.properties
│   ├── test/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── exp/
│   │   │           └── mobile/
│   │   │               └── tests/
│   │   │                   └── SampleTest.java
│   │   └── resources/
│   │       └── testng.xml
├── pom.xml
└── README.md
```

### src/main/java
Contains the main application code.

### src/main/resources
Contains application configuration files.

### src/test/java
Contains the test classes.

### src/test/resources
Contains the TestNG configuration file.

### pom.xml
Maven configuration file for managing dependencies and build configuration.

### README.md
The project documentation file.
