package br.edu.utfpr.dto;

import lombok.Builder;
import lombok.Data;
import br.edu.utfpr.excecao.NomeClienteMenor5CaracteresException;

@Data
@Builder
public class ClienteDTO {

    public static ClienteDTO ClienteDTOBuilder;
    private int id;
    private String nome;
    private int idade;
    private String telefone;
    private double limiteCredito;
    private PaisDTO pais;

   

    public void setNome(String nome) throws NomeClienteMenor5CaracteresException {
        if (nome.length() < 5)
            throw new NomeClienteMenor5CaracteresException(nome);

        this.nome = nome;
    }
    public PaisDTO getPaisID(){
        return pais;
        
    }

    public void setEmail(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}