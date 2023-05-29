import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FileReadTest {
    @Test
    public void testGetBinaryFrequency_EmptyFile() {
        //given
        Map<Integer, Integer> expectedFrequency = new HashMap<>();
        String fileName = "empty_file.bin";

        //when
        Map<Integer, Integer> result = FileRead.getBinaryFrequency(fileName);

        //then
        assertEquals(expectedFrequency, result);
    }
    @Test
    public void testGetBinaryFrequency_NonExistingFile() {
        //given
        String fileName = "non_existing_file.bin";

        //then
        Map<Integer, Integer> result = FileRead.getBinaryFrequency(fileName);

        //when
        assertNull(result);
    }
    @Test
    public void testGetBinaryFrequency_UnreadableFile() {
        //given
        String fileName = "unreadable_file.bin";

        //when
        Map<Integer, Integer> result = FileRead.getBinaryFrequency(fileName);

        //then
        assertNull(result);
    }
    @Test
    public void testGetBinaryFrequency_FileNotFound() {
        // given
        String fileName = "nonexistentfile.txt";

        // when
        Map<Integer, Integer> actualFrequency = FileRead.getBinaryFrequency(fileName);

        // then
        assertNull(actualFrequency);
    }
}
