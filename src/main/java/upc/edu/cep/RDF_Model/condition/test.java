package upc.edu.cep.RDF_Model.condition;

import upc.edu.cep.RDF_Model.Operators.ComparasionOperatorEnum;

import java.util.LinkedList;

/**
 * Created by osboxes on 25/05/17.
 */
public class test {
    public static void main(String[] args) throws Exception {
        Operand test = new GroupBy(null, null, null);
        System.out.println(ComparasionOperatorEnum.EQ.toString());
        LinkedList<String> aaa = new LinkedList<>();
        aaa.add("aaaa");
        aaa.add("abbb");
        aaa.add("accc");
        aaa.add("addd");
        aaa.add("aeee");
        System.out.println(aaa.pollFirst());
        System.out.println(aaa.pollFirst());
        System.out.println(aaa.pollFirst());
        System.out.println(aaa.pollFirst());
        System.out.println(aaa.pollFirst());
        System.out.println(aaa.pollFirst());
    }
}
