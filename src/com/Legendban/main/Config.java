package com.Legendban.main;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	private Main main = Main.getInstance();
	private boolean isNewFile;
	private File currentDirectory;
	private File file;
	private FileConfiguration fileConfig;

	public Config(String directory, String filename, boolean isNewFile) {
		this.isNewFile = isNewFile;

		createDirectory(directory);
		createFile(directory, filename);
		
		this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
	}

	public void createDirectory(String directory) {
		this.currentDirectory = main.getDataFolder();
		if (directory != null) {
			this.currentDirectory = new File(main.getDataFolder(), directory.replace("/", File.separator));
			this.currentDirectory.mkdir();
		}
	}

	public void createFile(String directory, String filename) {
		this.file = new File(this.currentDirectory, filename);
		if (!this.file.exists()) {
			if (this.isNewFile) {
				try {
					this.file.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				main.saveResource( directory != null ? directory + File.separator + filename : filename, false);
			}
		}
	}
	
	public FileConfiguration getConfig() {
		return this.fileConfig;
	}
	
	public void save() {
		try {
			this.fileConfig.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reload() {
		this.fileConfig.setDefaults(YamlConfiguration.loadConfiguration(this.file));
	}
}
