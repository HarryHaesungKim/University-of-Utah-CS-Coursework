// Author: Harry Kim & Braden Morfin Spring 2021
// CS 3500 TankWars Project
// University of Utah

using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using TankWars;

namespace Model
{
    /// <summary>
    /// This class represents the data used to interpret controls to send to the server.
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class ControlCommands
    {
        /// <summary>
        /// The direction the player wishse to move (up, down, left, right, and none if still).
        /// </summary>
        [JsonProperty(PropertyName = "moving")]
        private string directionOfMovement { get; set; }
        /// <summary>
        /// The way the player desires to fire (main, alt, and "none" if not firing).
        /// </summary>
        [JsonProperty(PropertyName = "fire")]
        private string fire { get; set; }
        /// <summary>
        /// The direction at which the player wishes to fire .
        /// </summary>
        [JsonProperty(PropertyName = "tdir")]
        private Vector2D aim { get; set; }

        /// <summary>
        /// An Empty default constructor for JSON.
        /// </summary>
        public ControlCommands()
        {

        }

        /// <summary>
        /// A constructor that creates a ControlCommands object with the specified parameters.
        /// </summary>
        /// <param name="moving"> The direction the player wishes to move. </param>
        /// <param name="fire"> The type of fire the player wishes to shoot. </param>
        /// <param name="direction"> The direction the player wishes to fire (if they fire). </param>
        public ControlCommands(string moving, string fire, Vector2D direction)
        {
            this.directionOfMovement = moving;
            this.fire = fire;
            this.aim = direction;
        }
    }
}
