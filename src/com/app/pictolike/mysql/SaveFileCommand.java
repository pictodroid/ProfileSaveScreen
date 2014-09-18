package com.app.pictolike.mysql;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.app.pictolike.data.PictoFile;

class SaveFileCommand extends MySQLCommand {
	String m_strUserName;
	String m_strFileName;
	String m_strDateCreated;
	String m_strLocationCreated;

	
	SaveFileCommand(String username,String filename, String datecreated,String locationcreated ) {
		m_strUserName = username;
		m_strFileName = filename;
		m_strDateCreated=datecreated;
		m_strLocationCreated=locationcreated;
		
	}

	@Override
	void command() {
		ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		InputStream is;
		String result = "";
		
		// connect server.
		try {
			HttpPost httpPost = new HttpPost(MySQLConnect.LINK_SENTFILE);
			nameValuePair.add(new BasicNameValuePair(MySQLConnect.USER_NAME, m_strUserName));
			nameValuePair.add(new BasicNameValuePair(MySQLConnect.FIELD_NAME, m_strFileName));
			nameValuePair.add(new BasicNameValuePair(MySQLConnect.DATE_CREATED, m_strDateCreated));
			nameValuePair.add(new BasicNameValuePair(MySQLConnect.LOCATION_CREATED, m_strLocationCreated));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = MySQLConnect.HTTP_CLIENT.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			setErrorCode(MySQLConnect.ERR_CONNECTION_FAILED);
			e.printStackTrace();
			return;
		}
		
		// convert response to string.
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line + "\n");
			result = sb.toString();
		} catch (Exception e) {
			setErrorCode(MySQLConnect.ERR_LOAD_FAILED);
			e.printStackTrace();
			return;
		}

		// parsing json data		
		try {
			JSONArray jarray = new JSONArray(result);
			JSONObject json_data = jarray.getJSONObject(0);
			PictoFile file = new PictoFile();
			file.username=json_data.getString(MySQLConnect.USER_NAME);
			file.filename = json_data.getString(MySQLConnect.FILE_NAME);
			file.dateCreated = json_data.getString(MySQLConnect.DATE_CREATED);
			file.locationCreated = json_data.getString(MySQLConnect.LOCATION_CREATED);
			
			setResult(file);			
		} catch (Exception e) {
			setErrorCode(MySQLConnect.ERR_PARSE_FAILED);
			e.printStackTrace();
			return;
		}
	}
}
