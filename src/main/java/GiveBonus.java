//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GiveBonus {
    private static Data citireDate(String var0) throws IOException {
        Data var1 = new Data();
        var1.sA = new Angajat[10];
        var1.sV = new Vanzare[10];

        try (BufferedReader var2 = new BufferedReader(new FileReader(var0))) {
            String var3 = var2.readLine();
            var1.nA = var3 == null ? 0 : Integer.parseInt(var3.trim());

            for(int var4 = 0; var4 < var1.nA; ++var4) {
                Angajat var5 = new Angajat();
                var5.n = var2.readLine();
                var5.d = var2.readLine();
                var5.f = var2.readLine();
                var5.ss = Integer.parseInt(var2.readLine().trim());
                var1.sA[var4] = var5;
            }

            var3 = var2.readLine();
            var1.nV = var3 == null ? 0 : Integer.parseInt(var3.trim());

            for(int var9 = 0; var9 < var1.nV; ++var9) {
                Vanzare var10 = new Vanzare();
                var10.d = var2.readLine();
                var10.sv = Integer.parseInt(var2.readLine().trim());
                var1.sV[var9] = var10;
            }
        }

        return var1;
    }

    private static void afisareDate(String var0, int var1, int var2, Angajat[] var3) throws IOException {
        try (BufferedWriter var4 = new BufferedWriter(new FileWriter(var0))) {
            var4.write("code=" + var1);
            var4.newLine();
            if (var2 > 0) {
                var4.write("nE=" + var2);
                var4.newLine();

                for(int var5 = 0; var5 < var2; ++var5) {
                    Angajat var6 = var3[var5];
                    var4.write(var6.n + " " + var6.d + " " + var6.f + " " + var6.ss);
                    var4.newLine();
                }
            }
        }

    }

    private static int acordaBonus(int var0, Angajat[] var1, int var2, Vanzare[] var3) {
        if (var0 != 0 && var2 != 0) {
            int var4 = Integer.MIN_VALUE;
            String var5 = null;

            for(int var6 = 0; var6 < var2; ++var6) {
                if (var3[var6].sv > var4) {
                    var4 = var3[var6].sv;
                    var5 = var3[var6].d;
                }
            }

            boolean var8 = false;

            for(int var7 = 0; var7 < var0 && !var8; ++var7) {
                if (var1[var7].d.equals(var5)) {
                    var8 = true;
                }
            }

            if (!var8) {
                return 2;
            } else {
                for(int var9 = 0; var9 < var0; ++var9) {
                    if (var1[var9].d.equals(var5)) {
                        if (var1[var9].ss > 5000) {
                            var1[var9].ss += 500;
                        }

                        if ("manager".equals(var1[var9].f)) {
                            var1[var9].ss += 500;
                        }

                        if (var1[var9].ss <= 5000 && !"manager".equals(var1[var9].f)) {
                            var1[var9].ss += 1000;
                        }
                    }
                }

                return 0;
            }
        } else {
            return 1;
        }
    }

    public static void main(String[] var0) throws Exception {
        Data var1 = citireDate("IN.TXT");
        int var2 = acordaBonus(var1.nA, var1.sA, var1.nV, var1.sV);
        afisareDate("OUT.TXT", var2, var1.nA, var1.sA);
        System.out.println("GiveBonus ... done!");
    }

    private static class Angajat {
        String n;
        String d;
        String f;
        int ss;
    }

    private static class Vanzare {
        String d;
        int sv;
    }

    private static class Data {
        int nA;
        int nV;
        Angajat[] sA;
        Vanzare[] sV;
    }
}
