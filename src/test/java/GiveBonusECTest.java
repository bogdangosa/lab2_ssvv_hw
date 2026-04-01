import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GiveBonusECTest {

    private final String IN_FILE = "IN.TXT";
    private final String OUT_FILE = "OUT.TXT";

    @AfterEach
    public void tearDown() {
        new File(IN_FILE).delete();
        new File(OUT_FILE).delete();
    }

    private void createInTxt(String content) throws IOException {
        try (FileWriter writer = new FileWriter(IN_FILE)) {
            writer.write(content);
        }
    }

    private int getReturnCodeFromOut() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(OUT_FILE))) {
            String firstLine = reader.readLine();
            if (firstLine != null && firstLine.startsWith("code=")) {
                return Integer.parseInt(firstLine.split("=")[1]);
            }
        }
        return -1;
    }

    private int getSalaryFromOut(String employeeName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(OUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(employeeName + " ")) {
                    String[] parts = line.split(" ");
                    return Integer.parseInt(parts[parts.length - 1]);
                }
            }
        }
        throw new RuntimeException("Employee " + employeeName + " not found in OUT.TXT");
    }

    // --- TEST CASES ---

    @Test
    @DisplayName("TC1: Salary <= 5000, 'dev' -> +1000 bonus")
    public void testTC1() throws Exception {
        createInTxt("1\nA\nIT\ndev\n3000\n1\nIT\n100\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(4000, getSalaryFromOut("A"));
    }

    @Test
    @DisplayName("TC2: Salary > 5000, 'dev' -> +500 bonus")
    public void testTC2() throws Exception {
        createInTxt("1\nB\nIT\ndev\n6000\n1\nIT\n100\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(6500, getSalaryFromOut("B"));
    }

    @Test
    @DisplayName("TC3: Salary <= 5000, 'manager' -> +500 bonus")
    public void testTC3() throws Exception {
        createInTxt("1\nC\nIT\nmanager\n4000\n1\nIT\n100\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(4500, getSalaryFromOut("C"));
    }

    @Test
    @DisplayName("TC4: Salary > 5000, 'manager' -> +500 bonus")
    public void testTC4() throws Exception {
        createInTxt("1\nD\nIT\nmanager\n7000\n1\nIT\n100\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        // NOTE: This will fail! The professor's code gives them 8000 (double bonus).
        assertEquals(7500, getSalaryFromOut("D"));
    }

    @Test
    @DisplayName("TC5: Employee dept != max sale dept -> Salary unchanged")
    public void testTC5() throws Exception {
        // We need 2 employees so that the winning department isn't empty (which would trigger RC=2).
        // 1 Emp in IT (Winner), 1 Emp in HR (Loser). 2 Sales (IT=100, HR=50). Max sale is IT.
        String inputData =
                "2\n" +
                        "Winner\nIT\ndev\n3000\n" +   // This guy prevents RC=2
                        "E\nHR\ndev\n3000\n" +        // This is the guy we are actually testing
                        "2\n" +
                        "IT\n100\n" +
                        "HR\n50\n";

        createInTxt(inputData);
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut(), "RC should be 0 because IT has an employee");
        assertEquals(3000, getSalaryFromOut("E"), "HR employee's salary should remain unchanged");
        assertEquals(4000, getSalaryFromOut("Winner"), "IT employee should get the +1000 bonus");
    }

    @Test
    @DisplayName("TC6: numEmp = 0 -> Return Code 1")
    public void testTC6() throws Exception {
        createInTxt("0\n1\nIT\n100\n");
        GiveBonus.main(new String[]{});

        assertEquals(1, getReturnCodeFromOut());
    }

    @Test
    @DisplayName("TC7: numSales = 0 -> Return Code 1")
    public void testTC7() throws Exception {
        createInTxt("1\nA\nIT\ndev\n3000\n0\n");
        GiveBonus.main(new String[]{});

        assertEquals(1, getReturnCodeFromOut());
    }

    @Test
    @DisplayName("TC8: No employees in the max sale dept -> Return Code 2")
    public void testTC8() throws Exception {
        createInTxt("1\nA\nHR\ndev\n3000\n1\nIT\n100\n");
        GiveBonus.main(new String[]{});

        assertEquals(2, getReturnCodeFromOut());
    }

    @Test
    @DisplayName("TC9: numEmp = -1 -> Should throw Error")
    public void testTC9() {
        // We use assertThrows because BBT dictates negative sizes should throw an exception.
        // NOTE: This test will fail because the program just returns RC 2 instead of throwing an error.
        assertThrows(Exception.class, () -> {
            createInTxt("-1\n1\nIT\n100\n");
            GiveBonus.main(new String[]{});
        }, "Program should crash/throw error on negative array size");
    }

    @Test
    @DisplayName("TC10: Salary = -500 -> Should throw Error")
    public void testTC10() {
        // NOTE: This test will fail because the program silently processes the negative salary.
        assertThrows(Exception.class, () -> {
            createInTxt("1\nA\nIT\ndev\n-500\n1\nIT\n100\n");
            GiveBonus.main(new String[]{});
        }, "Program should crash/throw error on negative salary");
    }
}