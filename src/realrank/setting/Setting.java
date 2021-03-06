package realrank.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.stream.JsonReader;

public class Setting {

	private static Setting setting = new Setting();

	private Map<String, String> db = new HashMap<String, String>();
	private Map<String, String> domain = new HashMap<String, String>();
	private Map<String, String> general = new HashMap<String, String>();

	private Setting() {
		String path = Setting.class.getResource("/").getPath();
		if ( path.contains("/WEB-INF/") ) {
			path += "../";
		} else {
			path = "./WebContent/WEB-INF/";
		}

		try {
			JsonReader reader = new JsonReader(new FileReader(path
					+ "setting.realrank"));
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("database")) {
					readDBSettings(reader);
					continue;
				}
				if (name.equals("domain")) {
					readDomainSettings(reader);
					continue;
				}
				if (name.equals("general")) {
					readGeneralSettings(reader);
					continue;
				}
				reader.skipValue();
			}
			reader.endObject();
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Current Path: " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String get(String type, String key) {
		switch (type) {
		case "db":
			return setting.db.get(key);
		case "general":
			return setting.general.get(key);
		case "domain":
			return setting.domain.get(key);
		default:
			return null;
		}

	}

	private void readDBSettings(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String dbn = reader.nextName();
			if (dbn.equals("url")) {
				db.put("url", reader.nextString());
			} else if (dbn.equals("id")) {
				db.put("id", reader.nextString());
			} else if (dbn.equals("password")) {
				db.put("password", reader.nextString());
			}
		}
		reader.endObject();
	}
	
	private void readDomainSettings(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String dbn = reader.nextName();
			if (dbn.equals("url")) {
				domain.put("url", reader.nextString());
			} 
		}
		reader.endObject();
	}

	private void readGeneralSettings(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String dbn = reader.nextName();
			if (dbn.equals("url")) {
				general.put("url", reader.nextString());
			} else if (dbn.equals("controllerPath")) {
				general.put("controllerPath", reader.nextString());
			} else if (dbn.equals("jspPath")) {
				general.put("jspPath", reader.nextString());
			}
		}
		reader.endObject();
	}

}