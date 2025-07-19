## Summary
    The task for this etude was to create a class that displays a koch
    snowflake to the user in a gui. A Koch snowflake is a triangle that
    gains more triangles on each of it's sides for different order.
    I.e., if you have a Koch snowflake of order 1 it is just a triangle,
    order two is a triangle except each of the three sides has a triangle
    added to the middle of the line, creating an almost star. The gui
    should be responsive (i.e., it can adapt to change and input) and
    allow for user input for their desired snowflake order, at which
    point it shoudl display this snowflake.

## Initial Plan/thoughts
    Initially, going into this problem, I figured I'd draw triangles in the
    specified locations and draw a white line over the base of them each time,
    however, I just realised that you can't draw triangles in Java using
    graphics. However, theres a better solution which is instead using lines,
    probably the more commonly thought of solution.
    Currently, I've split the problem up into a few steps. First, I need to
    remember how to draw a basic line and then figure out how to draw a triangle
    using coordinates. However, rhe height of a triangle must be calculated to
    get the third coordinate as this number is never a whole number.
    I also need to figure out a way to calculate the coordinates for each line
    when an order is added.
    Finally, I need to create a frame with two buttons (one to add an order and
    one to minus an order), as well as the order and the snowflake.

## Implementation
    The first thing I did when implementing my program was try and figure out
    how I was going to calculate the line coordinates. You need to calculate
    the coordinates of four new lines for each line when adding an order. The
    first two were simple, as they had they were simply the same line but a third
    of the size, one starting from (x1,y1) and one finishing at (x2,y2). The
    second two however, were a lot more difficult. For these two, the first
    coordinate of each were easy as we already calculate them. However, the
    other coordinate, which both lines shared was very difficult to calcute.
    This coordinate was the tip of the newly created triangle. I origianlly
    thought all I had to too was figure out the height and add or minus it to
    the y coordinate, however, what I forgot to consider were angles. I then
    that this method of calculating the height, calculating the angle and
    adding the height to the angle to get the coordinate was a lot more
    complicated than I wanted my code to be. So instead I thought of another
    method. By evaluating the images of a koch snowflake, I realised every line
    formed one of the sides of a hexagon. Using this method I assigned a final
    int at the end of the double array (the arry containing the coordinates)
    which told what direction the line was facing. This int was from 1-6,
    1 being a flat line pointing up, and the rest order clockwise around the
    hexagon. I then wrote specifications for each number, on how the coordinates
    should be calculated and this seemed to work.
    Next I had to write my KochSnowflake class which used the DimensionCreator
    class to calculate dimensions for the snowflakes. In this class I created
    two panels, one where I wanted the snowflake to be and one for my buttons
    and order number. I made a plus and a minus button; when plus was pressed
    and order was added (adding one to the order text field and changing the
    snowflake accordingly) and when minus was pressed an order was minused.
    I made it that the buttons could only go to 0 (a blank screen).
    Then, to implement the snowflake, I first created a triangle for order one
    and added the coordinates to an ArrayList of double[]. I then added this
    ArrayList<double[]> to another ArrayList so I didn't have to always
    recalculated line coordinates. Once the first order was added, I simply
    called DimensionCreator (if plus was pressed) which calculated and
    returned the dimensions for the higher order. Then, the lines method was
    called which first cleard the old snowflake and then displayed the new
    snowflake using the calculated coordinates (these coordinates were also
    saved in the snowflakes ArrayList<ArrayList<double[]>>).
    I also just added in a way to set the order with a text box rather than
    the buttons, this makes it so you can go straight to the order rather
    than having to press plus (or minus) over and over. It works very
    similar to the buttons except it uses a loop.
    
## Testing and errors throughout
    From what I can tell, the code currently works completely. Throughout the
    process however, I had a lot of issues. The dimension calculations I've
    talked about was one. I also had an issue where the third coordinate
    wouldn't be calculated and would resort to (0,0), the default. I found
    out this was because, when the line was 1 or 6, it would add one or minus
    one giving a lineNo of 7 or 0 which were invalid line numbers. I fixed
    this by simply adding an if statement saying, if lineNo == 1 (or 6){
    d[4] = 6} (or 1). This ensured lineNo stayed within the boundries of 1-6.
    Another issue, probably my main issue, that I found was determining
    whether a line should be 5 or 2, 3 or 6, or 1 or 4. This was because these
    lines were the exact direction except they pushed the new triangle in
    different directions. I tried a lot of methods to fix this and finally
    realised that the top half of the hexagon (so 6, 1, and 2) acted the
    opposite to the bottom half (3, 4, and 5) whereas I had always assumed
    they worked the same way. To fix this I made 6, 1, and 2 act the opposite
    to 3, 4, and 5 when setting lineNo for the newly created lines.
    The final error I had was that when the order started getting higher, the
    triangles being created started looking very warped. I realised this was
    because the numbers were becoming so small that when I set the doubles to
    ints, the rounding error became very apparant (i.e., the line length was
    close to 3 resulting in the created coordinate attaching to the closest
    whole number even if that number wasn't near the actual number, e.g., 1.5
    became 2 which is apparently far from 1.5, especially when working with a
    third of the line distance). To fix this I used Graphics2d instead of 
    graphics as this allows lines to be drawn with doubles rather than ints.
    Currently, the program works completely, however it starts to get very
    slow around order 7-8 which is just an expected efficieny issue when you
    have to draw that many lines. The problem itself is exponential:
    (4^x - 4^(x-1)) in the terms of amount of lines being drawns so it's not
    surprising that there would be efficiency issues with higher orders.

## Code Fixes
    I had a few things I needed to fix with my code after the first time
    round. This mainly included keeping the image when the user resizes the
    window, and resizing the image accordingly. As well as retaining the
    image after maximising, restoring, and resizing.
    To keep the image I simply created a component listener which redrew
    everytime the component was resized, it also changed the dimensions of
    the snowflake accordingly to account for the change in the size of the 
    window. This was done by making the dimensions of the original snowflake
    i.e., triangle, dynamic and then changing these dimensions and redrawing
    everytime a resized occured (by calling setSnowflake).
    To keep the image after minimising, maximising and restoring I simply
    called repaint() where necessary.

## Code fixes Bugs
    I had a few errors while doing all this, the main one was that I
    originally drew a rectangle the colour of the background to erase the
    shape but not so large that it erased the buttons. However, this kept
    excess drawings after maximising and restoring, to fix it I simply
    used super.paintComponent in my paintcomponent method which redrew
    over everything. This however, caused all the compoennts of selection
    panel to disappear when I pressed a button or inputted an order. To
    fix this I simply called repaint and revalidate after a buttonistener
    or textListener was called. This did cause the panel to disappear while
    drawing but I didn't mind that as a feature.
    Finally, I had one error where when restoring the panel.x coordinate
    came back as negative even though it couldn't possibly be negaive. To 
    fix this I just keep track of the relevant variables before maximising
    and then used those variables when restoring.
    