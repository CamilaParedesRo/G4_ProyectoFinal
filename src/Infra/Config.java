package Infra;

import java.net.URL;
import javax.swing.JOptionPane;
public abstract class Config {
    public static final URL URL_LOGO    = Config.class.getResource("/Resource/logo.png");
    public static final String DATABASE = "jdbc:sqlite:database//PoliAsistencia.sqlite";
    public static final String DATAFILE = "Data\\poliAsistencia.csv";
    public static final String LOGFILE  = "Data\\log.txt";

    public static final void showMsg(String msg){
        JOptionPane.showMessageDialog(null, msg, "PoliAsistencia", JOptionPane.INFORMATION_MESSAGE);
    }
    public static final void showMsgError(String msg){
        JOptionPane.showMessageDialog(null, msg, "PoliAsistencia", JOptionPane.OK_OPTION);
    }
    public static final boolean showConfirmYesNo(String msg){
        return (JOptionPane.showConfirmDialog(null, msg, "🐜 PoliAsistencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
    }
}
