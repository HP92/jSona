package de.roth.jsona.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.roth.jsona.mediaplayer.PlayBackMode;
import de.roth.jsona.tag.detection.DetectorRule;
import de.roth.jsona.tag.detection.DetectorRuleConfig;
import de.roth.jsona.tag.detection.FilenameDetectorRule;

/**
 * Singleton that represents the configuration of the application with all its
 * attributes.
 *
 * @author Frank Roth
 *
 */
public class Config {

	private static Config instance;

	/**
	 * Return a configuration instance
	 *
	 * @return Config
	 */
	public static Config getInstance() {
		if (instance == null) {
			instance = fromDefaults();
		}
		return instance;
	}

	public String PATH_TO_VLCJ;
	public int MAX_SEARCH_RESULT_AMOUNT;
	public int VOLUME;
	public ArrayList<String> FOLDERS;
	public PlayBackMode PLAYBACK_MODE;
	public String SENT_TO_PATH;
	public int RECENTLY_ADDED_UNITL_TIME_IN_DAYS;
	public String THEME;
	public int KEY_SKIP_TIME;
	public boolean WINDOW_UNDECORATED;
	public String TITLE;
	public int MIN_HEIGHT;
	public int MIN_WIDTH;
	public boolean COLORIZE_ITEMS;
	public ArrayList<DetectorRuleConfig> FILEPATH_BASED_MUSIC_INFORMATIONS;

	public Config() {
		// default values
		this.TITLE = "jSona powered by VLCJ and  JavaFX!";
		this.THEME = "grey";
		this.VOLUME = 80;
		this.MAX_SEARCH_RESULT_AMOUNT = 512;
		this.PLAYBACK_MODE = PlayBackMode.NORMAL;
		this.KEY_SKIP_TIME = 10;
		this.RECENTLY_ADDED_UNITL_TIME_IN_DAYS = -7;
		this.WINDOW_UNDECORATED = false;
		this.MIN_HEIGHT = 400;
		this.MIN_WIDTH = 600;
		this.COLORIZE_ITEMS = true;
	}

	/**
	 * Load the configuration from the over given file
	 *
	 * @param file
	 *            for the configuration
	 */
	public static void load(File file) {
		instance = fromFile(file);

		// no config file found
		if (instance == null) {
			instance = fromDefaults();
		}
	}

	/**
	 * Load the configuration from the over given path
	 *
	 * @param file
	 *            for the configuration
	 */
	public static void load(String file) {
		load(new File(file));
	}

	/**
	 * Load the configuration from the default values, no file required.
	 *
	 * @return
	 */
	private static Config fromDefaults() {
		Config config = new Config();
		return config;
	}

	/**
	 * Save the configuration to the over given file path.
	 *
	 * @param file
	 */
	public void toFile(String file) {
		toFile(new File(file));
	}

	/**
	 * Save the configuration to the over given file
	 *
	 * @param file
	 */
	public void toFile(File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonConfig = gson.toJson(this);
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			writer.write(jsonConfig);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static Config fromFile(String path) {
		return fromFile(new File(path));
	}

	/**
	 * Converts a string array list with file paths in a file array list
	 *
	 * @param filepaths
	 *            - String array with file paths
	 * @return ArrayList<File> files
	 */
	public static ArrayList<File> asFileArrayList(ArrayList<String> filepaths) {
		ArrayList<File> files = new ArrayList<File>();
		for (String f : filepaths) {
			files.add(new File(f));
		}
		return files;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

	private static Config fromFile(File configFile) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
			return gson.fromJson(reader, Config.class);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
