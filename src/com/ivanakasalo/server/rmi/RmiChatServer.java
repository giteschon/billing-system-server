/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanakasalo.server.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.*;

/**
 * This class starts RMI Registry and sends and receives chat messages.
 *
 * RmiChatServer is used with rmi_test.jar (imported package rmi.*) which
 * contains rmi interface and implementation that client and server use. Jar
 * file can be found in lib folder and is added through project properties. See
 * Rmi_test project for rmi.*
 *
 * RMI registry connects to default port 1099 and binded name fro connection is
 * MyRmiServer
 *
 * @author Ivana Kasalo
 */
public class RmiChatServer {

    private static final int RMIPORT = 1099;
    private static final String MY_RMI_SERVER = "MyRmiServer";

    private List<IRmiChat> clientList = new ArrayList<>();

    //prevent garbage collection...
    private static Registry registry;

    private String message;

    /**
     * Constructor starts the registry
     */
    public RmiChatServer() {
        super();
        startRmiRegistry();
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    /**
     * RMI registry starts from the project and dies with JVM. It shows the
     * message on which port RMI is running and binds the name which enables
     * future use.
     *
     * @throws RemoteException
     */
    private void startRmiRegistry() {
        try {
            RmiChat remoteObject = new RmiChat();

            //proxy == local representation
            IRmiChat proxy = (IRmiChat) UnicastRemoteObject.exportObject(remoteObject, 0);
            clientList.add(proxy);

            //opens rmiregistry (without cmd) and dies with the virtual machine
//            Registry registry= LocateRegistry.getRegistry("127.0.0.1",1099);
            registry = LocateRegistry.createRegistry(RMIPORT);
            System.out.println("RMI Registry is running on port " + RMIPORT);
            //registry name (of the created one)
            registry.rebind(MY_RMI_SERVER, proxy);

            //registry.rebind(MY_RMI_SERVER, (IRmiChat) UnicastRemoteObject.exportObject(remoteObject, 0));
        } catch (RemoteException ex) {
            Logger.getLogger(RmiChatServer.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (AlreadyBoundException ex) {
//            Logger.getLogger(RmiChatServer.class.getName()).log(Level.SEVERE, null, ex);
//        }
        }
    }

    /**
     * Method sends messages to all clients connected to the server (clientList
     * is in Server.java) Method broadcastMessage is part of the rmi import. See
     * Rmi_test project
     */
    public void sendMessageToClients() {
        for (IRmiChat rmiCLient : clientList) {
            try {
                String msg = rmiCLient.broadcastMessage();
                System.out.println("Rmi message received: " + msg);
                setMessage(msg);
                //rmiCLient.broadcastMessage();

            } catch (RemoteException ex) {
                Logger.getLogger(RmiChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
