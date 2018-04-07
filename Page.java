/** \file */

/** \brief This file implements a DFS file page.
 */

public class Page{
    int number;
    int guid;
    int size;

    /**
 	* Constructs a page instance
 	*/
    Page(){
        number = 0;
        int guid = 0;
        int size = 0;
    }

    /**
 	* Constructs a page instance with info
 	* \param num number
 	* \param guid id
 	* \param sz size
 	*/
    Page(int num, int gid, int sz){
        number = num;
        guid = gid;
        size = sz;
    }

    /**
 	* Returns number
 	* \return number
 	*/
    int getNumber(){ return number; }

    /**
 	* Returns guid
 	* \return guid
 	*/
    int getGuid(){ return guid; }

    /**
 	* Returns page size
 	* \return page size
 	*/
    int getSize(){ return size; }

    /**
 	* Sets page number
 	* \param num page number
 	*/
    void setNumber(int num){ number = num; }

    /**
 	* Sets guid
 	* \param gid id
 	*/
    void setGuid(int gid){ guid = gid; }

    /**
 	* Sets page size
 	* \param sz size
 	*/
    void setSize(int sz){ size = sz; }
}