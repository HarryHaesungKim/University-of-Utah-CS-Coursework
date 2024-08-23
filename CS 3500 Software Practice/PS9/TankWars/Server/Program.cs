// Author: Harry Kim & Braden Morfin Spring 2021
// CS 3500 TankWars Project
// University of Utah

using Controller;
using Model;
using NetworkUtil;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Xml;
using TankWars;

namespace Server
{
    class Program
    {
        /// <summary>
        /// Set everything up and start an event loop for accepting connections, and start the frame loop (this one is not an event loop, and is on its own thread).
        /// </summary>
        /// <param name="args"></param>
        static void Main(string[] args)
        {
            // Start the server
            ServerController controller = new ServerController();
            controller.StartSever();
            Stopwatch watch = new Stopwatch();
            // Starts infinite loop
            while (true)
            {
                watch.Start();
                // Ensures that the world is only updated every so often measured in milliseconds (keeps constant frames per second).
                while (watch.ElapsedMilliseconds < controller.GetGameInfo().Item2) { /* do nothing */ }
                watch.Stop();
                watch.Restart();
                // update the world.
                controller.Update();
            }
            //Console.Read();
        }
    }
}
