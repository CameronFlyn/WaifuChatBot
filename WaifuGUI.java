package waifu.controller;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.lang.Math;

public class WaifuGUI extends JFrame implements KeyListener {
	
	JPanel p = new JPanel();
	JMenuBar menubar = new JMenuBar();
	JTextArea dialog = new JTextArea(20,50);
	JTextArea input = new JTextArea(1,50);
	JScrollPane scroll = new JScrollPane(
			dialog,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	BufferedImage myPicture = ImageIO.read(new File(null, title));
	JLabel picLabel = new JLabel(new ImageIcon(myPicture));

	static int answers = 0;
	
	public static void main(String[] args) {
		new WaifuGUI();
	}
	
	public WaifuGUI() {
		super("Waifu Chat Bot");
		setSize(600, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		dialog.setEditable(false);
		input.addKeyListener(this);
		p.setLayout(new BorderLayout());
		p.add(menubar, BorderLayout.NORTH);
		p.add(scroll, BorderLayout.EAST);
		p.add(input, BorderLayout.SOUTH);
		p.add(picLabel, BorderLayout.WEST);
		
		JMenu waitype = new JMenu("Waifu Type");
		menubar.add(waitype);
		JMenuItem tsun = new JMenuItem("Tsundere");
		tsun.addActionListener(new Tsunaction());
		waitype.add(tsun);
		/* JMenuItem kuu = new JMenuItem("Kuudere");
		kuu.addActionListener(new Kuuaction());
		waitype.add(kuu); */
		JMenuItem yan = new JMenuItem("Yandere");
		yan.addActionListener(new Yanaction());
		waitype.add(yan);
		/* JMenuItem loli = new JMenuItem("Loli");
		loli.addActionListener(new Loliaction());
		waitype.add(loli);   */
		JMenuItem exit = new JMenuItem("Exit");
		waitype.add(exit);
		
		dialog.setText("Choose your Waifu from the menu. Default is set to tsundere mode, take care.\n");
		
		exit.addActionListener(new exitaction());
		
		add(p);
		
		setVisible(true);
		
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			input.setEditable(false);
		
			String quote = input.getText();
			input.setText("");
			addText("-->You: "+quote);
			quote = quote.trim();
			while(
				quote.charAt(quote.length()-1)=='!' ||
				quote.charAt(quote.length()-1)=='.' ||
				quote.charAt(quote.length()-1)=='?'
			){
				quote = quote.substring(0,quote.length()-1);
			}
			
			quote = quote.trim();
			
			byte response = 0;
			int j = 0;
			String[][] chatBot = chatBot();
			String[] botName = {"Tsundere Waifu","Kuudere Waifu","Yandere Waifu","Loli Waifu"};
			
			while(response == 0) {
				if(inArray(quote.toLowerCase(),chatBot[j*2])) {
					response = 2;
					int r = (int)Math.floor(Math.random()*chatBot[(j*2)+1].length);
					addText("\n-->"+botName[answers]+": "+chatBot[(j*2)+1][r]);
				}
				j++;
				if(j*2==chatBot.length-1 && response == 0){
					response = 1;
				}
			}
			
			if(response == 1) {
				int r = (int)Math.floor(Math.random()*chatBot[chatBot.length-1].length);
				addText("\n-->"+botName[answers]+": "+chatBot[chatBot.length-1][r]);
			}
			
			addText("\n");
		}
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			input.setEditable(true);
		}
	}
	
	public void keyTyped(KeyEvent e){}
	
	public void addText(String str) {
		dialog.setText(dialog.getText()+str);
	}
	
	public boolean inArray(String in, String[] str) {
		boolean match = false;
		for(int i = 0; i < str.length; i++) {
			if (str[i].equals(in)){
				match = true;
			}
		}
		return match;
	}
	
