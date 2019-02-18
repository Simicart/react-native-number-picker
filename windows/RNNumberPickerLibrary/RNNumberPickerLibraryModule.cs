using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Number.Picker.Library.RNNumberPickerLibrary
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNNumberPickerLibraryModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNNumberPickerLibraryModule"/>.
        /// </summary>
        internal RNNumberPickerLibraryModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNNumberPickerLibrary";
            }
        }
    }
}
