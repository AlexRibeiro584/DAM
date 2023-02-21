package cardmodel;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//Author: Alejandro Ribeiro Carretero
//Deck class used for a card game, with shuffling and initialization
public class Deck {
    private List<Card> cards;
    /*We initialize the deck by iterating the 2 enums and getting every possible combination,
    then we shuffle it*/
    public Deck(){
        this.cards = new LinkedList<>();
        for (CardSuit suit: CardSuit.values()) {
            for (CardSymbol symbol: CardSymbol.values()) {
                cards.add(new Card(suit,symbol));
            }
        }
        Shuffle();
    }
    //I made a specific method for shuffling in case I need to do it multiple times
    public void Shuffle(){
        Collections.shuffle(this.cards);
    }
    /*Through the use of LinkedList we can access the first item and remove it
    faster than in an ArrayList
     */
    public synchronized Card NextCard(){
        Card next = this.cards.get(0);
        this.cards.remove(0);
        return next;
    }
}
