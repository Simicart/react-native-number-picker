# react-native-number-picker-library

[![npm version](https://img.shields.io/npm/v/react-native-number-picker-library.svg?style=flat-square)](https://www.npmjs.com/package/react-native-number-picker-library) <a><img src="https://david-dm.org/crabbynguyen/react-native-number-picker.svg?style=flat-square" alt="dependency status"></a>

A Native number picker for both Android & iOS

## Getting started

`$ npm install react-native-number-picker-library --save`

### Mostly automatic installation

`$ react-native link react-native-number-picker-library`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-number-picker-library` and add `RNNumberPickerLibrary.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNNumberPickerLibrary.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`

- Add `import com.reactlibrary.RNNumberPickerLibraryPackage;` to the imports at the top of the file
- Add `new RNNumberPickerLibraryPackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-number-picker-library'
   project(':react-native-number-picker-library').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-number-picker-library/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
     compile project(':react-native-number-picker-library')
   ```

## Usage

```javascript
import RNNumberPickerLibrary from "react-native-number-picker-library";

//Method createDialog(objectForConfig, callBackForDoneClick, callbackForCancelClick)
RNNumberPickerLibrary.createDialog(
  {
    minValue: 0,
    maxValue: 100,
    selectedValue: 10,
    doneText: "Done", // only for Android
    doneTextColor: "#000000", // only for Android
    cancelText: "Cancel", // only for Android
    cancelTextColor: "#000000" // only for Android
  },
  (error, data) => {
    if (error) {
      console.error(error);
    } else {
      console.log(data);
    }
  },
  (error, data) => {
    if (error) {
      console.error(error);
    } else {
      console.log(data);
    }
  }
);
```
