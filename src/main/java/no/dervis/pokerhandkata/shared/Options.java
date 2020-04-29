package no.dervis.pokerhandkata.shared;

/**
 * Defines options for card games and cardsets 
 * 
 * @author Dervis Mansuroglu
 *
 */
public class Options {
	
	public enum CardPriorityStatus {
		CARDPRIORITY_ON(0xFFA), CARDPRIORITY_OFF(0xFFF);
		private final int val;
		CardPriorityStatus(int val) {
			this.val = val;
		}
		public int getVal() {return val;}
	}
	
	public enum IncludeJokerCard {
		JOKER_ON(1), JOKER_OFF(0);
		private final int val;
		IncludeJokerCard(int val) {
			this.val = val;
		}
		public int getVal() {return val;}
	}
	
}
