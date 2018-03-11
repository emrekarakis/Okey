# Okey
Initially, the project have not reached the expected result of which player has the best hand.Still, I wanted to explain what I am trying to do.
All the tasks until reaching the decision stage are performed.At the decision point, I am fairly confused,that's why, I can not manage the time.At that point,my idea was something like below:


Okey => 5 red
Combination List: 6,6,6/13,13/9,10/  either  10-10  or 10-11 will be in combination list. 
We will decide according to the increase of WinPoint.Black 10 is used in 2 different place(**)
Example Case: 
Player :1.player                           
HasJoker?   Yes            
DifferentColorCase     6,6,6/13,13/10,10                                       
SameColorConsequtive       /9,10/10,11/6,7                                                                                                             
WinPoint  
(Yellow)6,(Blue)6,(Blue)6/(Red)13,(Blue)13/10(Yellow),10(Blue)/10(Blue)-11(Blue)
 -1 +1 -2 -1 -2 +2 -1 +1 -2 (-5) calculation without Joker                                          -1 Red +1Red /-2 blue,-1Yellow /-2 blue +2 blue/-1 black 9 +1 black 9/-2 black 12
If there is a right or left value in other players deck, write -1.Otherwise, the possibility of coming tile is handled.																									 
For 10-11 and 10-10 JOKER is placed both right and left then win-point is calculated.	
+8 okey-10-11-okey +1 ===> 
+9 -5 ==> 4 calculation with Joker							
*6,7 can be a combination, but if 6 is handled in consequtive, it will  disrupt hand.It is not assessed in combination list.																			  
																																														  
																		  
																																														  
1.player's Hand => Red 3,13,5 Yellow 10,6,9,2 Blue 1,6,1 Black 7,6,13,11,10

2.player                                   Yes                    5,5/12,12/2,2                            /1,2

Red 5,11,7,2
Yellow 12,4
Blue 5,7,3,10,10
Black 2,12,1


3.player 								   No                      8,8,8/13,13/9,9                           /8,9/7,8,9/
Red 6,8
Yellow 13,8,13,4,1,9                                                      
Blue 13
Black 7,9,5,3,8

4.player                                   No                      /5,5,5/12,12/11,11/                         /2,3/12,13/11,12
Red 11,1
Yellow 5,7,5
Blue 3,12,5,2,13
Black  12,8,5,11



https://github.com/emrekarakis/Okey.git
1) Has Player Joker?
2) Binary,Triple,Quadruple,Quintet  possible results must be analyzed in a descending order.For example, firstly quintet case is analyzed 
Sort each stack of player.
if there is no quintet sequence in current list,
       look for quadruple sequence in current list
	   if there is no quadruple sequence in current list 
	       look for triple sequence in current list 
           if there is no triple sequence in current list 
                look for binary sequence in current list
           else 
                look for binary sequence in remaining list 		   
		else 
		   look for triple sequence in remaining list 
		   if there is no triple sequence in remaining list 
		      look for  binary sequence in remaining list
			  if there is no binary sequence in remaining list
			       nothing
		   else 
		       look for binary sequence in remaining list 
else
       look for quadruple sequence in remaining list in current List
       
          if there is no quadruple sequence in current list 
	          look for triple sequence in current list 
            if there is no triple sequence in current list 
                look for binary sequence in current list
            else 
                look for binary sequence in remaining list 	


          else 
		     look for triple sequence in remaining list 
		     if there is no triple sequence in remaining list 
		        look for  binary sequence in remaining list
			    if there is no binary sequence in remaining list
			       nothing
		     else 
		          look for binary sequence in remaining list 
				  
				  


3- After obtaining differentColorSequence and sameColorSequence and PerCounts of each player. 
We can determine a parameter called WinPoint 
For example, for each possible sequence, for instance in the first player				  
For yellow 6, blue 6, black 6 sequence, we can look for red 6 in the list  of other player.Then, we can say that 
Player 3 has Red 6 in this case,  thus we increase the winPoint  according to other players' hand for each possible sequence.After that,
Each player will have perCount,residualCount,residualList and winPoint.Then we will be able to comment on which player has a closest hand to win in this case.


However, there are several exceptional cases:
i-)Where will we place on Joker tile?

We can decide on where we place on Joker Tile by considerating the increase of WinPoint.
We need to try on different possible results of sequence.Then, again we can calculate the winPoint.Then, the best point is discovered according to which option will bring us to the greatest winPoint.



ii-) (**)
  a)Supposing that we have a one black 10 and if it is used in any sequence 
  Then, we make this black 10 as a combination tile.That is to say,include it any sequence 
  
  b)Supposing that if we have 2 black 10, if one of them is  used in any combination, then make this tile as a combination tile,then make other 10 as a residual tile.
    if two of them are used ,make them as a combination tile
	
Find the Residual Tiles for each stack.
Get 2 of players having the minimum residualCount eliminate others
Look for whether they have a Joker Tile  or not 
Analyze winPoints of players in consequtive possibilities.
Find the winning cue!

 
 




