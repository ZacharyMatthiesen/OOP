package com.cstaley.JavaOO.CodeWorlds.V2.release;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/* A HerdAnimal extends Animal behavior it two ways.  First, a HerdAnimal
 *  follows Animal behavior only until it finds another HerdAnimal to follow.
 *  Thenceforth, it imitates the steps of its leader identically.  Second, a
 *  HerdAnimal maintains a list of followers, who register with it by providing
 *  an object implementing interface Follower. The HerdAnimal notifies all its
 *  followers of each move it makes, so they can respond.  And if the
 *  starts following another HerdAnimal, it tells its followers to do so also.
 *  
 *  HerdAnimal leaves isGoodLeader abstract, so it must be implemented by
 *  any derived form of HerdAnimal, based on the derived form's following
 *  logic.
 */
public abstract class HerdAnimal extends Animal {
   private static ArrayList all = new ArrayList();
   
   private HerdAnimal myLeader = null;
   
   public HerdAnimal(Vector loc, Vector vlc) {
      super(loc, vlc);
      all.add(this);
   }
   
   abstract boolean isGoodLeader(HerdAnimal possibleLeader);
   
   @Override
   public void step() {
      HerdAnimal leader;
      Vector oldLoc = loc;
      
      if (myLeader == null) {  // If leaderless
         super.step();                     // plod on per usual Animal behavior
         leadFollowers(loc.minus(oldLoc)); // and lead your followers
 
         // Seek a leader
         for (Iterator itr = all.iterator(); itr.hasNext();) {
            leader = (HerdAnimal)itr.next();
            leader = leader.myLeader != null ? leader.myLeader : leader;
            
            if (leader != this && isGoodLeader(leader)) {
               System.out.printf("%s follows %s\n", this.toString(), leader.toString());
               myLeader = leader;
               myLeader.addFollower(new Follower());
               handOffFollowers(myLeader);
               break;
            }
         }
      }
   }

   // Follower code ----------------------------------
   
   public class Follower {
      public void leaderStep(Vector step)      {loc.plusBy(step);}
      public void changeLeader(HerdAnimal ldr) {
         myLeader = ldr;
         myLeader.addFollower(this);
      }
   }

   private LinkedList<Follower> myFollowers = new LinkedList<Follower>();

   public void addFollower(Follower follower) {myFollowers.add(follower);}
   
   public void leadFollowers(Vector incr) {
      for (Iterator<Follower> itr = myFollowers.iterator(); itr.hasNext();)
         itr.next().leaderStep(incr);
   }
   
   public void handOffFollowers(HerdAnimal newLeader) {
      for (Follower flw: myFollowers)
         flw.changeLeader(newLeader);
      myFollowers.clear();
   }
}