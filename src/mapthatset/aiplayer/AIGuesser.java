package mapthatset.aiplayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import mapthatset.aiplayer.util.Knowledge;
import mapthatset.sim.*;

public class AIGuesser extends Guesser
{
	int intMappingLength;
	ArrayList< Integer > alGuess = new ArrayList< Integer >();
	String strID = "AIGuesser";
	
	ArrayList< Integer > alQueryContent;
	
	private Set<Knowledge> kb;
	
	public void startNewMapping( int intMappingLength ) {
		kb = new HashSet<Knowledge>();
		this.intMappingLength = intMappingLength;
		alGuess = new ArrayList< Integer >();
	}
	
	@Override
	public GuesserAction nextAction() {
		GuesserAction gscReturn = null;
		alQueryContent = new ArrayList< Integer >();
		
		boolean done = true;
		ArrayList<Integer> answers = new ArrayList<Integer>();
		
		for (int i = 1; i <= intMappingLength; i++) {
			
			boolean found = false;
			
			for (Knowledge k : kb) {
				if (k.getPreimage().size() == 1 && k.getPreimage().contains(i) && k.getImage().size()==1) {
					found = true;
					answers.add(k.getImage().iterator().next());
				}
			}
			
			if (!found) {
				done = false;
				break;
			}
		}
		
		if (done) {
			GuesserAction guess = new GuesserAction("g", answers);
			return guess;
		}
		
		gscReturn = new GuesserAction( "q", alQueryContent );
		return gscReturn;
	}
	
	@Override
	public void setResult( ArrayList< Integer > alResult ) {
		
		Set<Integer> pi = new HashSet<Integer>();
		Set<Integer> i = new HashSet<Integer>();
		
		pi.addAll(alQueryContent);
		i.addAll(alResult);
		
		Knowledge gained = new Knowledge(pi, i);
		kb.add(gained);
		
		this.infer();
	}

	/**
	 * stub
	 */
	private void infer() {
		
	}
	
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return strID;
	}
}
