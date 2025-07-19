## Initial thoughts
Initially this problem seemed very straight forward, all thatwas required was
to create a class that can convert from whatever form of coordinates used to 
standard form or decimal degrees form. To do this I first researched how the
coordinates could be represented and then how to convert them. The ways that the
forms can be represented however was a lot more complicated than I initially
thought. There are three main ways, DMS (decimal, minutes, seconds), DecMin
(decimal, minutes) and DecDeg (decimal degrees) and while the conversion for
each is very simple, how each can be represented can vary. This meant the main
problem for converting from whatever form it's in to standard form would be 
splitting the data into what the user's actually trying to represent. To do this
I first looked at all possible ways each of the three pieces of data could be
represented and made sure to account for all of them. I got that information
from the wikipedia page that I used to get conversion formulas:
https://en.wikiversity.org/wiki/Geographic_coordinate_conversion 

402646302N 0795855903W
40:26:46.302N 079:58:55.903W
40°26′46″N 079°58′56″W
40d 26′ 46″ N 079d 58′ 56″ W

40.446195N 79.982195W
40.446195, -79.982195
40.446195,-79.982195

40° 26.7717, -79° 58.93172

N40:26:46.302 W079:58:55.903
N40°26′46″ W079°58′56″
N40d 26′ 46″ W079d 58′ 56″
N40.446195 W79.982195
From this I derived a plan for seperating the data

## Plan for Split

I organised the example outputs into each of the three forms:
DMS:
402646302N 0795855903W
40:26:46.302N 079:58:55.903W
40°26′46″N 079°58′56″W
40d 26′ 46″ N 079d 58′ 56″ W
N40:26:46.302 W079:58:55.903
N40°26′46″ W079°58′56″
N40d 26′ 46″ W079d 58′ 56″

DecMin:
40° 26.7717, -79° 58.93172

DecDeg:
40.446195N 79.982195W
40.446195, -79.982195
40.446195,-79.982195
N40.446195 W79.982195
I then created base cases with assumptions. My first base case was that the user
split the data with commas, and I assumed they only used commas (if I were to
try to account to ever random input the code would not be readable nor practical,
therefore, I'm assuming that the user uses the same seperator if they have three
inputs (i.e., coordinate1, coordinate2, name)). This is because commas are only
ever used as a seperator in valid input (unlike spaces). If this standard was
met (and there were a valid amount of inputs (i.e., 2 or 3)), then I returned
the array.
If however, the standard wasn't met, it allowed me to rule all inputs using a
comma as a seperator so I could assume the seperator was a space (the only other
seperator used). Also note, I assumed that a seperator was used and the input
wasn't just the two coordinates (and name) in succession.
Now the issue was, sometimes space isn't only used as a seperator for
whole coordinates but also degrees, minutes and seconds between coordinates.
However, there was a solution, the only case where this could happen was DMS
or DegMin, in which case, there should always be a direction letter (N, S, W, E)
representing the direction. 
The next step, therefore, was to determine if I could split the input using just
spaces. I.e., was the code in the following format:
40.446195N 79.982195W
or something similar. For this I simply did the same as the comma, I split and
check that there were the right amount of items in the array. This works as there
would only ever be 2 or 3 items in the array if the input was correct in the
format above (i.e., if this returns true, the format must be correct unless the
user has had multiple errors in his input which would be caught later). For
example, if the user entered:
40.446195N79 .982195W
It would return true, however, it would be caught later as an error (however, 
again, we assume consistent input).

Now we move on to the case where the input is seperated by spaces but so is
other parts of the input. It was during this part that I realised it was easier
to just seperate the user input to two parts, one for the name (if they want
one) and then one for the coordinates, this simplified my splitting part and
required a lot less unnecessary code. I would have had to have seperated them
later anyway so it only made sense.
By doing this it made my next step very simply, firstly check if there was
(N, S, W, E) (which there should be, if not I talk about below), then check
if the first character was one of (N, S, W, E) or not, if it was, seperate
the coordinates using the space before the second of (N, S, W, E) else, 
seperate after the first of (N, S, W, E), remember we assume consistent input.
If, however, there is none of (N, S, W, E), we can assume it's in the case:
40d 26′ 46″ 079d 58′ 56″ in which simply we can just split by the space after
the first ". 
This is all for the splitting part of the code, any cases that aren't included
are assumed to be invalid inputs as they aren't on the list above and don't
make sense in the context of the problem.

## Implementation of Split

I started writing the first part of this code which worked out well for the
most part. In my code I had a main method (which asked for the name and the
coordinates), this will be changed later when I start using multiple classes.
I had a split class which split the coordinates into their two counterparts. 
I had a locations method which gave the locations of the NSEW (needed for the
third case in the split class). And finally I had an answer class which simply
took a string and returned the users answer to this string in stdin (just
reduced reused code).
After doing this I started testing some basic cases, through testing I found
all of my cases worked, which was good, however, also some cases which
shouldn't have worked also worked (such as ejjerj ejrjerj). This is because
I haven't done any testing for what is in the input, just that the seperator
is valid, as this is checked in other methods. However, I realised for me to
check this in other methods, I would need to create a method that would ask
the user to put in new coordinates in the current ones were found to not work.

## Plan for Identification of type

To identify the type, I just need to look at the characteristics for each type
that seperates them from the other. DMS in normal form has a seconds part 
(which the others don't), therefore, I simply need to say, if this character (")
is in the string, return DMS. For minutes, I simply say if (') is in the string
but not ("), return DecMin. Else return standard form.
However, the alternative case is if there are : instead of normal DSM or DesMin
form, in this case, if you can split the two using : and it equals 3 return DSM,
if it returns 2, return DecMin, else check for first state.

## Implementation

Currently this implementation works for all cases, however, it requires the
input to be valid (i.e., must have correct formatting), else it will assume
that the input is in DegDec form, in which case, when trying to turn into a
number it will not work.

## Calculations

I inputted all the relevant calculations for DMS to DegDec DegMin to DegDec
and DegDec to DegDec using the wikipedia page. Once this stage was done I 
created a method that called one of the methods based on the types.

## Error handling

To handle errors along the way I simply turned a String variable 
errorMessage != null which told the program something went wrong and to
restart.

## Creating features

I created features using a method createFeature which converted the name and
coordinates into a string. I then added all these features to a feature
collection with featureCollectionCreation. After this I wrote the feature
collection to a geoJSON file using fileCreator.

## Writing to the desktop
To write the file to geojson.io, a website that supports geojson files, I
turned the file into a string, turned that string into an encoded string 
(i.e., replaced symbols with hexidecimal equivalents). And opened that
URL to the default desktop.
