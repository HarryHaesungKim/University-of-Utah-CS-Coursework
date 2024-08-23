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
    /// This class reprensents a beam object.
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class Beam
    {
        /// <summary>
        /// A unique int ID for each beam.
        /// </summary>
        [JsonProperty(PropertyName = "beam")]
        private int ID { get; set; }
        /// <summary>
        /// A Vector2D object that is the origin point of this beam.
        /// </summary>
        [JsonProperty(PropertyName = "org")]
        private Vector2D origin { get; set; }
        /// <summary>
        /// The Vector2D object that is the direction at which this beam is traveling.
        /// </summary>
        [JsonProperty(PropertyName = "dir")]
        private Vector2D direction { get; set; }
        /// <summary>
        /// The unique int ID of the tank that fired this beam.
        /// </summary>
        [JsonProperty(PropertyName = "owner")]
        private int owner { get; set; }

        /// <summary>
        /// An empty default constructor for JSON.
        /// </summary>
        public Beam()
        {

        }

        /// <summary>
        /// This method returns this beam's unique ID.
        /// </summary>
        /// <returns> This beams ID </returns>
        public int GetID()
        {
            return ID;
        }
        /// <summary>
        /// This method returns this beam's origin represented as a Vector2D object.
        /// </summary>
        /// <returns> This beams Vector2D origin </returns>
        public Vector2D GetOrigin()
        {
            return origin;
        }
        /// <summary>
        /// This method returns this beam's direction represented as a Vector2D object.
        /// </summary>
        /// <returns> This beams direction Vector2D </returns>
        public Vector2D GetDirection()
        {
            return direction;
        }
    }
}
