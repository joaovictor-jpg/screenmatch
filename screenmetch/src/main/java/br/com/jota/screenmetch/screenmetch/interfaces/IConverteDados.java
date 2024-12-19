package br.com.jota.screenmetch.screenmetch.interfaces;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
