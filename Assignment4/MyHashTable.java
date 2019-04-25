//Rene Gagnon, 260801777
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
    	if(initialCapacity == 0) {
    		numBuckets = 10;
    	}else {
    		numBuckets = initialCapacity;
    	}
        numEntries = 0;
        buckets = new ArrayList<LinkedList<HashPair<K,V>>>(numBuckets);
        // fill the list with empty lists
        for(int i = 0; i< numBuckets; i++) {
        	buckets.add(i, new LinkedList<HashPair<K,V>>());
        }
        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets vairable. Usefull for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
    	if (key != null) {
    		V oldValue;
	        int bucketIndex = hashFunction(key);
	        HashPair<K,V> curNode;
	        HashPair<K,V> hp = new HashPair<K,V>(key, value);
	        
	        // check if we need to rehash
	        if(((double)(this.numEntries + 1))/((double)(this.numBuckets)) >= MAX_LOAD_FACTOR){
	        	rehash();
	        	//recompute the index;
	        	bucketIndex = hashFunction(key);
	        }
	        // if we have hashpair(s) at that index
	        if(!this.buckets.get(bucketIndex).isEmpty()) {
	        	//LI = this.buckets.get(bucketIndex).listIterator();	        	
	        	// look in the bucket if we can find the key
	        	for(HashPair<K,V> e: this.buckets.get(bucketIndex)) {
	        		curNode = e;
		        	// found a matching key, so replace its value with the new one
		        	if(curNode.getKey().equals(key)) {
		        		oldValue = curNode.getValue();
		        		curNode.setValue(value);
		        		return oldValue;
		        	}
	        	}
	        	// couldn't find the key in the bucket so add it to the end
	        	this.buckets.get(bucketIndex).add(hp);
	        	this.numEntries = this.numEntries + 1;
	        	return null;
	        	
	        }else { // else we just add the hashpair to this index
	        	this.buckets.get(bucketIndex).add(hp);
	        	this.numEntries = this.numEntries + 1;
	        	return null;
	        }
    	}else {
    		return null;	
    	}
        //  ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime = O(1)
     */
    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
    	if(key != null) {
	    	int bucketIndex = hashFunction(key);
	    	// look in the bucket
	    	for(HashPair<K, V> node: this.buckets.get(bucketIndex)) {
	        	// found a matching key; return it
	        	if(node.getKey().equals(key)) {
	        		return node.getValue();
	        	}
	    	}
    	}
        return null;
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
    	if(key != null) {
	    	int bucketIndex = hashFunction(key);
	    	if(!this.buckets.get(bucketIndex).isEmpty()) {
		    	V value;
		    	// look in the bucket
		    	for(HashPair<K, V> node: this.buckets.get(bucketIndex)) {
		        	// found a matching key; return it and delete the hashpair
		        	if(node.getKey().equals(key)) {
		        		value = node.getValue();
		        		this.buckets.get(bucketIndex).removeFirstOccurrence(node);
		        		this.numEntries = this.numEntries - 1;
		        		return value;
		        	}
		    	}
	    	}
    	}
        return null;
        //ADD YOUR CODE ABOVE HERE
    }
    
    // Method to double the size of the hashtable if load factor increases
    // beyond MAX_LOAD_FACTOR.
    // Made public for ease of testing.
    
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
    	this.numBuckets = this.numBuckets * 2;
        HashPair<K, V> curHP;
        MyHashTable<K,V> newTable = new MyHashTable<K, V>(this.numBuckets);
        for(int i = 0; i< this.buckets.size();i++) {
        	for(int j = 0; j<this.buckets.get(i).size();j++) {
        		curHP = this.buckets.get(i).get(j);
            	newTable.put(curHP.getKey(), curHP.getValue());
        	}
        }
        buckets.clear();
        buckets = newTable.buckets;
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     */
    
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
    	ArrayList<K> keyList = new ArrayList<K>(numEntries);
    	MyHashIterator entriesIterator = iterator();
        while(entriesIterator.hasNext()) {
        	keyList.add(entriesIterator.next().getKey());
        }
    	return keyList;
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(n)
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE
    	MyHashTable<V,Integer> hashset;
        MyHashIterator entriesIterator = iterator();
        hashset = new MyHashTable<V,Integer>(2*numEntries);

        while(entriesIterator.hasNext()) {
        	hashset.put(entriesIterator.next().getValue(),1);
        }
        return hashset.keys();
        //ADD CODE ABOVE HERE
    }
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }
    
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        private LinkedList<HashPair<K,V>> entries = new LinkedList<HashPair<K,V>>();
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	// fill the entries list with all the hashpairs
            for(int i =0; i < buckets.size(); i++) {
            	for(HashPair<K,V> e : buckets.get(i)) {
            		if(e != null) {
            			entries.add(e);
            		}
            	}
            }
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE    	
        	if(entries.peek() != null) {
        		return true;
        	}
			return false;
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	return entries.removeFirst();
            //ADD YOUR CODE ABOVE HERE
        }
        
    }
}
