package mainGame;
import cardPack.Card;
import cardPack.Rank;
import cardPack.Suit;
import java.util.*;
public class HelpingMethods {
    public static List<Card> cardsWithRank(Card card, List<Card>... opponentCards) {
        List<Card> res = new ArrayList<>();
        int count = 0;
        for (List<Card> opponent : opponentCards) {
            for (Card opponentCard : opponent) {
                if (opponentCard.getRank() == card.getRank()) {
                    res.add(opponentCard);
                    count++;
                }
            }
        }
        return res;
    }
    public static Set<Rank> getUniqueRanks(List<Card> cards) {
        Set<Rank> uniqueRanks = new HashSet<>();
        for (Card card : cards) {
            uniqueRanks.add(card.getRank());
        }
        return uniqueRanks;
    }
    public static boolean canBeatCard(Card card, Card recaptureCard, Suit trumpSuit) {
        if (card.getSuit() == recaptureCard.getSuit()) {
            return card.getRank().ordinal() > recaptureCard.getRank().ordinal();
        } else if (card.getSuit() == trumpSuit ) {
            return true; // Козырная карта всегда бьет
        } else {
            return false;
        }
    }
    public static List<Card> sortHand(List<Card> hand, Suit trumpSuit) {
        if(hand.size()<2){
            return hand;
        }
        Comparator<Card> cardComparator = new Comparator<Card>() { // Создаем компаратор для сортировки карт
            @Override
            public int compare(Card card1, Card card2) {
                if (card1.getSuit() != trumpSuit && card2.getSuit() != trumpSuit) {
                    // Если обе карты не являются козырями, сравниваем их ранг
                    return Integer.compare(card1.getRank().ordinal(), card2.getRank().ordinal());
                } else if (card1.getSuit() == trumpSuit && card2.getSuit() == trumpSuit) {
                    // Если обе карты являются козырями, сравниваем их ранг
                    return Integer.compare(card1.getRank().ordinal(), card2.getRank().ordinal());
                } else if (card1.getSuit() != trumpSuit) {
                    // Если только card1 не является козырем, она идет первой
                    return -1;
                } else {
                    // Иначе card2 не является козырем, она идет первой
                    return 1;
                }
            }
        };
        Collections.sort(hand, cardComparator); // Сортируем массив карт, используя созданный компаратор
        return hand;
    }





    public static List<Card> ThrowCard( List<Card> me, List<Card> desc, Suit trumpSuit) {
        List<Card> possibleCards = new ArrayList<>();// здесь карты, которые в теории мы можем подкинуть
        Set<Rank> unRanks = getUniqueRanks(desc);
        for (Card card : me) {
            if (unRanks.contains(card.getRank()) ) {
                possibleCards.add(card);
            }
        }
        possibleCards = sortHand(possibleCards,trumpSuit);
        if(!possibleCards.isEmpty()){
            return possibleCards;
        }
        return null;
    }



}
