package br.com.ambientinformatica.senai.universitario.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Conversor<E> {

    public List<E> coverterParaArrayList(Iterator<E> itParametro) {
        ArrayList<E> lstRetorno = new ArrayList<E>();
        while (itParametro.hasNext()) {
            lstRetorno.add(itParametro.next());
        }
        return lstRetorno;
    }

    public Set<E> converterParaSet(List<E> lst) {
        Set<E> set = new HashSet<E>();
        Iterator<E> it = lst.iterator();
        while (it.hasNext()) {
            set.add(it.next());
        }
        return set;
    }
}
