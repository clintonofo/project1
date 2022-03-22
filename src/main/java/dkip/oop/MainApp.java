package dkip.oop;

import java.io.IOException;
import java.util.*;

//Project Theme :
public class MainApp {
    //ArrayList<Players> players;

    public static void main(String[] args)
    {
        MainApp theApp = new MainApp();
        System.out.println("Isaac's 2nd project");
        System.out.println("- the english premier league.");
        theApp.start();
    }
    private void start()
    {
        try
        {
            displayMainMenu();        // User Interface - Menu
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        map1();
        map2();
//        Players player1 = new Players("Jadon","Sancho","Right Wing", "Egypt",29);
//        Players player2 = new Players("Son","Heung-Min","Right Wing", "South Korea",29);
//        Players player3 = new Players("Diogo","Jota","Striker", "Portugal",25);
//        Players player4 = new Players("Cristiano","Ronaldo","Striker", "Portugal",37);
//        Players player5 = new Players("Harry","Kane","Striker", "Egypt",28);
//        Players player6 = new Players("Sadio","Mane","Left Wing", "Senegal",29);
//        Players player7 = new Players("Ivan","Toney","Striker", "England",26);
//        Players player8 = new Players("Riyad","Mahrez","Right Wing", "Algeria",31);
//        Players player9 = new Players("Raheem","Sterling","Left Wing", "England",27);
//        Players player10 = new Players("Jamie","Vardy","Striker", "England",35);
//
//        players = new ArrayList<>();
//        players.add(player1);
//        players.add(player2);
//        players.add(player3);
//        players.add(player4);
//        players.add(player5);
//        players.add(player6);
//        players.add(player7);
//        players.add(player8);
//        players.add(player9);
//        players.add(player10);

        map3();
        System.out.println("Program ending, Goodbye");
    }
    private void displayMainMenu() throws IOException
    {

        final String MENU_ITEMS = "\n*** MAIN MENU OF OPTIONS ***\n"
                + "1. Premier League Table - hashmap\n"
                + "2. Premier League Players Details - treemap\n"
                + "3. Premier League Team\n"
                + "4. Exit\n"
                + "Enter Option [1,4]";

        final int TABLE = 1;
        final int PLAYER = 2;
        final int TEAM = 3;
        final int EXIT = 4;

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do
        {
            System.out.println("\n" + MENU_ITEMS);
            try
            {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option)
                {
                    case TABLE:
                        System.out.println("Prem table option chosen");
                        map1();
                        break;
                    case PLAYER:
                        System.out.println("Players option chosen");
                        map2();
                        break;
                    case TEAM:
                        System.out.println("Team details option chosen");
                        map3();
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid option - please enter number in range");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException e)
            {
                System.out.print("Invalid option - please enter number in range");
            }
        } while (option != EXIT);

        System.out.println("\nExiting Main Menu, goodbye.");

    }
    public static void map1(){
        // Map: Table (String) => ArrayList object (containing list of teams)
        Map< String, ArrayList<String>> premTable = new HashMap<>();
        ArrayList<String> premList = new ArrayList<>();
        premList.add("Manchester City");
        premList.add("Liverpool");
        premList.add("Chelsea");
        premList.add("Manchester United");
        premList.add("West Ham United");
        premList.add("Arsenal");
        premList.add("Tottenham Hotspur");
        premList.add("Wolverhampton Wanderers");
        premList.add("Southampton");
        premList.add("Brighton & Hove Albion");
        premTable.put("First ten", premList);
        String key = "First ten";
        premList = premTable.get(key);

        System.out.print(key + " club: ");
        for (String s : premList) {
            System.out.print(s + ", ");
        }
        for (Map.Entry<String, ArrayList<String>> entry : premTable.entrySet()) {
            key = entry.getKey();
            ArrayList<String> list = entry.getValue();
            System.out.println("Name: " + key + ", Friend: " + list);
        }
        for (int i = 0; i < premList.size(); i++) {
            // Get each item (in this case print each item)
            System.out.println(premList.get(i));
        }
    }
    public static void map2(){
        TreeMap<Long, Players> playerTreeMap = new TreeMap<>();
        playerTreeMap.put(200034L, new Players("Bruno", "Fernandes", "Attacking Midfielder", "Portugal", 27));
        playerTreeMap.put(200334L, new Players("Andrew", "Robertson", "Left Back", "Scotland", 28));
        playerTreeMap.put(202034L, new Players("Paul", "Pogba", "Center Midfielder", "France", 29));
        playerTreeMap.put(210034L, new Players("Michail", "Antonio", "Striker", "Jamaica", 31));
        playerTreeMap.put(500034L, new Players("Gabriel", "Jesus", "Striker", "Brazil", 24));
        playerTreeMap.put(207034L, new Players("Mason", "Mount", "Attacking Midfielder", "England", 23));
        playerTreeMap.put(200934L, new Players("Kelechi", "Iheanacho", "Striker", "Portugal", 25));
        playerTreeMap.put(200134L, new Players("Neal", "Maupay", "Striker", "France", 25));
        playerTreeMap.put(202434L, new Players("Jarrod", "Bowen", "Left Wing", "England", 25));
        playerTreeMap.put(212734L, new Players("Conor", "Gallagher", "Midfielder", "England", 22));


        Set<Long> keySet = playerTreeMap.keySet();

        for (Long key : keySet) {
            Players Players = playerTreeMap.get(key);
            System.out.println(" Key:  " + key + ", First Name: " + Players.getFirstName() + ", Last name: " + Players.getLastName()
                    + ", Age:" + Players.getAge() + ", Position:" + Players.getPosition() + ", State:" + Players.getPosition());
        }
    }
    public static void map3(){
    }
}
