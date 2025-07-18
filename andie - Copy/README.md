# andie

## Features

### Extended Filters (Callum Cooper)

- Access: Any kernel based filter - implemented using a abstract class KernelFilter
- Testing: Tested all filters that were using a kernel to ensure that they work on transparent images and images with alpha channels.
- No known issues.

### Filters with negative results (Callum Cooper)

- Access: Filter menu -> Emboss / Edge detection
- Testing: Tested with emboss and edge detection to ensure that they produce the proper results.
- No known issues.

### Emboss and Edge detection filters (Adam Aizal)

- Access: Filter menu -> Emboss / Edge detection
- Testing: I tested the filter on different images, colours and formats. The direction of the filters are correct, and different for each choice.
- No known issues.

### Posterise effect (Curtis Mellsop)

- Access: Colour menu -> Posterise
- Testing: To test I went through all the equations required to produce the correct colour and ensured the right int was produced each time based on the input, i.e., I made sure the math all worked as expected. Then checked result on output with expected result.
- No known issues

### Mouse selection (Sam Merton)

- Access: Hold and drag the mouse on the selection.
- Testing: Prevented user from selecting outside of image. Also when user starts selection within the image but moves the mouse outside, the selection is automatically limited to the image boundary but still moves relative to the mouse. When a selection operation occurs, such as crop or draw, it automatically deselects.
- No known issues

### Crop to selection (Curtis Mellsop)

- Access: Select a portion of the image -> selection -> Crop
- Testing: Took screenshots to ensure croped image was same as the original selection. Then tested with transparent images.
- No known issues

### Drawing functions (Curtis Mellsop)

- Access: Select a portion of the image -> selection -> Drawing Operations
- Testing: Ensure the correct shape was produced, fill/not fill was valid and the colour was correct. Tested to make sure a shape could only be drawn given a selection. For transparent images, if a a shape was drawn on both the transparent part and the actual image,it would become an extension of the image. i.e., the actual image (not the transparent part) would increase in size and the amount of transparent pixels would decrease (by the overlap).
- No known issues

### Macros (Callum Cooper)

- Access:
    - To record a macro: Macros menu -> stop/record a macro -> Apply your operations -> Macros menu -> stop/record a macro.
    - To play a macro: Macros menu -> Play macro
- Testing: Tested with multiple images and all of the operations performable. Prevents out of bounds selection by not applying the operation.
- No known issues.

### Show us something... - Adding text to image - (Callum Cooper)

- Access: Select a portion of the image -> selection -> Add text
- Testing: Tested on all image types.
- No known issues.

### Sharpen Filter (Callum Cooper)

- Access: Filter menu -> Sharpen filter, Toolbar, `Ctrl + K`
- Testing: Tested on multiple images of various formats and color types. Tested by continously sharpening, looks weird, works fine. All worked correctly.
- No known issues.

### Gaussian Filter (Callum Cooper)

- Access: Filter menu -> Gaussian filter, Toolbar, `Ctrl + P`
- Testing: Tested similarly to sharpen filter. Works fine on varying images, formats and color types. Gaussian function is tested in the JUnit tests and works as expected.
- No known issues.

### Median Filter (Callum Cooper)

- Access: Filter menu -> Median filter, Toolbar, `Ctrl + L`
- Testing: Works on all file formats and color types. Median array function is tested in the JUnit tests and passes.
- No known issues - Edges are calculated using a grid that fits that region.

### Brightness Adjustment (Curtis Mellsop)

- Access: Colour menu -> Brightness/Contrast, Toolbar, `Ctrl + N`
- Testing: Originally tested by checking expected argb values with produced values given specific changes in brightness by printing original and changed argb values and calculating expected values then comparing. Then checked visually comparing to the provided example on blackboard.
- No known issues.

### Contrast Adjustment (Curtis Mellsop)

- Access: Colour menu -> Brightness/Contrast, Toolbar, `Ctrl + N`
- Testing: Same testing method as brightness, once both worked I tested together in the same way.
- No known issues.

### Image Resize (Sam Merton)

