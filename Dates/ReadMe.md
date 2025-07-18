## Initial Plan/thoughts
                                                                                |
    My initial thoughts of this problem is that it can be simplified into two 
    main problems. The first being converting the stdin date into the proper 
    formatted stdout date. The second being confirming whether the date is valid
    or invalid. The first task is somewhat complex, because there are many
    different ways of expressing dates, and there may also by typos in the users
    input.
    The first part associated with problem 1 is to split the inputed date into
    day, month and year. The main issue associated with this is the
    identification of the specific symbol used to split the day, month and year
    (e.g., "-", " ", "/").

## Implementation
    After starting the coding process I realised it would be more benficial to
    check the validity of the date as I went along and everytime there was
    somwthing that didn't work as expected I called a metho which printed out
    the date followed by ' - INVALID' and printed the error message to stderr.
    This implementation worked well cause when I found new errors, I simply
    added some code which called the method.
    In terms of the code itself I split the checking and formatting into three
    steps, one for the day, one for the month, and one for the year. To do this
    I first figured out the seperator being used and split the date into each
    part using the .split function with the seperator as the argument. I
    checked the validity of the date as I went, for example, checking that a
    valid seperator was used.
    I first checked the valdity of the year, as this part  didn't relyon one
    of the other two parts. To calculate the year I simply checked if it was a
    number of length 2 or 4 (the requirements), and then checked that it was
    within the valid range and returned the input if it was.
    Next I calculated the month as it also didn't rely on either of the other
    two parts. To calculate the month I first worked out if the user entered
    a number or a string. To check the validity of each I checked if the number
    was between 1 and 12 and checked if the string was of a valid format and a
    valid month. If all was valid I returned the month in the correct format.
    Finally, I checked the day as it required both the month and year to be
    calculated. First I checked if tke day was in the valid input format: d
    or dd. Then I check if it was in the valid range for the month it was in.
    Finally, if it was feb 29 I checked that the year was a leap year using
    the year.
    If valid I then printed the result to stdout. If invalid at any point, the
    invalid method would have been called printing the original date followed
    by ' - INVALID' to stdout and printing the error message to stderr.
    
## Testing
    To test my program I inputted all the base cases to check it worked as
    expected, which, after a few fixes I found it did. Some errors I ran into
    were that my year range was wrong (didn't incldue 1753 and 3000), my year
    would work when y was inputted, the month would work with incorrect
    formatting, 29th of feb would work for any year and finally, the date
    would print even when invalid. Currently I can't find any issues with the
    code, however, autojudge is still saying it's the wrong answer but it
    doesn't say what is wrong and I've done significant testing and can't find
    any more issues.
    Just found one more error in my code from re-reading, I original thought
    the month format had to be in the format: MMM, mmm, or Mmm. However, I
    realised that it actually just had to be upper case first, or all lower
    case meaning MmM and MMm should also be accepted.