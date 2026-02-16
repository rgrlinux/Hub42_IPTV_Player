package com.hub42.hub42_iptv_player.data
import com.hub42.hub42_iptv_player.model.Channel

class M3UParser {
    fun parse(m3uRawData: String, filterType: String): List<Channel> {
        val list = mutableListOf<Channel>()
        val lines = m3uRawData.lines()

        var currentName = ""
        var currentLogo = ""
        var currentCategory = ""

        for (line in lines) {
            val row = line.trim()

            if (row.startsWith("#EXTINF:")) {
                // Tenta pegar o nome após a última vírgula
                currentName = row.substringAfterLast(",").trim()
                // Tenta extrair logo e categoria
                currentLogo = extractAttribute(row, "tvg-logo")
                currentCategory = extractAttribute(row, "group-title")
            } else if (row.isNotEmpty() && !row.startsWith("#")) {
                // Se a linha é uma URL, criamos o objeto
                val url = row

                // LOG PARA DEBUG: Isso vai aparecer no seu Logcat
                println("DEBUG_PARSER: Nome encontrado: $currentName | Categoria: $currentCategory")

                // Se o filtro for "MOVIE", aceitamos categorias que tenham 'Filmes', 'Cinema' ou 'Movies'
                val isMovieCategory = currentCategory.contains("movie", ignoreCase = true) ||
                        currentCategory.contains("filme", ignoreCase = true) ||
                        currentCategory.contains("cinema", ignoreCase = true)

                // Se você quer que TUDO da lista apareça na busca (para testar), comente a linha abaixo e use 'true'
                if (filterType == "MOVIE" && isMovieCategory) {
                    list.add(Channel(currentName, url, currentLogo, currentCategory, "MOVIE"))
                } else if (filterType == "ALL") {
                    list.add(Channel(currentName, url, currentLogo, currentCategory, "LIVE"))
                }
            }
        }
        return list
    }

    private fun extractAttribute(line: String, attribute: String): String {
        val key = "$attribute=\""
        if (!line.contains(key)) return ""
        val start = line.indexOf(key) + key.length
        val end = line.indexOf("\"", start)
        return if (start != -1 && end != -1) line.substring(start, end) else ""
    }
}