package br.com.jorgevmachado.marketSpringApi.services;

import br.com.jorgevmachado.marketSpringApi.domain.*;
import br.com.jorgevmachado.marketSpringApi.domain.enumerations.EstadoPagamento;
import br.com.jorgevmachado.marketSpringApi.domain.enumerations.Perfil;
import br.com.jorgevmachado.marketSpringApi.domain.enumerations.TipoCliente;
import br.com.jorgevmachado.marketSpringApi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
//
	public void instantiateTestDatabase() throws ParseException {

        Categoria categoria1 = new Categoria(null, "Informática");
        Categoria categoria2 = new Categoria(null, "Escritório");
        Categoria categoria3 = new Categoria(null, "Cama mesa e banho");
        Categoria categoria4 = new Categoria(null, "Eletrônicos");
        Categoria categoria5 = new Categoria(null, "Jardinagem");
        Categoria categoria6 = new Categoria(null, "Decoração");
        Categoria categoria7 = new Categoria(null, "Perfumaria");

        Produto produto1 = new Produto(null, "Computador", 2000.00);
        Produto produto2 = new Produto(null, "Impressora", 800.00);
        Produto produto3 = new Produto(null, "Mouse", 80.00);
        Produto produto4 = new Produto(null, "Mesa de escritório", 300.00);
        Produto produto5 = new Produto(null, "Toalha", 50.00);
        Produto produto6 = new Produto(null, "Colcha", 200.00);
        Produto produto7 = new Produto(null, "TV true color", 1200.00);
        Produto produto8 = new Produto(null, "Roçadeira", 800.00);
        Produto produto9 = new Produto(null, "Abajour", 100.00);
        Produto produto10 = new Produto(null, "Pendente", 180.00);
        Produto produto11 = new Produto(null, "Shampoo", 90.00);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Collections.singletonList(produto2));
        categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
        categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
        categoria4.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3, produto7));
        categoria5.getProdutos().addAll(Collections.singletonList(produto8));
        categoria6.getProdutos().addAll(Arrays.asList(produto9, produto10));
        categoria7.getProdutos().addAll(Collections.singletonList(produto11));

        produto1.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2, categoria4));
        produto3.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
        produto4.getCategorias().addAll(Collections.singletonList(categoria2));
        produto5.getCategorias().addAll(Collections.singletonList(categoria3));
        produto6.getCategorias().addAll(Collections.singletonList(categoria3));
        produto7.getCategorias().addAll(Collections.singletonList(categoria4));
        produto8.getCategorias().addAll(Collections.singletonList(categoria5));
        produto9.getCategorias().addAll(Collections.singletonList(categoria6));
        produto10.getCategorias().addAll(Collections.singletonList(categoria6));
        produto11.getCategorias().addAll(Collections.singletonList(categoria7));

        Estado estado1 = new Estado(null, "Minas Gerais");
        Estado estado2 = new Estado(null, "São Paulo");

        Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
        Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
        Cidade cidade3 = new Cidade(null, "Campinas", estado2);

        estado1.getCidades().addAll(Collections.singletonList(cidade1));
        estado2.getCidades().addAll(Arrays.asList(cidade1, cidade2));

        Cliente cliente1 = new Cliente (
                null,
                "Maria Silva",
                "jorge.vmachado@hotmail.com",
                "36378912377",
                TipoCliente.PESSSOAFISICA,
                passwordEncoder.encode("123")
        );
        cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Cliente cliente2 = new Cliente (
                null,
                "Ana Costa",
                "jorge.vmachado@gmail.com",
                "19277229004",
                TipoCliente.PESSSOAFISICA,
                passwordEncoder.encode("123")
        );
        cliente2.getTelefones().addAll(Arrays.asList("37363323", "10838393"));
        cliente2.addPerfil(Perfil.ADMIN);

        Endereco endereco1 = new Endereco(
                null,
                "Rua Flores",
                "300",
                "Apto 303",
                "Jardin",
                "38220834",
                cliente1,
                cidade1
        );
        Endereco endereco2 = new Endereco(
                null,
                "Avenida Matos",
                "105",
                "Sala 800",
                "Centro",
                "38777012",
                cliente1,
                cidade2
        );
        Endereco endereco3 = new Endereco(
                null,
                "Avenida Floriano",
                "2106",
                null,
                "Centro",
                "48777012",
                cliente2,
                cidade2
        );

        cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
        cliente2.getEnderecos().addAll(Collections.singletonList(endereco3));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:321"), cliente1, endereco1 );
        Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);

        Pagamento pagamento1 = new PagamentoComCartao(
                null,
                EstadoPagamento.QUITADO,
                pedido1,
                6
        );
        pedido1.setPagamento(pagamento1);
        Pagamento pagamento2 = new PagamentoComBoleto(
                null,
                EstadoPagamento.PENDENTE,
                pedido2,
                sdf.parse("20/10/2017 00:00"),
                null
        );
        pedido2.setPagamento(pagamento2);
        cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

        ItemPedido itemPedido1 = new ItemPedido(
                pedido1,
                produto1,
                0.00,
                1 ,
                2000.00
        );
        ItemPedido itemPedido2 = new ItemPedido(
                pedido1,
                produto3,
                0.00,
                2,
                80.00
        );
        ItemPedido itemPedido3 = new ItemPedido(
                pedido2,
                produto2,
                100.00,
                1,
                800.00
        );
        pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
        pedido2.getItens().addAll(Collections.singletonList(itemPedido3));

        produto1.getItens().addAll(Collections.singletonList(itemPedido1));
        produto2.getItens().addAll(Collections.singletonList(itemPedido3));
        produto3.getItens().addAll(Collections.singletonList(itemPedido2));

        categoriaRepository.saveAll(Arrays.asList(
                categoria1,
                categoria2,
                categoria3,
                categoria4,
                categoria5,
                categoria6,
                categoria7
        ));
        produtoRepository.saveAll(Arrays.asList(
                produto1,
                produto2,
                produto3,
                produto4,
                produto5,
                produto6,
                produto7,
                produto8,
                produto9,
                produto10,
                produto11
        ));
        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));
        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
        itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
    }

}
