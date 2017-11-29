package nl.ru.ai.exercise1;

public enum SortingMethod {
  BUBBLE_SORT, INSERTION_SORT, SELECTION_SORT, QUICK_SORT, RADIX_SORT;
  public String toString()
  {
    switch(this)
    {
      case BUBBLE_SORT:
        return "Bubble sort";
      case INSERTION_SORT:
        return "Insertion sort";
      case SELECTION_SORT:
        return "Selection sort";
      case QUICK_SORT:
        return"Quick sort";
      case RADIX_SORT:
        return "Radix sort, only works when sorting on track length.";
      default:
        return null;
    }
  }
}
