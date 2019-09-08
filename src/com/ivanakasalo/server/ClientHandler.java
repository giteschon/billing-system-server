/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanakasalo.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Runnable class which handles client connection (needed for multihhreading).
 * Server creates new Thread which handles DataInputStream and DataOutputStream,
 * sends response to client and receives client requests.
 *
 * @author Ivana Kasalo
 */
public class ClientHandler implements Runnable {

    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;

    /**
     * Constructor take following parameters:
     *
     * @param client Socket for client connection
     * @param dis DataInputStream
     * @param dos DataOutputStream
     */
    public ClientHandler(Socket client, DataInputStream dis, DataOutputStream dos) {
        this.client = client;
        this.dis = dis;
        this.dos = dos;
    }

    /**
     * Overrides run method Method receives message from dis.readUTF(); and
     * sends it to all clients in a list (@see Server) with
     * dos.writeUTF(receivedMessage);
     *
     * While loops breaks when there is no message or when client disconnects.
     * Program prints that client has disconnected
     *
     */
    @Override
    public void run() {
        //receive message from client
        String receivedMessage = "";

        while (true) {

            try {
                if (dis.readUTF() == null) {
                    break;
                }

                receivedMessage = dis.readUTF();
                System.out.println("Received message: " + receivedMessage);

                //send message to all clients
                for (ClientHandler clientHandler : Server.clientsList) {
                    //add message to dataoutputstream
                    clientHandler.dos.writeUTF(receivedMessage);
                    //  dos.writeBoolean(saveInProgress);

//                if (client.isConnected()) {
//                if (client.isClosed()) {
//                    Server.clientsList.remove(clientHandler);
//                }
                    //}
                    //bool
//             if (dis.readInt() != null) {
//                //             boolean saveInProgress= dis.readBoolean();
////             System.out.println("Received bool: " + saveInProgress);
//            }
                }

            } catch (IOException ex) {
                System.out.println(client + " has disconnected.");
                ex.printStackTrace();
                Server.clientsList.remove(this);
//            try {
//                client.close();
//            } catch (IOException ex1) {
//                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex1);
//            }
                break;

            }

//        try {
//                        
//             boolean saveInProgress= dis.readBoolean();
//             System.out.println("Received bool: " + saveInProgress);
//             
//             //send message to all clients
//            for (ClientHandler clientHandler : Server.clientsList) {
//                              dos.writeBoolean(saveInProgress);
//            }
//    
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        }
        }

    }
}
