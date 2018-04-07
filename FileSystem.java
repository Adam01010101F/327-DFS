/** \file */

/** \brief This file implements a given distributed file system.
 */

public class FileSystem{
    Metadata metadata;

    /**
 	* Constructs a DFS instance
 	*/
    FileSystem(){ metadata = new Metadata(); }

    /**
 	* Constructs a DFS instance with metadata
 	* \param md metadata
 	*/
    FileSystem(Metadata md){ metadata = md; }

    /**
 	* Returns metadata
 	* \return metadata
 	*/
    Metadata getMetadata(){ return metadata; }

    /**
 	* Sets metadata in file system
 	* \param md metadata
 	*/
    void setMetadata(Metadata md){ metadata = md; }
}