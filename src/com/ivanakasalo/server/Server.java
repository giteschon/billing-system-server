/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanakasalo.server;

import com.ivanakasalo.server.rmi.RmiChatServer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for server implementation.
 *
 * @author Ivana Kasalo
 */
public class Server {

    /**
     * @param
     */
    private final int PORT = 1234;
    private ServerSocket serverSocket;

    Socket client;
    //list of all connected clients
    public static List<ClientHandler> clientsList = new ArrayList<>();

    private boolean finish = false;
    private DataInputStream dis;
    private DataOutputStream dos;

    /**
     * Server starts from constructor.
     */
    public Server() {
        start();
    }

    int counter = 0;

    ClientHandler handler;

    /**
     * Multithreaded Server runs on port 1234 and waits for clients to connect.
     *
     * Start method also starts RMI registry when clients connect.
     *
     * @see RmiChatServer
     *
     * In while loop server waits for messages (either from chat or from
     * DataStreams.
     * @see ClientHandler
     *
     * Server runs in while(true) loop and connection can't be closed. Only
     * client's connection can be closed. When client closes the connection,
     * server prints the message
     *
     *  * @throws IOException
     */
    private void start() {
        try {
            RmiChatServer rmi = new RmiChatServer();
            //set up local host, otherwise it's 0.0.0.0
            //SOLVED
            //new ServerSocket(9090, 0, InetAddress.getByName("localhost"))
            // serverSocket = new ServerSocket(PORT, 0,InetAddress.getLocalHost());
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server has been started. Server: " + serverSocket);
            System.out.println("Waiting for clients ...");

            //run infinite loop to connect clients
            while (true) {
//
//                if (counter >= 1 && clientsList.isEmpty()) {
//
//                    serverSocket.close();
//                    break;
//                }

                //accept connection from client & print info message
                client = serverSocket.accept();
                System.out.println("Client" + client + " is connected.");

                while (true) {

                    String msg = "";
                    if (rmi.getMessage() == null) {
                        break;
                    }
                    msg = rmi.getMessage();
                    System.out.println(msg);

                    if (rmi.getMessage().equals(msg)) {
                        break;
                    }
                    rmi.sendMessageToClients();
                    //ovo je tu kako ne bi cijelo vrijeme vrtio petlju -> jendom dobi i kraj

                }

                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());

                //new client thread -> clienthandler -> multiple clients
                handler = new ClientHandler(client, dis, dos);

                //make client thread == multiple clients -> multithreading
                Thread t = new Thread(handler);
                clientsList.add(handler);
                t.start();

//                    if (client.isClosed()) {
//                        break;
//                    }
//                }
//                if (client.isClosed()) {
//                    client.close();
//                    continue;
//                }
//                ServerThread serverConnection = new ServerThread(client, this);
//                serverConnection.start();
                //multiple connections
                //clientsList.add(serverConnection);
                //   counter++;
//                
                // System.out.println(counter);
//ServerThread serverThread= new ServerThread(client, serverSocket);
//serverThread.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            clientsList.remove(handler);
        } finally {
            // closeConnection();
        }

    }

    private void closeConnection() {
        try {
            System.out.println("Server connection is closed.");
            dis.close();
            dos.close();
            //client.close();
            serverSocket.close();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
