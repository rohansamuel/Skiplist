// Starter code for Project 2: skip lists
// Do not rename the class, or change names/signatures of methods that are declared to be public.




//rks160130

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;



public class SkipList<T extends Comparable<? super T>> {
    static final int PossibleLevels = 33;
    Entry<T>head,tail;
    Entry<T>[] last;
    int maxLevel;
    int size;
    Random random;
   
    
    static class Entry<E> {
	E element;
	Entry<E>[] next;
	Entry<E> prev;


	@SuppressWarnings("unchecked")
	public Entry(E x, int lev) {
	    element = x;
	    next = new Entry[lev];  
	}

	public E getElement() {
	    return element;
	    
	}
    }
    

    
    

    // Constructor
    @SuppressWarnings("unchecked")
	public SkipList() {
    head=new Entry<T>(null,PossibleLevels);
    	tail=new Entry<T>(null,PossibleLevels);
    size=0;
    	maxLevel=1;
    last=new Entry[33];
    
    random=new Random();
    
   for(int i=0;i>33;i++) {// setting the head to reach the tail
    	head.next[i]=tail;
    }
    	}
    
    
    
    public int chooselevel() {
    	
	int lev=1+Integer.numberOfTrailingZeros(random.nextInt());
    	if (lev>maxLevel) 
    	maxLevel=lev; 	
    	return lev;
    }

  
    public Entry<T> find (T x){
    	Entry<T> p= head;
    	for (int i=maxLevel-1; i>=0; i--) { //going through level
    		while((p.next[i]!=null) && (p.next[i].element.compareTo(x)<0)) {
    			p=p.next[i];
    	}
    	
    		last[i]=p; //node which search came down from level i to i-1
    }
    		return p;
    }
    
    

	// Add x to list. If x already exists, reject it. Returns true if new node is added to list
    public boolean add(T x) {
    	Entry<T>p=head;
    	if(contains(x)) { //reject duplicate
    		return false;
    	}	
    	int lev=chooselevel(); //length of next[] for x's entry
    	
    	for (int i=maxLevel-1; i>=0; i--) {
    		while((p.next[i]!=null) && (p.next[i].element.compareTo(x)<0)) {
    			p=p.next[i];
    	
    	}
    		last[i]=p;
    }
    	
    Entry<T>	ent=new Entry<T>(x,lev);
    	for (int i=0;i<=lev-1;i++) {
    		ent.next[i]=last[i].next[i];
    		last[i].next[i]=ent;
    		}
    	size=size+1; //update size
	return true;
	
    }

 
    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
    	
    Entry<T> result=find(x);//element in the node
    
    if((result.element!=null)){ //if result in null
    	
    		
    			if((result.element).compareTo(x)>0)// element greater than x
    			return result.next[0].element;// return next element
    			
    			else {
    				return result.next[0].next[0].element;//if the prev element is null, return the 
    				                                      //element after next
    			}
    		}
    else {
    	return x;
    }
    }
    


    	
    

    // Does list contain x?
    public boolean contains(T x) {
    find(x);
    	if(last[0].element!=null && last[0].element.compareTo(x)==0)//while element not null
    
    		return true;
    	else
	return false;
    }

    // Return first element of list
    public T first() {
    	Entry<T>p=head;
    	
	return p.next[0].element;//first element after head
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
    	
    		Entry<T> result=find(x);//element in the node
        if((result.element)==null){
        	
        		return last[0].element;//next element
        		}else {
        			if((result.element).compareTo(x)<=0);//if less than x
        			return result.element;
        		}
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n) {
    	if (n<0 || n>size-1) {
    		//throw new NoSuchElementException();
    		return null;
    	}
    	Entry<T>p=head;
    	for(int i=0;i<=n;i++) { // going through the list
    		
    		p=p.next[0]; // return the next
    	}
    	return p.element;	
    }

    // Is the list empty?
    public boolean isEmpty() {
	return size==0; 
    }

    // Iterate through the elements of list in sorted order
    
    public Iterator<T> iterator() {
	SKIPIterator<T>it=new SKIPIterator<T>();
	return it;
    }
   protected class SKIPIterator implements Iterator<T> {
  SkipList<T>list;
  Entry<T>head;
  int level;
  private Entry<T> SkipNode;

    public	SKIPIterator(Entry<T>head) {
    	  this.SkipNode=head;
    	}

		@Override
		public boolean hasNext() {
			
			return SkipNode.next[0]!=null;
		}

		@Override
		public T next() {
			SkipNode=SkipNode.next[0];
			return SkipNode.element;
		}
		}
    
    

 

    // Return last element of list--->size-1
	public T last() {
    	Entry<T> p=head;
     	for (int i=maxLevel-1; i>=0; i--) {
    		while((p.next[i]!=null) && (p.next[i].next[0]!=null)) //while the element is not null go to the next and while next is not null
    		p=p.next[i];//get the next element
     	}
    	
	return (T) p.next[0].element; //return element
    }

    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
    if(!contains(x)) { //if duplicate
     return null;
    }
    Entry<T>ent=last[0].next[0];
    for(int i=0;i<ent.next.length-1;i++) {
    	last[i].next[i]=ent.next[i];
    }
 	size=size-1;
    return ent.element;
    }

    // Return the number of elements in the list
    public int size() {
	return size;//returning size
    }
}