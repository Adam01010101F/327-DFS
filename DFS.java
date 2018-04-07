/** \file */

/** \brief This file implements all the functions for a distributed file system.
 *
 * Functions for a DFS include join, ls (list), touch, delete,
 * read (read a given page), tail (read first page), head (read last page),
 * append, and mv (rename file).
 */

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.rmi.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.math.BigInteger;
import java.security.*;
import com.google.gson.stream.JsonToken;


/* JSON Format

 {
    "metadata" :
    {
        file :
        {
            name  : "File1"
            numberOfPages : "3"
            pageSize : "1024"
            size : "2291"
            page :
            {
                number : "1"
                guid   : "22412"
                size   : "1024"
            }
            page :
            {
                number : "2"
                guid   : "46312"
                size   : "1024"
            }
            page :
            {
                number : "3"
                guid   : "93719"
                size   : "243"
            }
        }
    }
}
 
 
 */


public class DFS
{
    int port;
    Chord  chord;
    
    /**
 	* Hashes an object using MD5
 	* \param object the item to hash
 	* \return randomly generated ID
 	*/
    private long md5(String objectName)
    {
        try
        {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(objectName.getBytes());
            BigInteger bigInt = new BigInteger(1,m.digest());
            return Math.abs(bigInt.longValue());
        }
        catch(NoSuchAlgorithmException e)
        {
                e.printStackTrace(); 
        }
        return 0;
    }
    
    /**
 	* Constructs a file system with a port
 	* \param port the destination for the system to connect
 	*/
    public DFS(int port) throws Exception
    {
        
        this.port = port;
        long guid = md5("" + port);
        chord = new Chord(port, guid);
        Files.createDirectories(Paths.get(guid+"/repository"));
    }
    
    /**
 	* Join distributed file system
 	* \param in the scanner object from client
 	*/
    public void join(Scanner in) throws Exception
    {
        System.out.print("Type in the IP address: ");
        String ip = in.next();
        
        if(!ip.equals(null)){
            System.out.print("Type in the port: ");
            int port = in.nextInt();
        }

        chord.joinRing(ip, port);
        chord.Print();
    }

    //QUESTION: do we ignore this JsonParser in favor of the GSON?
  /*  public JSonParser readMetaData() throws Exception
    {
        JsonParser jsonParser _ null;
        long guid = md5("Metadata");
        ChordMessageInterface peer = chord.locateSuccessor(guid);
        InputStream metadataraw = peer.get(guid);
        // jsonParser = Json.createParser(metadataraw);
        return jsonParser;
    }*/

