package mapthatset.aiplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import mapthatset.sim.*;

public class AIMapper extends Mapper
{
	int intMappingLength;
	String strID = "DumbMapper";
	
	private ArrayList<Integer> getNewMapping()
	{
		ArrayList<Integer> alNewMapping = new ArrayList<Integer>();
		Map<Integer, Integer> repeated_numbers= new HashMap<Integer,Integer>();
		Random rdmGenerator = new Random();
		double prob_repetition = 0;
		int max_repetitions = 0;
		int repetitions = 0;
		int cardinality;
		int lowerbound;
		int upperbound;
		
//		for(int i = 1 ; i<=intMappingLength; i++)
//			alNewMapping.add(new Integer(i));
		
//		prob_repetition = ((rdmGenerator.nextInt(intMappingLength))+1)/intMappingLength;
//		if (prob_repetition > 0.9)
//		{
//			max_number_of_repetitions =  
//		}
//		else
		{
			lowerbound = intMappingLength/2 - rdmGenerator.nextInt(intMappingLength/2);
			upperbound = intMappingLength - (lowerbound/2);
			cardinality = rdmGenerator.nextInt(upperbound-lowerbound)+lowerbound;
			max_repetitions = intMappingLength - cardinality;
			int num=0;
			int rep=0;
			//int flag=0;
			while(repetitions < max_repetitions)
			{
				while(true)
				{
					num = (rdmGenerator.nextInt(intMappingLength))+1;
					if(!(repeated_numbers.containsKey(num)))
						break;
				}
				while(true)
				{
					rep = ((rdmGenerator.nextInt(cardinality))+1);
					if(rep <= (max_repetitions-repetitions))
							break;
				}			
				repeated_numbers.put(num, rep);
				repetitions += rep;
			}
			
			int count = 0;
				
			for (Integer k : repeated_numbers.keySet())
			{
				int n = k;
				int r = repeated_numbers.get(k);
				while(r!=0)
				{
					alNewMapping.add(n);
					r--;
					count++;
				}
			}
			
			
			for(int i = 0; i < intMappingLength-count; i++)
			{
				alNewMapping.add( rdmGenerator.nextInt( intMappingLength ) + 1 );
			}
			
			alNewMapping = shuffle(alNewMapping);
		}
		System.out.println( "The mapping is: " + alNewMapping );
		return alNewMapping;
	}
	
	public ArrayList<Integer> shuffle(ArrayList<Integer> mapping)
	{
		Random rdmGenerator = new Random();
		for (int i=0; i<intMappingLength; i++)
		{
		    int randomPosition = rdmGenerator.nextInt(intMappingLength);
		    int temp = (Integer)mapping.get(i);
		    mapping.set(i, mapping.get(randomPosition));
		    mapping.set(randomPosition, temp);
		}
		return mapping;
	}

	@Override
	public void updateGuesserAction(GuesserAction gsaGA) 
	{
		// dumb mapper do nothing here
	}

	@Override
	public ArrayList<Integer> startNewMapping(int intMappingLength) 
	{
		// TODO Auto-generated method stub
		this.intMappingLength = intMappingLength;
		return getNewMapping();
	}

	@Override
	public String getID() 
	{
		// TODO Auto-generated method stub
		return strID;
	}
}
