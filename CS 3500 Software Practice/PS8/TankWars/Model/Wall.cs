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
    /// This class represents a wall in tank wars.
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class Wall
    {
        /// <summary>
        /// The unique int ID of this wall.
        /// </summary>
        [JsonProperty(PropertyName = "wall")]
        private int ID { get; set; }

        /// <summary>
        /// An end point of this wall given as Vector2D object.
        /// </summary>
        [JsonProperty(PropertyName = "p1")]
        private Vector2D point1 { get; set; }
        /// <summary>
        /// The other end point of this wall given as Vector2D object.
        /// </summary>
        [JsonProperty(PropertyName = "p2")]
        private Vector2D point2 { get; set; }
        /// <summary>
        /// An empty default constructor for JSON.
        /// </summary>
        public Wall()
        {
        }
        /// <summary>
        /// This method returns this wall's unique ID.
        /// </summary>
        /// <returns> This wall's ID. </returns>
        public int GetID()
        {
            return this.ID;
        }
        /// <summary>
        /// This method returns the two end points of this wall.
        /// </summary>
        /// <returns> A tuple containg this wall's two endpoints. </returns>
        public Tuple<Vector2D, Vector2D> GetLocation()
        {
            return new Tuple<Vector2D, Vector2D>(this.point1, this.point2);
        }

        /// <summary>
        /// This method returns the distance between the given Tuple of Vector2D objects that represents the locations of the two endpoints.
        /// </summary>
        /// <param name="location"> The Tuple that contains the two endpoints of a wall. </param>
        /// <returns> The distance between the given two endpoints. </returns>
        public int getDistance(Tuple<Vector2D, Vector2D> location)
        {
            // Gets the distance between the two points by subtracting them.
            if(location.Item1.GetX() == location.Item2.GetX())
            {
                return Convert.ToInt32(location.Item1.GetY() - location.Item2.GetY());
            }
            else
            {
                return Convert.ToInt32(location.Item1.GetX() - location.Item2.GetX());
            }
        }
    }
}
