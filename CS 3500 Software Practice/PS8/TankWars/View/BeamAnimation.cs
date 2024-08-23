// Author: Harry Kim & Braden Morfin Spring 2021
// CS 3500 TankWars Project
// University of Utah

using Model;
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
    /// This class represents the "animation" that takes place when a player shoots a beam.
    /// </summary>
    class BeamAnimation
    {
        // provided by controller when beam event is fired by server.
        private Vector2D origin;
        private Vector2D orientation;

        private int numFrames = 0;
        /// <summary>
        /// The BeamAnimation constructor that takes in the position to start the beam and the direction the beam is fired.
        /// </summary>
        /// <param name="position"> The beam's origin. </param>
        /// <param name="direction"> The direction of the beam being fired at. </param>
        public BeamAnimation(Vector2D position, Vector2D direction)
        {
            origin = new Vector2D(position);
            orientation = new Vector2D(direction);
        }

        /// <summary>
        /// Returns the beam's origin.
        /// </summary>
        /// <returns> The beam's origin. </returns>
        public Vector2D GetOrigin()
        {
            return origin;
        }

        /// <summary>
        /// Returns the beam's orientation.
        /// </summary>
        /// <returns> The beam's orientation. </returns>
        public Vector2D GetOrientation()
        {
            return orientation;
        }

        /// <summary>
        /// This method draws the beam animation by each frame determined by the numFrames.
        /// </summary>
        /// <param name="o"> The object to draw. <param>
        /// <param name="e"> The PaintEventArgs to access the graphics. </param>
        public void BeamDrawer(object o, PaintEventArgs e)
        {
            using(Pen pen = new Pen(Color.Red, 20.0f - numFrames))
            {
                e.Graphics.DrawLine(pen, new Point(0, 0), new Point(0, -5000));
            }

            using (Pen pen = new Pen(Color.White, 10.0f - numFrames))
            {
                e.Graphics.DrawLine(pen, new Point(0, 0), new Point(0, -5000));
            }

            numFrames++;
        }

        /// <summary>
        /// Returns the current number of frames a BeamAnimation object currently holds.
        /// </summary>
        /// <returns> The number of "frames". </returns>
        public int GetNumFrames()
        {
            return numFrames;
        }
    }
}
