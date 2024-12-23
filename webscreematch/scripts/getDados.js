const baseURL = 'http://localhost:8082';

export default function getDados(endPoint) {
    return fetch(`${baseURL}${endPoint}`)
        .then(response => response.json())
        .catch(error => {
            console.error('Erro ao acessar o endpoint /series/top5:', error);
        })
}