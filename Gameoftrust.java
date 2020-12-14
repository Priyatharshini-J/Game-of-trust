
import java.util.*;
import java.lang.*;
import java.io.*;

abstract class Player
{
    String name;
    int score;
    String moveType;
    abstract  void setScore(int score);
    abstract  int getScore();
    abstract  void setmoveType(String moveType);
    abstract  String getmoveType();
}


class PlayerA extends Player
{
    String name;
    String moveType;
    int score;
    PlayerCompare playcompare = new PlayerCompare();
     void playerA(String name){
        System.out.println("In A name");
        this.name = name;
        playcompare.playcomp(name);
    }
     String getPlayerA(){
        return name;
     }
     void setScore(int score){
        System.out.println("Score in setA : " + score);
        this.score = score;
    }
      int getScore(){
        System.out.println("Score in getA : " + this.score);
        return this.score;
    }
     void setmoveType(String move){
        this.moveType = move;
    }
     String getmoveType(){
        System.out.println("IN A move" + moveType);
        return this.moveType;
    }
}

class PlayerB extends Player
{
    String name;
    String moveType;
    int score;
    PlayerCompare playcompare = new PlayerCompare();
     void playerB(String name){
        System.out.println("IN B");
        this.name = name;
        playcompare.playcomp(name);
    }
    String getPlayerB(){
        return name;
     }
     void setScore(int score){
        System.out.println("Score in setB : " + score);
        this.score = score;
    }
      int getScore(){
        System.out.println("Score in getB : " + this.score);
        return this.score;
    }
     void setmoveType(String move){
        this.moveType = move;
    }
     String getmoveType(){
        return this.moveType;
    }
}

class PlayerCompare
{

    PlayerA playa = new PlayerA();
    PlayerB playb = new PlayerB();
    String nameA = playa.getPlayerA();
    String nameB = playb.getPlayerB();

    void playcomp(String name){
        if(name.equals(nameA) && name.equals("Copycat")){
        System.out.println("IN A copycat");
         String move = playb.getmoveType();
         if(move.equals(null))  {
            String movea = "Cooperate";
            playa.setmoveType(movea);
         }
         else {
            playa.setmoveType(move);
         }
        }
        else if(name.equals(nameA) && name.equals("Cooperate")){
            String movea = "Cooperate";
            playa.setmoveType(movea);
         }
         if(name.equals(nameB) && name.equals("Copycat")){
         System.out.println("IN B copycat");
         String move = playa.getmoveType();
         if(move.equals(null))  {
            String moveb = "Cooperate";
            playb.setmoveType(moveb);
         }
         else {
            playb.setmoveType(move);
         }
        }
        else if(name.equals(nameB) && name.equals("Cooperate")){
            String moveb = "Cooperate";
            playb.setmoveType(moveb);
         }
        }
}

class Machine
{
    Scanner in = new Scanner (System.in);
    PlayerA playa = new PlayerA();
    PlayerB playb = new PlayerB();
    // PlayerA playera;
    // PlayerA playerb;
    int coinA;
    int coinB;
     void calculateCoins(String moveA, String moveB){
    System.out.println("IN mac");
     String aopt = moveA;
     String  bopt = moveB;
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
}


class Movecal{

    Machine mac = new Machine();
    PlayerA playera = new PlayerA();
    PlayerB playerb = new PlayerB();
    String movetypeA = playera.getmoveType();
    String movetypeB = playerb.getmoveType();
    String finalmoveA;
    String finalmoveB;
    int rounds;
    Scanner scan = new Scanner (System.in);
    public void inputtimes(int rounds){
    this.rounds = rounds;
    for (int i=1; i<=rounds; i++)
    {
    System.out.println("IN roundscal");
    System.out.println("Round " + i);
    System.out.println("Input from PlayerA - Cheat/Cooperate");
    if(movetypeA.equals("null")){
    String moveA = scan.next();
    finalmoveA = moveA;
    playera.setmoveType(moveA);
    }
    else
    {
        String moveA = movetypeA;
        finalmoveA = moveA;
        System.out.println(moveA);
        playera.setmoveType(moveA);
    }
    System.out.println("Input from PlayerB - Cheat/Cooperate");
    if(movetypeB.equals("null")){
    String moveB = scan.next();
    finalmoveB = moveB;
    playerb.setmoveType(moveB);
    }
    else
    {
        String moveB = movetypeB;
        finalmoveB = moveB;
        System.out.println(moveB);
        playerb.setmoveType(moveB);
    }
    mac.calculateCoins(finalmoveA, finalmoveB);
    }
}
}

class Rounds
{
    int rounds = 0;
    Movecal movcal = new Movecal();
    public void rounds(int round)
    {
        System.out.println("IN rounds");
        this.rounds = round;
        System.out.println("In rou" + rounds);
        movcal.inputtimes(rounds);
    }
}

public class Gameoftrust
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
    PlayerA playera = new PlayerA();
    PlayerB playerb = new PlayerB();
    Rounds round = new Rounds();
    playera.playerA(nameA);
    playerb.playerB(nameB);
    round.rounds(rounds);
    System.out.println("Score of A is: " + playera.getScore());
    System.out.println("Score of B is: " + playerb.getScore());
  }
}
