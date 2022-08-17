package com.meli.freshWarehouse.service;

import com.meli.freshWarehouse.model.*;
import org.springframework.stereotype.Component;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@Component
public class ExternalAPIHelper {

    public static Freight calculateFreight(Freight freight){
        Map<String, String> request = new HashMap<>();
        Map<String, String> response = new HashMap<>();
        Integer quantity = freight.getCartProducts().stream().mapToInt(ShoppingCartProduct::getQuantity).sum();

        initializeRequest(freight, request, quantity);

        //Atributos
        String nCdEmpresa = "";
        String sDsSenha = "";
        String nCdServico = request.get("servico");
        String sCepOrigem = request.get("cepOrigem");
        String sCepDestino = request.get("cepDestino");
        String nVlPeso = request.get("peso");
        String nCdFormato = request.get("formato"); //Pacote ou caixa - 1
        String nVlComprimento = request.get("comprimento");
        String nVlAltura = request.get("altura");
        String nVlLargura = request.get("largura");
        String nVlDiametro = request.get("diametro");
        String sCdMaoPropria = "n";
        String nVlValorDeclarado = "0";
        String sCdAvisoRecebimento = "n";
        String StrRetorno = "xml";

        //URL do webservice Correio
        String urlString = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx";

        //Parâmetros
        Properties parameters = new Properties();

        parameters.setProperty("nCdEmpresa", nCdEmpresa);
        parameters.setProperty("sDsSenha", sDsSenha);
        parameters.setProperty("nCdServico", nCdServico);
        parameters.setProperty("sCepOrigem", sCepOrigem);
        parameters.setProperty("sCepDestino", sCepDestino);
        parameters.setProperty("nVlPeso", nVlPeso);
        parameters.setProperty("nCdFormato", nCdFormato);
        parameters.setProperty("nVlComprimento", nVlComprimento);
        parameters.setProperty("nVlAltura", nVlAltura);
        parameters.setProperty("nVlLargura", nVlLargura);
        parameters.setProperty("nVlDiametro", nVlDiametro);
        parameters.setProperty("sCdMaoPropria", sCdMaoPropria);
        parameters.setProperty("nVlValorDeclarado", nVlValorDeclarado);
        parameters.setProperty("sCdAvisoRecebimento", sCdAvisoRecebimento);
        parameters.setProperty("StrRetorno", StrRetorno);

        //Iterador, para criar a URL
        Iterator i = parameters.keySet().iterator();

        //Contador
        int counter = 0;

        //Percorrer parâmetros
        while (i.hasNext()) {

            //Nome
            String name = (String) i.next();

            //Valor
            String value = parameters.getProperty(name);

            //Adicionar um conector (? ou &)
            urlString += (++counter == 1 ? "?" : "&") + name + "=" + value;

        }

        try {
            //Cria objeto URL
            URL url = new URL(urlString);

            //Cria objeto httpurlconnection
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();

            //Method set
            connection.setRequestProperty("Request-Method", "GET");

            //Prepara a variável para ler o resultado
            connection.setDoInput(true);
            connection.setDoOutput(false);

            //Conecta a url destino
            connection.connect();

            //Abre a conexão pra input
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            //Lê
            StringBuffer newData = new StringBuffer();
            String s = "";
            while (null != ((s = br.readLine()))) {
                newData.append(s);
            }
            br.close();

            //Prepara o XML que está em string para executar leitura por nodes
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(newData.toString()));
            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("cServico");

            //Faz a leitura dos nodes
            for (int j = 0; j < nodes.getLength(); j++) {
                Element element = (Element) nodes.item(j);

                NodeList valor = element.getElementsByTagName("Valor");
                NodeList prazoEntrega = element.getElementsByTagName("PrazoEntrega");

                Element line = (Element) valor.item(0);
                Element prazo = (Element) prazoEntrega.item(0);

                response.put("quantidadeDias", getCharacterDataFromElement(prazo));
                response.put("valor", getCharacterDataFromElement(line));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

        freight.setDeliveryDate(LocalDate.now().plusDays(Long.parseLong(response.get("quantidadeDias"))));
        freight.setTotal(Double.parseDouble(response.get("valor").replace(",", ".")));
        return freight;
    }

    private static void initializeRequest(Freight freight, Map<String, String> request, Integer quantity) {
        request.put("servico", String.valueOf(freight.getShippingCode()));
        request.put("cepOrigem", freight.getCartProducts().stream()
                        .findFirst()
                        .flatMap(cartProduct -> cartProduct.getProduct().getSections().stream().map(Section::getWarehouse)
                                .findFirst())
                        .map(Warehouse::getZipCode)
                        .stream()
                        .findFirst().orElse("07750-974").replace("-", ""));
        request.put("cepDestino", freight.getBuyer().getZipCode().replace("-", ""));
        request.put("peso",  String.valueOf(quantity > 5 ? quantity :  5));
        request.put("diametro", String.valueOf(1));
        request.put("largura", String.valueOf(quantity > 10 ? quantity : 10));
        request.put("altura", String.valueOf(1));
        request.put("comprimento", String.valueOf( quantity > 15 ? quantity : 15 ));
        request.put("formato", "1");
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
