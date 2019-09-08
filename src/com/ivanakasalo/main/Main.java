/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanakasalo.main;

import com.ivanakasalo.server.Server;

/**
 * This is a main class which starts the server
 *
 * @author Ivana Kasalo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Process javaClassProcess= null;
//        Process rmiregistry=null;
//        try {
//            //Server server= new Server();
//            
//            //create jar file
////            String command="jar cvf rmichat.jar com.ivanakasalo.server.rmi\\*.class";
////            //start rmiregistry
//            Runtime runtime= Runtime.getRuntime();
//            //rmiregistry=runtime.exec("cd ")
//           // javaClassProcess=runtime.exec(command);
//           
//           String[] cmdArray={"cd C:\\Users\\Ivy\\Documents\\Algebra\\BillingServer\\src\\com\\ivanakasalo","java -Djava.rmi.server.codebase=file:\\C:\\Users\\Ivy\\Documents\\Algebra\\BillingServer\\rmichat.jar",
//           "main.Main"};
//           // Process pr= runtime.exec("java -Djava.rmi.server.codebase=file:\\C:\\Users\\Ivy\\Documents\\Algebra\\BillingServer\\rmichat.jar");
//            Process pr1=runtime.exec(cmdArray);
//
//            
//            new RmiChatServer();
//            new Server();
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            javaClassProcess.destroyForcibly();
//        }

//Server server = new Server();
        new Server();

    }

}
