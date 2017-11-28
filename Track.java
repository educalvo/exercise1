package nl.ru.ai.exercise1;

public class Track implements Comparable<Track> {
	public String artist; // name of artist
	public String cd; // cd title
	public int year; // year production
	public int track; // track number
	public String title; // track title
	public String tags; // track tags
	public Length time; // track length
	public String country; // artist country

	/**
	 * Compare this track with an other
	 * 
	 * @return -1 if this track is smaller, 0 if equal and 1 if this track is larger
	 */
	public int compareTo(Track other) {
		Exercise1.NumberOfComparisons++;
		return Integer.compare(time.seconds + time.minutes*60, other.time.seconds + other.time.minutes*60);
	}
/*	public int compareTo(Track other) {
		if (artist.compareTo(other.artist) == 0) {
		Exercise1.NumberOfComparisons++;
			if (cd.compareTo(other.cd) == 0) {
			Exercise1.NumberOfComparisons++;
				if (Integer.compare(year, other.year) == 0) {
					Exercise1.NumberOfComparisons++;
					return Integer.compare(track, other.track);
				}
				Exercise1.NumberOfComparisons++;
				return Integer.compare(year, other.year);
			}
			Exercise1.NumberOfComparisons++;
			return cd.compareTo(other.cd);
		}
		Exercise1.NumberOfComparisons++;
		return artist.compareTo(other.artist);
	}*/
}

