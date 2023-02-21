package cardmodel;

import java.io.Serializable;

//Author: Alejandro Ribeiro Carretero
//Card stores all the data related to a card for easy access
public class Card implements Serializable {
    private CardSuit suit;
    private CardSymbol symbol;
    public Card(CardSuit suit, CardSymbol symbol){
        this.suit = suit;
        this.symbol = symbol;
    }
    //Getter for the score value in each symbol
    public float GetNumericValue(){
        return switch (symbol){
            case SJ, SQ, SK -> 0.5f;
            case SA -> 1;
            case S2 -> 2;
            case S3 -> 3;
            case S4 -> 4;
            case S5 -> 5;
            case S6 -> 6;
            case S7 -> 7;
        };
    }

    @Override
    public String toString() {
        return symbol.toString().charAt(1) + " " + suit.toString();
    }
}
