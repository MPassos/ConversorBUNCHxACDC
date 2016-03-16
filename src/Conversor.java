/*
 * Decompiled with CFR 0_114.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conversor {
    private File arquivo;
    private FileReader leitor;
    private FileWriter escritor;
    private BufferedReader lbuffer;
    private BufferedWriter ebuffer;
    private String nomearquivo;

    public Conversor(File arquivo, String nome) throws IOException {
        this.arquivo = arquivo;
        this.nomearquivo = nome;
        String arquivonovo = this.arquivo.getName().substring(0, 6) + this.nomearquivo;
        try {
            this.leitor = new FileReader(this.arquivo);
            this.escritor = new FileWriter(arquivonovo);
            this.lbuffer = new BufferedReader(this.leitor);
            this.ebuffer = new BufferedWriter(this.escritor);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LerArquivo() throws IOException {
        String arquivonovo = this.arquivo.getName().substring(0, 6) + this.nomearquivo;
        this.leitor = new FileReader(arquivonovo);
        this.lbuffer = new BufferedReader(this.leitor);
        while (this.lbuffer.ready()) {
            System.out.println(this.lbuffer.readLine());
        }
        this.lbuffer.close();
        this.leitor.close();
    }

    public void converterA() throws IOException {
        String convertida = "";
        while (this.lbuffer.ready()) {
            convertida = convertida + "depends ";
            convertida = convertida + this.lbuffer.readLine();
            convertida = convertida + "\n";
            this.ebuffer.write(convertida);
            convertida = "";
        }
        this.ebuffer.close();
        this.escritor.close();
        this.lbuffer.close();
        this.leitor.close();
    }

    public void converterB() throws IOException {
        String convertida = "";
        Vector<String> elementos = new Vector<String>(10, 1);
        String cluster = "";
        while (this.lbuffer.ready()) {
            int ch = this.lbuffer.read();
            if (ch == 32 || ch == 40 || ch == 41) continue;
            if (ch == 61) {
                cluster = convertida;
                convertida = "";
                continue;
            }
            if (ch == 44) {
                elementos.add(convertida);
                convertida = "";
                continue;
            }
            if (ch == 10 || ch == 13) {
                elementos.add(convertida);
                convertida = "";
                for (int i = 0; i < elementos.size(); ++i) {
                    convertida = convertida + "contain ";
                    convertida = convertida + cluster;
                    convertida = convertida + " " + elementos.get(i);
                    convertida = convertida + "\n";
                    this.ebuffer.write(convertida);
                    convertida = "";
                }
                cluster = "";
                elementos.removeAllElements();
                continue;
            }
            char c = (char)ch;
            if (!(convertida = convertida + Character.toString(c)).equals("SS")) continue;
            convertida = "";
        }
        this.ebuffer.close();
        this.escritor.close();
        this.lbuffer.close();
        this.leitor.close();
    }

    public void converterC() throws IOException {
        String aux = "";
        String dep = "";
        Vector<String> v = new Vector<String>(10, 1);
        int c = 2;
        while (this.lbuffer.ready()) {
            char ch;
            while (c != 35) {
                c = this.lbuffer.read();
                if (c >= 48 && c <= 57 || c == 32) continue;
                if (c == 10) {
                    v.add(aux);
                    aux = "";
                    continue;
                }
                if (c == 13) continue;
                ch = (char)c;
                aux = aux + Character.toString(ch);
            }
            aux = "";
            this.lbuffer.readLine();
            while (c != -1) {
                c = this.lbuffer.read();
                if (c == 32) {
                    aux = aux + v.get(Integer.parseInt(dep) - 1);
                    aux = aux + " ";
                    dep = "";
                    continue;
                }
                if (c == 10) {
                    aux = aux + dep;
                    aux = aux + "\n";
                    this.ebuffer.write(aux);
                    dep = "";
                    aux = "";
                    continue;
                }
                if (c == 13) continue;
                ch = (char)c;
                dep = dep + Character.toString(ch);
            }
        }
        this.ebuffer.close();
        this.escritor.close();
        this.lbuffer.close();
        this.leitor.close();
    }
}
