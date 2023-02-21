package com.apistarwars.ApiStarWars.utilidades;

import com.apistarwars.ApiStarWars.entidades.Films;
import com.google.gson.Gson;

public class JsonUtils {

	public static <T> T devolverObjetoGsonGenerico(String url, Class<T> clase) {
        return new Gson().fromJson(InternetUtils.readUrl(url),clase);
	}
}
