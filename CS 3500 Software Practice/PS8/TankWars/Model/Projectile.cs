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
    /// This class represents a projectile shot by a tank in tank wars.
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class Projectile
    {
        /// <summary>
        /// The unique int ID given to this projectile.
        /// </summary>
        [JsonProperty(PropertyName = "proj")]
        private int ID { get; set; }
        /// <summary>
        /// Vector2D that represents the current location of this projectile.
        /// </summary>
        [JsonProperty(PropertyName = "loc")]
        private Vector2D location { get; set; }
        /// <summary>
        /// A Vector2D object that represents this projectiles direction of movement.
        /// </summary>
        [JsonProperty(PropertyName = "dir")]
        private Vector2D direction { get; set; }
        /// <summary>
        /// A boolean that keeps track whether or not this projectile is active.
        /// </summary>
        [JsonProperty(PropertyName = "died")]
        private bool didDie { get; set; }
        /// <summary>
        /// The unique int ID of the tank that fired this projectile.
        /// </summary>
        [JsonProperty(PropertyName = "owner")]
        private int ownerID { get; set; }

        /// <summary>
        /// An empty default constructor for JSON.
        /// </summary>
        public Projectile()
        {

        }

        /// <summary>
        /// This method returns this projectiles unique ID.
        /// </summary>
        /// <returns> This projectiles unique ID. </returns>
        public int GetID()
        {
            return ID;
        }
        /// <summary>
        /// This method returns the unique ID of the tank that fired this projectile.
        /// </summary>
        /// <returns> The ID of the tank that fired this projectile. </returns>
        public int GetOwnerID()
        {
            return ownerID;
        }
        /// <summary>
        /// This method returns this projectiles Vector2D location.
        /// </summary>
        /// <returns> This projectiles Vector2D location. </returns>
        public Vector2D GetLocation()
        {
            return location;
        }
        /// <summary>
        /// This method returns whether this projectile is active.
        /// </summary>
        /// <returns> Whether this projectile is active. </returns>
        public bool GetDied()
        {
            return didDie;
        }
        /// <summary>
        /// This method returns the Vector2D direction in which this projectile is moving.
        /// </summary>
        /// <returns> The direction at which the projectile is moving. </returns>
        public Vector2D GetDirection()
        {
            return direction;
        }
    }
}
