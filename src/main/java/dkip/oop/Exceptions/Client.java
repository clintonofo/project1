package dkip.oop.Exceptions;
import dkip.oop.DAOs.MySqlPlayerDao;
import dkip.oop.DAOs.PlayerDaoInterface;
import dkip.oop.DTOs.team;
import dkip.oop.Exceptions.DaoException;

import dkip.oop.DAOs.MySqlPlayerDao;
import dkip.oop.DAOs.PlayerDaoInterface;

import dkip.oop.Exceptions.DaoException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        Client client = new Client();
        client.start();
    }

    public void start()
    {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort() );

            System.out.println("Client message: The Client is running and has connected to the server");

            System.out.println("Please enter a command:  (\"DisplayPlayerBySalary\" to get player by salary,  or  " +
                    "\"DisplayAllPlayers\" to get Display all Entities, or \"AddPlayer\" to get add, or \"DeletePlayer\" to get delete , or \"Get summary data\" to Summary) \n>");
            String command = in.nextLine();
            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers
            socketWriter.println(command);
            Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply
            boolean keep_looping = true;
            while(keep_looping == true) {
                if (command.startsWith("DisplayPlayerBySalary"))   //we expect the server to return a time
                {
                    Scanner keyboard = new Scanner(System.in);
                    System.out.println("Enter player salary");
                    int salary = keyboard.nextInt();
                    command = command +" "+salary;  // "DisplayPlayerById 24"
                    socketWriter.println(command);  // sends command request to server
                    // wait for and then read response
                    String response = socketReader.nextLine();
                    System.out.println("Client message: Response from server: \""  + response +"\"");
                }
                else if(command.startsWith("DisplayAllPlayers")){
                    String players = socketReader.nextLine();
                    System.out.println("Client message: Response from server: \"" + players + "\"");
                }
                else if(command.startsWith("AddPlayer")){
                    int salary =-1;
                    String playerName = "";
                    String teamName = "";
                    String manager = "";
                    String coach = "";
                    String city = "";
                    Scanner keyboard = new Scanner(System.in);

                    while (salary < 1) {
                        System.out.println("Enter player salary: ");
                        salary = keyboard.nextInt();
                    }
                    ;

                    while (playerName == "") {
                        System.out.println("Enter Player  Name: ");
                        playerName = keyboard.next();
                    }
                    ;

                    while (teamName == "") {
                        System.out.println("Enter team Name: ");
                        teamName = keyboard.next();
                    }
                    ;

                    while (manager == "") {
                        System.out.println("Enter manager name: ");
                        manager = keyboard.next();
                    }
                    ;

                    while (coach == "") {
                        System.out.println("Enter coach name: ");
                        coach = keyboard.next();
                    }
                    ;

                    while (city == "") {
                        System.out.println("Enter city name: ");
                        city = keyboard.next();
                    }
                    ;
                    String players = socketReader.nextLine();
                    System.out.println("Client message: Response from server: \"" + players + "\"");
                }
                else if(command.startsWith("DeletePlayer")){
                    String players = socketReader.nextLine();
                    Scanner keyboard = new Scanner(System.in);
                    System.out.println("Enter player salary you want to delete");
                    int salary = keyboard.nextInt();
                    if (players != null)
                        if (players != null)
                            System.out.println("Player with salary " + salary + " was found and deleted");
                        else
                            System.out.println("Player with " + salary + " was not found");

                    System.out.println("Client message: Response from server: \"" + players + "\"");
                }
                else
                {
                    String input = socketReader.nextLine();
                    System.out.println("Client message: Response from server: \"" + input + "\"");
                }
                System.out.println("Enter next command: ");
                command = in.nextLine();
                socketWriter.println(command);
            }

            socketWriter.close();
            socketReader.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: "+e);
        }
    }
}

