import java.util.Scanner;
public class TUBES {
    static void tampilkanDaftarAlat(String[] namaAlat, int[][] dataAlat) {
        System.out.println();
        System.out.println("=== Daftar Alat ===");
        System.out.println("Kode\tNama Alat\tTarif/Hari\tStok");
        for (int i = 0; i < namaAlat.length; i++) {
            String pemisahNama = "\t";
            if (namaAlat[i].length() < 8) {
                pemisahNama = "\t\t";
            }
            String tarif = "Rp" + dataAlat[i][0];
            String pemisahTarif = "\t";
            if (tarif.length() < 8) {
                pemisahTarif = "\t\t";
            }
            System.out.println((i + 1) + "\t" + namaAlat[i] + pemisahNama + tarif + pemisahTarif + dataAlat[i][1]);
        }
        System.out.println();
    }
    static int hitungSubtotal(int tarif, int jumlah, int lama) {
        int subtotal = tarif * jumlah * lama;
        return subtotal;
    }
    static int hitungDiskon(int subtotal) {
        int persen = 0;
        if (subtotal >= 600000) {
            persen = 10;
        } else if (subtotal >= 300000) {
            persen = 5;
        } else {
            persen = 0;
        }
        int potongan = subtotal * persen / 100;
        return potongan;
    }
    static void cetakStruk(String nama, int jumlah, int lama, int subtotal, int potongan, int totalBayar) {
        System.out.println();
        System.out.println("========== STRUK PENYEWAAN ==========");
        System.out.println("Nama alat   : " + nama);
        System.out.println("Jumlah unit : " + jumlah);
        System.out.println("Lama sewa   : " + lama + " hari");
        System.out.println("Subtotal    : " + formatRupiah(subtotal));
        System.out.println("Potongan    : " + formatRupiah(potongan));
        System.out.println("Total bayar : " + formatRupiah(totalBayar));
        System.out.println("=====================================");
        System.out.println();
    }
    static String formatRupiah(int nominal) {
        String hasil = "Rp" + String.format("%,d", nominal).replace(',', '.');
        return hasil;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String[] namaAlat = {"Proyektor", "Speaker", "Mikrofon", "Layar proyektor"};
        int[][] dataAlat = {{100000, 3}, {150000, 2}, {25000, 6}, {50000, 4}};

        int totalTransaksi = 0;
        int akumulasiTotalBayar = 0;
        int lanjut = 1;
        do {
            tampilkanDaftarAlat(namaAlat, dataAlat);

            int indeks = -1;
            boolean kodeValid = false;
            while (!kodeValid) {
                System.out.print("Masukkan kode alat (1-" + namaAlat.length + "): ");
                int kode = input.nextInt();
                if (kode < 1 || kode > namaAlat.length) {
                    System.out.println("Alat tidak ditemukan.");
                } else if (dataAlat[kode - 1][1] == 0) {
                    System.out.println("Stok tidak mencukupi.");
                } else {
                    indeks = kode - 1;
                    kodeValid = true;
                }
            }
            int jumlah = 0;
            boolean jumlahValid = false;
            while (!jumlahValid) {
                System.out.print("Masukkan jumlah unit: ");
                jumlah = input.nextInt();
                if (jumlah <= 0) {
                    System.out.println("Jumlah unit harus lebih dari 0.");
                } else if (jumlah > dataAlat[indeks][1]) {
                    System.out.println("Stok tidak mencukupi.");
                } else {
                    jumlahValid = true;
                }
            }
            int lama = 0;
            boolean lamaValid = false;
            while (!lamaValid) {
                System.out.print("Masukkan lama sewa (1-7 hari): ");
                lama = input.nextInt();
                if (lama < 1 || lama > 7) {
                    System.out.println("Lama sewa harus 1 sampai 7 hari.");
                } else {
                    lamaValid = true;
                }
            }
            int subtotal = hitungSubtotal(dataAlat[indeks][0], jumlah, lama);
            int potongan = hitungDiskon(subtotal);
            int totalBayar = subtotal - potongan;
            dataAlat[indeks][1] = dataAlat[indeks][1] - jumlah;
            totalTransaksi++;
            akumulasiTotalBayar = akumulasiTotalBayar + totalBayar;
            cetakStruk(namaAlat[indeks], jumlah, lama, subtotal, potongan, totalBayar);
            boolean lanjutValid = false;
            while (!lanjutValid) {
                System.out.print("Lanjut transaksi? (1 = lanjut, 0 = selesai): ");
                lanjut = input.nextInt();
                if (lanjut == 0 || lanjut == 1) {
                    lanjutValid = true;
                } else {
                    System.out.println("Pilihan tidak valid, masukkan 1 atau 0.");
                }
            }
            System.out.println();
        } while (lanjut == 1);
        System.out.println("RINGKASAN AKHIR");
        System.out.println("Total transaksi berhasil : " + totalTransaksi);
        System.out.println("Akumulasi total bayar    : " + formatRupiah(akumulasiTotalBayar));
        System.out.println("Sisa stok:");
        for (int i = 0; i < namaAlat.length; i++) {
            System.out.println("- " + namaAlat[i] + ": " + dataAlat[i][1]);
        }
        input.close();
    }
}