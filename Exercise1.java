package nl.ru.ai.exercise1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Exercise1
{
  private static final String DATABASE_FILENAME="songs.txt";
  public static int NumberOfComparisons = 0;
  public static Random rand = new Random(42); // used for quick sort
  
  /*
   * Here we go
   */
  public static void main(String[] args)
  {
    try
    {
      ArrayList<Track> database=new ArrayList<Track>();
      /*
       * Read database
       */
      int length = readDatabase(database);
      System.out.printf("%d songs read from datatabase '%s'\n",database.size(),DATABASE_FILENAME);
      /*
       * Ask for sorting method
       */
      Scanner input=new Scanner(System.in);
      SortingMethod method=askForSortingMethod(input);
      NumberOfComparisons = 0;
      /*
       * Sort 
       */
      System.out.printf("Sorting with %s\n",method);
      switch(method)
      {
        case BUBBLE_SORT:
          bubbleSort(database, length);
          break;
        case INSERTION_SORT:
          insertionSort(database, length);
          break;
        case SELECTION_SORT:
          selectionSort(database, length);
          break;
        case QUICK_SORT:
				  quickSort(database, 0, length);
				  break;
			  case RADIX_SORT:
				  radixSort(database, length);
				  break;
      }
      System.out.println("Sorted!");
      /*
       * Show result
       */
      dumpDatabase(database);
      System.out.printf("%s comparisons made.", NumberOfComparisons);
    }
    catch(FileNotFoundException exception)
    {
      System.out.printf("Error opening database file '%s': file not found\n",DATABASE_FILENAME);
    }
  }
  private static void dumpDatabase(ArrayList<Track> database)
  {
    for(int i=0;i<database.size();i++)
    {
      System.out.printf("%-26s %-32s %4d %s\n",database.get(i).artist,database.get(i).cd,database.get(i).track,database.get(i).time);
    }
  }
  /**
   * Ask the user for a sorting method
   * @param input scanner used for asking
   * @return sorting method
   */
  private static SortingMethod askForSortingMethod(Scanner input)
  {
    /*
     * Show possible sorting methods
     */
    for(SortingMethod method : SortingMethod.values())
      System.out.printf("%d : %s\n",method.ordinal(),method);
    /*
     * Loop until valid choice
     */
    SortingMethod choice=null;
    while(choice==null)
    {
      System.out.println("Enter choice: ");
      int selection=input.nextInt();
      if(selection>=0&&selection<SortingMethod.values().length)
        choice=SortingMethod.values()[selection];
      else
        System.out.println("Invalid choice, try again!");
    }
    return choice;
  }
  /**
   * Reads the cd database from the file 'songs.txt' into the specified track array
   * @param database this is the database that will be filled with the input.
   * @return number of tracks read
   * @throws FileNotFoundException 
   */
  static void readDatabase(ArrayList<Track> database) throws FileNotFoundException
  {
    Integer numberOfTracks = 0;
    FileInputStream inputStream=new FileInputStream(DATABASE_FILENAME);
    Scanner scanner=new Scanner(inputStream);
    while(scanner.hasNext())
    {
      Track track=new Track();
      track.artist=scanner.nextLine();
      track.cd=scanner.nextLine();
      track.year=scanner.nextInt();
      scanner.nextLine();
      track.track=scanner.nextInt();
      scanner.nextLine();
      track.title=scanner.nextLine();
      track.tags=scanner.nextLine();
      track.time=new Length(scanner.nextLine());
      track.country=scanner.nextLine();
      database.add(track);
      numberOfTracks ++;
    }
    scanner.close();
    return numberOfTracks;
  }
  /*************** Auxiliary array routines from lecture ***************/
  /**
   * Checks if the slice of the specified array is sorted
   * @param array
   * @param slice
   * @return true if the slice of the array is in ascending order, false otherwise
   */
  static <T extends Comparable<T>> boolean isSorted(ArrayList<T> array, Slice slice)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid() : "Slice should be valid";
    for(int i=slice.from;i<slice.upto-1;i++)
      if(array.get(i).compareTo(array.get(i+1))>0)
        return false;
    return true;
  }
  /**
   * Find position in array slice where to insert new element
   * @param array
   * @param slice
   * @param y element for which the position should be returned
   * @return position where to insert
   */
  static <T extends Comparable<T>> int findInsertPosition(ArrayList<T> array, Slice slice, T y)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid() : "Slice should be valid";
    assert isSorted(array,slice);
    for(int i=slice.from;i<slice.upto;i++)
      if(array.get(i).compareTo(y)>=0)
        return i;
    return slice.upto;
  }
  /**
   * Shifts all elements in the slice one position to the right
   * @param array
   * @param slice
   */
  static <T extends Comparable<T>> void shiftRight(ArrayList<T> array, Slice slice)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid()&&slice.from<array.size() : "Slice should be valid";
    for(int i=slice.upto;i>slice.from;i--)
      array.set(i, array.get(i-1));
  }
  /**
   * Insert an element to a sorted array and keep it sorted
   * @param array
   * @param length length
   * @param y element to be added
   * @return new length
   */
  static <T extends Comparable<T>> int insert(ArrayList<T> array, int length, T y)
  {
    assert array!=null : "Array should be initialized";
    assert length>=0 : "Length cannot be negative";
    assert isSorted(array,new Slice(0,length)) : "Array should be sorted";
    int position=findInsertPosition(array,new Slice(0,length),y);
    shiftRight(array,new Slice(position,length));
    array.set(position, y);
    return length+1;
  }
  /**
   * Swap two elements in an array
   * @param array
   * @param i
   * @param j
   */
  private static <T extends Comparable<T>> void swap(ArrayList<T> array, int i, int j)
  {
    assert array!=null : "Array should be initialized";
    assert i>=0&&i<array.size() : "First index is invalid";
    assert j>=0&&j<array.size() : "Second index is invalid";
    T help=array.get(i);
    array.set(i, array.get(j));
    array.set(j, help);
  }
  /*************** Array based Sorting routines from lecture ***************/
  /**
   * Sorts an array in situ in ascending order using selection sort
   * @param array
   * @oaram length
   */
  static <T extends Comparable<T>> void selectionSort(ArrayList<T> array, int length)
  {
    assert array!=null : "array should be initialized";
    for(int i=0;i<length;i++)
    {
      int j=indexOfSmallestValue(array,new Slice(i,length));
      swap(array,i,j);
    }
  }
  /**
   * Finds index of smallest value in array slice
   * @param array
   * @param slice
   * @return index of smallest value
   */
  static <T extends Comparable<T>> int indexOfSmallestValue(ArrayList<T> array, Slice slice)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid()&&slice.upto<=array.size() : "Slice should be valid";
    assert slice.upto-slice.from>0 : "Slice should be non-empty";
    int index=slice.from;
    for(int i=slice.from+1;i<slice.upto;i++)
      if(array.get(i).compareTo(array.get(index))<0)
        index=i;
    return index;
  }
  /**
   * Sorts an array in situ in ascending order using bubble sort
   * @param array
   * @param length
   */
  static <T extends Comparable<T>> void bubbleSort(ArrayList<T> array, int length)
  {
    assert array!=null : "array should be initialized";
    while(!bubble(array,new Slice(0,length)))
        length--;
  }
  /**
   * Swap all adjacent pairs in the array slice that are not in the right order
   * @param array
   * @param slice
   * @return array slice is sorted
   */
  static <T extends Comparable<T>> boolean bubble(ArrayList<T> array, Slice slice)
  {
    assert array!=null : "Array should be initialized";
    assert slice.isValid()&&slice.upto<=array.size() : "Slice should be valid";
    boolean isSorted=true;
    for(int i=slice.from;i<slice.upto-1;i++)
      if(array.get(i).compareTo(array.get(i+1))>0)
      {
        swap(array,i,i+1);
        isSorted=false;
      }
    return isSorted;
  }
  /**
   * Sorts an array in situ in ascending order using insertion sort
   * @param array
   * @param length
   */
  static <T extends Comparable<T>> void insertionSort(ArrayList<T> array, int length)
  {
    assert array!=null : "array should be initialized";
    for(int i=0;i<length;i++)
      insert(array,i,array.get(i));
  }
  
  /**
	 * Everything below here is the radix sorting algorithm, it was not needed,
	 * though it was fun to make it.
	 */

	/**
	 * Performs 5 passes.
	 * 
	 * @param database
	 * @param length
	 */
	private static <T extends Comparable<T>> void radixSort(ArrayList<Track> database, int length) {
		assert database != null : "array should be initialized";
		int nrOfPasses = 4;
		for (int x = nrOfPasses; x >= 0; x--)
			makePass(database, x );
		
	}

	/**
	 * Makes a pass, first comparing the last digit, then the one before that, then
	 * the one before that, etc.
	 * 
	 * @param database
	 * @param i
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> void makePass(ArrayList<T> database, int i) {
		assert database != null : "array should be initialized";
		bucketList.initialize();
		fillBuckets((ArrayList<Track>) database, i);
		emptyBuckets(database, i);
	}

	/**
	 * Empties the buckets fifo, first bucket 0, then bucket 1, etc.
	 * 
	 * @param database
	 * @param i
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> void emptyBuckets(ArrayList<T> database, int i) {
		assert database != null : "array should be initialized";
		int counter = 0;
		for (int x = 0; x < 10; x++) {
			while (bucketList.buckets[x].size() > 0) {
				database.set(counter, (T) bucketList.buckets[x].get(0));
				counter++;
				bucketList.buckets[x].remove(0);
			}
		}
	}

	/**
	 * Fills the buckets
	 * The switch switches on a character in a String
	 * The casts is made from the number of seconds in a track
	 * The number of seconds is simply seconds + 60*minutes
	 * 
	 * @param numbers
	 * @param i
	 */
	@SuppressWarnings("unchecked")
	private static void fillBuckets(ArrayList<Track> numbers, int i) {
		for (int x = 0; x < numbers.size(); x++) {
			numberOfComparisons += 3;
			switch ((String.format("%05d", (numbers.get(x).time.seconds + (60 * numbers.get(x).time.minutes)))
					.charAt(i))) {
			case '0':
				bucketList.buckets[0].add(numbers.get(x));
				break;
			case '1':
				bucketList.buckets[1].add(numbers.get(x));
				break;
			case '2':
				bucketList.buckets[2].add(numbers.get(x));
				break;
			case '3':
				bucketList.buckets[3].add(numbers.get(x));
				break;
			case '4':
				bucketList.buckets[4].add(numbers.get(x));
				break;
			case '5':
				bucketList.buckets[5].add(numbers.get(x));
				break;
			case '6':
				bucketList.buckets[6].add(numbers.get(x));
				break;
			case '7':
				bucketList.buckets[7].add(numbers.get(x));
				break;
			case '8':
				bucketList.buckets[8].add(numbers.get(x));
				break;
			case '9':
				bucketList.buckets[9].add(numbers.get(x));
				break;
			default:
				break;
			}
		}
	}

	/**
	 * This is the amazing recursive quicksort algorithm. It works in situ
	 * 
	 * @param database
	 * @param start
	 * @param end
	 */
	private static <T extends Comparable<T>> void quickSort(ArrayList<T> database, int start, int end) {
		assert database!=null:"Array must be initialized";
		if (end - start > 0) {
			int randomNum = start + rand.nextInt(end - start);
			for (int x = start; x < end; x++) {
				numberOfComparisons += 1;
				if (x < randomNum) {
					if (database.get(x).compareTo(database.get(randomNum)) > 0) {
						database.add(end - 1, database.get(x));
						database.remove(x);
						randomNum--;
						x--;
					}
				} else if (x > randomNum) {
					if (database.get(x).compareTo(database.get(randomNum)) < 0) {
						database.add(start, database.get(x));
						database.remove(x + 1);
						randomNum++;
						x--;
					}
				}
			}
			quickSort(database, start, randomNum);
			quickSort(database, randomNum + 1, end);
		}
	}
}
