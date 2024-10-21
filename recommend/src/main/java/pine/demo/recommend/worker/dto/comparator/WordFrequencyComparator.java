package pine.demo.recommend.worker.dto.comparator;

import pine.demo.recommend.worker.dto.HeapNodeDto;

import java.util.Comparator;

public class WordFrequencyComparator implements Comparator<HeapNodeDto> {
    @Override
    public int compare(HeapNodeDto x, HeapNodeDto y) {
        int compareCount = Integer.compare( // count min heap
                x.getCount(), y.getCount()
        );

        if (compareCount != 0) return compareCount;

        return CharSequence.compare( // word max heap
                y.getWord(), x.getWord()
        );
    }
}

