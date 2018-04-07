/** \file */

/** \brief This file implements a given distributed file system's metadata.
 */

public class Metadata{
    mFile file[];

    /**
 	* Constructs a metadata instance
 	*/
    Metadata(){ file = new mFile[2]; }

    /**
 	* Constructs a DFS instance with files
 	* \param tFiles files
 	*/
    Metadata(mFile tFiles[]){ file = tFiles; }

    /**
 	* Returns file
 	* \return file
 	*/
    mFile[] getFile(){ return file; }

    /**
 	* Sets file in metadata
 	* \param tFile file
 	*/
    void setFile(mFile tFile[]){ file = tFile; }
}