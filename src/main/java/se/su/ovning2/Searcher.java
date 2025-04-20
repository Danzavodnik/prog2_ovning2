package se.su.ovning2;

import java.util.*;

public class Searcher implements SearchOperations {
    private TreeMap<Integer, Set<Recording>> recordingsByYear;
    private HashMap<String, Set<Recording>> recordingsByArtist;
    private final Set<Recording> recordingSet;
    private final Set<String> artistSet;
    private final Set<String> genreSet;
    private final Set<String> titleSet;

    public Searcher(Collection<Recording> data) {

        this.recordingSet = new HashSet<>(data);
        this.artistSet = new HashSet<>();
        this.genreSet = new HashSet<>();
        this.titleSet = new HashSet<>();
        this.recordingsByYear = new TreeMap<>();
        this.recordingsByArtist = new HashMap<>();

        for (Recording recording : recordingSet) {
            titleSet.add(recording.getTitle());
            genreSet.addAll(recording.getGenre());
            artistSet.add(recording.getArtist());
            recordingsByYear.computeIfAbsent(recording.getYear(), y -> new HashSet<>()).add(recording);
            recordingsByArtist.computeIfAbsent(recording.getArtist(), a -> new HashSet<>()).add(recording);
        }
    }

    @Override
    public long numberOfArtists() {
        return artistSet.size();
    }

    @Override
    public long numberOfGenres() {
        return genreSet.size();
    }

    @Override
    public long numberOfTitles() {
        return titleSet.size();
    }

    @Override
    public boolean doesArtistExist(String name) {
        return artistSet.contains(name);
    }

    @Override
    public Collection<String> getGenres() {
        HashSet<String> genreHashSet = new HashSet<>(genreSet);
        return Collections.unmodifiableCollection(genreHashSet);
    }

    @Override
    public Recording getRecordingByName(String title) {
        for (Recording recording : recordingSet) {
            if (recording.getTitle().equals(title)) {
                return recording;
            }
        }
        return null;
    }

    @Override
    public Collection<Recording> getRecordingsAfter(int year) {
        SortedMap<Integer, Set<Recording>> recordingsAfterYear = recordingsByYear.tailMap(year);
        Set<Recording> afterYearCollection = new HashSet<>();
        for (Set<Recording> query : recordingsAfterYear.values()) {
            afterYearCollection.addAll(query);
        }
        return Collections.unmodifiableSet(afterYearCollection);
    }

    @Override
    public SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist) {
        Set<Recording> artistRecordings = recordingsByArtist.get(artist);

        if(artistRecordings == null){
            return Collections.emptySortedSet();
        }
        SortedSet<Recording> sortedSet = new TreeSet<>(Comparator.comparingInt(Recording::getYear));
        sortedSet.addAll(artistRecordings);
        return Collections.unmodifiableSortedSet(sortedSet);
    }

    @Override
    public Collection<Recording> getRecordingsByGenre(String genre) {
        HashSet<Recording> genreCollection = new HashSet<Recording>();
        for (Recording recording : recordingSet) {
            if (recording.getGenre().contains(genre)) {
                genreCollection.add(recording);
            }
        }
        return Collections.unmodifiableSet(genreCollection);
    }

    @Override
    public Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo) {
        SortedMap<Integer, Set<Recording>> recordingMap = recordingsByYear.subMap(yearFrom, yearTo + 1);
        HashSet<Recording> genreByYearCollection = new HashSet<Recording>();

        for (Set<Recording> recordings : recordingMap.values()) {
            for (Recording recording : recordings) {
                if (recording.getGenre().contains(genre)) {
                    genreByYearCollection.add(recording);
                }
            }
        }
        return Collections.unmodifiableSet(genreByYearCollection);
    }

    @Override
    public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
        HashSet<Recording> newRecordsCollection = new HashSet<Recording>();

        for (Recording record : offered) {
            if (!recordingSet.contains(record)) {
                newRecordsCollection.add(record);
            }
        }
        return Collections.unmodifiableSet(newRecordsCollection);
    }
}
