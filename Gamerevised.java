
import java.util.*;
import java.lang.*;
import java.io.*;

enum Move {
    CHEAT, COOPERATE; 
}

abstract class Player
{
    String name, type;
    Move moveType = null;
    int score;
    Machine mac;
    Scanner scan;
    void setPlayerType(String name){
        this.type = type;
    }
     String getPlayerType(){
        return this.name;
     }
    void setPlayerName(String name){
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
     abstract Move getMoveType();
}

class Copycat extends Player
{
    Copycat(Machine mac){
         this.mac = mac;
    }
    Move getMoveType(){
        Move oppmove = mac.getNextMove();
        if(oppmove == null)
        {
             this.moveType = Move.COOPERATE;

        }
        else
        {
            int move = oppmove.ordinal();
            this.moveType = Move.values()[move];
        } 
        return this.moveType;
    }
}

class Grudger extends Player
{
    int cheatcount = 0;
    Grudger(Machine mac){
         this.mac = mac;
    }
    Move getMoveType(){
        Move oppmove = mac.getNextMove();
        if((cheatcount == 0 && oppmove == Move.CHEAT) || cheatcount == 1)
        {
             this.moveType = Move.CHEAT;
             this.cheatcount = 1;

        }
        else
        {
            this.moveType = Move.COOPERATE;
        } 
        return this.moveType;
    }
}

class Detective extends Player
{
    Move initialMoves[] = {Move.COOPERATE,Move.CHEAT,Move.COOPERATE,Move.COOPERATE};
    int cheatcount = 0;

    Detective(Machine mac){
         this.mac = mac;
    }

    Move getMoveType()
    {
        int i = mac.i;
        if(i <= 4)
        {
            Move oppmove = mac.getNextMove();
            if(oppmove == Move.CHEAT)
            {
                cheatcount = cheatcount + 1;
            }
             this.moveType = initialMoves[i-1];
        }
        else if( i >= 5 && cheatcount == 0)
        {
             this.moveType = Move.CHEAT;
        }
        else if( i >= 5 && cheatcount >= 1 )
        {
            Move oppmove = mac.getNextMove();
            int move = oppmove.ordinal();
            this.moveType = Move.values()[move];
        }
        return this.moveType;

    }
}

class Cooperate extends Player
{
    Move getMoveType(){
        return this.moveType = Move.COOPERATE;
    }
}

class Cheat extends Player
{
    Move getMoveType(){
        return this.moveType = Move.CHEAT;
    }
}

class Human extends Player
{
    Human(Scanner in){
        this.scan = in;
    }
    Move getMoveType(){
        int move = scan.nextInt();
        return this.moveType = Move.values()[move];
    }
}

class Machine
{
    Player playera;
    Player playerb;
    int rounds;
    Move finalmoveA, finalmoveB; 
    int coinA;
    int coinB;
    public int i;
    void calculateCoins(Move moveA, Move moveB){
     // String aopt = moveA;
     // String bopt = moveB;
    int  scoreA = playera.getScore();
    int  scoreB = playerb.getScore();
    if(moveA == Move.CHEAT && moveB == Move.CHEAT){
        this.coinA = scoreA;
        this.coinB = scoreB;
    }
     else if(moveA == Move.COOPERATE && moveB == Move.COOPERATE){
        this.coinA = scoreA + 2;
        this.coinB = scoreB + 2;
    }
    else if(moveA == Move.CHEAT && moveB == Move.COOPERATE){
        this.coinA = scoreA + 3;
        this.coinB = scoreB - 1;
    }
    else if(moveA == Move.COOPERATE && moveB == Move.CHEAT){
        this.coinA = scoreA - 1;
        this.coinB = scoreB + 3;
    }
    playera.setScore(coinA);
    playerb.setScore(coinB);
    }
    void play(Player playera,Player playerb, int round){
    this.playera = playera;
    this.playerb = playerb;
    this.rounds = round;
    for(i=1; i<=rounds; i++)
    {
        System.out.println("Round " + i);
        System.out.println("Input from " + playera.getPlayer() + "(Player A) - 0(Cheat)/1(Cooperate)");
        finalmoveA = playera.getMoveType();
        System.out.println("The move of "+ playera.getPlayer() +" is : " + finalmoveA.ordinal());
        System.out.println("Input from " + playerb.getPlayer() + "(Player B) - 0(Cheat)/1(Cooperate)");
        finalmoveB = playerb.getMoveType();
        System.out.println("The move of "+ playerb.getPlayer() +" is : " +finalmoveB.ordinal());
        calculateCoins(finalmoveA, finalmoveB);
    }
}
    int playerFind = 1;
    public Move getNextMove(){
        Move returnMove = null;
        if((playerFind %2 == 1) && (playera instanceof Copycat || playera instanceof Grudger || playera instanceof Detective))
        {
            System.out.println("B move : " + finalmoveB);
            returnMove = finalmoveB;
            playerFind = playerFind + 1;
        }
        else if((playerFind %2 == 0) && (playerb instanceof Copycat || playerb instanceof Grudger || playerb instanceof Detective))
        {
            System.out.println("A move : " + finalmoveA);
            returnMove = finalmoveA;
            playerFind = playerFind + 1;
        }
        return returnMove;
    }
}

public class Gamerevised
{
    public static Scanner in = new Scanner (System.in);
    public static Player playera = null;
    public static Player playerb = null;
    public static Machine mac = new Machine();
    public static int alternateEnter = 1;
  static void createPlayer(String type)
    {
    String playerType = type.toLowerCase();
    if(alternateEnter % 2 == 1){
        switch(playerType){
            case "copycat" : playera = new Copycat(mac); break;
            case "cooperate" : playera = new Cooperate();break;
            case "human" : playera = new Human(in); break;
            case "grudger" : playera = new Grudger(mac); break;
            case "cheat" : playera = new Cheat(); break;
            case "detective" : playera = new Detective(mac);break;
        }
        alternateEnter = alternateEnter + 1;
    }
    else
    {
        switch(playerType){
            case "copycat" : playerb = new Copycat(mac); break;
            case "cooperate" : playerb = new Cooperate();break;
            case "human" : playerb = new Human(in); break;
            case "grudger" : playerb = new Grudger(mac); break;
            case "cheat" : playerb = new Cheat(); break;
            case "detective" : playerb = new Detective(mac);break;
        }
        alternateEnter = alternateEnter + 1;
    }
}
  public static void main (String[]args)
  {
    System.out.println ("Let's begin the game.");
    System.out.println ("Tell me the number of rounds you would like to play");
    int rounds = in.nextInt ();
    System.out.println ("Choose playerA type - Copycat/Cooperate/Human/Cheat/Grudger/Detective");
    String typeA = in.next ();
    createPlayer(typeA);
    System.out.println ("Choose playerB type - Copycat/Cooperate/Human/Cheat/Grudger/Detective");
    String typeB = in.next ();
    createPlayer(typeB);
    System.out.println ("Tell the name of playerA");
    String nameA = in.next ();
    System.out.println ("Tell the name of playerB");
    String nameB = in.next ();
    playera.setPlayerName(nameA);
    playerb.setPlayerName(nameB);
    mac.play(playera, playerb, rounds);
    System.out.println("Score of " + playera.getPlayer() + "(Player A) is: " + playera.getScore());
    System.out.println("Score of " + playerb.getPlayer() + "(Player B) is: " + playerb.getScore());
  }
}