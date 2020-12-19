
import java.util.*;
import java.lang.*;
import java.io.*;

enum Move
{
  CHEAT, COOPERATE;
}

abstract class Player
{
  String name, type;
  Move moveType = null;
  int score;
  void setPlayerName (String name)
  {
    this.name = name;
  }
  String getPlayerName ()
  {
    return this.name;
  }
  void setScore (int score)
  {
    this.score = score;
  }
  int getScore ()
  {
    return this.score;
  }
  abstract Move getMoveType ();
}

class Copycat extends Player
{
  Machine mac;
    Copycat (Machine mac)
  {
    this.mac = mac;
  }
  Move getMoveType ()
  {
    Move oppmove = mac.getNextMove ();
    if (oppmove == null)
    {
    this.moveType = Move.COOPERATE;
    }
    else
    {
    int move = oppmove.ordinal ();
    this.moveType = Move.values ()[move];
    }
    return this.moveType;
  }
}

class Grudger extends Player
{
  Machine mac;
  int cheatcount = 0;
    Grudger (Machine mac)
  {
    this.mac = mac;
  }
  Move getMoveType ()
  {
    Move oppmove = mac.getNextMove ();
    if ((cheatcount == 0 && oppmove == Move.CHEAT) || cheatcount == 1)
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
  Machine mac;
  Move initialMoves[] = { Move.COOPERATE, Move.CHEAT, Move.COOPERATE, Move.COOPERATE };
  int cheatcount = 0;

  Detective (Machine mac)
  {
    this.mac = mac;
  }

  Move getMoveType ()
  {
    Move oppmove = mac.getNextMove ();
    int i = mac.i;
    if (i <= 4)
    {
      if (oppmove == Move.CHEAT)
        {
          cheatcount = cheatcount + 1;
        }
        this.moveType = initialMoves[i - 1];
    }
    else if (i >= 5 && cheatcount == 0)
    {
        this.moveType = Move.CHEAT;
    }
    else if (i >= 5 && cheatcount >= 1)
    {
        this.moveType = oppmove;
    }
    return this.moveType;

  }
}

class Cooperate extends Player
{
  Machine mac;
    Cooperate (Machine mac)
  {
    this.mac = mac;
  }
  Move getMoveType ()
  {
    mac.getNextMove ();
    return this.moveType = Move.COOPERATE;
  }
}

class Cheat extends Player
{
  Machine mac;
    Cheat (Machine mac)
  {
    this.mac = mac;
  }
  Move getMoveType ()
  {
    mac.getNextMove ();
    return this.moveType = Move.CHEAT;
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
  void calculateCoins (Move moveA, Move moveB)
  {
    int scoreA = playera.getScore ();
    int scoreB = playerb.getScore ();
    if (moveA == Move.CHEAT && moveB == Move.CHEAT)
      {
    this.coinA = scoreA;
    this.coinB = scoreB;
      }
    else if (moveA == Move.COOPERATE && moveB == Move.COOPERATE)
      {
    this.coinA = scoreA + 2;
    this.coinB = scoreB + 2;
      }
    else if (moveA == Move.CHEAT && moveB == Move.COOPERATE)
      {
    this.coinA = scoreA + 3;
    this.coinB = scoreB - 1;
      }
    else if (moveA == Move.COOPERATE && moveB == Move.CHEAT)
      {
    this.coinA = scoreA - 1;
    this.coinB = scoreB + 3;
      }
    playera.setScore (coinA);
    playerb.setScore (coinB);
  }
  void play (Player playera, Player playerb)
  {
    this.playera = playera;
    this.playerb = playerb;
    for (i = 1; i <= 10; i++)
      {
    finalmoveA = playera.getMoveType ();
    finalmoveB = playerb.getMoveType ();
    calculateCoins (finalmoveA, finalmoveB);
      }
  }
  int playerFind = 1;
  Move returnMove = null;
  Move getNextMove ()
  {
    if (playerFind % 2 == 1)
      {
    this.returnMove = finalmoveB;
    playerFind = playerFind + 1;
      }
    else if (playerFind % 2 == 0)
      {
    this.returnMove = finalmoveA;
    playerFind = playerFind + 1;
      }
    return this.returnMove;
  }
  void makingNull()
  {
    finalmoveA = null;
    finalmoveB = null;
  }
}

public class Tournament
{
  public static Player playera = null;
  public static Player playerb = null;
  public static Machine mac = new Machine ();
  public static int copycatScore, cooperateScore, grudgerScore, cheatScore, detectiveScore;
  static Player createPlayerObject (String type)
  {
    Player player = null;
    switch (type)
      {
      case "Copycat":player = new Copycat (mac);
      break;
      case "Cooperate":player = new Cooperate (mac);
      break;
      case "Grudger":player = new Grudger (mac);
      break;
      case "Cheat":player = new Cheat (mac);
      break;
      case "Detective":player = new Detective (mac);
      break;
      }
    return player;
  }
  static void finalScore(String type, int score)
  {
    switch (type)
      {
      case "Copycat":copycatScore = copycatScore + score;
      break;
      case "Cooperate":cooperateScore = cooperateScore + score;
      break;
      case "Grudger":grudgerScore = grudgerScore + score;
      break;
      case "Cheat":cheatScore = cheatScore + score;
      break;
      case "Detective":detectiveScore = detectiveScore + score;
      break;
      }
  }
  public static void main (String[]args)
  {
    int i, j;
    String players[] = {"Copycat", "Cheat", "Cooperate", "Grudger", "Detective"};
    for (i = 0; i < players.length - 1; i++)
    {
      for(j = i + 1; j<= players.length - 1; j++)
      {
      playera = createPlayerObject(players[i]);
      playerb = createPlayerObject(players[j]);
      playera.setPlayerName (players[i]);
      playerb.setPlayerName (players[j]);
      mac.play(playera, playerb);
      String typeA = playera.getPlayerName();
      String typeB = playerb.getPlayerName();
      int scoreA = playera.getScore();
      int scoreB = playerb.getScore();
      finalScore(typeA, scoreA);
      finalScore(typeB, scoreB);
      mac.makingNull();
    }
    }
    System.out.println("Final score of Copycat is : " + copycatScore);
    System.out.println("Final score of Cheat is : " + cheatScore);
    System.out.println("Final score of Cooperate is : " + cooperateScore);
    System.out.println("Final score of Grudger is : " + grudgerScore);
    System.out.println("Final score of Detective is : " + detectiveScore);
  }
}
