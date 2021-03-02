//package net.ddns.fantsasia.audio_work_technology;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AWT_001 {
	
	public static void main(String[] args) {
		System.out.println("ファイルを選択してください。");
		System.out.println("=== サポートされている形式 ===");
		Type[] typeArray = AudioSystem.getAudioFileTypes();
		for(Type type : typeArray) {
			System.out.println(type.toString());
		}
		AudioInputStream  ais1 = null;
		AudioInputStream  ais2 = null;

		try {
			System.out.println("？");
			Scanner scan1 = new Scanner(System.in);
			String str1 = scan1.next();
			ais1 = AudioSystem.getAudioInputStream(new File("/home/akira/" + str1));
			AudioFormat af1 = ais1.getFormat();
			DataLine.Info info1 = new DataLine.Info(Clip.class, af1);
			Clip clip1 = (Clip)AudioSystem.getLine(info1);
			clip1.open(ais1);
			Sound sound1 = new Sound(clip1);
			sound1.play();
//					Thread.sleep((int)3/16);
			while(true) {
				System.out.println("？");
				Scanner scan2 = new Scanner(System.in);
				String str2 = scan2.next();
				ais2 = AudioSystem.getAudioInputStream(new File("/home/akira/" + str2));
				AudioFormat af2 = ais2.getFormat();
				DataLine.Info info2 = new DataLine.Info(Clip.class, af2);
				Clip clip2 = (Clip)AudioSystem.getLine(info2);
				clip2.open(ais2);
				Sound sound2 = new Sound(clip2);
//					Thread.sleep((int)3/16);
				if(clip1 == clip2) {
					sound1.play();
				} else {
					sound1.stop();
					sound1 = sound2;
					sound1.play();
				}
			}
		} catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		} finally {
			try {
				ais1.close();
			} catch(IOException e) {
					e.printStackTrace();
			}
		}
	}
}

class Sound {
	Clip clip;
	
	Sound(Clip clip) {
		this.clip = clip;
	}
	
	void play() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	void stop() {
		clip.stop();
		clip.flush();
	}
}
