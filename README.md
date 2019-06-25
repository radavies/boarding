# Boarding
A terminal style airplane boarding simulation.

The program simulates the boarding of an A320 style aircraft using several different boarding methods:

* In order back to front
* In order front to back
* Outside In (window seats first)
* Zone boarding (4 boarding sections starting at the rear, passengers random within zone)
* Random

The program outputs each step as an "overhead map". The left set of three are the port side seats, the single set in the middle is the isle and the right set of three are the starboard side seats.

* 0 = Space empty
* X = Space taken by one passenger
* M = Space taken by multiple passengers (only allowed in isle when a row must shuffle to allow a passenger into a seat)

At the end of a run the number of steps for each boarding is displayed. Typically outside in appears to be significantly better than any other method. The commonly used zone method is typcially quite a poor performer, ranking simillarly to fully random.

**Map Example**

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[000][X][000]

[XXX][X][XXX]

[X00][M][XXX]

[XXX][X][X00]

[000][0][0X0]

[XX0][0][X0X]

[000][0][X0X]

[X0X][X][0X0]

[XXX][0][XXX]

[XXX][0][XXX]

[XXX][0][XXX]

[XXX][0][XXX]

[XXX][0][XXX]

[XXX][0][XXX]

[XXX][0][XXX]


