package okey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Helper {
	private Printer printer = new Printer();
	private List<List<Tiles>> sequenceList = new ArrayList<List<Tiles>>();
	private List<List<Tiles>> distributedList = new ArrayList<List<Tiles>>();
	private List<Tiles> middleClusterOfTiles = new ArrayList<Tiles>();
	private Player currentPlayer = new Player();
	private Tiles indicatorTile = new Tiles();
	private List<Tiles> allTiles = new ArrayList<Tiles>();
	private List<Player> players = new ArrayList<Player>();
	private static Random randomGenerator = new Random();
	private Tiles okeyTile = new Tiles();

	public Helper() {

	}

	public void fillTiles() {

		for (int i = 0; i <= 52; i++) {
			Tiles tiles = new Tiles();

			if (i >= 0 && i < 13) {
				tiles.setCode(i);
				tiles.setColor("YELLOW");
				tiles.setRealNumber(i + 1);

			} else if (i >= 13 && i < 26) {
				tiles.setCode(i);
				tiles.setColor("BLUE");
				tiles.setRealNumber(i - 12);

			} else if (i >= 26 && i < 39) {
				tiles.setCode(i);
				tiles.setColor("BLACK");
				tiles.setRealNumber(i - 25);

			} else if (i >= 39 && i <= 51) {
				tiles.setCode(i);
				tiles.setColor("RED");
				tiles.setRealNumber(i - 38);

			} else {
				tiles.setColor("");
				tiles.setCode(i);
			}
			allTiles.add(tiles);
			allTiles.add(tiles);

		}
	}

	public <T> List<Tiles> divideList(List<Tiles> lists, int fromIndex, int endIndex) {
		List<Tiles> temp = new ArrayList<>();
		temp = lists.subList(fromIndex, endIndex);
		return temp;
	}

	public <T> List<List<Tiles>> divideListOfList(List<List<Tiles>> lists, int fromIndex, int endIndex) {
		List<List<Tiles>> temp = new ArrayList<>();
		temp = lists.subList(fromIndex, endIndex);
		return temp;
	}

	public void createPlayers() {

		for (int i = 0; i < 4; i++) {
			Player player = new Player();
			player.setName("PLAYER" + (i + 1));
			player.setNumber(i + 1);
			players.add(player);
		}

		setPlayers(players);
	}

	public void determineIndicatorTile() {

		int index = randomGenerator.nextInt(allTiles.size());
		indicatorTile = allTiles.get(index);
		setIndicatorTile(indicatorTile);
		if (indicatorTile.getCode() == 52) {
			determineIndicatorTile();
		}

	}

	public void determineOkeyTile() {
		Integer code = indicatorTile.getCode();
		String color = indicatorTile.getColor();
		Integer realNumber = indicatorTile.getRealNumber();
		if (indicatorTile.getCode() == 12 || indicatorTile.getCode() == 25 || indicatorTile.getCode() == 38
				|| indicatorTile.getCode() == 51) {
			okeyTile.setCode(indicatorTile.getCode() - 12);
		} else {
			okeyTile.setCode((code + 1));
		}

		okeyTile.setColor(color);
		okeyTile.setRealNumber((realNumber + 1) % 13 == 0 ? 13 : (realNumber + 1) % 13);
		setOkeyTile(okeyTile);
	}

	public void putJokerRealValueToFalseJoker() {
		for (int i = 0; i < allTiles.size(); i++) {
			if (allTiles.get(i).getCode() == 52) {
				allTiles.get(i).setColor(okeyTile.getColor());
				allTiles.get(i).setRealNumber(okeyTile.getRealNumber());
			}
		}
	}

	public void chooseRandomPlayer() {
		int index = randomGenerator.nextInt(players.size());
		currentPlayer = players.get(index);
		setCurrentPlayer(currentPlayer);
		players.remove(index);
	}

	public void aligningFiveGroupsOfTiles() {
		List<Tiles> subList = new ArrayList<Tiles>();
		for (int start = 0; start < allTiles.size(); start += 5) {
			int end = Math.min(start + 5, allTiles.size());
			if (start == allTiles.size() - 6 && end == allTiles.size() - 1) {
				end = allTiles.size();
				subList = allTiles.subList(start, end);
				start = allTiles.size();
			} else {
				subList = allTiles.subList(start, end);
			}
			sequenceList.add(subList);

		}
	}

	public void distributeTiles() {
		List<Tiles> deckOfCurrentPlayer = new ArrayList<Tiles>();
		List<List<Tiles>> separatedList = new ArrayList<List<Tiles>>();
		List<List<Tiles>> remainingList = new ArrayList<List<Tiles>>();
		distributedList = sequenceList.subList(0, 12);
		separatedList = divideListOfList(distributedList, 0, 3);
		System.out.println(
				"The first 3 element inside 12 elements distributed list  is given directly to the chosen player.");
		printer.printListOfList(separatedList);
		remainingList = new ArrayList<>(sequenceList.subList(12, 21));
		deckOfCurrentPlayer = combineSubList(separatedList);
		updateDistributedList(distributedList);
		getCurrentPlayer().setStack(deckOfCurrentPlayer);
		sequenceList.clear();
		sequenceList = new ArrayList<>(remainingList);
		mergeOtherSubList();

	}

	public void updateDistributedList(List<List<Tiles>> distributed) {
		List<List<Tiles>> updatedList = new ArrayList<>(distributed);
		updatedList.remove(0);
		updatedList.remove(0);
		updatedList.remove(0);
		distributedList.clear();
		distributedList = new ArrayList<>(updatedList);

	}

	private static List<Tiles> combineSubList(List<List<Tiles>> tiles) {
		List<Tiles> individualDeck = new ArrayList<Tiles>();
		for (int j = 0; j < tiles.size(); j++) {
			individualDeck.addAll(tiles.get(j));
		}
		return individualDeck;
	}

	public void mergeOtherSubList() {
		List<List<Tiles>> separatedList = new ArrayList<List<Tiles>>();
		List<Tiles> deckOfOtherPlayer = new ArrayList<Tiles>();
		int counter = 0;

		for (int i = 0; i < players.size(); i++) {
			separatedList = new ArrayList<List<Tiles>>();
			deckOfOtherPlayer = new ArrayList<Tiles>();
			separatedList = divideListOfList(distributedList, counter, counter += 3);
			deckOfOtherPlayer = combineSubList(separatedList);
			deckOfOtherPlayer = chooseRandomTile(deckOfOtherPlayer);
			printer.printList(deckOfOtherPlayer);
			players.get(i).setStack(deckOfOtherPlayer);
		}

	}

	public List<Tiles> chooseRandomTile(List<Tiles> tiles) {
		Tiles currentTile = new Tiles();
		int index = randomGenerator.nextInt(tiles.size());
		currentTile = tiles.get(index);
		tiles.remove(index);
		middleClusterOfTiles.add(currentTile);
		return tiles;
	}

	public void settingTilesForPlayer() {
		List<Tiles> currentPlayerStack = new ArrayList<Tiles>(this.currentPlayer.getStack());
		for (int i = 0; i < currentPlayerStack.size(); i++) {

		}
	}

	public void differentColorCase(Player player) {
		Map<Integer, List<Tiles>> possiblePerForDifferentColors = new HashMap<Integer, List<Tiles>>();
		List<Tiles> addedTiles = null;
		List<Tiles> currentPlayerStack = new ArrayList<Tiles>(player.getStack());
		Tiles currentTile = new Tiles();
		Map<Integer, Integer> counterTiles = new HashMap<Integer, Integer>();
		int counter = 0;
		for (int i = 0; i < currentPlayerStack.size(); i++) {
			currentTile = currentPlayerStack.get(i);
			counter = 1;
			addedTiles = new ArrayList<Tiles>();
			addedTiles.add(currentTile);
			for (int j = 0; j < currentPlayerStack.size(); j++) {
				if ((i != j && (!currentTile.getColor().equalsIgnoreCase(currentPlayerStack.get(j).getColor()))
						&& currentTile.getCode() != currentPlayerStack.get(j).getCode())) {
					if (currentTile.getRealNumber() == currentPlayerStack.get(j).getRealNumber()) {
						counter++;
						addedTiles.add(currentPlayerStack.get(j));

					}

				}

			}
			if (counter == 2 || counter == 3 || counter == 4) {
				possiblePerForDifferentColors.put(currentTile.getRealNumber(), addedTiles);
			}
			counterTiles.put(currentTile.getRealNumber(), counter);

		}
		player.setPossiblePerForDifferentColors(possiblePerForDifferentColors);
		printer.printMapWithList(possiblePerForDifferentColors);
		printer.printMap(counterTiles);
	}

	public void differentColorCaseForOtherPlayer() {
		for (int i = 0; i < players.size(); i++) {
			differentColorCase(players.get(i));
		}
	}

	public void sameColorConsequtiveCase() {
		List<Tiles> currentPlayerStack = new ArrayList<Tiles>(this.currentPlayer.getStack());
		Tiles currentTile = new Tiles();
		List<Tiles> addedTiles = null;
		Map<Integer, List<Tiles>> consequtive = new HashMap<Integer, List<Tiles>>();
		for (int i = 0; i < currentPlayerStack.size(); i++) {
			currentTile = currentPlayerStack.get(i);
			addedTiles = new ArrayList<Tiles>();
			for (int j = 0; j < currentPlayerStack.size(); j++) {
				if ((i != j) && (currentTile.getColor().equalsIgnoreCase(currentPlayerStack.get(j).getColor()))) {
					if (currentTile.getCode() != 12 || currentTile.getCode() != 25 || currentTile.getCode() != 38
							|| currentTile.getCode() != 51) {
						if (((currentTile.getCode() + 1) == currentPlayerStack.get(j).getCode())) {
							addedTiles.add(currentTile);
							addedTiles.add(currentPlayerStack.get(j));

						}
					} else {
						if (currentTile.getCode() == 12 && currentPlayerStack.get(j).getCode() == 0) {
							addedTiles.add(currentTile);
							addedTiles.add(currentPlayerStack.get(j));

						} else if (currentTile.getCode() == 25 && currentPlayerStack.get(j).getCode() == 13) {
							addedTiles.add(currentTile);
							addedTiles.add(currentPlayerStack.get(j));

						} else if (currentTile.getCode() == 38 && currentPlayerStack.get(j).getCode() == 26) {
							addedTiles.add(currentTile);
							addedTiles.add(currentPlayerStack.get(j));

						} else if (currentTile.getCode() == 51 && currentPlayerStack.get(j).getCode() == 39) {
							addedTiles.add(currentTile);
							addedTiles.add(currentPlayerStack.get(j));

						}
					}
				}

			}
			printer.printList(addedTiles);
			consequtive.put(2, addedTiles);

		}
		printer.printMapWithList(consequtive);

	}

	public void sortPlayersDeck(Player player) {
		for (int i = 0; i < player.getStack().size(); i++) {
			Collections.sort(player.getStack());

		}
		printer.printList(player.getStack());
	}

	public List<Tiles> updateCurrentPlayerStack(List<Tiles> stack, int count, int index) {
		List<Tiles> temp = new ArrayList<Tiles>(stack);
		for (int i = 0; i < count; i++) {
			temp.remove(index);
		}
		return temp;
	}

	public void sameColorConsequtive() throws Exception {
		sortPlayersDeck(this.currentPlayer);
		List<Tiles> currentPlayerStack = new ArrayList<Tiles>(this.currentPlayer.getStack());
		Tiles currentTile = new Tiles();
		List<Tiles> addedTiles = null;
		List<Tiles> remainingTiles = new ArrayList<Tiles>();
		Map<Integer, List<Tiles>> consequtive = new HashMap<Integer, List<Tiles>>();
		List<List<Tiles>> generalSameColorConsequtiveList = new ArrayList<List<Tiles>>();

		for (int i = 0; i < currentPlayerStack.size(); i++) {
			currentTile = currentPlayerStack.get(i);
			addedTiles = new ArrayList<Tiles>();
			for (int j = 0; j < currentPlayerStack.size() - 4; j++) {
				if ((i != j) && (currentTile.getColor().equalsIgnoreCase(currentPlayerStack.get(j).getColor()))) {
					if ((j + 3) <= currentPlayerStack.size()) {
						addedTiles = lastElementConditionForQuintet(currentTile, currentPlayerStack.get(j),
								currentPlayerStack.get(j + 1), currentPlayerStack.get(j + 2),
								currentPlayerStack.get(j + 3));
						if (addedTiles != null) {
							remainingTiles = new ArrayList<Tiles>();
							// remainingTiles=divideList(currentPlayerStack, i,
							// j);
							remainingTiles = updateCurrentPlayerStack(currentPlayerStack, 5, i);
							generalSameColorConsequtiveList.add(addedTiles);

						} else {

							addedTiles = lastElementConditionForQuadruple(currentTile, currentPlayerStack.get(j),
									currentPlayerStack.get(j + 1), currentPlayerStack.get(j + 2));
							if (addedTiles != null) {
								remainingTiles = new ArrayList<Tiles>();
								// remainingTiles=divideList(currentPlayerStack,
								// i,
								// j);
								remainingTiles = updateCurrentPlayerStack(currentPlayerStack, 4, i);
								generalSameColorConsequtiveList.add(addedTiles);
							} else {
								addedTiles = lastElementConditionForTriple(currentTile, currentPlayerStack.get(j),
										currentPlayerStack.get(j + 1));
								if (addedTiles != null) {
									remainingTiles = new ArrayList<Tiles>();
									// remainingTiles=divideList(currentPlayerStack,
									// i, j);
									remainingTiles = updateCurrentPlayerStack(currentPlayerStack, 3, i);
									generalSameColorConsequtiveList.add(addedTiles);
								} else {
									addedTiles = lastElementConditionForBinary(currentTile, currentPlayerStack.get(j));
									if (addedTiles != null) {
										remainingTiles = new ArrayList<Tiles>();
										// remainingTiles=divideList(currentPlayerStack,
										// i, j);
										remainingTiles = updateCurrentPlayerStack(currentPlayerStack, 2, i);
										generalSameColorConsequtiveList.add(addedTiles);
									} else {
										throw new Exception(
												"Any length of list to be added to generalList could not be found.");
									}
								}
							}

						}
					}
				}
			}
		}

	}

	public List<Tiles> lastElementConditionForBinary(Tiles currentTile, Tiles compareValue) {
		List<Tiles> addedTiles = new ArrayList<Tiles>();

		if (currentTile.getCode() != 12 || currentTile.getCode() != 25 || currentTile.getCode() != 38
				|| currentTile.getCode() != 51) {
			if (((currentTile.getCode() + 1) == compareValue.getCode())) {
				addedTiles.add(currentTile);
				addedTiles.add(compareValue);

			}

		} else {
			if (currentTile.getCode() == 12 && compareValue.getCode() == 0) {
				addedTiles.add(currentTile);
				addedTiles.add(compareValue);

			} else if (currentTile.getCode() == 25 && compareValue.getCode() == 13) {
				addedTiles.add(currentTile);
				addedTiles.add(compareValue);

			} else if (currentTile.getCode() == 38 && compareValue.getCode() == 26) {
				addedTiles.add(currentTile);
				addedTiles.add(compareValue);

			} else if (currentTile.getCode() == 51 && compareValue.getCode() == 39) {
				addedTiles.add(currentTile);
				addedTiles.add(compareValue);

			}
		}

		return addedTiles;

	}

	public List<Tiles> lastElementConditionForTriple(Tiles currentTile, Tiles firstCompVal, Tiles secondCompVal) {
		List<Tiles> addedTiles = new ArrayList<Tiles>();
		if ((currentTile.getCode() != 11 || currentTile.getCode() != 24 || currentTile.getCode() != 37
				|| currentTile.getCode() != 50)
				|| (currentTile.getCode() != 12 || currentTile.getCode() != 25 || currentTile.getCode() != 38
						|| currentTile.getCode() != 51)) {
			if (((currentTile.getCode() + 1) == firstCompVal.getCode())
					&& ((currentTile.getCode() + 2) == secondCompVal.getCode())
					&& ((currentTile.getCode() + 3) == secondCompVal.getCode())) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);

			}

		} else {
			if (currentTile.getCode() == 11 && firstCompVal.getCode() == 12 && secondCompVal.getCode() == 0) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);

			} else if (currentTile.getCode() == 24 && firstCompVal.getCode() == 25 && secondCompVal.getCode() == 13) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);

			} else if (currentTile.getCode() == 37 && firstCompVal.getCode() == 38 && secondCompVal.getCode() == 26) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);

			} else if (currentTile.getCode() == 50 && firstCompVal.getCode() == 51 && secondCompVal.getCode() == 39) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);

			}
		}
		return addedTiles;
	}

	public List<Tiles> lastElementConditionForQuadruple(Tiles currentTile, Tiles firstCompVal, Tiles secondCompVal,
			Tiles thirdCompVal) {
		List<Tiles> addedTiles = new ArrayList<Tiles>();
		if ((currentTile.getCode() != 10 || currentTile.getCode() != 23 || currentTile.getCode() != 36
				|| currentTile.getCode() != 49)
				|| (currentTile.getCode() != 11 || currentTile.getCode() != 24 || currentTile.getCode() != 37
						|| currentTile.getCode() != 50)
				|| (currentTile.getCode() != 12 || currentTile.getCode() != 25 || currentTile.getCode() != 38
						|| currentTile.getCode() != 51)) {
			if (((currentTile.getCode() + 1) == firstCompVal.getCode())
					&& ((currentTile.getCode() + 2) == secondCompVal.getCode())
					&& ((currentTile.getCode() + 3) == thirdCompVal.getCode())) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);

			}

		} else {
			if (currentTile.getCode() == 10 && firstCompVal.getCode() == 11 && secondCompVal.getCode() == 12
					&& thirdCompVal.getCode() == 0) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);

			} else if (currentTile.getCode() == 23 && firstCompVal.getCode() == 24 && secondCompVal.getCode() == 25
					&& thirdCompVal.getCode() == 13) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);
			} else if (currentTile.getCode() == 36 && firstCompVal.getCode() == 37 && secondCompVal.getCode() == 38
					&& thirdCompVal.getCode() == 26) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);

			} else if (currentTile.getCode() == 49 && firstCompVal.getCode() == 50 && secondCompVal.getCode() == 51
					&& thirdCompVal.getCode() == 39) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);

			}
		}
		return addedTiles;
	}

	public List<Tiles> lastElementConditionForQuintet(Tiles currentTile, Tiles firstCompVal, Tiles secondCompVal,
			Tiles thirdCompVal, Tiles fourthCompVal) {
		List<Tiles> addedTiles = new ArrayList<Tiles>();
		if ((currentTile.getCode() != 10 || currentTile.getCode() != 23 || currentTile.getCode() != 36
				|| currentTile.getCode() != 49)
				|| (currentTile.getCode() != 11 || currentTile.getCode() != 24 || currentTile.getCode() != 37
						|| currentTile.getCode() != 50)
				|| (currentTile.getCode() != 12 || currentTile.getCode() != 25 || currentTile.getCode() != 38
						|| currentTile.getCode() != 51)
				|| (currentTile.getCode() != 9 || currentTile.getCode() != 22 || currentTile.getCode() != 35
						|| currentTile.getCode() != 48)) {
			if (((currentTile.getCode() + 1) == firstCompVal.getCode())
					&& ((currentTile.getCode() + 2) == secondCompVal.getCode())
					&& ((currentTile.getCode() + 3) == thirdCompVal.getCode())
					&& ((currentTile.getCode() + 4) == fourthCompVal.getCode())) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);
				addedTiles.add(fourthCompVal);
			}

		} else {
			if (currentTile.getCode() == 9 && firstCompVal.getCode() == 10 && secondCompVal.getCode() == 11
					&& thirdCompVal.getCode() == 12 && fourthCompVal.getCode() == 0) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);
				addedTiles.add(fourthCompVal);

			} else if (currentTile.getCode() == 22 && firstCompVal.getCode() == 23 && secondCompVal.getCode() == 24
					&& thirdCompVal.getCode() == 25 && fourthCompVal.getCode() == 13) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);
				addedTiles.add(fourthCompVal);
			} else if (currentTile.getCode() == 35 && firstCompVal.getCode() == 36 && secondCompVal.getCode() == 37
					&& thirdCompVal.getCode() == 38 && fourthCompVal.getCode() == 26) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);
				addedTiles.add(fourthCompVal);

			} else if (currentTile.getCode() == 48 && firstCompVal.getCode() == 49 && secondCompVal.getCode() == 50
					&& thirdCompVal.getCode() == 51 && fourthCompVal.getCode() == 39) {
				addedTiles.add(currentTile);
				addedTiles.add(firstCompVal);
				addedTiles.add(secondCompVal);
				addedTiles.add(thirdCompVal);
				addedTiles.add(fourthCompVal);

			}
		}
		return addedTiles;
	}

	public void shuffleList() {
		Collections.shuffle(allTiles);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Tiles> getAllTiles() {
		return allTiles;
	}

	public void setAllTiles(List<Tiles> allTiles) {
		this.allTiles = allTiles;
	}

	public Tiles getIndicatorTile() {
		return indicatorTile;
	}

	public void setIndicatorTile(Tiles indicatorTile) {
		this.indicatorTile = indicatorTile;
	}

	public Tiles getOkeyTile() {
		return okeyTile;
	}

	public void setOkeyTile(Tiles okeyTile) {
		this.okeyTile = okeyTile;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<List<Tiles>> getSequenceList() {
		return sequenceList;
	}

	public void setSequenceList(List<List<Tiles>> sequenceList) {
		this.sequenceList = sequenceList;
	}

	public List<List<Tiles>> getDistributedList() {
		return distributedList;
	}

	public void setDistributedList(List<List<Tiles>> distributedList) {
		this.distributedList = distributedList;
	}

	public List<Tiles> getMiddleClusterOfTiles() {
		return middleClusterOfTiles;
	}

	public void setMiddleClusterOfTiles(List<Tiles> middleClusterOfTiles) {
		this.middleClusterOfTiles = middleClusterOfTiles;
	}

}
