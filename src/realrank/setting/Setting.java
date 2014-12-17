package realrank.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class Setting {
	private final static String FILE_PATH = "WebContent/WEB-INF/setting.realrank";

	private static Setting instance = new Setting();
	private DatabaseSetting db = new DatabaseSetting();

	private DomainSetting domainSetting;

	public DatabaseSetting getDb() {
		return db;
	}

	public DomainSetting getDomain() {
		return domainSetting;
	}

	public static Setting getInstance() {
		return instance;
	}

	private Setting() {
		try {
			JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("database")) {
					readDBSettings(reader);
					continue;
				}
				if (name.equals("domain")) {
					readDomainSetting(reader);
					continue;
				}
				reader.skipValue(); // avoid some unhandle events
			}
			reader.endObject();
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Current Path: "
					+ System.getProperty("user.dir"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readDomainSetting(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String domain = reader.nextName();
			if (domain.equals("url")) {
				domainSetting = new DomainSetting(reader.nextString());
			}
		}
		reader.endObject();
	}

	private void readDBSettings(JsonReader reader) throws IOException {
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
	}

}
