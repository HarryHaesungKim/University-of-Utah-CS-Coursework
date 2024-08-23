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