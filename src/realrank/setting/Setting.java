package realrank.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class Setting {

	private static Setting instance = new Setting();
	
	private DatabaseSetting db = new DatabaseSetting();

	public DatabaseSetting db() {
		return db;
	}

	public static Setting getInstance() {
		return instance;
	}

	private Setting() {
		try {
			JsonReader reader = new JsonReader(new FileReader(
					"setting.realrank"));
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("database")) {
					reader.beginObject();
					while (reader.hasNext()) {
						String dbn = reader.nextName();
						if (dbn.equals("url")) {
							db.setUrl(reader.nextString());
						} else if (dbn.equals("id")) {
							db.setId(reader.nextString());
						} else if (dbn.equals("password")) {
							db.setPassword(reader.nextString());
						}
					}
					reader.endObject();
				} else {
					reader.skipValue(); // avoid some unhandle events
				}
			}
			reader.endObject();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
