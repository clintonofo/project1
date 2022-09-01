package dkip.oop.Exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dkip.oop.DAOs.MySqlPlayerDao;
import dkip.oop.DAOs.PlayerDaoInterface;
import dkip.oop.DTOs.team;
import dkip.oop.Exceptions.DaoException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server
{
    public static void main(String[] args)
    {
        Server server = new Server();
        server.start();
    }

    public void start()
    {
        try
        {
            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect
            while (true)    // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection,
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, clientNumber)); // create a new ClientHandler for the client,
                t.start();                                                  // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }

        } catch (IOException e)
        {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
    {
        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;
        PlayerDaoInterface IPlayerDao;

        public ClientHandler(Socket clientSocket, int clientNumber)
        {
            IPlayerDao = new MySqlPlayerDao();
            try
            {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing

            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            String message;

            try
            {
                while ((message = socketReader.readLine()) != null)
                {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

                    if (message.startsWith("DisplayPlayerBySalary"))
                    {
                        System.out.println("in DisplayPlayerBySalary.......");
                       try {
                            String tokens[] = message.split(" ");
                            String salary = tokens[1];
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            //convert list of players to JSON
                            System.out.println("in DisplayPlayerBySalary...... about to call dao.");
                            String players = gson.toJson(IPlayerDao.findPlayerBySalary(Integer.parseInt(salary)));
                            System.out.println("Players by salary = " + players);
                            socketWriter.println(players);
                        } catch( DaoException e )
                        {
                            e.printStackTrace();
                        }
                    }
                    else if (message.startsWith("DisplayAllPlayers"))
                    {
                        System.out.println("in DisplayAllPlayers.......");
                        try {
                            List players = IPlayerDao.FindAllPlayersJsonForamt() ;
                            System.out.println("Players = " + players);
                            socketWriter.println(players);
                        } catch( DaoException e ) {
                            e.printStackTrace();
                        }
                    }
                    else if (message.startsWith("AddPlayer"))
                    {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String players = null;
                        int salary =-1;
                    String playerName = "";
                    String teamName = "";
                    String manager = "";
                    String coach = "";
                    String city = "";
                        try {
                            IPlayerDao.addPlayer(salary, playerName, teamName, manager, coach, city);
                            System.out.println("Insert New PLayer is a SUCCESS!");
                            players = gson.toJson(IPlayerDao.findAllPlayers());
                            System.out.println("Players = " + players);
                        } catch (DaoException e) {
                            e.printStackTrace();
                        }
                        socketWriter.println(players);  // send message to client
                    }
                    else if (message.startsWith("DeletePlayer"))
                    {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String players = null;
                        PlayerDaoInterface IPlayerDao = new MySqlPlayerDao();
                        try {
                            String displaySalary = socketReader.readLine();
                            players =gson.toJson(IPlayerDao.deletePlayerBySalary(Integer.parseInt(displaySalary)));
                            System.out.println("Players by salary = " + players);
                            if (players != null)
                                System.out.println("Player with salart " + displaySalary + " was found and deleted");
                            else
                                System.out.println("Player with " + displaySalary + " was not found");
                        } catch (DaoException e) {
                            e.printStackTrace();
                        }
                        socketWriter.println(players);  // send message to client
                    }
                    else
                    {
                        socketWriter.println("I'm sorry I don't understand :(");
                    }
                }

                socket.close();

            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
        }
    }

}
