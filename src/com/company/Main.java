import java.util.*;
import java.lang.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

//Rules
//One word is given to every person before giving
//it to anyone they have to make sure that their data disappear
//now at the end of the game they have to vote on the basis
//of the hints they were giving to each other on which basis
//they can win the game when they find the undercover or Mr White

class Game {

    //objects declaration
    Random random = new Random();
    Scanner  input = new Scanner(System.in);

    //Variables & Arrays
    public	String [] Normal = {"Bottle", "Fork", "Cup", "Stair", "Pen", "Shampoo", "Light Bulb", "Horse", "Sunflower", "CocaCola", "Airplane","Mirror","Truck","Screw Driver","Voilen","Chair","Professor","Goat","Coffee","Shoes","Table"};

    public	String [] Imposter = {"Gallon", "Spoon", "Mug", "Ladder", "Pencil", "Soap", "Candle", "Donkey", "Jasmine", "7up", "Helicopter","Glass","Dumper","Wrench","Guitar","Sofa","Teacher","Sheep","Tea","Sneaker","Bench"};

    public ArrayList <String> TempName = new ArrayList <String>();
    public ArrayList <String> NameList = new ArrayList <String>();
    public ArrayList <String> Imposters = new ArrayList <String>();
    public ArrayList <String> MrWhites = new ArrayList <String>();
    public ArrayList <Integer> RepeatedCheck = new ArrayList <Integer>();
    public String Word;
    public String Name;
    public char Continue;
    public char ControlKey = 'O';
    public int range  = Normal.length;
    public int WordSelection;
    public int count = 0;
    public int PlayerNo = -1;
    public int SetName = 0;
    public int Vote;
    public int number = 2;
    public int Tries;
    public int NoOfPlayers;
    public int  ImposterNo;
    public int MrWhiteNo = -1;
    public int ImposterWord;
    public int Remaining;
    public String PlayerType ="Undercover";

    public void PlayerNames() {

        System.out.print("\nNo of Players are: ");
        NoOfPlayers = input.nextInt();
        if (NoOfPlayers < 4 || NoOfPlayers > 10) {
            System.out.println("Min player are 4 and maximum are 10");
            PlayerNames();
        }
        System.out.println("*Enter Player's Names*");
        input.nextLine();
        while (SetName != NoOfPlayers) {
            System.out.print("Player " + (SetName + 1) + "'s Name : ");
            Name = input.nextLine();
            if (!NameList.contains(Name)) {
                NameList.add(Name);
                SetName++;
            } else {
                System.out.print("\nThis Name already exist in this room.\nPlease make sure there is a difference\n\n");
            }
        }
    }

    public void ImposterSelection() {
        ImposterNo = random.nextInt(NoOfPlayers);
        while (RepeatedCheck.contains(ImposterNo)) {
            ImposterNo = random.nextInt(NoOfPlayers);
        }
        RepeatedCheck.add(ImposterNo);
        Imposters.add(NameList.get(ImposterNo));
    }

    public void MrWhiteSelection() {

        while (MrWhiteNo == -1 || RepeatedCheck.contains(MrWhiteNo) || MrWhiteNo >= NoOfPlayers) {
            MrWhiteNo = random.nextInt(NoOfPlayers);
            int add = random.nextInt(number);
            add++;
            MrWhiteNo = MrWhiteNo + add;
        }

        RepeatedCheck.add(MrWhiteNo);
        MrWhites.add(NameList.get(MrWhiteNo));
    }

    public void WhiteImposterSelection() {
        if (SetName == 0) {
            PlayerNames();
        }
        Collections.shuffle(NameList);
        TempName.addAll(NameList);
        WordSelection = random.nextInt(range);
        ImposterWord = random.nextInt(number);
        ImposterSelection();
        if (NoOfPlayers > 4) {
            MrWhiteSelection();
            if (NoOfPlayers == 7) {
                MrWhiteNo = -1;
                MrWhiteSelection();
            } else if (NoOfPlayers == 10) {
                MrWhiteNo = -1;
                ImposterSelection();
                MrWhiteSelection();
            }
        }
        if (NoOfPlayers > 3 && NoOfPlayers<5) {
            System.out.println("1 Undercover is among us");
            Remaining++;
        }
        else if (NoOfPlayers >4 && NoOfPlayers <8) {
            System.out.println("1 Undercover & 1 MrWhite are among us");
            Remaining = Remaining +2;
            PlayerType = "Undercover or MrWhite";
        }
        else {
            System.out.println("2 UnderCover & 2 MrWhie are among us");
            Remaining = Remaining +4;
            PlayerType = "Undercover or MrWhite";
        }
    }

