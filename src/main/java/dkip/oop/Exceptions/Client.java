package dkip.oop.Exceptions;


import dkip.oop.DTOs.team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

    public class Client {
        public static void main(String[] args) {
            Client client = new Client();
            client.start();
        }

        public void start() {
            Scanner in = new Scanner(System.in);
            try {
                Socket socket = new Socket("localhost", 8080);  // connect to server socket
                System.out.println("Client: Port# of this client : " + socket.getLocalPort());
                System.out.println("Client: Port# of Server :" + socket.getPort());
                System.out.println("Client message: The Client is running and has connected to the server");

                OutputStream os = socket.getOutputStream();
                PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers
                Scanner socketReader = new Scanner(socket.getInputStream());

                final String MENU_ITEMS = "\n*** MAIN MENU OF OPTION FOR OOPCA5 PART 4***\n"
                        + "1. Display player By salary\n"
                        // + "2. Display All\n"
                        + "3. Exit\n"
                        + "Enter Option [1,3]";

                final int DISPLAYBYSALARY = 1;
                final int DISPLAYALL = 2;
                final int EXIT = 3;
                int option = 0;
                do {
                    System.out.println("\n" + MENU_ITEMS);
                    // wait for, and retrieve the reply
                    String usersInput = in.nextLine();
                    Gson gson = new Gson();
                    Type playerListType = new TypeToken<List<team>>() {
                    }.getType();
                    List<team> playersList;
                    String command;

                    option = Integer.parseInt(usersInput);
                    switch
                    (option) {
                        case DISPLAYBYSALARY:
                            System.out.println("Please enter an salary : ");
                            int salary = in.nextInt();
                            in.nextInt();

                            command = "bysalary " + salary;
                            socketWriter.println(command.toLowerCase());
                            int findSalary = socketReader.nextInt();
                            playersList = gson.fromJson(String.valueOf(findSalary), playerListType);
                            for (team t : playersList) {
                                System.out.println(t);
                            }
                            break;
                        case DISPLAYALL:
                            System.out.println("Display all option choosen");
                            command = "all";
                            socketWriter.println(command.toLowerCase());
                            String findAll = socketReader.nextLine();
                            playersList = gson.fromJson(findAll, playerListType);
                            for (team t : playersList) {
                                System.out.println(t);
                            }
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid option - please enter number in range");
                            break;
                    }
                } while (option != EXIT);

                socketWriter.close();
                socketReader.close();
                socket.close();
                System.out.println("\nExiting Main Menu.");
                ;
            } catch (IOException e) {
                System.out.println("Client message: IOException: " + e);
            }
        }
    }

