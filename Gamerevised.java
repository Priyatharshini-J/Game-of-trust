
import java.util.*;
import java.lang.*;
import java.io.*;

enum Moves {
    CHEAT, COOPERATE; 
}

abstract class Player
{
    String name;
    String moveType;
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
     String getmoveType(){
        return this.moveType;
    }
    abstract void setmoveType(String move);
}

class Copycat extends Player
{
    void setmoveType(String move){
        if(move == null){this.moveType = "Cooperate";}
        else{this.moveType = move;} 
    }
}

class Cooperate extends Player
{
    void setmoveType(String move){
        this.moveType = "Cooperate";
    }
}

class Human extends Player
{
    void setmoveType(String move){
        this.moveType = move;
    }
}


class Machine
{
    Player playa, playb;
    Scanner scan;
    int rounds;
    String finalmoveA;
    String finalmoveB;

    Machine(Player playera,Player playerb, int round, Scanner scan){
       this.playa = playera;
       this.playb = playerb;
       this.rounds = round;
       this.scan = scan;
    }
    int coinA;
    int coinB;
     void calculateCoins(String moveA, String moveB){
     String aopt = moveA;
     String bopt = moveB;
     int  scoreA = playa.getScore();
     int  scoreB = playb.getScore();
    if(aopt.equals("Cheat") && bopt.equals("Cheat")){
        this.coinA = scoreA;
        this.coinB = scoreB;
    }
     else if(aopt.equals("Cooperate") && bopt.equals("Cooperate")){
        this.coinA = scoreA + 2;
        this.coinB = scoreB + 2;
    }
    else if(aopt.equals("Cheat") && bopt.equals("Cooperate")){
        this.coinA = scoreA + 3;
        this.coinB = scoreB - 1;
    }
    else if(aopt.equals("Cooperate") && bopt.equals("Cheat")){
        this.coinA = scoreA - 1;
        this.coinB = scoreB + 3;
    }
    playa.setScore(coinA);
    playb.setScore(coinB);
    }
    void repeattimes(){
        for(int i=1; i<=rounds; i++)
        {
        String nameA = playa.getPlayer();
        String nameB = playb.getPlayer();
        String moveA = playa.getmoveType();
        String moveB = playb.getmoveType();
        System.out.println("Round " + i);
        System.out.println("Input from " + nameA + "(Player A) - Cheat/Cooperate");
        if(nameA.equals("Copycat")){
            playa.setmoveType(moveB);
            finalmoveA = playa.getmoveType();
            System.out.println(finalmoveA);
        }
        else if(nameA.equals("Cooperate"))
        {
            playa.setmoveType(moveB);
            finalmoveA = playa.getmoveType();
            System.out.println(finalmoveA);
        } 
        else
        {
            finalmoveA = scan.next();
            playa.setmoveType(finalmoveA);
        } 
        System.out.println("Input from " + nameB + "(Player B) - Cheat/Cooperate");
        if(nameB.equals("Copycat")){
            playb.setmoveType(moveA);
            finalmoveB = playa.getmoveType();
            System.out.println(finalmoveB);
        }
        else if(nameB.equals("Cooperate"))
        {
            playb.setmoveType(moveA);
            finalmoveB = playa.getmoveType();
            System.out.println(finalmoveB);
        } 
        else
        {
            finalmoveB = scan.next();
            playb.setmoveType(finalmoveB);
        } 
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
    if(nameA.equals("Cooperate")){
    Player playera = new Cooperate();
    }
    else if (nameA.equals("Copycat")){
    Player playera = new Copycat();
    }
    else if (nameA.equals("Human")){
    Player playera = new Human();
    }
    if(nameB.equals("Cooperate")){
    Player playerb = new Cooperate();
    }
    else if (nameB.equals("Copycat")){
    Player playerb = new Copycat();
    }
    else if (nameB.equals("Human")){
    Player playerb = new Human();
    }
    playera.player(nameA);
    playerb.player(nameB);
    Machine mac = new Machine(playera, playerb, rounds, in);
    mac.repeattimes();
    System.out.println("Score of " + playera.getPlayer() + "(Player A) is: " + playera.getScore());
    System.out.println("Score of " + playerb.getPlayer() + "(Player B) is: " + playerb.getScore());
  }
}