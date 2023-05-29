import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

class DataSaveTest {

    @Test
    void givenEmptyFile_whenGetFrequencyMap_thenEmptyMapReturned() {
        // given
        String filePath = "testFiles/test.txt";

        // when
        HashMap<Integer, Integer> frequencyMap;
        try {
            frequencyMap = DataSave.getFrequencyMap(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("IOException occurred");
            return;
        }

        // then
        Assertions.assertTrue(frequencyMap.isEmpty(), "Frequency map should be empty");
    }

    @Test
    void givenFileWithUniqueCharacters_whenGetFrequencyMap_thenCorrectMapReturned() {
        // given
        String filePath = "testFiles/test3.txt";

        // when
        HashMap<Integer, Integer> frequencyMap;
        try {
            frequencyMap = DataSave.getFrequencyMap(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("IOException occurred");
            return;
        }

        // then
        HashMap<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(65, 1); // 'A'
        expectedMap.put(66, 1); // 'B'
        expectedMap.put(67, 1); // 'C'
        expectedMap.put(68, 1); // 'D'
        expectedMap.put(69, 1); // 'E'
        expectedMap.put(70, 1); // 'F'
        Assertions.assertEquals(expectedMap, frequencyMap, "Frequency map does not match expected result");
    }

    @Test
    void givenFileWithRepeatedCharacters_whenGetFrequencyMap_thenCorrectMapReturned() {
        // given
        String filePath = "testFiles/test2.txt";

        // when
        HashMap<Integer, Integer> frequencyMap;
        try {
            frequencyMap = DataSave.getFrequencyMap(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("IOException occurred");
            return;
        }

        // then
        HashMap<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(65, 3); // 'A'
        expectedMap.put(66, 3); // 'B'
        expectedMap.put(67, 3); // 'C'
        expectedMap.put(68, 3); // 'D'
        Assertions.assertEquals(expectedMap, frequencyMap, "Frequency map does not match expected result");
    }
}