    public void ContinueGame() {
        System.out.print("Continue by enter anything: ");
        Continue = input.next().charAt(0);
    }

    public void KeyControl() {
        System.out.println("For Skip enter : s or S");
        System.out.print("Enter anykey expect s or S: ");
        ControlKey = input.next().charAt(0);
        ControlKey = Character.toUpperCase(ControlKey);
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n");
    }

    public void Default() {
        clearScreen();
        TempName.removeAll(TempName);
        RepeatedCheck.removeAll(RepeatedCheck);
        Imposters.removeAll(Imposters);
        MrWhites.removeAll(MrWhites);
        Vote = 0;
        Tries = NoOfPlayers - 2;
        count = 0;
        MrWhiteNo = -1;

    }

    public void Vote() {
        Tries = NoOfPlayers - 2;
        while (Vote != NoOfPlayers - 2) {

            System.out.println("\nDiscuss & Vote");
            System.out.println("Tries left " + Tries + "\n");
            for (int i = 0; i < TempName.size(); i++) {
                System.out.println((i + 1) + " " + TempName.get(i));
            }

            System.out.println("\nRemaining "+PlayerType+" "+Remaining+"\n");

            System.out.print("\nEnter player number to eleminate: ");
            PlayerNo = input.nextInt();
            if (PlayerNo > 0  && PlayerNo < (TempName.size() + 1)) {
                System.out.println("\n" + TempName.get(PlayerNo - 1) + " Eliminated");
                if (Imposters.contains(TempName.get(PlayerNo - 1))) {
                    Imposters.remove(TempName.get(PlayerNo - 1));
                    System.out.println(TempName.get(PlayerNo - 1) + " was the "+PlayerType);
                    Remaining--;
                }

                else if (MrWhites.contains(TempName.get(PlayerNo - 1))) {
                    MrWhites.remove(TempName.get(PlayerNo - 1));
                    System.out.println(TempName.get(PlayerNo - 1) + " was the "+PlayerType);
                    Remaining--;
                }
                else {
                    System.out.println(TempName.get(PlayerNo - 1) + " was not the "+PlayerType);
                }

                if (Imposters.isEmpty() &&  MrWhites.isEmpty()) {
                    Vote = NoOfPlayers - 2;
                    Tries = 0;
                } else {
                    Vote ++;
                    Tries --;
                    TempName.remove(PlayerNo - 1);
                }

            } else {
                System.out.println("\nPlease input available player number\n");
            }
        }
        if (ControlKey != 'S') {
            if (Imposters.isEmpty() &&  MrWhites.isEmpty()) {
                System.out.println("Civilian Wins");
            } else {
                System.out.println("Civilian Loss");
                System.out.println("Remaining Imposter" + Imposters);
                System.out.println("Remaining Mr White"+MrWhites);

            }
            ContinueGame();
        }
    }

    public void RandomWord(int WordRandom) {

        switch (WordRandom) {
            case 0:
                Word = Imposter[WordSelection];
                break;
            case 1:
                Word = Normal[WordSelection];
                break;
        }
    }

    public void gameStarts() {

        while (ControlKey == 'O') {
            WhiteImposterSelection();
            //m			System.out.println(NameList);
            //		System.out.println(ImposterNo);
            while (count != NoOfPlayers && ControlKey  != 'S') {
                System.out.println(NameList.get(count) + "'s Turn");
                ContinueGame();
                if (Imposters.contains(NameList.get(count))) {
                    RandomWord(ImposterWord);
                    System.out.println("Your word is: " + Word);
                } else if (MrWhites.contains(NameList.get(count))) {
                    System.out.println("You are Mr White");
                } else {
                    if (ImposterWord == 0) {
                        ImposterWord = 1;
                        RandomWord(ImposterWord);
                        ImposterWord = 0;
                    } else {
                        ImposterWord = 0;
                        RandomWord(ImposterWord);
                        ImposterWord = 1;
                    }
                    System.out.println("Your word is: " + Word);
                }
                count++;
                KeyControl();
                clearScreen();
            }
            if (ControlKey != 'S') {
                Vote();
            }
            ControlKey = 'S';
            while (ControlKey == 'S') {
                Default();
                ControlKey = 'O';

            }
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Game obj = new Game();
        obj.gameStarts();

    }
}