package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.model.Cliente;
import sample.model.Endereco;
import sample.model.JDBCClienteDAO;
import sample.model.Telefone;

import java.text.ParseException;
import java.util.ArrayList;

public class cadastro_clientes {

        @FXML
        private TextField tfNome, tfEmail, tfFone, tfCel;

        @FXML
        private TextField tfRua, tfBairro,tfCidade, tfNum, tfComplemento;
        @FXML
        private Label lbGarra;

        public void initialize() throws ParseException {
            MaskFormatter fixo = new MaskFormatter(tfFone);
            fixo.setMask(0);
            MaskFormatter movel = new MaskFormatter(tfCel);
            movel.setMask(1);
            int idnovo = 10000;

            for(Cliente c : JDBCClienteDAO.getInstance().list()){
                idnovo = c.getGarra()+1;
            }
            this.lbGarra.setText("ID: "+idnovo);
        }
        void processResult() {
            int i=0;
            //CLIENTE
            String nome;
            String email;
            String fone1, fone2;

            int garra;
            //ENDEREÇO
            String rua, numero, balneario, cidade, complemento;

            fone1 = tfFone.getText();
            fone2 = tfCel.getText();

            nome = tfNome.getText();
            email = tfEmail.getText();
            rua = tfRua.getText();
            balneario = tfBairro.getText();
            cidade = tfCidade.getText();
            numero = tfNum.getText();
            complemento = tfComplemento.getText();

            try{
                Endereco e = new Endereco(rua, balneario, cidade, complemento, numero);
                Cliente c = null;

                if(nome != null && email != null){
                    c = new Cliente(nome, email, e);
                }else{
                    Alerta.user_not_filled();
                }
                ArrayList<Telefone> telefones = new ArrayList<>();

                if(fone1 != null) {
                    Telefone fixo = new Telefone(fone1);
                    telefones.add(fixo);
                    c.getTelefone().addAll(telefones);
                }
                if(fone2 != null) {
                    Telefone movel = new Telefone(fone2);
                    telefones.add(movel);
                    c.getTelefone().addAll(telefones);
                }

                JDBCClienteDAO.getInstance().create(c, e);

            }catch (Exception d){
                System.out.println("??");
            }
        }

}
