import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GiveBonusOurs {

    // --- INNER DATA CLASSES ---
    private static class Angajat {
        String n;  // name
        String d;  // department
        String f;  // function
        int ss;    // salary
    }

    private static class Vanzare {
        String d;  // department
        int sv;    // sumSale
    }

    private static class Data {
        int nA;
        int nV;
        Angajat[] sA;
        Vanzare[] sV;
    }

    // --- FILE I/O ---
    private static Data citireDate(String fileName) throws IOException {
        Data data = new Data();
        data.sA = new Angajat[100];
        data.sV = new Vanzare[100];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            data.nA = (line == null || line.trim().isEmpty()) ? 0 : Integer.parseInt(line.trim());

            for (int i = 0; i < data.nA; i++) {
                Angajat emp = new Angajat();
                emp.n = reader.readLine().trim();
                emp.d = reader.readLine().trim();
                emp.f = reader.readLine().trim();
                emp.ss = Integer.parseInt(reader.readLine().trim());
                data.sA[i] = emp;
            }

            line = reader.readLine();
            data.nV = (line == null || line.trim().isEmpty()) ? 0 : Integer.parseInt(line.trim());

            for (int i = 0; i < data.nV; i++) {
                Vanzare sale = new Vanzare();
                sale.d = reader.readLine().trim();
                sale.sv = Integer.parseInt(reader.readLine().trim());
                data.sV[i] = sale;
            }
        }
        return data;
    }

    private static void afisareDate(String fileName, int returnCode, int numEmployees, Angajat[] employees) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("code=" + returnCode);
            writer.newLine();
            if (numEmployees > 0) {
                writer.write("nE=" + numEmployees);
                writer.newLine();
                for (int i = 0; i < numEmployees; i++) {
                    Angajat emp = employees[i];
                    writer.write(emp.n + " " + emp.d + " " + emp.f + " " + emp.ss);
                    writer.newLine();
                }
            }
        }
    }

    private static int acordaBonus(int numEmp, Angajat[] employees, int numSales, Vanzare[] sales) {
        if (numEmp < 0 || numSales < 0) {
            throw new IllegalArgumentException("Array size cannot be negative");
        }

        if (numEmp == 0 || numSales == 0) {
            return 1;
        }

        int maxSale = Integer.MIN_VALUE;
        String maxSaleDept = null;
        for (int i = 0; i < numSales; i++) {
            if (sales[i].sv > maxSale) {
                maxSale = sales[i].sv;
                maxSaleDept = sales[i].d;
            }
        }

        boolean hasEmployeesInMaxDept = false;
        for (int i = 0; i < numEmp; i++) {
            if (employees[i].ss < 0) {
                throw new IllegalArgumentException("Salary cannot be negative");
            }
            if (employees[i].d.equals(maxSaleDept)) {
                hasEmployeesInMaxDept = true;
            }
        }

        if (!hasEmployeesInMaxDept) {
            return 2;
        }

        for (int i = 0; i < numEmp; i++) {
            if (employees[i].d.equals(maxSaleDept)) {
                boolean isManager = "manager".equalsIgnoreCase(employees[i].f);
                boolean earnsOver5000 = employees[i].ss > 5000;

                if (isManager || earnsOver5000) {
                    employees[i].ss += 500;
                } else {
                    employees[i].ss += 1000;
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) throws Exception {
        Data data = citireDate("IN.TXT");
        int returnCode = acordaBonus(data.nA, data.sA, data.nV, data.sV);
        afisareDate("OUT.TXT", returnCode, data.nA, data.sA);
    }
}