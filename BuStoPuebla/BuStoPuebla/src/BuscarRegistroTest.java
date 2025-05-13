import static org.junit.Assert.*;
import java.awt.event.ActionEvent;

import org.junit.Before;
import org.junit.Test;
import javax.swing.*;

public class BuscarRegistroTest {
    private JTextField[] campos;
   

    @Test
    public void Correo_CampoVacio() {
        BuscarRegistro b= new BuscarRegistro(campos, null);
        assertEquals(true, b.camposVacios("", "12345678"));
    }
    @Test
    public void Contraseña_CampoVacio() {
        BuscarRegistro b= new BuscarRegistro(campos, null);
        assertEquals(true, b.camposVacios("fernanda@gmail.com", ""));
    }
    @Test
    public void ambos_CampoVacio() {
        BuscarRegistro b= new BuscarRegistro(campos, null);
        assertEquals(true, b.camposVacios("", ""));
    }
    @Test
    public void CamposCompletos() {
        BuscarRegistro b= new BuscarRegistro(campos, null);
        assertEquals(false, b.camposVacios("fernanda@gmail.com", "12345678"));
    }
    @Test
    public void encriptarSHA256() {
        BuscarRegistro b = new BuscarRegistro(campos, null);
        String esperado = "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f"; // SHA-256 de "12345678"
        String resultado = b.encriptarSHA256("12345678");
        assertEquals(esperado, resultado);
    }
    @Test
    public void encriptarSHA256_cuandoOcurreException() {
        BuscarRegistro b = new BuscarRegistro(campos, null);
        String entrada = null;
        String resultado = b.encriptarSHA256(entrada);
        assertNull("Cuando ocurre excepción, debe retornar la misma entrada", resultado);
    }
    @Test
    public void encriptarSHA256_conCadenaVacia() {
        BuscarRegistro b = new BuscarRegistro(campos, null);
        String entrada = "";
        String esperado =  "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

        String resultado = b.encriptarSHA256(entrada);
        assertEquals("El hash de cadena vacía debe ser el valor estándar",
                     esperado, resultado);
    }
    @Test
    public void encriptarSHA256_diferentesEntradasDanDiferenteHash() {
        BuscarRegistro b = new BuscarRegistro(campos, null);
        String hash1 = b.encriptarSHA256("uno");
        String hash2 = b.encriptarSHA256("dos");

        assertNotEquals("Entradas distintas no deben dar lo mismo", hash1, hash2);
    }
    @Test
    public void encriptarSHA256_mismoHashParaMismaEntrada() {
        BuscarRegistro b = new BuscarRegistro(campos, null);
        String hash1 = b.encriptarSHA256("entrada");
        String hash2 = b.encriptarSHA256("entrada");
        assertEquals("Misma entrada deben dar lo mismo", hash1, hash2);
    }
    @Test
    public void encriptarSHA256_longitudDe64() {
        BuscarRegistro b = new BuscarRegistro(campos, null);
        String hash = b.encriptarSHA256("abc123");
        assertEquals(64, hash.length());
    }


    }