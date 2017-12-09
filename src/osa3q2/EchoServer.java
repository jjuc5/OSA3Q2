/*
    Course: OS Design & Systems Programming
    Professor: Cristina Ribeiro
    Assignment: Assignment 3 - Q2
    Authors: Melanie Iarocci, John Urbanowicz
    Date: 12/09/2017
 */
package osa3q2;

import java.io.*;
import java.net.*;

/**
 * This class represents a multi-threaded Echo Server. It accepts client
 * connection and waits for user input. Upon receipt, it returns the value
 * received from the client. It also detects proper closure of the client
 * connection. It can accept connections from multiple clients concurrently.
 *
 * @author Melanie Iarocci, John Urbanowicz
 */
public class EchoServer implements Runnable {

    Socket clientSocket;
    public static ServerSocket serverSocket;

    public EchoServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * This is the main method for the EchoServer class.
     *
     * @param args
     */
    public static void main(String[] args) {
        serverSocket = null;
        EchoServer s = new EchoServer(9999);
    }

    /**
     * EchoServer method creates and instantiates a serverSocket socket. This
     * will create new server sockets for each client that attempts to connect.
     *
     * @param portnum the port number argument passed
     */
    public EchoServer(int portnum) {
        try {
            serverSocket = new ServerSocket(portnum);

            int threadCount = 1;

            while (true) {
                Socket newSock = serverSocket.accept();
                System.out.println("New client connected.");
                Runnable newRunnable = new EchoServer(newSock);
                Thread newThread = new Thread(newRunnable);
                newThread.start();
                System.out.println("Thread: " + threadCount);
                threadCount++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * This run method represents a new client response thread.
     */
    @Override
    public void run() {
        System.out.println("Inside Run");

        try {
            try {
                // Loop that runs while the client connection is still valid
                while (true) {
                    // Accept client connections and sets up input/output objects
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
                    pw.println("Welcome to the Echo Server.  Enter message to return or 'exit' to close client.");
                    String line;

                    // Returns values received from client
                    while (true) {
                        line = br.readLine();
                        if (line != null) {
                            if (line.equals("exit")) {
                                System.out.println("Client has closed the connection.");
                                break;
                            } else {
                                pw.println("Received: " + line);
                            }
                        }
                    }
                }
            } finally {
                clientSocket.close();
            }
        } catch (Exception ex) {
            System.out.println();
        }
    }
}
