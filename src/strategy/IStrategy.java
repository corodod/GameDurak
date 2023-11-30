package strategy;
import cardPack.Card;
import mainGame.Player;
import cardPack.Suit;
import mainGame.Table;

import java.util.List;

//@FunctionalInterface
public interface IStrategy {
     String getNameStrategy();
    Card attack(Player me, Player defPlayer, Table table);
    void setTrumpSuit(Suit trumpSuit);
    Card deff(Player me, Card recaptureCard, Table table, List<Card> cardsInTable, List<Card> cardsForAttack);
    Card throwCard(Player me, List<Card> desc, List<Player> players, List<Card> cardsOut);
    boolean areThrowCard(Player me, List<Card> desc, List<Player> players,List<Card> cardsOut);
}
