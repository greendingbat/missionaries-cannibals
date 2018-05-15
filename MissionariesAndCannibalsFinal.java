// Matthew Burns
// CS211 Winter 2018
// Code to recursively solve the missionaries and cannibals problem


import java.util.*;

public class MissionariesAndCannibalsFinal {
   private int totalM;
   private int totalC;
   private int rightM;
   private int rightC;
   private int leftM;
   private int leftC;
   private int boatSize;
   private int moveNum;
   private String solutionSequence;

   public static void main(String[] args) {
   // this code can accept an arbitrary number of missionaries, cannibals, and boat size
   // BUT BE CAREFUL, as some combinations of elements cannot be solved and will result in infinite recursion
      MissionariesAndCannibalsFinal test = new MissionariesAndCannibalsFinal(3, 3, 2);
   }
   
   public MissionariesAndCannibalsFinal(int m, int c, int boat) {
      totalM = m;
      totalC = c;
      // all missionaries and cannibals start on left side of river
      leftM = totalM;
      leftC = totalC;
      boatSize = boat;
      solutionSequence = "";
      System.out.printf("Initial problem state (boat size %d):\n", boatSize);
      System.out.println(toString());
      explore("");
      System.out.println("Solved with this seqence of moves:");
      System.out.println(solutionSequence);
   }
   
   private void moveBoat(int m, int c, int moveNum) {
   // the direction of the boat can be derived from the move number,
   // as moves must alternate in direction
      if (moveNum % 2 == 0) {
         // ***LEFT*** --> right
         leftM -= m;
         leftC -= c;
         rightM += m;
         rightC += c;
                  
      } else {
         // left <--- ***RIGHT***
         rightM -= m;
         rightC -= c;
         leftM += m;
         leftC += c;        
      }
      
   }
   
      
   private boolean safeMove(int m, int c, int moveNum) {
      moveBoat(m, c, moveNum);
      if (rightM < 0 || rightC < 0 || leftM < 0 || leftC < 0) {
         // immediately fail if either shore contains less than zero of either type of person
         moveBoat(m, c, moveNum+1);
         return false;
      } else if (leftM != 0 && leftM < leftC) {
         // more C than (non-zero) M on left
         moveBoat(m, c, moveNum+1);
         return false;
      } else if (rightM != 0 && rightM < rightC) {
         // more C than (non-zero) M on right
         moveBoat(m, c, moveNum+1);
         return false;
      } else {
         moveBoat(m, c, moveNum+1);
         return true;
      }      
   }
   
         
   // This is the recursive function
   private boolean explore(String prevMove) {
      if (rightM == totalM && rightC == totalC) {
         System.out.println("Solved!\n");
         return true; // if everyone is on the right side, problem is solved
      } else {
         // try all move permutations
         for (int m=0; m<=boatSize; m++) {
            for (int c=0; c<=boatSize - m; c++) {            
               String currMove = m + "m" + c + "c";
               if (m == 0 && c == 0 || prevMove.equals(currMove)) {
                  continue; // ignore 0m0c moves or moves that undo the last move
               }
               
               if (safeMove(m, c, moveNum)) {// check validity
                  // make move
                  moveBoat(m, c, moveNum);
                  String dir;
                  if (moveNum % 2 == 0) {
                     dir = "--->";
                  } else {
                     dir = "<---";
                  }
                  System.out.printf("moving %dm and %dc %s\n", m, c, dir);
                  moveNum += 1;
                  System.out.println(toString());
                  if (explore(currMove)) {
                     solutionSequence += currMove + " " + dir + " \n";
                     return true;
                  }
                  
                  // undo move
                  moveNum -= 1;
                  moveBoat(m, c, moveNum+1); // calling moveBoat with moveNum+1 results in the opposite move being applied                 
                  System.out.println("undoing...");
                  System.out.println(toString());                 
               }    
            }
         }
         return false;            
      }      
   }
   
   public String toString() {
      return "M: " + leftM + "\t||\t M: " + rightM + "\nC: " + leftC + "\t||\t C: " + rightC + "\n";
   }

}