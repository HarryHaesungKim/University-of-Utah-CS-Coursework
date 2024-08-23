3/27/2021
Today we read over the assignment and set up the required projects. We also created classes in the model to represent the world
(World, Tank, Beam, Projectile, Wall, Powerups). 

4/7/21
We decided to make the beam a red lazer. It looks a lot better and awesome in our opinion.

4/8/21
For the death animation, we decided to make a sort of flash explosion, instead of particles, that kinda pulse out in all directions.
This is represented as the tank exploding so hard that they create a "blinding flash of light" and persists until the tank respawns.
This was intentional and is a design choice.

4/10/21
We changed our mind about the death animation. Now the tanks just explode with a few particles going in a few directions because we
were having bugs with drawing the previous death animation when players would disconnect. This ensures that the death animation does
not continuously animate when players disconnect.

*FOR PS9*

4/19/21
We added an extra game setting to the settings file called GameMode. To run the default gamemode, GameMode in the settings file must be 0.
To play the alternate gamemode, Gamemode in the settings file must be 1. Upon running, the server will run the specified gamemode only.
If a chosen gamemode doesn't exist (like 3 or something), the gamemode will default to settings for GameMode 0. If the settings file is
formatted incorrectly, an error will be shown that states that the file was unreadable.

4/20/21
For the alternate gamemode, we decided to do something similar to "One in the Chamber" from Call of Duty. In this gamemode, tanks can
only fire beams. A player starts off with one beam when they spawn in. Killing another player with their beam will replenish their beam
(gives them another shot). If a player misses and has no beam shots left, they must find a powerup within the world. Only players with
0 shots can pick up owerups. Only one powerup can spawn in the world at once. The number of beams a player has is kept after death unless
they have 0 shots in which case they will recieve one upon respawning. Score is kept the same (number of kills).