    /**
 	* Reads metadata
 	* \return metadata
 	*/
    //Will need to throw exception?
    public JsonReader readMetaData(){
        ChordMessageInterface peer = null;
        JsonReader jReader = null;
        InputStream mdRaw = null;
        long guid = md5("Metadata");
        
            try { peer = chord.locateSuccessor(guid);}
            catch(Exception e){ e.printStackTrace();}
        try{mdRaw = peer.get(guid);} //Retrieve InputStream from Chord
        catch(IOException e){e.printStackTrace();}

        try{
            jReader = new JsonReader(new InputStreamReader(mdRaw, "UTF-8"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return jReader;
    }
    
    /**
 	* Prints structure and objects of a file system
 	* \param jReader the Json reader
 	*/
    public void getFileSystem(JsonReader jReader){
        String json = "{\"brand\" : \"Toyota\", \"doors\" : 5}";
        JsonReader jsonReader = new JsonReader(new StringReader(json));

        try {
            while(jsonReader.hasNext()){
                JsonToken nextToken = jsonReader.peek();
                System.out.println(nextToken);
                if(JsonToken.BEGIN_OBJECT.equals(nextToken)){

                    jsonReader.beginObject();
        
                } else if(JsonToken.NAME.equals(nextToken)){
        
                    String name  =  jsonReader.nextName();
                    System.out.println(name);
        
                } else if(JsonToken.STRING.equals(nextToken)){
        
                    String value =  jsonReader.nextString();
                    System.out.println(value);
        
                } else if(JsonToken.NUMBER.equals(nextToken)){
        
                    long value =  jsonReader.nextLong();
                    System.out.println(value);
        
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        // FileSystem fSys = null;
        // Gson gson = new Gson();

        // try{
        //     fSys = gson.fromJson(jReader, FileSystem.class); // Retrieve FileSystem object from json file
        // }
        // catch(Exception e){ e.printStackTrace();}
        
        // return fSys;
    }

    /*public void writeMetaData(InputStream stream) throws Exception
   {
       JsonParser jsonParser _ null;
       long guid = md5("Metadata");
       ChordMessageInterface peer = chord.locateSuccessor(guid);
       peer.put(guid, stream);
   }
  */

    /**
 	* To write out the index info that has been read from a file
 	* \param stream the file system info that is being written to the file system
 	*/
    public void writeMetaData(InputStream stream){
        long guid = md5("Metadata");
        ChordMessageInterface peer = null;

        try {
            peer = chord.locateSuccessor(guid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            peer.put(guid, stream);  
        } catch (Exception e){e.printStackTrace();}
    }
    
    /**
 	* Renames metadata
 	* \param in the scanner object from client
 	*/
    public void mv(Scanner in) throws Exception
    {
        String oldname = "";
        String newname = "";
        System.out.print("Type in the old name for the metadata: ");
        if(in.hasNext()) {
            oldname = in.next();
        }

        System.out.print("Type in the new name for the metadata: ");
        if(in.hasNext()) {
            newname = in.next();
        }
        // TODO:  Change the name in Metadata
        // Write Metadata
    }

    /**
 	* Prints a list of all the files in the DFS
 	* \return list of all the files in the DFS
 	*/
    public String ls() throws Exception
    {
        String listOfFiles = "";
        JsonReader reader = readMetaData();;
        //FileSystem fSys = getFileSystem(reader);
        getFileSystem(reader);

        //for all files in metadata
        //  print filename
        //  append to listOfFiles
        return listOfFiles;
    }
 
    /**
 	* Creates file in DFS
 	* \param in the scanner object from client
 	*/
    public void touch(Scanner in) throws Exception
    {
        String filename = "";
        System.out.print("Type in the file name: ");
        if(in.hasNext()){
            filename = in.next();
        }

        // TODO: Create the file fileName by adding a new entry to the Metadata
        //create file and pass file name as parameter
        //add file to metadata
    }

    /**
 	* Deletes file and metadata
 	* \param in the scanner object from client
 	*/
    public void delete(Scanner in) throws Exception
    {
        String filename = "";
        System.out.print("Type in the file name: ");
        if(in.hasNext()){
            filename = in.next();
        }

        String[] parsed = filename.split("/");
        long guid = Long.parseLong(parsed[1]);
        long guidObject = Long.parseLong(parsed[3]);

        InputStream file = get(guidObject);
        
        //maybe pass a different filename through new FileReader()?
        FileSystem test = gson.fromJson(new FileReader(filename), FileSystem.class);
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 10; j++){
                peer = chord.locateSuccessor(test.getMetadata().getFile()[i].getPage()[j].getGuid());
                peer.delete(test.getMetadata().getFile()[i].getPage()[j].getGuid());
            }
        } 
        delete(guidObject);
        put(guid, file);
    }
    
    /**
 	* Reads a given page of a file
 	* \param in the scanner object from client
 	* \return the desired page file
 	*/
    public Byte[] read(Scanner in) throws Exception
    {
        String filename = "";
        int pageNumber = 0;
        System.out.print("Type in the file name: ");
        if(in.hasNext()){
            filename = in.next();
        }
        System.out.print("Type in the page number: ");
        if(in.hasNextInt()){
            pageNumber = in.nextInt();
        }
        
        // TODO: read pageNumber from fileName
        //search for file name in file system
        //if file found
        //  return page[pageNumber]
        //else
        return null;
    }
    
    /**
 	* Reads last page of a file
 	* \param in the scanner object from client
 	* \return the last page file
 	*/
    public Byte[] tail(Scanner in) throws Exception
    {
        String filename = "";
        System.out.print("Type in the file name: ");
        if(in.hasNext()){
            filename = in.next();
        }
        
        String[] parsed = filename.split("/");
        long guid = Long.parseLong(parsed[1]);
        long guidObject = Long.parseLong(parsed[3]);
        InputStream file = get(guidObject);
        
        //maybe pass a different filename through new FileReader()?
        FileSystem test = gson.fromJson(new FileReader(filename), FileSystem.class);
        if(file != null){
        	int index = test.getMetadata().getFile().getNumberOfPages();
        	return test.getMetadata().getFile().getPage()[index - 1];
        }
        else
        	return null;
    }

    /**
 	* Reads first page of a file
 	* \param in the scanner object from client
 	* \return the first page file
 	*/
    public Byte[] head(Scanner in) throws Exception
    {
        String filename = "";
        System.out.print("Type in the file name: ");
        if(in.hasNext()){
            filename = in.next();
        }
        
        String[] parsed = filename.split("/");
        long guid = Long.parseLong(parsed[1]);
        long guidObject = Long.parseLong(parsed[3]);
        InputStream file = get(guidObject);
        
        //maybe pass a different filename through new FileReader()?
        FileSystem test = gson.fromJson(new FileReader(filename), FileSystem.class);
        if(file != null)
        	return test.getMetadata().getFile()[i].getPage()[0];
        else
        	return null;
    }

    /**
 	* Add to file
 	* \param in the scanner object from client
 	*/
    public void append(Scanner in) throws Exception
    {
        String filename = "";
        System.out.print("Type in the file name: ");
        if(in.hasNext()){
            filename = in.next();
        }
        
        //add size
        Byte[] data = new Byte[10];
        
        // TODO: append data to fileName. If it is needed, add a new page.
        // Let guid be the last page in Metadata.filename
        // ChordMessageInterface peer = chord.locateSuccessor(guid);
        // peer.put(guid, data);
        //add to page[pageSize]
        // Write Metadata        
    }
    
}
