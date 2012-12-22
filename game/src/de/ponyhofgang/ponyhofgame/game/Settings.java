package de.ponyhofgang.ponyhofgame.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import de.ponyhofgang.ponyhofgame.framework.FileIO;

public class Settings {
	public static boolean musicEnabled = true;
	public static boolean sfxEnabled = true;
	public static boolean touchEnabled = true;
	public final static String file = ".ponyracing";

	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			musicEnabled = Boolean.parseBoolean(in.readLine());
			sfxEnabled = Boolean.parseBoolean(in.readLine());
			touchEnabled = Boolean.parseBoolean(in.readLine());
		} catch (IOException e) {
			// :( It's ok we have defaults
		} catch (NumberFormatException e) {
			// :/ It's ok, defaults save our day
		} finally {
			try {
				if (in != null)

					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					files.writeFile(file)));
			out.write(Boolean.toString(musicEnabled));
			out.write("\n");
			out.write(Boolean.toString(sfxEnabled));
			out.write("\n");
			out.write(Boolean.toString(touchEnabled));
		} catch (IOException e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}
}