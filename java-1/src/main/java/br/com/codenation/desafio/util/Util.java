package br.com.codenation.desafio.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Util {

	public static <T> List<T> head(List<T> list, Integer top) {
		List<T> subList = new ArrayList<>();
		
		Integer listSize = Optional.of(list).orElse(new ArrayList<>()).size();
		
		if(listSize >= top) {
			subList = list.subList(0, top);
		}
		
		return subList;
	}
}
