import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class JsonParser {
   static public void main (final String[] args){
       Gson gson = new Gson();
       int nId = 312;
       FileWriter fWriter = null;
       FileReader fReader = null;
       Page page[] = new Page[2]; //Blank Pages - Shows up as null

       //Creating Song Files to be stored within metadata - Arbitrary Values
       mFile songs[] = new mFile[2];
       songs[0] = new mFile("LOLSMH", 1, 10, 10, page);
       songs[1] = new mFile("Pheonix", 1, 15, 15, page);

       //Creating Metadata objects and file strucuture object
       Metadata mData = new Metadata(songs);
       FileSystem uFS = new FileSystem(mData);

       // Examples of Writing to JSON
       try {
           fWriter = new FileWriter("327FS.json"); //Create a writer to write to json file
           fWriter.write(gson.toJson(uFS)); //Write FileSystem object to json file
           System.out.println("OG\n" + gson.toJson(uFS)); //Display json contents
       }
       catch (IOException e){ e.printStackTrace();}
       finally {
           if(fWriter != null){
               try{fWriter.close();}
               catch (IOException e){e.printStackTrace();}
           }
       }

       // Example of Reading from a JSON
       try{
           fReader = new FileReader("327FS.json"); //Create a reader to view json file
           zFS = gson.fromJson(fReader, FileSystem.class); // Retrieve FileSystem object from json file
           System.out.print("ZFS\n"+gson.toJson(zFS)); //Display Json Contents
       }
       catch(FileNotFoundException e){ e.printStackTrace();}
       finally{
           if(fReader != null){
               try{fReader.close();}
               catch (IOException e){e.printStackTrace();}
           }
       }
   }
    // Gson gson;
    // mFile file;
    // Metadata metadata;
    // FileSystem cFS;

    // JsonParser(){
    //     gson = new Gson();
    //     file = new File();
    //     metadata = new Metadata();
    //     cFS = new FileSystem();
    // }
}