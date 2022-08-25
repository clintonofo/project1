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
                    int player_id =-1;
                    String firstname = "";
                    String lastname = "";
                    String position = "";
                    String state = "";
                    int age = -1;
                    Scanner keyboard = new Scanner(System.in);

                    while (player_id < 1) {
                        System.out.println("Enter player Id: ");
                        player_id = keyboard.nextInt();
                    }
                    ;

                    while (firstname == "") {
                        System.out.println("Enter Player First Name: ");
                        firstname = keyboard.next();
                    }
                    ;

                    while (lastname == "") {
                        System.out.println("Enter Player Last Name: ");
                        lastname = keyboard.next();
                    }
                    ;

                    while (position == "") {
                        System.out.println("Enter Player Position: ");
                        position = keyboard.next();
                    }
                    ;

                    while (state == "") {
                        System.out.println("Enter Player Nationality: ");
                        state = keyboard.next();
                    }
                    ;

                    while (age <= 0) {
                        System.out.println("Enter Player's age: ");
                        age = keyboard.nextInt();
                    }
                    ;
                    String players = socketReader.nextLine();
                    System.out.println("Client message: Response from server: \"" + players + "\"");
                }
                else if(command.startsWith("DeletePlayer")){
                    String players = socketReader.nextLine();
                    Scanner keyboard = new Scanner(System.in);
                    System.out.println("Enter player id you want to delete");
                    String player_Id = keyboard.next();
                    if (players != null)
                        if (players != null)
                            System.out.println("Player with id " + player_Id + " was found and deleted");
                        else
                            System.out.println("Player with " + player_Id + " was not found");

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

