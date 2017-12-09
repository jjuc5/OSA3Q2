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
 * This class represents the client. It allows one to connect to the echo server
 * and to take input from the user, outputting it towards the Echo server. User
 * may also choose to sever the connection by inputting "exit". To run more
 * clients, please run more instances of this file.
 *
 * @author Melanie Iarocci, John Urbanowicz
 */
public class EchoClient extends Thread {

    /**
     * The main method for the client class.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            // Establishes a socket with loopback address selected and port 9999
            // Sets up input readers and output stream to send data to Echo Server
            Socket s = new Socket("localhost", 9999);
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            BufferedReader con = new BufferedReader(new InputStreamReader(System.in));
            String line;
            // While the user has not inputted the exit clause to close connection,
            // the loop will continually query the user for input
            do {
                line = br.readLine();
                if (line != null) {
                    if (line.equals("exit")) {
                        s.close();
                    }
                    System.out.println(line);
                }
                line = con.readLine();
                pw.println(line);
            } while (!line.trim().equals("exit"));

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
