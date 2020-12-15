
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
     void playerA(String name){
        this.name = name;
    }
     String getPlayerA(){
        return this.name;
     }
     void setScore(int score){
        this.score = score;
    }
      int getScore(){
        return this.score;
    }
     void setmoveType(String move){
        this.moveType = move;
    }
     String getmoveType(){
        return this.moveType;
    }
}

class PlayerB extends Player
{
    String name;
    String moveType;
    int score;
     void playerB(String name){
        this.name = name;
    }
    String getPlayerB(){
        return this.name;
     }
     void setScore(int score){
        this.score = score;
    }
      int getScore(){
        return this.score;
    }
     void setmoveType(String move){
        this.moveType = move;
    }
     String getmoveType(){
        return this.moveType;
    }
}

class Copycat
{
    String move;
    void copycat(Player playercomp, Player player){
        move = playercomp.getmoveType();
        if(player instanceof PlayerA && move == null)
        {
            player.setmoveType("Cooperate");
        }
        else
        {
            player.setmoveType(move);
        } 
    }
}

class Cooperate
{
    void cooperate(Player player){
        player.setmoveType("Cooperate");
    }
}


class Machine
{
    PlayerA playa;
    PlayerB playb;
    Machine(PlayerA playera, PlayerB playerb){
       this.playa = playera;
       this.playb = playerb;
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
}


class Movecal{

    Machine mac;
    PlayerA playera;
    PlayerB playerb;
    Copycat copycat;
    Cooperate cooperate;
    String movetypeA;
    String movetypeB;
    Scanner scan;
    Movecal(PlayerA playera, PlayerB playerb, Machine mac, Scanner in, Copycat copycat, Cooperate cooperate){
    this.playera = playera;
    this.playerb = playerb;
    this.mac = mac;
    this.scan = in;
    this.copycat = copycat;
    this.cooperate = cooperate;
    }
    String finalmoveA;
    String finalmoveB;
    int rounds;
    public void inputtimes(int rounds){
    this.rounds = rounds;
    for (int i=1; i<=rounds; i++)
    {
    movetypeB = playerb.getmoveType();  
    String nameA = playera.getPlayerA();
    String nameB = playerb.getPlayerB();  
    System.out.println("Round " + i);
    System.out.println("Input from " + nameA + "(Player A) - Cheat/Cooperate");
        if(nameA.equals("Copycat")){
            copycat.copycat(playerb, playera);
            finalmoveA = playera.getmoveType();
            System.out.println(finalmoveA);
        }
        else if(nameA.equals("Cooperate"))
        {
            cooperate.cooperate(playera);
            finalmoveA = playera.getmoveType();
            System.out.println(finalmoveA);
        } 
        else
        {
            finalmoveA = scan.next();
            playera.setmoveType(finalmoveA);
        } 
    System.out.println("Input from " + nameB + "(Player B) - Cheat/Cooperate");
        if(nameB.equals("Copycat")){
            copycat.copycat(playera, playerb);
            finalmoveB = playerb.getmoveType();
            System.out.println(finalmoveB);
        }
        else if(nameB.equals("Cooperate"))
        {
            cooperate.cooperate(playerb);
            finalmoveB = playerb.getmoveType();
            System.out.println(finalmoveB);
        } 
        else
        {
            finalmoveB = scan.next();
            playerb.setmoveType(finalmoveB);
        } 
    mac.calculateCoins(finalmoveA, finalmoveB);
}
}
}
class Rounds
{
    int rounds = 0;
    Movecal movcal;

    public void rounds(Movecal movecal,int round)
    {
        this.movcal = movecal;
        this.rounds = round;
        movcal.inputtimes(rounds);
    }
}

public class Gameoftrustbots
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
    Copycat copycat =  new Copycat();
    Cooperate cooperate =  new Cooperate();
    playera.playerA(nameA);
    playerb.playerB(nameB);
    Machine mac = new Machine(playera, playerb);
    Movecal movecal = new Movecal(playera,playerb,mac,in,copycat, cooperate);
    round.rounds(movecal,rounds);
    System.out.println("Score of " + playera.getPlayerA() + "(Player A) is: " + playera.getScore());
    System.out.println("Score of " + playerb.getPlayerB() + "(Player B) is: " + playerb.getScore());
  }
}