	class exitaction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			System.exit(0);
		}
	}
	
	class Tsunaction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			answers = 0;
			dialog.setText("~~~~~ CAUTION: Tsundere mode activated. ~~~~~\n");
		}
	}
	
	class Kuuaction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			answers = 1;
			dialog.setText("~~~~~ Kuudere mode activated. ~~~~~\n");
		}
	}
	
	class Yanaction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			answers = 2;
			dialog.setText("~~~~~ CAUTION: Yandere mode activated ~~~~~\n");
		}
	}
	
	class Loliaction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			answers = 3;
			dialog.setText("~~~~~ Loli mode activated ~~~~~\n");
		}
	}
	
	public String[][] chatBot() {
		
				
		if (answers == 0) {
			String[][] tsun = {
					//Greetings
					{"hello","hi","good morning","good afternoon","good evening","hey","yo"},
					{"Go away","Get away from me","Don't talk to me","Who do you think you are?","Are you talking to me?",
						"... Hi... I'm only saying it out of common courtesy, not because I like you or anything...",
						"Hi. Not that I care, but how are you?","Don't introduce yourself so casually to me. Who do you think you are?"},
					//How are you?
					{"how are you","how r u","how are u", "how r you"},
					{"Fine... not like I care, but how are you?","How do you think I am, idiot!","I'm fine. Go away","I-I'm fine"},
					//Response to "how are you?" - Dont ask how tsundere is
					{"im good","good","i'm good"},
					{"Ok. Now go away","Ok. Idiot...","Ok. You can stop talking to me now","That's g-good I guess"},
					//Response to "how are you?" - Ask how tsundere is
					{"good. how r u","good. u", "good. how are u","good. how r you","good. how are you","good. how bout u","good. how about you",
						"good. what about you", "good. what about u","im good. how r u","im good. u", "im good. how are u","im good. how r you",
						"im good. how are you","im good. how bout u","im good. how about you","im good. what about you", "im good. what about u"},
					{"How do you think I am, idiot!","Go away. But I'm fine","I-I'm fine"},
					//Wats up?
					{"what is up","wats up","whats up","what's up"},
					{"Not much. Definitley not thinking of you","None of your business","Nothing you need to be concerned about",
						"Who do you think you are addressing your master so casually?",},
					//Do you like me?
					{"do you like me","you like me","do u like me"},
					{"I-I DO NOT!!!","Why would I like you?","NO! GO AWAY! SHUT UP!"},
					//Blush
					{"are you blushing","r u blushing","are u blushing","r you blushing","you're blushing","you are blushing","u are blushing"},
					{"I AM NOT!","I'm not. It's a.. a.. a fever. Just a fever.","Why would I blush at someone like you?"},
					//Marriage
					{"marry me","will you marry me"},
					{"HELL NO!","Who do you think you are?","Who do you think you're asking?","A-Are you asking me?","... a-are you being serious?"},
					//Why?
					{"why are you so cold","why not","why","why are you such a bitch","why are u so cold","y are you so cold", "y are u so cold"},
					{"Because you're a big pervert","Because! Just because!","A dog shouldn't talk back to its master"},
					//Yes
					{"yea","yes","ya","yeah","alright","sure","uh huh","right"},
					{"Um... ok","Oh","Uh...","Uh..... GO AWAY! SHUT UP!"},
					//No
					{"no","no way","nope","nah"},
					{"Ok. Now go away","Idiot...","You're such an idiot","Oh"},
					//Response to "who do you think you are?"
					{"your lover","ur lover"},
					{"Don't kid yourself","Do you really mean that?","D-Don't say something so embarassing","W-We're not that close"},
					//General (Catch all)
					{"Go away!","Shut up!","Shut up! Shut up! Shut up!","U-Umm... no","....","U-Uh..."},
			};
			
			return tsun;	
			
		} else if (answers == 2) {
			String[][] yan = {
					//Greetings
					{"hello","hi","hey","yo"},
					{"Heyy","H-Hi...","Hello dear!","Hey, I've been thinking about you ;)",
						"Hey! I remember you. You're that guy I can't stop thinking about",
						"Hi!","Hey, I had a dream about you last night. Couldn't stop thinking about you ever since"},
					//Dear?
					{"dear"},
					{"Oh, I'm so happy you called me that. Let's get married right away", 
						"Did you just call me dear? Because to be honest, I have feelings for you too"},
					//Really?
					{"really","for real"},
					{"Yes. I'm always thinking of you","Of course. I would kill for your love","Yup. Nobody else will do except you"},
					//OH
					{"o", "oh", "okay","ok"},
					{"Yea, I've already thought of names for our children",
						"Yes. I plan on killing all the other girls that like you by the end of the week"},
					//How are you?
					{"how are you","how r u","how are u", "how r you","oh. how are you","oh. how r u","oh. how are u", "oh. how r you",},
					{"Good. I've been thinking of names for our kids", "Great. Just killed a girl that liked you",
						"Alright. Couldn't sleep much last night because I was thinking about you. But you're here now so it's all better =)"},
					//Wats up
					{"what is up","wats up","whats up","what's up"},
					{"Not much. Just killed a girl that liked you", "Oh you know, just thinking of good names for our children",
						"Oh, just thinking about you"},
					//You're crazy
					{"youre crazy","you are crazy","you're crazy","youre nuts","you are nuts","you're nuts"},
					{"WHY CAN'T YOU UNDERSTAND MY LOVE FOR YOU?!","I.. don't.. want.. to.. kill.. you..",
						"There, there. I'll open your eyes for you. Hehe"},
					//General (Catch all)
					{"Hehehehehe...","BWAHAHAHAHA!!!","Kill.... kill.... kill","Must... kill","You will be mine. Hehehe"}
			};
			
			return yan;
			
		} else if (answers == 3) {
			String[][] loli = {
					//Greetings
					{"hello","hi","hey","yo"},
					{"Hi~! Onii-chan!"},
					//Good Morning
					{"good morning","morning"},
					{"Good morning, Onii-chan!","Good morning. Did you sleep well, Onii-chan?","*rubs eyes* I'm still sleepy, Onii-chan."},
					//How are you?
					{"how are you","how are u","how r you","how r u"},
					{},
					//General (Catch all)
					{}
			};
			
			return loli;
			
		} else {
			return null;
		}
	}
}
