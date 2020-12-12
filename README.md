import java.util.*;
import java.lang.*;
import java.io.*;

class basic
{
    private int coinA;
    private int coinB;
    public void calculate (int acoin, int bcoin, String aopt, String bopt){
        if(aopt.equals("Cheat") && bopt.equals("Cheat")){
        this.coinA = acoin;
        this.coinB = bcoin;
    }
     else if(aopt.equals("Cooperate") && bopt.equals("Cooperate")){
        this.coinA = acoin + 2;
        this.coinB = bcoin + 2;
    }
    else if(aopt.equals("Cheat") && bopt.equals("Cooperate")){
        this.coinA = acoin + 3;
        this.coinB = bcoin - 1;
    }
    else if(aopt.equals("Cooperate") && bopt.equals("Cheat")){
        this.coinA = acoin - 1;
        this.coinB = bcoin + 3;
    }
    }
    public int getCoinA(){
            return coinA;
    }
    public int getCoinB(){
        return coinB;
    }
}
public class Main
{
  public static void main (String[]args)
  {
    System.out.println ("Let's begin the game.");
    basic game = new basic();
    for (int i=1; i<=5; i++){
    System.out.println("Round " + i);
    Scanner in = new Scanner(System.in);
    System.out.println("Input from A player - Cheat/Cooperate");
    String aopt = in.next();
    System.out.println("Input from B player - Cheat/Cooperate");
    String bopt = in.next();
    int acoin = game.getCoinA();
    int bcoin = game.getCoinB();
    game.calculate(acoin,bcoin,aopt,bopt);
    }
    System.out.println("Score of A is: " + game.getCoinA());
    System.out.println("Score of B is: " + game.getCoinB());
  }
}

