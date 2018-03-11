package okey;

public class Main {

	static Printer printer = new Printer();
	static Helper helper = new Helper();

	public static void main(String[] args) {

		helper.fillTiles();
	    //printer.printList(helper.getAllTiles());
		helper.shuffleList();
		//printer.printList(helper.getAllTiles());
		helper.determineIndicatorTile();
		//printer.printTile(helper.getIndicatorTile());
		helper.determineOkeyTile();
		helper.putJokerRealValueToFalseJoker();
		printer.printTile(helper.getOkeyTile());
		//printer.printList(helper.getAllTiles());
		helper.createPlayers();
		helper.chooseRandomPlayer();
		//printer.printPlayers(helper.getPlayers());
		System.out.println("-------------------------");
		printer.setCurrentPlayer(helper.getCurrentPlayer());
		//printer.printChosenPlayer();
		helper.aligningFiveGroupsOfTiles();
        //printer.printListOfList(helper.getSequenceList());  
		helper.distributeTiles();
		System.out.println("--------------------------Other player's list--------------");
		//printer.printListOfList(helper.getDistributedList());
		System.out.println("------------------------- Each player' list after distribution----------------------");
		printer.setPlayers(helper.getPlayers());
		System.out.println("Current Player's Stack");
        printer.printList(helper.getCurrentPlayer().getStack());
        printer.printStackOfEachPlayer();
        helper.differentColorCase(helper.getCurrentPlayer());
        helper.differentColorCaseForOtherPlayer();
        System.out.println("----------------------------");
        helper.sameColorConsequtiveCase();
        System.err.println("************");
        helper.sortPlayersDeck(helper.getCurrentPlayer());
        System.out.println("/////////////");
        try {
			helper.sameColorConsequtive();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
  
        
        
        
        
        
	}

}
