/**
 * 
 */
package org.dpr.mykeys.app;

import java.io.File;

import org.apache.commons.io.FilenameUtils;


/**
 * @author Buck
 *
 */
public class BagInfo implements NodeInfo {

	String name;
	String path;

	/**
	 * //TODO
	 * 
	 * @param fileName
	 */
	public BagInfo(String fileName) {
		String name = fileName.substring(fileName.lastIndexOf("\\") + 1,
				fileName.length());
		this.path = fileName;
		this.name = name;
	}

	public BagInfo(File file) {
	    String path=file.getPath();
        setPath(FilenameUtils.getPath(path));
        setName(FilenameUtils.getName(path));
	}
	
	   public BagInfo() {

	    }

	/**
	 * .
	 * 
	 * <BR>
	 * 
	 * 
	 * @return
	 * 
	 * @see org.dpr.mykeys.app.NodeInfo#isOpen()
	 */
	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * .
	 * 
	 * <BR>
	 * 
	 * 
	 * @param isOpen
	 * 
	 * @see org.dpr.mykeys.app.NodeInfo#setOpen(boolean)
	 */
	@Override
	public void setOpen(boolean isOpen) {
		// TODO Auto-generated method stub

	}

	/**
	 * Retourne le name.
	 * 
	 * @return String - le name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Affecte le name.
	 * 
	 * @param name
	 *            le name � affecter.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retourne le path.
	 * 
	 * @return String - le path.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Affecte le path.
	 * 
	 * @param path
	 *            le path � affecter.
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
