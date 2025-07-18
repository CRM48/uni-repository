## Initial thoughts
    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |
Initially this problem looks super straight forward, create a main method which
takes two integer inputs. Create a calculation class which returns the formula
and create a factorization class which simply performs a factorization of
whatever int is given to it. Initially this looks like it works, however, it's
once you start using integers bigger than 20 that it starts to not. This is
because we are limited to integers of size 64 bits or smaller. 

## Plans
After this discovery we had one main revelation: in maths, it doesn't matter
what order a set of numbers are multiplied or divided, you will always get the
same result (assuming you multiply and divide the same numbers). Working on this
idea at first we thought it'd be best to extend our current class by just
multiplying the final outcome by any ints bigger than 20 (assuming n-k was less
than 20). E.g., if we had n = 23 and k = 5, then we would do 20!/(5!*18!) * 21 *
22 * 23. This did work, but only for a few ints beyond 20.

It was after testing a few other methods to extend the porgram that we realised
this wasn't the way to approach the problem. By just extending a program that
was already close to the max bit length you could only get so far. Because of
this we though of a new idea to solve the problem.

The new idea involved looking back at our original revelation, using more maths
to solve the problem. By deconstructing the formula it was easy to seperate it
into smaller ints, the question was how do we solve the formula sperated. We 
figured the best way would be to solve the formula all together and
continuously multiply then divide until we got to the correct answer. To do
this we first had to examine the formula: n!/k!(n-k!). One main thing to
notice is that k! and n-k! will always be less than n!. Because of this, you
can always divide n by one of them to require less overall calculations. For
example, say you had n = 10 and k = 2, instead of calculating:
(10*9*8*7*6*5*4*3*2*1) / (2*1)*(8*7*6*5*4*3*2*1) you could instead simplify
the formula and calculate: (10*9) / (2*1) by doing n/n-k first. Or, if k was
bigger than n-k you could instead simplify with n/k first as this would
simplify it further. This wasn't much of a revelation but it was a start. Now
the question is, how can we alternate between multiply and divide?

To do this, we thought it would be best to create a loop that multiplys
by the first digit of n, and the first digit of k (or n-k depending on what
one is smaller). To do this we created a loop that started at n and ended at
min. Min was a long that was the larger of k or n-k. The looped decreased by
1 each time till n = min. In the loop we multiplied nk (our long that keeps
track of the product of each calculation) by i and divided by n - min. In the
example above this would be (1 * 10) (10 / 2) (for the first iteration) then 
(5 * 9) (45 / 1) for the second (resulting in 45 as the final answer).

After submitting this implementation on autojudge, it still said it was the
wrong answer despite working for all the smaller cases. We figured out that
this was due to us using long values instead of double values, while this
worked for the first implementation, it didn't work for the second as we had
to divide by values that didn't always result in a positive number