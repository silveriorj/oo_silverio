package sample.controller;

import javafx.scene.control.TextField;
import sample.model.Cliente;
import sample.model.Telefone;

class manutencao_controller {

    static void detalhes_cliente(Cliente cliente, TextField tfID, TextField tfNome, TextField tfEmail, TextField tfFixo, TextField tfMovel, TextField tfRua, TextField tfNumero, TextField tfComplemento, TextField tfBairro, TextField tfCidade){
        tfID.setText(String.valueOf(cliente.getGarra()));
        tfNome.setText(cliente.getNome());
        tfEmail.setText(cliente.getEmail());

        int a = 0;
        if(cliente.getTelefone()!=null) {
            for (Telefone t : cliente.getTelefone()) {
                if (t != null && a == 0) {
                    tfFixo.setText(String.valueOf(t));
                } else if (t != null) {
                    tfMovel.setText(String.valueOf(t));
                }
                a++;
            }
        }

        if(cliente.getEndereco()!=null){
            if(cliente.getEndereco().getRua()!=null){
                tfRua.setText(cliente.getEndereco().getRua());
            }if(cliente.getEndereco().getNumero()!=null){
                tfNumero.setText(cliente.getEndereco().getNumero());
            }if(cliente.getEndereco().getComplemento()!=null){
                tfComplemento.setText(cliente.getEndereco().getComplemento());
            }if(cliente.getEndereco().getBairro()!=null){
                tfBairro.setText(cliente.getEndereco().getBairro());
            }if(cliente.getEndereco().getCidade()!=null){
                tfCidade.setText(cliente.getEndereco().getCidade());
            }
        }
    }
}
