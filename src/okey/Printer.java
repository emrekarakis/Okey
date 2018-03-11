package okey;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Printer {

	private Player currentPlayer;
	private List<Player> players;

	public Printer(Player player) {
		this.currentPlayer = new Player();
	}

	public Printer() {

	}

	public void printChosenPlayer() {
		System.out.println("Chosen Random Player => " + currentPlayer.getName() + "-" + currentPlayer.getNumber());
	}

	public void printPlayers(List<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			System.out.print(players.get(i).getNumber() + "- ");
			System.out.println(players.get(i).getName());

		}

	}

	public void printList(List<Tiles> tiles) {
		for (int i = 0; i < tiles.size(); i++) {
			System.out.println(
					tiles.get(i).getCode() + "->" + tiles.get(i).getColor() + "-" + tiles.get(i).getRealNumber());

		}
	}

	public void printListOfList(List<List<Tiles>> tiles) {
		for (int i = 0; i < tiles.size(); i++) {
			for (int j = 0; j < tiles.get(i).size(); j++) {
				System.out.println(tiles.get(i).get(j).getCode() + "->" + tiles.get(i).get(j).getColor() + "-"
						+ tiles.get(i).get(j).getRealNumber());
			}
			System.out.println("------------------------");
		}
	}

	public void printStackOfEachPlayer() {
		for (int k = 0; k < players.size(); k++) {
			System.out.println("--------------" + (k + 1) + ".player's Stack-------------------");
			printList(players.get(k).getStack());

		}
	}

	public void printMap(Map<Integer, Integer> hmap) {
		for (Integer each : hmap.keySet()) {
			System.out.println("Key-->" + each + " Value-->" + hmap.get(each));
		}
	}
	
	public void printMapWithList(Map<Integer, List<Tiles>> hmap){
		for (Integer each : hmap.keySet()) {
			System.out.println("Key-->" + each);
			printList(hmap.get(each));
		}
	}

	public void printTile(Tiles tiles) {
		System.out.println(tiles.getCode() + "->" + tiles.getColor() + "-" + tiles.getRealNumber());
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
