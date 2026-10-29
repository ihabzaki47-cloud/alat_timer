import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

public class Main extends JFrame {

    // === PALET WARNA ===
    static final Color COLOR_BG          = new Color(0xF7, 0xF8, 0xFA);
    static final Color COLOR_NAVY        = new Color(0x1A, 0x1A, 0x2E);
    static final Color COLOR_GREEN       = new Color(0x16, 0xC7, 0x84);
    static final Color COLOR_GREEN_HOVER = new Color(0x12, 0xA8, 0x6E);
    static final Color COLOR_BLUE        = new Color(0x3A, 0x86, 0xFF);
    static final Color COLOR_BLUE_HOVER  = new Color(0x29, 0x70, 0xE8);
    static final Color COLOR_RED         = new Color(0xFF, 0x4D, 0x4D);
    static final Color COLOR_RED_HOVER   = new Color(0xE0, 0x33, 0x33);
    static final Color COLOR_TEXT_DARK   = new Color(0x1A, 0x1A, 0x2E);
    static final Color COLOR_TEXT_MED    = new Color(0x4B, 0x55, 0x63);
    static final Color COLOR_TEXT_LIGHT  = new Color(0x9C, 0xA3, 0xAF);
    static final Color COLOR_BORDER      = new Color(0xE5, 0xE7, 0xEB);
    static final Color COLOR_WHITE       = Color.WHITE;

    static final String PLACEHOLDER_CLUB = "Isi nama club";

    // === KOMPONEN UI ===
    JLabel labelWaktu;
    JLabel labelNilai;
    JTextField inputNamaClub;
    JButton tombolMulai;
    JButton tombolReset;
    JButton tombolSelesai;
    JComboBox<String> pilihJarak;
    JTextArea areaPutaran;
    JTextArea areaHistory;

    // === VARIABEL TIMER ===
    Timer timer;
    int  waktuBerjalan = 0;
    long waktuMulai    = -1;

    public Main() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {}
        setIconImage(new ImageIcon(".unknown/unknown.jpg").getImage());

