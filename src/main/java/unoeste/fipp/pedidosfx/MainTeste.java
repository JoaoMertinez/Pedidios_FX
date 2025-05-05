package unoeste.fipp.pedidosfx;

import unoeste.fipp.pedidosfx.db.dal.CategoriaDAL;
import unoeste.fipp.pedidosfx.db.dal.PedidoDAL;
import unoeste.fipp.pedidosfx.db.dal.ProdutoDAL;
import unoeste.fipp.pedidosfx.db.entidade.Categoria;
import unoeste.fipp.pedidosfx.db.entidade.Pedido;
import unoeste.fipp.pedidosfx.db.entidade.Produto;
import unoeste.fipp.pedidosfx.db.entidade.TipoPagamento;
import unoeste.fipp.pedidosfx.db.util.SingletonDB;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class MainTeste {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha o local para salvar o PDF");
        if(!SingletonDB.conectar())
        {
            System.out.println("Erro: "+SingletonDB.getConexao().getMensagemErro());
        }
//        System.out.println("Classe de Teste");
//        Categoria categoria=new Categoria("Cerveja");
//        CategoriaDAL dal=new CategoriaDAL();
//        if(dal.gravar(categoria))
//            System.out.println("Categoria armazenada");
//        else System.out.println("Erro: "+ SingletonDB.getConexao().getMensagemErro());
//
//        categoria=dal.get(1);
        ProdutoDAL produtoDAL=new ProdutoDAL();
//        //categoria.setNome(categoria.getNome().toUpperCase());
//        //dal.alterar(categoria);
//        //dal.apagar(categoria);
//        //List<Categoria> categoriaList=dal.get("");
//        //System.out.println(categoriaList);
//        Produto produto=new Produto("lokal","cerveja pilsen importada",1.8,categoria);
//        produtoDAL.gravar(produto);
//        produtoDAL.gravar(new Produto("Corote Pessego","bebida destilada de alta qualidade",4.25,categoria));
        List <Produto> estoque = produtoDAL.get("");
        for(Produto produto : estoque)
            System.out.println(produto.getNome()+" "+produto.getCategoria().getNome());
//        Pedido pedido=new Pedido(LocalDate.now(),"Rita","18999998888",0,
//                'N',new TipoPagamento(1,"pix"));
//        pedido.addItem(estoque.get(0),12);
//        estoque.get(1).setId(100);
//        pedido.addItem(estoque.get(1),1);
//        PedidoDAL pedidoDAL=new PedidoDAL();
//        if(!pedidoDAL.gravar(pedido))
//            System.out.println("Erro: "+SingletonDB.getConexao().getMensagemErro());
    }
}
