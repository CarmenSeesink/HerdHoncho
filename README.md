<!-- PROJECT LOGO -->
<br />
<h6 align="center">Carmen Seesink IDV 304</h6>
<p align="center">
</br>
   
  <a href="https://github.com/CarmenSeesink/HerdHoncho">
    <img src="Images/logo.png" alt="Logo" width="140" height="140">
  </a>
  
  <h3 align="center">Herd Honcho</h3>

  <p align="center">
    A tool to enhance livestock farming productivity
  </p>


<!-- TABLE OF CONTENTS -->
## Table of Contents

* [Project Description](#project-description)
  * [Built with](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [How to install](#how-to-install)
* [Artificial Intelligence integration](#artificial-intelligence-integration)
* [API](#api)
* [Features and Functionality](#features-and-functionality)
   * [Scan tag](#scan-tag)
   * [Detect and recognise tag digits](#detect-and-recognise-tag-digits)
   * [Read tag digits](#read-tag-digits)
   * [Color detection](#color-detection)
   * [Year predictor](#year-predictor)
   * [Authentication](#authentication)
   * [Livestock management](#livestock-management)
   * [Employee management](#employee-management)
* [Contributing](#contributing)
* [License](#license)
* [Acknowledgements](#acknowledgements)


<!--PROJECT DESCRIPTION-->
## Project Description

![image1][image1]

In the livestock sector of agriculture within South Africa, several factors have combined to strain producer margins in 2020.

In the annual drought report, the findings revealed that the effect of drought had a significant impact on average herd size, livestock feeding, and sheep flock. Unfortunately, livestock farmers did not receive any form of assistance from the government.

In 2015, just ten percent of organisations reported that they use AI technology. Fast forward to 2020 and that number rose to 37 percent (Gartner, 2020). This represents an impressive 270 percent growth in just four years.

Statistics show that 54 percent of business executives say that their adoption of AI within the workplace has led to a boost in productivity (PWC, 2018).

The latest AI statistics show that as of 2019, 62 percent of consumers say they are willing to use AI to improve their experience (Salesforce, 2019). 44 percent of firms using AI report a reduction in business costs in departments where AI is implemented (McKinsey, 2019). Coupled with a growth in revenue by many of these organisations, the cost benefits of AI are clear.  


Herd Honcho is a management app which will allow livestock farmers to administer their livestock and employees effectively. The system will allow farm workers to scan the tags of various livestock, using AI, and submit data specific to the identified animal. 

Furthermore, the system will allow farmers to track the status of their various livestock, and also enable them to manage their employees. The app will be built for Android and desktop devices. 

### Built With

* [Android Studio]( https://developer.android.com/studio)
* [Java]( https://www.java.com/en/)
* [ML Kit]( https://developers.google.com/ml-kit)
* [Firebase]( https://firebase.google.com)

<!-- GETTING STARTED -->
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

For development, the latest version of Android Studio is required. The latest version can be downloaded from: 
* [Android Studio]( https://developer.android.com/studio)

### How to install
 
1. Clone the repo
```sh
git clone https:://github.com/CarmenSeesink/HerdHoncho.git
```
2. Open the project

Use `Open Project` in Android Studio

3. Run the project

Build and run the project on your emulator, this project was specifically built for Nexus and Pixel devices.

## Artificial Intelligence integration

### Optical Character Recognition

Optical Character Recognition (OCR) is a form of technology that identifies characters like numbers and letters included within an image. Sometimes known as Text Recognition, OCR also recognises patterns and classifies information for Artificial Intelligence to use.

### Text to speech

Text to speech is a type of assistive technology that reads digital text aloud. With a click of a button or the touch of a finger, TTS can take words on a computer or other digital device and convert them into audio.

## API

### Android Palette

Palette is a new API for Android that allows you to extract and make use of colors in an image. It also has a support library in order for older versions of Android to make use of Palette.

<!-- FEATURES AND FUNCTIONALITY-->
## Features and Functionality

![image2][image2]

### Scan tag

Users can scan the tags of livestock using their devices' camera.

### Detect and recognise tag digits

Using the scanned tag, and OCR integration the device detects and recognises the tags digits, without the user having to manually type in the tag digits. 

### Read tag digits

Users can receive audio feedback once the tag digits are recognised. This allows the user to make updates to the tag digits, without having to read between the device and the tag.

![image3][image3] 

### Color detection

The app detects the dominant color from the scanned tag.

### Year predictor

Based on the dominant color the app predicts the year the livestock was born or brought to the farm.

### Authentication

Using Firebase authentication, the app's login and sign up is secure.

### Livestock management

Users can manage their various livestocks and keep up to date with their livestock's status.

### Employee management

Users can manage their employees and keep up to date with their employees' current location and status.

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<!-- AUTHORS -->
## Authors

* **Carmen Seesink** - [CarmenSeesink](https://github.com/CarmenSeesink)

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* [Recognize Text in Images with ML Kit on Android]( https://www.youtube.com/watch?v=_qrI1JUCMjI)
* [Palette Class to Extract Colors from An Image - Android Studio]( https://www.youtube.com/watch?v=-s13C93-v_Q)
* [How to connect Android with Firebase database]( https://www.youtube.com/watch?v=lnidtzL71ZA)
* [Recognize Text in Images (OCR)]( https://www.youtube.com/watch?v=pXGr6bQHcgY)

<!-- MARKDOWN LINKS & IMAGES -->
[image1]: Images/1.png
[image2]: Images/2.png
[image3]: Images/3.png