- Access: Transformation menu -> Image Resize, Toolbar, `Ctrl + R`
- Testing: Ensured it doesn't accept negative numbers
- No known issues.

### Image Rotations (Curtis Mellsop)

- Access: Transformation menu -> Image Rotations, Toolbar, `Ctrl + B`
- Testing: Ran through all three possibilities and checked each worked. Also tested they worked after another rotation had already occured.
- No known issues

### Image Flip (Adam Aizal)

- Access: Transformation menu -> Image Flip, Toolbar, `Ctrl + F`
- Testing: Flip tested on multiple files. Flipping twice returns the original image.
- No known issues.

### Image Export (Callum Cooper)

- Access: File menu -> Export file, `Ctrl + R`
- Testing: Tested on multiple files, formats and color types. Also tested on files that have had their contents modified through filters.
- No known issues.

### Exception Handling (Sam Merton)

- Access: When the user does something they shouldn't, an exception dialogue is shown.
- Testing: Works as expected
- Exceptions handled: Null file (handled when user tries to edit an image without one selected), null image exception where the user tries to export without having an image selected, and undo/redo when there is nothing to redo or undo, opening/saving/exporting is handled
- No known issues.

### Toolbar (Curtis Mellsop)

- Access: Below the menu bar.
- Testing: Just went through all the buttons and tested they produced the expected results.
- Features available & why: I made almost all the features available, undo and redo, zoom in and zoom out, mean, median, gaussian and sharpen filter, brightness and contrast, and greyscale, rotate resize and flip image. I just didn't include zoom full cause I felt it wasn't needed. The reason I added almost all the features is because there aren't so many that it takes up too much space and it's a lot easier to remove them in the future if need be than it is to add a new feature (you can just code comment them off if you don't want to use them).
- No known issues.

### Keyboard Shortcuts (Adam Aizal | Callum Cooper)

- Access: Pressing the keyboard commands.
- Testing: Tested all keyboard commands to ensure they work properly. They do.
- Features available & why: Shortcuts made for Undo/Redo, and every operation in ANDIE. Undo/Redo is a necessity for image processing programs. Other shortcuts are optional, but still made available for better usability.
- No known issues.

## How to build & run

Andie requires no external dependencies and therefore can be built with pure java.
### Build
1. Clone andie to your local computer `git clone https://altitude.otago.ac.nz/cosc202-gentlemen/andie`
2. Cd into the andie directory `cd andie`
3. build the andie class files `javac -d bin/ src/main/cosc202/andie/**/*.java src/main/cosc202/andie/*.java`

### Run
1. Run andie with the following java command `java -cp bin cosc202.andie.Andie`

## Contributing
### Testing
Ensure that for a completed feature update all tests run successfully.
You can buld & run the tests with the following command after following the build guide.
```bash
javac -d bin/ -cp \"bin;lib/junit-platform-console-standalone-1.8.2.jar\" src/test/cosc202/andie/*.java
java -jar lib/junit-platform-console-standalone-1.8.2.jar -cp bin/ --scan-class-path
```

To add a test use the corresponding *Test.java file in test/cosc202/andie.

### Setup pre-commit hooks
All the java code in this repository must comply with the [Google Java format](https://google.github.io/styleguide/javaguide.html).

To setup the pre-commit hooks follow these steps:
###### Note: Ensure you select the add to path box when installing python.
1. Install [Python](https://www.python.org/)
2. Install pre-commit through python `pip install pre-commit`
3. Install the git hooks using pre-commit `pre-commit install`
4. Install the git commit hooks using pre-commit `pre-commit install --hook-type commit-msg`

Whenever you commit your code it will be automatically formatted to follow the conventions.

### Git commit conventions
Commits must follow the [conventional commit format](https://www.conventionalcommits.org/en/v1.0.0/).

### Documentation
Documentation is available [here](https://cosc202-gentlemen.cspages.otago.ac.nz/andie/docs/cosc202/andie/package-summary.html).

### Release
Release is available to download [here](https://cosc202-gentlemen.cspages.otago.ac.nz/andie/).
