package GUI;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class SplashScreen {
    private JWindow window;

    public SplashScreen() {
        window = new JWindow();

        // Cargar imagen del splash correctamente
        ImageIcon splashImage = new ImageIcon(getClass().getResource("LogoEPN.jpg"));

        JLabel label = new JLabel(splashImage);
        window.getContentPane().add(label, BorderLayout.CENTER);

        // Ajustar tamaño y centrar
        window.pack();
        window.setLocationRelativeTo(null);
    }

    public void mostrar() {
        window.setVisible(true);

        try {
            Thread.sleep(3000);  // Mantener splash por 3 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        window.setVisible(false);
        window.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SplashScreen splash = new SplashScreen();
                splash.mostrar();
            }
        });
    }
}