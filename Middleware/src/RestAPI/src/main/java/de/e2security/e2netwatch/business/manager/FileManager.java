
/*
    Copyright (C) 2017 e-ito Technology Services GmbH
    e-mail: info@e-ito.de
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


package de.e2security.e2netwatch.business.manager;

import java.io.File;
import java.util.List;

/**
 * Managing files component
 * 
 * @author Hrvoje
 *
 */
public interface FileManager {
	
	/**
	 * Get the file from filesystem
	 * 
	 * @param url location of the file
	 * 
	 * @return file
	 */
	File getFile(String url);
	
	/**
	 * Getting the list of file names located in location
	 * 
	 * @param location where to get file names
	 * 
	 * @return list of file names
	 */
	List<String> getListOfFileNames(final String location);
}