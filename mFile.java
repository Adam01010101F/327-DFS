/** \file */

/** \brief This file implements a DFS file.
 */

public class mFile {
    private String name;
    private int numberOfPages;
    private int pageSize;
    private int size;
    private Page page[];

    /**
 	* Constructs a metadata instance
 	*/
    mFile() {
        name = "";
        numberOfPages = 0;
        pageSize = 0;
        size = 0;
        page = new Page[10];
    }

    /**
 	* Constructs a DFS instance with info
 	* \param mFilename filename
 	* \param numPages page count
 	* \param pSize page size
 	* \param sz file size
 	* \param pg pages
 	*/
    mFile(String mFilename, int numPages, int pSize, int sz, Page pg[]) {
        name = mFilename;
        numberOfPages = numPages;
        pageSize = pSize;
        size = sz;
        page = pg;
    }

    /**
 	* Returns file name
 	* \return filename
 	*/
    String getName(){ return name; }

    /**
 	* Returns page count
 	* \return page count
 	*/
    int getNumberOfPages(){ return numberOfPages; }

    /**
 	* Returns page size
 	* \return page size
 	*/
    int getPageSize(){ return pageSize; }

    /**
 	* Returns file size
 	* \return file size
 	*/
    int getSize(){ return size; }

    /**
 	* Returns page
 	* \return page
 	*/
    Page[] getPage(){ return page; }

    /**
 	* Sets file name
 	* \param nm name
 	*/
    void setName(String nm){ name = nm; }

    /**
 	* Sets page count
 	* \param numOPages page count
 	*/
    void setNumberOfPages(int numOPages){ numberOfPages = numOPages; }

    /**
 	* Sets page size
 	* \param pSize page size
 	*/
    void setPageSize(int pSize){ pageSize = pSize; }

    /**
 	* Sets file size
 	* \param sz size
 	*/
    void setSize(int sz){ size = sz; }

    /**
 	* Sets pages
 	* \param pg pages
 	*/
    void setPage(Page pg[]){ page = pg; }
}