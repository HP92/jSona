package de.roth.jsona.file;

import java.io.File;
import java.util.LinkedList;

import de.roth.jsona.config.Config;
import de.roth.jsona.config.Global;

/**
 * Class to perform a file scan over the over given directories or files
 * 
 * @author Frank Roth
 * 
 */
public class FileScanner {

	/**
	 * Scan all over given files or folders
	 * 
	 * @param files
	 *            - files or folders to scan
	 * @param index
	 *            - unique index for this scan
	 * @param rootCall
	 *            - is this a root call always -> always use true
	 * @param listener
	 *            - listener that is informed about scan progress
	 * @param target
	 *            - Target folder
	 * @return
	 */
	public static LinkedList<File> scan(final File[] files, int index, boolean rootCall, FileScannerListener listener, String target) {
		LinkedList<File> allFiles = new LinkedList<File>();

		listener.scannerStart();

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					allFiles.addAll(scan(files[i].listFiles(), index, false, listener, target));
				} else {
					if (isValid(files[i].getName())) {
						allFiles.add(files[i]);
						listener.scannerFileRead(files[i]);
					}
				}

				if (rootCall) {
					listener.scannerRootFileRead(files[i], i);
				}
			}
		}

		if (rootCall) {
			listener.scannerFinished(target, allFiles);
		}

		return allFiles;
	}

	/**
	 * Check if the file is valid
	 * 
	 * @param name
	 * @return file is valid
	 */
	public static boolean isValid(String name) {
		for (String fileName : Global.IMPORT_FILE_FILTER) {
			fileName = fileName.toLowerCase();
			name = name.toLowerCase();
			if (fileName.equals(name) || name.endsWith(fileName)) {
				return false;
			}
		}

        final int extensionIDX = name.lastIndexOf('.');
        if (extensionIDX == -1)
            return false;
        else if (!Config.getInstance().INCLUDE_EXTENSIONS.isEmpty()) {
            final String extension = name.substring(extensionIDX);
            if (!Config.getInstance().INCLUDE_EXTENSIONS.contains(extension))
                return false;
        }
		return true;
	}
}
