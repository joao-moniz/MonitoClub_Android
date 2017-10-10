package com.example.joao_.monitoria.Modelo;

/**
 * Created by joao- on 02/06/2017.
 */

public class ModeloBanco {

    private String DescricaLinha;
    private String PalavraChave;

    public ModeloBanco() {
    }

    public String getDescricaLinha() {

        return DescricaLinha;
    }

    public void setDescricaLinha(String descricaLinha) {
        DescricaLinha = descricaLinha;
    }

    public String getPalavraChave() {
        return PalavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        PalavraChave = palavraChave;
    }

    public ModeloBanco(String descricaLinha, String palavraChave) {

        DescricaLinha = descricaLinha;
        PalavraChave = palavraChave;
    }
}
