// Skeleton implementation written by Joe Zachary for CS 3500, September 2013.
// Version 1.1 (Fixed error in comment for RemoveDependency.)
// Version 1.2 - Daniel Kopta 
//               (Clarified meaning of dependent and dependee.)
//               (Clarified names in solution/project structure.)
// Version 1.3 - Harry Kim
//               (Filled methods with unique code.)

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpreadsheetUtilities
{

    /// <summary>
    /// (s1,t1) is an ordered pair of strings
    /// t1 depends on s1; s1 must be evaluated before t1
    /// 
    /// A DependencyGraph can be modeled as a set of ordered pairs of strings.  Two ordered pairs
    /// (s1,t1) and (s2,t2) are considered equal if and only if s1 equals s2 and t1 equals t2.
    /// Recall that sets never contain duplicates.  If an attempt is made to add an element to a 
    /// set, and the element is already in the set, the set remains unchanged.
    /// 
    /// Given a DependencyGraph DG: 
    /// 
    ///    (1) If s is a string, the set of all strings t such that (s,t) is in DG is called dependents(s).
    ///        (The set of things that depend on s)    
    ///        
    ///    (2) If s is a string, the set of all strings t such that (t,s) is in DG is called dependees(s).
    ///        (The set of things that s depends on) 
    //
    // For example, suppose DG = {("a", "b"), ("a", "c"), ("b", "d"), ("d", "d")}
    //     dependents("a") = {"b", "c"}
    //     dependents("b") = {"d"}
    //     dependents("c") = {}
    //     dependents("d") = {"d"}
    //     dependees("a") = {}
    //     dependees("b") = {"a"}
    //     dependees("c") = {"a"}
    //     dependees("d") = {"b", "d"}
    /// </summary>
    public class DependencyGraph
    {
        private Dictionary<string, HashSet<string>> DependencyGraphDictionary;
        private int NumOfOrderedPairs = 0;

        /// <summary>
        /// Creates an empty DependencyGraph.
        /// </summary>
        public DependencyGraph()
        {
            DependencyGraphDictionary = new Dictionary<string, HashSet<string>>();
        }

        /// <summary>
        /// The number of ordered pairs in the DependencyGraph.
        /// </summary>
        public int Size
        {
            get { return NumOfOrderedPairs; }
        }

        /// <summary>
        /// The size of dependees(s).
        /// This property is an example of an indexer.  If dg is a DependencyGraph, you would
        /// invoke it like this:
        /// dg["a"]
        /// It should return the size of dependees("a")
        /// </summary>
        public int this[string s]
        {
            get
            {
                /// Looping through every dependency to find dependees of a dependent.
                int NumOfDependees = 0;
                foreach (KeyValuePair<string, HashSet<string>> entry in DependencyGraphDictionary)
                {
                    if (entry.Value.Contains(s))
                    {
                        NumOfDependees++;
                    }
                }
                return NumOfDependees;
            }
        }


        /// <summary>
        /// Reports whether dependents(s) is non-empty.
        /// </summary>
        public bool HasDependents(string s)
        {
            /// Ensuring that s even exists within DependencyGraphDictionary.
            if (DependencyGraphDictionary.ContainsKey(s))
                return DependencyGraphDictionary[s].Count() != 0;
            else
                return false;
        }


        /// <summary>
        /// Reports whether dependees(s) is non-empty.
        /// </summary>
        public bool HasDependees(string s)
        {
            /// At least one instance of s must exist as a dependent in order to be true.
            foreach (KeyValuePair<string, HashSet<string>> entry in DependencyGraphDictionary)
            {
                if (entry.Value.Contains(s))
                {
                    return true;
                }
            }
            return false;
        }


        /// <summary>
        /// Enumerates dependents(s).
        /// </summary>
        public IEnumerable<string> GetDependents(string s)
        {
            /// Simply returns Hashset of key s.
            if (DependencyGraphDictionary.ContainsKey(s))
                return DependencyGraphDictionary[s];
            else
                return new HashSet<string>();
        }

        /// <summary>
        /// Enumerates dependees(s).
        /// </summary>
        public IEnumerable<string> GetDependees(string s)
        {
            /// Builds a new HashSet of Dependees by iterating through DependencyGraphDictionary.
            HashSet<string> Dependees = new HashSet<string>();
            foreach (KeyValuePair<string, HashSet<string>> entry in DependencyGraphDictionary)
            {
                if (entry.Value.Contains(s))
                {
                    Dependees.Add(entry.Key);
                }
            }
            return Dependees;
        }


        /// <summary>
        /// <para>Adds the ordered pair (s,t), if it doesn't exist</para>
        /// 
        /// <para>This should be thought of as:</para>   
        /// 
        ///   t depends on s
        ///
        /// </summary>
        /// <param name="s"> s must be evaluated first. T depends on S</param>
        /// <param name="t"> t cannot be evaluated until s is</param> 
        public void AddDependency(string s, string t)
        {
            /// Ensuring no duplicates of a dependency.
            if (DependencyGraphDictionary.ContainsKey(s))
            {
                if (!DependencyGraphDictionary[s].Contains(t))
                {
                    DependencyGraphDictionary[s].Add(t);
                    NumOfOrderedPairs++;
                }
            }
            /// Else create a new dependency with given inputs.
            else
            {
                HashSet<string> Dependents = new HashSet<string>();
                DependencyGraphDictionary.Add(s, Dependents);
                DependencyGraphDictionary[s].Add(t);
                NumOfOrderedPairs++;
            }
        }


        /// <summary>
        /// Removes the ordered pair (s,t), if it exists
        /// </summary>
        /// <param name="s"></param>
        /// <param name="t"></param>
        public void RemoveDependency(string s, string t)
        {
            if (DependencyGraphDictionary[s].Contains(t))
            {
                /// Removes from the specified hashset.
                if (DependencyGraphDictionary[s].Count > 1)
                {
                    DependencyGraphDictionary[s].Remove(t);
                    NumOfOrderedPairs--;
                }
                /// if the hashset is empty, remove the key value pair altogether.
                else
                {
                    DependencyGraphDictionary.Remove(s);
                    NumOfOrderedPairs--;
                }
            }
        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (s,r).  Then, for each
        /// t in newDependents, adds the ordered pair (s,t).
        /// </summary>
        public void ReplaceDependents(string s, IEnumerable<string> newDependents)
        {
            /// Do nothing if there are no dependencies.
            if (NumOfOrderedPairs < 1)
            {
                return;
            }
            /// Create a new dependency if the key s does not exist within DependencyGraphDictionary.
            if (!DependencyGraphDictionary.ContainsKey(s))
            {
                foreach (string newDependent in newDependents)
                {
                    this.AddDependency(s, newDependent);
                }
                return;
            }
            /// Remove key value pair altogether and add new dependencies one by one with new dependents and the same dependee.
            NumOfOrderedPairs = NumOfOrderedPairs - DependencyGraphDictionary[s].Count;
            DependencyGraphDictionary.Remove(s);
            foreach (string newDependent in newDependents)
            {
                this.AddDependency(s, newDependent);
            }
        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (r,s).  Then, for each 
        /// t in newDependees, adds the ordered pair (t,s).
        /// </summary>
        public void ReplaceDependees(string s, IEnumerable<string> newDependees)
        {
            foreach (KeyValuePair<string, HashSet<string>> entry in DependencyGraphDictionary)
            {
                /// Removing every instance of s as a dependee. 
                if (entry.Value.Contains(s))
                {
                    entry.Value.Remove(s);
                    NumOfOrderedPairs--;
                }
            }

            /// Adding new dependencies one by one with new dependees and the same dependent.
            foreach (string newDependent in newDependees)
            {
                this.AddDependency(newDependent, s);
            }
        }
    }
}