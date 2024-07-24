package org.luiz.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Registro {
    private String data;
    private float cartao;
    private float dinheiro;
    private float pix;
    private float avista;
    private float aprazo;

    public String getData() {
        return this.data;
    }

    public void setData(String data) throws ParseException {
        this.data = data;
    }

    public float getCartao() {
        return cartao;
    }

    public void setCartao(float cartao) {
        this.cartao = cartao;
    }

    public float getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(float dinheiro) {
        this.dinheiro = dinheiro;
    }

    public float getPix() {
        return pix;
    }

    public void setPix(float pix) {
        this.pix = pix;
    }

    public float getAvista() {
        return avista;
    }

    public void setAvista(float avista) {
        this.avista = avista;
    }

    public float getAprazo() {
        return aprazo;
    }

    public void setAprazo(float aprazo) {
        this.aprazo = aprazo;
    }

    @Override
    public String toString() {
        return "{data: "+data+", cartão: "+cartao+", dinheiro: "+dinheiro+", PIX: "+pix+" a vista: "+avista+" a prazo: "+aprazo+"}";
    }
}


