package com.world;

import java.net.ServerSocket;
import java.util.Random;
import java.util.Scanner;

public class App {
	// static ServerSocket variable
	private static ServerSocket server;
	// socket server port on which it will listen
	private static int port = 5001;
	public static double treeFactor = 11;
	public static long worldSeed = "GANDALF".hashCode();
	public static Random rng = new Random(App.worldSeed);
	public static int TICKRATE = 200;

	public static void main(String[] args) {

		Map map = new Map();
		LifeManager lm = new LifeManager(map);
		WebsocketServer server = new WebsocketServer(map, lm);

		server.start();
		System.out.println(worldSeed);

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.nextLine();
			String[] sArgs = line.split(" ");
			String cmd = sArgs[0];
			String param = "";
			if (sArgs.length > 1) {
				param = sArgs[1];
			}

			System.out.println("Entered: " + cmd);
			if (cmd.equals("+")) {
				App.treeFactor = App.treeFactor + 1;
				System.out.println("increased: " + App.treeFactor);
				map.resetMap();
			}
			if (cmd.equals("-")) {
				App.treeFactor = App.treeFactor - 1;
				System.out.println("decreased: " + App.treeFactor);
				map.resetMap();
			}
			if (cmd.equals("life")) {
				lm.spawnLife(Integer.parseInt(param));
				System.out.println("Hello");
			}
			if (cmd.equals("exit")) {
				try {
					System.out.println("Shutting down");
					server.stop();
					return;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}