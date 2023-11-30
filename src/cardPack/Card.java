package cardPack;
public class Card {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    public Suit getSuit() {
        return suit;
    }
    public Rank getRank() {
        return rank;
    }
    public void displayCard() {
        System.out.println("Масть: " + suit + ", Ранг: " + rank);
    }
    @Override
    public String toString() {
        String result = "";
        switch (suit){
            case DIAMONDS:
                result+= ANSI_BLACK;
                result+="♠";
                break;
            case SPADES:
                result+= ANSI_GREEN;
                result+="♣";
                break;
            case CLUBS:
                result+= ANSI_YELLOW;
                result+="♦";
                break;
            case HEARTS:
                result+= ANSI_RED;
                result+="♥";
                break;
            default:
                break;
        }
        switch (rank){
            case SIX:
                result+="6";
                break;
            case SEVEN:
                result+="7";
                break;
            case EIGHT:
                result+="8";
                break;
            case NINE:
                result+="9";
                break;
            case TEN:
                result+="10";
                break;
            case JACK:
                result+="J";
                break;
            case QUEEN:
                result+="Q";
                break;
            case KING:
                result+="K";
                break;
            case ACE:
                result+="A";
                break;
            default:
                break;
        }
        result+=ANSI_RESET;
        result+=" ";
        return result;
    }
    public static boolean compareCards(Card card1, Card card2) {
        return card1.getSuit() == card2.getSuit() && card1.getRank() == card2.getRank();
    }
}

