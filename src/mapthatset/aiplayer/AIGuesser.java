package mapthatset.aiplayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import mapthatset.aiplayer.util.Inferrer;
import mapthatset.aiplayer.util.Knowledge;
import mapthatset.aiplayer.util.NumberCounter;
import mapthatset.aiplayer.util.TreeTest;
import mapthatset.sim.*;

public class AIGuesser extends Guesser
{
	int intMappingLength;
	ArrayList< Integer > alGuess = new ArrayList< Integer >();
	String strID = "AIGuesser";
	
	ArrayList< Integer > alQueryContent;
	
	private Set<Knowledge> kb;
	private Queue<Knowledge> newkb;
	
	private ArrayList<Integer> answers;
	
	private SortedSet<NumberCounter> freqs; 
	
	//TODO tune this
	private double cappedLength;
	
	TreeTest guesserList = new TreeTest();
	
	public void startNewMapping( int intMappingLength ) {
		kb = new HashSet<Knowledge>();
		newkb = new LinkedList<Knowledge>();
		
		answers = new ArrayList<Integer>();
		freqs = new TreeSet<NumberCounter>();
		
		for (int i = 0; i < intMappingLength; i++) {
			answers.add(-1);
			
			NumberCounter nc = new NumberCounter();
			nc.number = i+1;
			nc.freq = 0;
			
			freqs.add(nc);
		}
		
		cappedLength = intMappingLength * 1.0 / 2;
	
		this.intMappingLength = intMappingLength;
		alGuess = new ArrayList< Integer >();
		guesserList.initialize(intMappingLength);
	}
	
	@Override
	public GuesserAction nextAction() {
		GuesserAction gscReturn = null;
		
		if (kb.size()<2) {
			alQueryContent = guesserList.pop();
		} else {
			alQueryContent = getSmartGuess();
		}
		
		if (alQueryContent != null && !done()) {
			gscReturn = new GuesserAction( "q", alQueryContent );
		} else {
			gscReturn = new GuesserAction( "g", answers );	
		}
		
		return gscReturn;
	}
	
	@Override
	public void setResult( ArrayList< Integer > alResult ) {
		
		if (alQueryContent==null) return;
		if (alResult == null) return;
		
		alGuess.add( alResult.get( 0 ) );
		
		Set<Integer> pi = new HashSet<Integer>();
		Set<Integer> i = new HashSet<Integer>();
		
		pi.addAll(alQueryContent);
		i.addAll(alResult);
		
		Knowledge gained = new Knowledge(pi, i);
		
		//This is a really bad guess, we already know the result
		//TODO make sure this never happens
		if (kb.contains(gained)) return;
		
		if (kb.size()==0) {
			kb.add(gained);
		} else {
			newkb.add(gained);
			this.infer();
		}
	}

	/**
	 * stub
	 */
	private void infer() {
		//do {
			Inferrer.infer(kb, newkb, answers, freqs);
		//} while (!Inferrer.hasConverged());
	}
	
	private boolean done() {
		boolean answer = true;
		
		for (int i : answers) {
			answer = answer && (i!=-1);
			if (!answer) break;
		}
		
		System.out.println();
		
		return answer;
	}
	
	private ArrayList<Integer> getSmartGuess() {
		
		
		int intCappedLength = (int) Math.round(cappedLength);
		
		//TODO Tune these two params!
		int cappedDiff = 3;
		//TODO this is probably pretty naive
		int cappedIntersected = Math.min(1, intCappedLength); 
		
		/*
		System.out.println("\n***** CURRENT NUMBER COUNTERS *****");
		for (NumberCounter nc : freqs) {
			System.out.println(nc);
		}
		*/
		
		
		ArrayList<Integer> guess = new ArrayList<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		
		int initialFreq = -1;
		
		boolean stopAdding = false;
		
		for (NumberCounter nc : freqs) {
			int cur = nc.number;
			int freq = nc.freq;
			
			if (initialFreq==-1) initialFreq = freq;
			
			if (!stopAdding) {
				
				//Only query numbers not solved yet
				if (answers.get(cur-1)==-1) {
					//System.out.println(cur + " added to query");
					guess.add(cur);
				}
				
				if (freq - initialFreq >= intCappedLength) stopAdding = true;
				if (guess.size() >= intCappedLength) stopAdding = true;
			}
			
			stack.add(cur);
		}
		
		int intersectedAdded = 0;
		
		while (!stack.empty()) {
			int cur = stack.pop();
			
			//Never intersect with completely solved mappings (i.e., atomic mappings)
			if (answers.get(cur-1)!=-1) {
				continue;
			}
			
			intersectedAdded++;
			
			//TODO: potential bug, did not check for overlap with previous loop
			guess.add(cur);
			if (intersectedAdded >= cappedIntersected) break;
		}
		
		System.out.print("\n**** query: ");
		
		for (int i : guess) {
			System.out.print(i + " ");
		}
		
		System.out.println();
		
		cappedLength-=0.45;
		if (cappedLength<1) cappedLength = 1;
		
		return guess;
	}
	
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return strID;
	}
}
