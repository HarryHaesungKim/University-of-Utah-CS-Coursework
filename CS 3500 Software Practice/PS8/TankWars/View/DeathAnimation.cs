// Author: Harry Kim & Braden Morfin Spring 2021
// CS 3500 TankWars Project
// University of Utah

using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using TankWars;

namespace View
{
    /// <summary>
    /// This class represents the "animation" that takes place when a player dies.
    /// </summary>
    class DeathAnimation
    {
        private Vector2D location;
        private int numFrames;
        /// <summary>
        /// The DeathAnimation constructor that takes in the position to draw the animation.
        /// </summary>
        /// <param name="position"> The current position of the tank represented as a Vector2D object. </param>
        public DeathAnimation(Vector2D position)
        {
            location = position;
        }

        /// <summary>
        /// Gets the location of where to draw the animation.
        /// </summary>
        /// <returns> The location to draw the animation. </returns>
        public Vector2D GetLocation()
        {
            return location;
        }

        /// <summary>
        /// This method draws the death animation by each frame determined by the numFrames.
        /// </summary>
        /// <param name="o"> The object to draw. <param>
        /// <param name="e"> The PaintEventArgs to access the graphics. </param>
        public void DeathDrawer(object o, PaintEventArgs e)
        {
            // Drawing the "explostion" which moves via the number of frames in numerous directions.
            using (Pen pen = new Pen(Color.White, 3.0f))
            {
                Rectangle up = new Rectangle(0, 0 + numFrames, 3, 3);
                e.Graphics.DrawEllipse(pen, up);

                Rectangle down = new Rectangle(0, 0 - numFrames, 3, 3);
                e.Graphics.DrawEllipse(pen, down);

                Rectangle left = new Rectangle(0 - numFrames, 0, 3, 3);
                e.Graphics.DrawEllipse(pen, left);

                Rectangle right = new Rectangle(0 + numFrames, 0, 3, 3);
                e.Graphics.DrawEllipse(pen, right);

                Rectangle upleft = new Rectangle(0 - numFrames, 0 + numFrames, 3, 3);
                e.Graphics.DrawEllipse(pen, upleft);

                Rectangle downleft = new Rectangle(0 - numFrames, 0 - numFrames, 3, 3);
                e.Graphics.DrawEllipse(pen, downleft);

                Rectangle upright = new Rectangle(0 + numFrames, 0 + numFrames, 3, 3);
                e.Graphics.DrawEllipse(pen, upright);

                Rectangle downright = new Rectangle(0 + numFrames, 0 - numFrames, 3, 3);
                e.Graphics.DrawEllipse(pen, downright);
            }
            numFrames++;
        }

        /// <summary>
        /// Returns the current number of frames a DeathAnimation object currently holds.
        /// </summary>
        /// <returns> The number of "frames". </returns>
        public int GetNumFrames()
        {
            return numFrames;
        }
    }
}
