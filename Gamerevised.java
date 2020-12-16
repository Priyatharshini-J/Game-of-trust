
import java.util.*;
import java.lang.*;
import java.io.*;

enum Moves {
    CHEAT, COOPERATE; 
}

abstract class Player
{
    String name;
    Moves moveType = null;
    int score;
     void player(String name){
        this.name = name;
    }
     String getPlayer(){
        return this.name;
     }
     void setScore(int score){
        this.score = score;
    }
      int getScore(){
        return this.score;
    }
     Moves getmoveType(){
        return this.moveType;
    }
    abstract void setmoveType();
}

// class Copycat extends Player
// {

//     void setmoveType(int move){

//         if(move == null){this.moveType = Moves.COOPERATE;}
//         else{this.moveType = Moves.values()[move];} 
//     }
// }

class Cooperate extends Player
{
    void setmoveType(){
        this.moveType = Moves.COOPERATE;
    }
}

class Human extends Player
{
    Scanner scan = new Scanner(System.in);
    int move = scan.nextInt();
    void setmoveType(){
        this.moveType = Moves.values()[move];
    }
}


class Machine
{
    Player playa, playb;
    int rounds;

    Machine(Player playera,Player playerb, int round){
       this.playa = playera;
       this.playb = playerb;
       this.rounds = round;
    }
    int coinA;
    int coinB;
    String nameA = playa.getPlayer();
    String nameB = playb.getPlayer();
     void calculateCoins(Moves moveA, Moves moveB){
     // String aopt = moveA;
     // String bopt = moveB;
     int  scoreA = playa.getScore();
     int  scoreB = playb.getScore();
    if(moveA == Moves.CHEAT && moveB == Moves.CHEAT){
        this.coinA = scoreA;
        this.coinB = scoreB;
    }
     else if(moveA == Moves.COOPERATE && moveB == Moves.COOPERATE){
        this.coinA = scoreA + 2;
        this.coinB = scoreB + 2;
    }
    else if(moveA == Moves.CHEAT && moveB == Moves.COOPERATE){
        this.coinA = scoreA + 3;
        this.coinB = scoreB - 1;
    }
    else if(moveA == Moves.COOPERATE && moveB == Moves.CHEAT){
        this.coinA = scoreA - 1;
        this.coinB = scoreB + 3;
    }
    playa.setScore(coinA);
    playb.setScore(coinB);
    }
    void repeattimes(){
        System.out.println("In repeat");
        for(int i=1; i<=rounds; i++)
        {
        System.out.println("Round " + i);
        System.out.println("Input from " + nameA + "(Player A) - 0(Cheat)/1(Cooperate)");
        playa.setmoveType();
        Moves finalmoveA = playa.getmoveType();
        System.out.println("Input from " + nameB + "(Player B) - 0(Cheat)/1(Cooperate)");
        playb.setmoveType();
        Moves finalmoveB = playb.getmoveType();
        calculateCoins(finalmoveA, finalmoveB);
        }
    }
}

public class Gamerevised
{
  public static void main (String[]args)
  {
    System.out.println ("Let's begin the game.");
    Scanner in = new Scanner (System.in);
    System.out.println ("Tell me the number of rounds you would like to play");
    int rounds = in.nextInt ();
    System.out.println ("Choose playerA - Copycat/Cooperate/Human");
    String nameA = in.next ();
    System.out.println ("Choose playerB - Copycat/Cooperate/Human");
    String nameB = in.next ();
    Player playera = null;
    Player playerb = null;
    if(nameA.equals("Cooperate")){
    playera = new Cooperate();
    }
    else if (nameA.equals("Copycat")){
    playera = new Copycat();
    }
    else if (nameA.equals("Human")){
    playera = new Human();
    System.out.println("Here");
    }
    if(nameB.equals("Cooperate")){
    playerb = new Cooperate();
    }
    else if (nameB.equals("Copycat")){
    playerb = new Copycat();
    }
    else if (nameB.equals("Human")){
    playerb = new Human();
    }
    playera.player(nameA);
    playerb.player(nameB);
    Machine mac = new Machine(playera, playerb, rounds);
    mac.repeattimes();
    System.out.println("Score of " + playera.getPlayer() + "(Player A) is: " + playera.getScore());
    System.out.println("Score of " + playerb.getPlayer() + "(Player B) is: " + playerb.getScore());
  }
}