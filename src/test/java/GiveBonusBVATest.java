import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GiveBonusBVATest {

    private final String IN_FILE = "IN.TXT";
    private final String OUT_FILE = "OUT.TXT";

    // --- HELPER METHODS ---

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

    // --- BVA TEST CASES ---

    @Test
    @DisplayName("TC1 (BVA): salary = 0 (Lower bound) -> RC=0, Sal=1000")
    public void testTC1_SalaryZero() throws Exception {
        createInTxt("1\nA\nIT\ndev\n0\n1\nIT\n10\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(1000, getSalaryFromOut("A"));
    }

    @Test
    @DisplayName("TC2 (BVA): salary = -1 (Invalid lower bound) -> Throws Exception")
    public void testTC2_SalaryNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            createInTxt("1\nA\nIT\ndev\n-1\n1\nIT\n10\n");
            GiveBonus.main(new String[]{});
        });
    }

    @Test
    @DisplayName("TC3 (BVA): salary = 1 (Just above lower bound) -> RC=0, Sal=1001")
    public void testTC3_SalaryOne() throws Exception {
        createInTxt("1\nA\nIT\ndev\n1\n1\nIT\n10\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(1001, getSalaryFromOut("A"));
    }

    @Test
    @DisplayName("TC4 (BVA): salary = 4999 (Just below 5000 boundary) -> RC=0, Sal=5999")
    public void testTC4_Salary4999() throws Exception {
        createInTxt("1\nA\nIT\ndev\n4999\n1\nIT\n10\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(5999, getSalaryFromOut("A"));
    }

    @Test
    @DisplayName("TC5 (BVA): salary = 5000 (Exact boundary for +1000 bonus) -> RC=0, Sal=6000")
    public void testTC5_Salary5000() throws Exception {
        createInTxt("1\nA\nIT\ndev\n5000\n1\nIT\n10\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(6000, getSalaryFromOut("A"));
    }

    @Test
    @DisplayName("TC6 (BVA): salary = 5001 (Just above 5000 boundary, +500 bonus) -> RC=0, Sal=5501")
    public void testTC6_Salary5001() throws Exception {
        createInTxt("1\nA\nIT\ndev\n5001\n1\nIT\n10\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(5501, getSalaryFromOut("A"));
    }

    @Test
    @DisplayName("TC7 (BVA): numEmp = 0 (Lower bound valid empty array) -> RC=1")
    public void testTC7_NumEmpZero() throws Exception {
        createInTxt("0\n1\nIT\n10\n");
        GiveBonus.main(new String[]{});

        assertEquals(1, getReturnCodeFromOut());
    }

    @Test
    @DisplayName("TC8 (BVA): numEmp = -1 (Invalid lower bound) -> Throws Exception")
    public void testTC8_NumEmpNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            createInTxt("-1\n1\nIT\n10\n");
            GiveBonus.main(new String[]{});
        });
    }

    @Test
    @DisplayName("TC9 (BVA): numEmp = 1 (Just above lower empty bound) -> RC=0, Sal=4000")
    public void testTC9_NumEmpOne() throws Exception {
        createInTxt("1\nA\nIT\ndev\n3000\n1\nIT\n10\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(4000, getSalaryFromOut("A"));
    }

    @Test
    @DisplayName("TC10 (BVA): numSales = 0 (Lower bound valid empty array) -> RC=1")
    public void testTC10_NumSalesZero() throws Exception {
        createInTxt("1\nA\nIT\ndev\n3000\n0\n");
        GiveBonus.main(new String[]{});

        assertEquals(1, getReturnCodeFromOut());
    }

    @Test
    @DisplayName("TC11 (BVA): numSales = -1 (Invalid lower bound) -> Throws Exception")
    public void testTC11_NumSalesNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            createInTxt("1\nA\nIT\ndev\n3000\n-1\n");
            GiveBonus.main(new String[]{});
        });
    }

    @Test
    @DisplayName("TC12 (BVA): numSales = 1 (Just above lower empty bound) -> RC=0, Sal=4000")
    public void testTC12_NumSalesOne() throws Exception {
        createInTxt("1\nA\nIT\ndev\n3000\n1\nIT\n10\n");
        GiveBonus.main(new String[]{});

        assertEquals(0, getReturnCodeFromOut());
        assertEquals(4000, getSalaryFromOut("A"));
    }
}