package se.su.ovning2;

import java.util.Collection;
import java.util.Set;
import java.util.Objects;


public class Recording {
  private final int year;
  private final String artist;
  private final String title;
  private final String type;
  private final Set<String> genre;

  public Recording(String title, String artist, int year, String type, Set<String> genre) {
    this.title = title;
    this.year = year;
    this.artist = artist;
    this.type = type;
    this.genre = genre;
  }

  public String getArtist() {
    return artist;
  }

  public Collection<String> getGenre() {
    return genre;
  }

  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public int getYear() {
    return year;
  }

  @Override
  public boolean equals(Object object){
    if(this == object){
      return true;
    }
    if(!(object instanceof Recording)){
      return false;
    }
    Recording otherRecord = (Recording) object;

    if (this.year == otherRecord.year &&
            this.title.equals(otherRecord.title) &&
            this.artist.equals(otherRecord.artist)) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode(){
    return Objects.hash(title, artist, year, genre);
  }

  @Override
  public String toString() {
    return String.format("{ %s | %s | %s | %d | %s }", artist, title, genre, year, type);
  }
}
