/** \file */

/** \brief This file implements the client side of interacting with distributed file system.
 *
 * Instantiates a file system and allows a user to interact with a DFS with provided menu
 */

import java.rmi.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Client {
    DFS dfs;

    public Client(int p) throws Exception {
        dfs = new DFS(p);
        int port = p;
        
        // Testing
        FileSystem fileSystem = new FileSystem();
        Gson gson = new Gson();
        String fileSystemJson = gson.toJson(fileSystem);
       
        FileWriter fWriter = null;
        FileReader fReader = null;
        FileStream jsonFileStream = null;
        
        try {
            fWriter = new FileWriter("327FS.json"); //Create a writer to write to json file
            fWriter.write(gson.toJson(fileSystem)); //Write FileSystem object to json file            
            jsonFileStream = new FileStream("327FS.json");
            
            //System.out.println(convert(jsonFileStream, Charset.defaultCharset())); //Display json contents

            dfs.writeMetaData(jsonFileStream);
            JsonReader jReader = dfs.readMetaData();
            // jReader.setLenient(true);
            // jReader.beginArray();
            dfs.getFileSystem(jReader);
            // System.out.println(fileSystem.metadata.file[0].getPageSize());
        }
        catch (IOException e){ e.printStackTrace();}
        finally {
            if(fWriter != null){
                try{fWriter.close();}
                catch (IOException e){e.printStackTrace();}
            }
        }
        
        Scanner in = new Scanner(System.in);
        int input = 0;

        while (input != 10) {

            System.out.println("------MAIN MENU------");
            System.out.println("-------1. JOIN-------");
            System.out.println("-------2. LIST-------");
            System.out.println("-------3. TOUCH------");
            System.out.println("-------4. DELETE-----");
            System.out.println("-------5. READ-------");
            System.out.println("-------6. TAIL-------");
            System.out.println("-------7. HEAD-------");
            System.out.println("-------8. APPEND-----");
            System.out.println("-------9. MOVE-------");
            System.out.println("------10. EXIT-------");
            System.out.print("Type in an option number: ");

            if (in.hasNextInt()) {
                input = in.nextInt();

                switch (input) {
                case 1:
                    dfs.join(in);
                    break;
                case 2:
                    dfs.ls();
                    break;
                case 3:
                    dfs.touch(in);
                    break;
                case 4:
                    dfs.delete(in);
                    break;
                case 5:
                    dfs.read(in);
                    break;
                case 6:
                    dfs.tail(in);
                    break;
                case 7:
                    dfs.head(in);
                    break;
                case 8:
                    dfs.append(in);
                    break;
                case 9:
                    dfs.mv(in);
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
                }
                System.out.println();
            }
        }

        System.out.println("Exit Client.");
    }

    static public void main(String args[]) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("Parameter: <port>");
        }
        Client client = new Client(Integer.parseInt(args[0]));

    }
}