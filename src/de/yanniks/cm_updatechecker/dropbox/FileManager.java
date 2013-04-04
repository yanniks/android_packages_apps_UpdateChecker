package de.yanniks.cm_updatechecker.dropbox;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;

//DropBoxFileListConnector.getFileList();
//
//DropBoxFileListConnector.getFileState();
//DropBoxFileListConnector.deleteLocalFile();


class FileManager {

	
	
	// In the class declaration section:
	private DropboxAPI<AndroidAuthSession> mDBApi;

	public void DropBoxFileListConnector(Context context, String AppKey, String AppSecret) {
		// And later in some initialization function:
		AppKeyPair appKeys = new AppKeyPair(AppKey, AppSecret);
		AndroidAuthSession session = new AndroidAuthSession(appKeys, Session.AccessType.DROPBOX);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);

		// MyActivity below should be your activity class name
		mDBApi.getSession().startAuthentication(context);
	}
	
	public ArrayList<HashMap<String, Object>> getFileList() {
		Entry entries = null;
		try {
			entries = mDBApi.metadata("/", 100, null, true, null);
		} catch (DropboxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		for (Entry e : entries.contents) {
			if (!e.isDeleted) {
				Log.i("Is Folder",String.valueOf(e.isDir));
				Log.i("Item Name",e.fileName());
				
				if (true/*is zip*/ ) {
					HashMap<String, Object> elMap = new HashMap<String, Object>();
					elMap.put("filetitle", e.fileName());
					elMap.put("filestate", getFileState(e.fileName()));
					list.add(elMap);
				}
				
			}
		}
		
		return list;
	}
	
	private FileState getFileState(String StringFilename) {
		File file = new File(Environment.DIRECTORY_DOWNLOADS + StringFilename);
		if (!file.exists() && !file.isDirectory()) {
			return FileState.REMOTE_SAVED;
		}
		
		if (file.exists() && !file.isDirectory() && isValidDropBoxFile(StringFilename)) {
			return FileState.LOCAL_SAVED;
		}
		
		if (!isValidDropBoxFile(StringFilename)) {
			return FileState.DOWNLOADING;
		}
		
		return FileState.REMOTE_SAVED;
	}
	
	//http://stackoverflow.com/questions/304268/getting-a-files-md5-checksum-in-java
	private boolean isValidDropBoxFile(String StringFilename) {
		return true;
	}
		
	/**
	 * Delete the file on local sdcard 
	 * 
	 * @param filename
	 * @return If deleted successful or not
	 */
	public boolean deleteLocalFile(String filename) {
		File file = new File(Environment.DIRECTORY_DOWNLOADS + filename);
		return file.delete();
	}
}