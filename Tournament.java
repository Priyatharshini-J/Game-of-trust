
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
  int score = 0;
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
    Move oppmove = mac.getOppMove ();
    mac.getMyMove();
    if (oppmove == null)
    {
    this.moveType = Move.COOPERATE;
    }
    else
    {
      this.moveType = oppmove;
    }
    return this.moveType;
  }
}

class Copykitten extends Player
{
  Machine mac;
  int cheatcount = 0;
    Copykitten (Machine mac)
  {
    this.mac = mac;
  }
  Move getMoveType ()
  {
    Move oppmove = mac.getOppMove ();
    Move mymove = mac.getMyMove();
    if(oppmove == Move.CHEAT)
    {
      cheatcount = cheatcount + 1;
    }
    else
    {
        cheatcount = 0;
    } 
    int i = mac.i;
    if(cheatcount <= 2)
    {
      this.moveType = Move.COOPERATE;
    }
    else
    {
      this.moveType = oppmove;
    }
    return this.moveType;
  }
}

class Simpleton extends Player
{
  Machine mac;
  Simpleton(Machine mac)
  {
    this.mac = mac;
  }
  Move getMoveType ()
  {
    Move oppmove = mac.getOppMove ();
    Move mymove = mac.getMyMove();
    int i = mac.i;
    if(i == 1)
    {
      this.moveType = Move.COOPERATE;
    }
    if(oppmove == Move.COOPERATE)
    {
      this.moveType = mymove;
    }
    else if(oppmove == Move.CHEAT)
    {
      int moveno = mymove.ordinal();
      switch(moveno)
      {
        case 1 : this.moveType = Move.CHEAT;
        break; 
        case 0 : this.moveType = Move.COOPERATE;
        break;
      }
    }
      return this.moveType;
  }
}

class Randomplay extends Player
{
  Machine mac;
  int min = 0;
  int max = 1;
  Randomplay(Machine mac)
  {
    this.mac = mac;
  }
  Move getMoveType ()
  {
    mac.getOppMove();
    mac.getMyMove();
    int moveno =  (int)(Math.random()*(max-min+1)+min);
    this.moveType = Move.values ()[moveno];
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
    Move oppmove = mac.getOppMove ();
    mac.getMyMove();
    int i = mac.i;
    if(i == 1)
    {
        this.moveType = Move.COOPERATE;
    }
    else if ((cheatcount == 0 && oppmove == Move.CHEAT) || cheatcount == 1)
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
  Move initialMoves[] = { Move.COOPERATE, Move.CHEAT, Move.COOPERATE, Move.COOPERATE};
  int cheatcount = 0;

  Detective (Machine mac)
  {
    this.mac = mac;
  }

  Move getMoveType ()
  {
    Move oppmove = mac.getOppMove ();
    mac.getMyMove();
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
    mac.getOppMove ();
    mac.getMyMove();
    return this.moveType = Move.COOPERATE;
  }
}

class Cheat extends Player
{
  static int score;
  Machine mac;
    Cheat (Machine mac)
  {
    this.mac = mac;
  }
  Move getMoveType ()
  {
    mac.getOppMove ();
    mac.getMyMove();
    return this.moveType = Move.CHEAT;
  }
}

class Human extends Player
{
  Scanner scan;
  Machine mac;
    Human (Scanner in, Machine mac)
  {
    this.scan = in;
    this.mac = mac;
  }
  Move getMoveType ()
  {
    mac.getOppMove ();
    mac.getMyMove();
    System.out.println("Enter your move - 0(Cheat) / 1(Cooperate)");
    int move = scan.nextInt ();
    return this.moveType = Move.values ()[move];
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
  void play (Player playera, Player playerb, int rounds)
  {
    this.playera = playera;
    this.playerb = playerb;
    this.rounds = rounds;
    for (i = 1; i <= rounds; i++)
      {
    finalmoveA = playera.getMoveType ();
    finalmoveB = playerb.getMoveType ();
    calculateCoins (finalmoveA, finalmoveB);
      }
  }
  int playerFind = 1;
  Move returnMove = null;
  Move getOppMove ()
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
  Move getMyMove ()
  {
    if (playerFind % 2 == 1)
      {
    this.returnMove = finalmoveA;
    playerFind = playerFind + 1;
      }
    else if (playerFind % 2 == 0)
      {
    this.returnMove = finalmoveB;
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

class Tournamentmain
{
  int i, j, scoreA, scoreB;
  void playTournament(ArrayList<Player> players, int rounds, Machine mac)
  {
  int size = players.size();
  for (i = 0; i < (size - 1); i++)
    {
      for(j = i + 1; j <= (size - 1); j++)
      {
      mac.play(players.get(i), players.get(j),rounds);
      int scoreA = players.get(i).getScore();
      int scoreB = players.get(j).getScore();
      // System.out.println(scoreA + "   " + scoreB);
      mac.makingNull();
      }
      System.out.println("Final score of "+ players.get(i) + " is : " + players.get(i).getScore());
    }
      System.out.println("Final score of "+ players.get(i) + " is : " + players.get(i).getScore());
  }
}

 public class Tournament
 { 

  public static void main (String[] args)
  {
    Scanner scan = new Scanner(System.in);
    Machine mac = new Machine();
    Player copycat = new Copycat (mac);
    Player cheat = new Cheat (mac);
    Player cooperate = new Cooperate (mac);
    Player grudger = new Grudger (mac);
    Player detective = new Detective (mac);
    Player human = new Human(scan,mac);
    Player copykitten = new Copykitten (mac);
    Player simpleton = new Simpleton (mac);
    Player randomplay = new Randomplay (mac);
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(cooperate);
    players.add(grudger);
    players.add(copycat);
    players.add(cheat);
    players.add(detective);
    players.add(human);
    players.add(copykitten);
    players.add(simpleton);
    players.add(randomplay);
    Tournamentmain tournamentmain = new Tournamentmain();
    tournamentmain.playTournament(players,10,mac);
  }
}