        setTitle("Stopwatch Lari Estafet");
        setSize(1150, 640);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);

        // === PANEL JUDUL ===
        JPanel panelJudul = new JPanel(new BorderLayout());
        panelJudul.setBackground(COLOR_NAVY);
        panelJudul.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        JLabel labelJudul = new JLabel("STOPWATCH LARI ESTAFET");
        labelJudul.setFont(new Font("SansSerif", Font.BOLD, 22));
        labelJudul.setHorizontalAlignment(SwingConstants.CENTER);
        labelJudul.setForeground(COLOR_WHITE);
        panelJudul.add(labelJudul, BorderLayout.CENTER);
        add(panelJudul, BorderLayout.NORTH);

        // === PANEL UTAMA: 3 KOLOM ===
        JPanel panelUtama = new JPanel(new GridLayout(1, 3));
        panelUtama.setBackground(COLOR_BG);
        panelUtama.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // --- PANEL KIRI: WAKTU, INPUT, TOMBOL ---
        JPanel panelKiri = new JPanel();
        panelKiri.setLayout(new BoxLayout(panelKiri, BoxLayout.Y_AXIS));
        panelKiri.setBackground(COLOR_WHITE);
        panelKiri.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_BORDER),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        // Tampilan waktu & nilai
        JPanel panelWaktuNilai = new JPanel(new GridLayout(2, 1));
        panelWaktuNilai.setBackground(COLOR_NAVY);
        panelWaktuNilai.setBorder(BorderFactory.createEmptyBorder(22, 15, 22, 15));
        panelWaktuNilai.setMaximumSize(new Dimension(Integer.MAX_VALUE, 145));
        labelWaktu = new JLabel("00.00.000");
        labelWaktu.setFont(new Font("Monospaced", Font.BOLD, 46));
        labelWaktu.setHorizontalAlignment(SwingConstants.CENTER);
        labelWaktu.setForeground(COLOR_WHITE);
        labelNilai = new JLabel("NILAI : -");
        labelNilai.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelNilai.setHorizontalAlignment(SwingConstants.CENTER);
        labelNilai.setForeground(new Color(0x94, 0x94, 0xC0));
        panelWaktuNilai.add(labelWaktu);
        panelWaktuNilai.add(labelNilai);

        // Input nama club & jarak
        JPanel panelInput = new JPanel();
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setBackground(COLOR_WHITE);
        panelInput.setBorder(BorderFactory.createEmptyBorder(18, 16, 10, 16));

        JLabel lblNamaClub = new JLabel("NAMA CLUB");
        lblNamaClub.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblNamaClub.setForeground(COLOR_TEXT_MED);
        lblNamaClub.setAlignmentX(LEFT_ALIGNMENT);

        inputNamaClub = new JTextField(PLACEHOLDER_CLUB);
        inputNamaClub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        inputNamaClub.setBackground(COLOR_WHITE);
        inputNamaClub.setForeground(COLOR_TEXT_LIGHT);
        inputNamaClub.setCaretColor(COLOR_TEXT_DARK);
        inputNamaClub.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_BORDER),
            BorderFactory.createEmptyBorder(6, 4, 6, 4)
        ));
        inputNamaClub.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        inputNamaClub.setAlignmentX(LEFT_ALIGNMENT);

        // Placeholder behavior + Easter Egg
        inputNamaClub.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {
                if (inputNamaClub.getText().equals(PLACEHOLDER_CLUB)) {
                    inputNamaClub.setText("");
                    inputNamaClub.setForeground(COLOR_TEXT_DARK);
                    inputNamaClub.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_BLUE),
                        BorderFactory.createEmptyBorder(6, 4, 6, 4)
                    ));
                }
            }
            @Override public void focusLost(FocusEvent e) {
                String teks = inputNamaClub.getText().trim();
                String lower = teks.toLowerCase();

                // === EASTER EGG ===
                if (lower.equals("anomali") || lower.equals("nyawit") || lower.equals("akmal")) {
                    ImageIcon icon = new ImageIcon(".unknown/unknown.jpg");

                    // Cek: kalau foto gak ketemu, skip easter egg biar program tetep jalan
                    if (icon.getIconWidth() != -1 && icon.getIconHeight() != -1) {
                        int imgLebar = icon.getIconWidth();
                        int imgTinggi = icon.getIconHeight();

                        double skala = Math.min(600.0 / imgLebar, 600.0 / imgTinggi);
                        int baruLebar = (int)(imgLebar * skala);
                        int baruTinggi = (int)(imgTinggi * skala);

                        Image img = icon.getImage().getScaledInstance(baruLebar, baruTinggi, Image.SCALE_SMOOTH);
                        JLabel foto = new JLabel(new ImageIcon(img));

                        JDialog popup = new JDialog(Main.this, true);
                        popup.setUndecorated(true);
                        popup.setSize(baruLebar + 40, baruTinggi + 80);
                        popup.setLocationRelativeTo(Main.this);

                        JPanel panelHitam = new JPanel(new BorderLayout());
                        panelHitam.setBackground(new Color(0, 0, 0, 220));
                        foto.setHorizontalAlignment(SwingConstants.CENTER);
                        panelHitam.add(foto, BorderLayout.CENTER);

                        JLabel tutup = new JLabel("KLIK UNTUK TUTUP", SwingConstants.CENTER);
                        tutup.setFont(new Font("SansSerif", Font.BOLD, 14));
                        tutup.setForeground(Color.WHITE);
                        panelHitam.add(tutup, BorderLayout.SOUTH);
                        popup.add(panelHitam);

                        popup.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                                popup.dispose();
                            }
                        });

                        popup.setVisible(true);
                    }
                }

                if (teks.isEmpty()) {
                    inputNamaClub.setText(PLACEHOLDER_CLUB);
                    inputNamaClub.setForeground(COLOR_TEXT_LIGHT);
                }
                inputNamaClub.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_BORDER),
                    BorderFactory.createEmptyBorder(6, 4, 6, 4)
                ));
            }
        });

        JLabel lblJarak = new JLabel("JARAK ESTAFET");
        lblJarak.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblJarak.setForeground(COLOR_TEXT_MED);
        lblJarak.setAlignmentX(LEFT_ALIGNMENT);

        String[] dataJarak = {"4x100", "4x200", "4x400"};
        pilihJarak = new JComboBox<>(dataJarak);
        pilihJarak.setFont(new Font("SansSerif", Font.PLAIN, 13));
        pilihJarak.setBackground(COLOR_WHITE);
        pilihJarak.setForeground(COLOR_TEXT_DARK);
        pilihJarak.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        pilihJarak.setAlignmentX(LEFT_ALIGNMENT);
        pilihJarak.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));

        panelInput.add(lblNamaClub);
        panelInput.add(Box.createVerticalStrut(4));
        panelInput.add(inputNamaClub);
        panelInput.add(Box.createVerticalStrut(14));
        panelInput.add(lblJarak);
        panelInput.add(Box.createVerticalStrut(4));
        panelInput.add(pilihJarak);

        // Panel tombol
        JPanel panelTombol = new JPanel();
        panelTombol.setLayout(new BoxLayout(panelTombol, BoxLayout.Y_AXIS));
        panelTombol.setBackground(COLOR_WHITE);
        panelTombol.setBorder(BorderFactory.createEmptyBorder(14, 16, 16, 16));

        tombolMulai   = buatTombol("MULAI",   COLOR_GREEN, COLOR_GREEN_HOVER, COLOR_WHITE, 14);
        tombolSelesai = buatTombol("SELESAI", COLOR_GREEN, COLOR_GREEN_HOVER, COLOR_WHITE, 13);
        tombolReset   = buatTombol("RESET",   COLOR_RED,   COLOR_RED_HOVER,   COLOR_WHITE, 13);

        tombolReset.setVisible(false);
        tombolSelesai.setVisible(false);

        panelTombol.add(tombolMulai);
        panelTombol.add(Box.createVerticalStrut(8));
        panelTombol.add(tombolSelesai);
        panelTombol.add(Box.createVerticalStrut(8));
        panelTombol.add(tombolReset);

        panelKiri.add(panelWaktuNilai);
        panelKiri.add(panelInput);
        panelKiri.add(panelTombol);
        panelKiri.add(Box.createVerticalGlue());

        // --- PANEL TENGAH: CATATAN HASIL ---
        JPanel panelTengah = new JPanel(new BorderLayout());
        panelTengah.setBackground(COLOR_WHITE);
        panelTengah.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_BORDER));

        JPanel headerPutaran = new JPanel(new GridLayout(1, 2));
        headerPutaran.setBackground(COLOR_NAVY);
        headerPutaran.add(buatHeaderLabel("CATATAN"));
        headerPutaran.add(buatHeaderLabel("WAKTU"));
        panelTengah.add(headerPutaran, BorderLayout.NORTH);

        areaPutaran = new JTextArea();
        areaPutaran.setEditable(false);
        areaPutaran.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaPutaran.setBackground(COLOR_WHITE);
        areaPutaran.setForeground(COLOR_TEXT_DARK);
        areaPutaran.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        areaPutaran.setLineWrap(false);
        JScrollPane scrollPutaran = new JScrollPane(areaPutaran);
        scrollPutaran.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDER));
        scrollPutaran.getViewport().setBackground(COLOR_WHITE);
        panelTengah.add(scrollPutaran, BorderLayout.CENTER);

        // --- PANEL KANAN: HISTORY ---
        JPanel panelKanan = new JPanel(new BorderLayout());
        panelKanan.setBackground(COLOR_WHITE);
        panelKanan.add(buatHeaderLabel("HISTORY"), BorderLayout.NORTH);

        areaHistory = new JTextArea();
        areaHistory.setEditable(false);
        areaHistory.setFont(new Font("SansSerif", Font.PLAIN, 12));
        areaHistory.setBackground(COLOR_BG);
        areaHistory.setForeground(COLOR_TEXT_MED);
        areaHistory.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        areaHistory.setLineWrap(true);
        areaHistory.setWrapStyleWord(true);
        JScrollPane scrollHistory = new JScrollPane(areaHistory);
        scrollHistory.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDER));
        scrollHistory.getViewport().setBackground(COLOR_BG);
        panelKanan.add(scrollHistory, BorderLayout.CENTER);

        panelUtama.add(panelKiri);
        panelUtama.add(panelTengah);
        panelUtama.add(panelKanan);
        add(panelUtama, BorderLayout.CENTER);

        // === TIMER: update tiap 10ms ===
        timer = new Timer(10, e -> {
            waktuBerjalan = (int)(System.currentTimeMillis() - waktuMulai);
            labelWaktu.setText(formatWaktu(waktuBerjalan));
        });

        // === ACTION LISTENERS ===

        // MULAI: validasi & jalankan timer
        tombolMulai.addActionListener(e -> {
            String namaClub = inputNamaClub.getText().trim();
            if (namaClub.isEmpty() || namaClub.equals(PLACEHOLDER_CLUB)) {
                JOptionPane.showMessageDialog(null,
                    "Nama club wajib diisi sebelum memulai!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            waktuMulai = System.currentTimeMillis();
            timer.start();
            tombolMulai.setVisible(false);
            tombolReset.setVisible(true);
            tombolSelesai.setVisible(true);
            inputNamaClub.setEnabled(false);
            pilihJarak.setEnabled(false);
            labelNilai.setText("NILAI : -");
            labelNilai.setForeground(new Color(0x94, 0x94, 0xC0));
            areaPutaran.setText("");
        });

        // SELESAI: stop timer, hitung nilai, simpan history
        tombolSelesai.addActionListener(e -> {
            timer.stop();
            areaPutaran.setText("");
            areaPutaran.append("  SELESAI        " + formatWaktu(waktuBerjalan) + "\n");

            hitungNilai();

            String namaClub   = inputNamaClub.getText().trim();
            String jarak      = (String) pilihJarak.getSelectedItem();
            String nilaiAkhir = labelNilai.getText().replace("NILAI : ", "");

            if (!areaHistory.getText().isEmpty()) {
                areaHistory.append("----------------------------\n");
            }
            areaHistory.append(namaClub + "\n" + jarak
                + "  |  NILAI " + nilaiAkhir
                + "  |  " + formatWaktu(waktuBerjalan) + "\n");

            tombolSelesai.setVisible(false);
        });

        // RESET: kembalikan ke kondisi awal
        tombolReset.addActionListener(e -> {
            timer.stop();
            waktuBerjalan = 0;
            waktuMulai    = -1;
            labelWaktu.setText("00.00.000");
            labelNilai.setText("NILAI : -");
            labelNilai.setForeground(new Color(0x94, 0x94, 0xC0));
            areaPutaran.setText("");
            tombolReset.setVisible(false);
            tombolSelesai.setVisible(false);
            tombolMulai.setVisible(true);
            inputNamaClub.setEnabled(true);
            pilihJarak.setEnabled(true);
            inputNamaClub.setText(PLACEHOLDER_CLUB);
            inputNamaClub.setForeground(COLOR_TEXT_LIGHT);
            inputNamaClub.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_BORDER),
                BorderFactory.createEmptyBorder(6, 4, 6, 4)
            ));
        });

        setVisible(true);
    }

    // Buat label header navy
    JLabel buatHeaderLabel(String teks) {
        JLabel lbl = new JLabel(teks);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setBackground(COLOR_NAVY);
        lbl.setForeground(COLOR_WHITE);
        lbl.setOpaque(true);
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        return lbl;
    }

    // Buat tombol dengan efek hover
    JButton buatTombol(String teks, Color bgNormal, Color bgHover, Color fgWarna, int ukuranFont) {
        JButton tombol = new JButton(teks);
        tombol.setFont(new Font("SansSerif", Font.BOLD, ukuranFont));
        tombol.setBackground(bgNormal);
        tombol.setForeground(fgWarna);
        tombol.setOpaque(true);
        tombol.setBorderPainted(false);
        tombol.setFocusPainted(false);
        tombol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tombol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        tombol.setAlignmentX(LEFT_ALIGNMENT);
        tombol.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (tombol.isEnabled()) tombol.setBackground(bgHover);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) {
                tombol.setBackground(bgNormal);
            }
        });
        return tombol;
    }

    // === LOGIKA PENILAIAN ===
    void hitungNilai() {
        String jarak = (String) pilihJarak.getSelectedItem();
        int    total = waktuBerjalan;
        String nilai = "D";
        Color  warnaHuruf = COLOR_RED;

        if (jarak.equals("4x100")) {
            if      (total >= 44000 && total <= 46999) { nilai = "A";  warnaHuruf = COLOR_GREEN; }
            else if (total >= 47000 && total <= 49999) { nilai = "B";  warnaHuruf = COLOR_BLUE; }
            else if (total >= 50000 && total <= 53999) { nilai = "C";  warnaHuruf = new Color(0xF5, 0x9E, 0x0B); }
            else if (total >= 54000 && total <= 59000) { nilai = "D";  warnaHuruf = COLOR_RED; }
            else if (total < 44000)                    { nilai = "A+"; warnaHuruf = new Color(0x10, 0xB9, 0x81); }
        } else if (jarak.equals("4x200")) {
            if      (total >= 92000  && total <= 97999)  { nilai = "A";  warnaHuruf = COLOR_GREEN; }
            else if (total >= 98000  && total <= 104999) { nilai = "B";  warnaHuruf = COLOR_BLUE; }
            else if (total >= 105000 && total <= 112999) { nilai = "C";  warnaHuruf = new Color(0xF5, 0x9E, 0x0B); }
            else if (total >= 113000 && total <= 125000) { nilai = "D";  warnaHuruf = COLOR_RED; }
            else if (total < 92000)                      { nilai = "A+"; warnaHuruf = new Color(0x10, 0xB9, 0x81); }
        } else {
            if      (total >= 208000 && total <= 219999) { nilai = "A";  warnaHuruf = COLOR_GREEN; }
            else if (total >= 220000 && total <= 234999) { nilai = "B";  warnaHuruf = COLOR_BLUE; }
            else if (total >= 235000 && total <= 255999) { nilai = "C";  warnaHuruf = new Color(0xF5, 0x9E, 0x0B); }
            else if (total >= 256000 && total <= 280000) { nilai = "D";  warnaHuruf = COLOR_RED; }
            else if (total < 208000)                     { nilai = "A+"; warnaHuruf = new Color(0x10, 0xB9, 0x81); }
        }

        labelNilai.setText("NILAI : " + nilai);
        labelNilai.setForeground(warnaHuruf);
        labelNilai.revalidate();
        labelNilai.repaint();
    }

    // Format milidetik ke mm.ss.mmm
    String formatWaktu(int ms) {
        int menit     = ms / 60000;
        int detik     = (ms / 1000) % 60;
        int miliDetik = ms % 1000;
        return String.format("%02d.%02d.%03d", menit, detik, miliDetik);
    }

    public static void main(String[] args) {
        new Main();
    }
}
