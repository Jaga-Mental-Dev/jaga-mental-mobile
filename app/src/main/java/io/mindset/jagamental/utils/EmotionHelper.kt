package io.mindset.jagamental.utils

import io.mindset.jagamental.R
import kotlin.random.Random

class EmotionHelper() {
    private val emotionWords = mapOf(
        "marah" to listOf(
            "Kesel banget, ya? Kadang semuanya terasa nggak adil. Yuk, coba tulis di jurnal biar lega.",
            "Hari ini rasanya semua bikin emosi? Cerita di sini aja, mungkin bisa bantu.",
            "Aku ngerti kok, kadang ada hari yang bikin darah naik. Coba tulis, siapa tahu lebih ringan.",
            "Pasti capek banget marah-marah terus. Yuk, coba cerita di jurnal biar plong.",
            "Kalau lagi marah, rasanya semuanya salah. Tapi yuk coba tulis dulu, biar bisa pelan-pelan tenang.",
            "Lagi kesal sama seseorang atau sesuatu? Cerita di sini, siapa tahu bikin lega.",
            "Kadang emosi memang nggak tertahan, tapi nulis bisa jadi pelampiasan yang baik. Yuk, coba deh.",
            "Hari ini bikin kamu jadi lebih sensitif? Yuk, tuangkan perasaan itu di jurnal.",
            "Kesal nggak enak ditahan terus. Tulis aja di jurnal, nggak ada yang akan menilai.",
            "Lagi pengen teriak karena marah? Kalau nggak bisa, coba tulis dulu, yuk!"
        ),
        "netral" to listOf(
            "Hari ini biasa aja, ya? Tapi tetap boleh cerita di jurnal, siapa tahu ada hal kecil yang spesial.",
            "Nggak ada yang spesial, tapi tetap ada yang bisa dibagi. Yuk, tulis sesuatu!",
            "Kadang hari biasa juga layak diabadikan. Apa yang kamu rasakan hari ini?",
            "Semua terasa datar? Mungkin dengan menulis, hari ini bisa lebih berarti.",
            "Apa yang terjadi hari ini mungkin terasa biasa, tapi yuk tulis aja untuk mengingatnya.",
            "Nggak ada yang terlalu menarik? Itu nggak apa-apa. Coba cerita sedikit aja.",
            "Hari biasa juga punya cerita. Apa yang kamu pikirkan sekarang?",
            "Meskipun tenang, pasti ada sesuatu yang bisa ditulis. Yuk, tuangkan di jurnal.",
            "Kadang kita nggak sadar hal kecil yang berarti. Tulis aja dulu, siapa tahu ada maknanya.",
            "Netral juga oke kok. Cerita aja apa yang ada di pikiran."
        ),
        "sedih" to listOf(
            "Aku tahu ini hari yang berat. Yuk, coba tuliskan semuanya di jurnal, biar lega.",
            "Kalau lagi sedih, nggak ada salahnya cerita. Mulai dari sini aja yuk.",
            "Rasanya nggak nyaman, ya? Kadang menulis bisa bikin hati lebih tenang. Yuk, coba deh.",
            "Aku ngerti kok, kadang ada hari yang bikin kamu merasa down. Tulis aja dulu.",
            "Sedih nggak apa-apa, kok. Semua orang pasti pernah merasakannya. Yuk, coba cerita di jurnal.",
            "Apa yang bikin kamu merasa sedih hari ini? Tuliskan, siapa tahu bikin lebih ringan.",
            "Saat hati terasa berat, menulis bisa membantu. Yuk, coba ceritakan di sini.",
            "Kesedihanmu itu valid. Jangan dipendam terus, coba tulis aja dulu.",
            "Hari ini sulit, ya? Tulis aja perasaanmu, nggak ada yang akan menghakimi.",
            "Kalau terlalu sedih untuk bicara, mungkin menulis bisa membantu. Yuk, mulai dari sini."
        ),
        "senang" to listOf(
            "Hari ini seru banget, ya? Yuk, abadikan di jurnal biar selalu diingat.",
            "Rasanya menyenangkan banget! Coba cerita di jurnal biar makin terasa nyata.",
            "Momen bahagia ini layak diingat selamanya. Yuk, tuliskan sekarang juga!",
            "Senangnya hari ini bikin senyum terus. Yuk, coba cerita di jurnal.",
            "Pasti ada hal kecil yang bikin bahagia hari ini. Tulis aja, yuk.",
            "Hari ini kamu kelihatan bersinar! Jangan lupa ceritakan kebahagiaanmu di jurnal.",
            "Bahagia itu harus dirayakan, walau cuma dengan menulis di jurnal. Yuk, coba sekarang.",
            "Apa yang bikin kamu bahagia hari ini? Cerita aja di jurnal, biar makin terasa indahnya.",
            "Kebahagiaanmu itu berharga. Abadikan di jurnal biar bisa dikenang kapan saja.",
            "Rasanya bahagia banget? Yuk, luangkan waktu sebentar untuk menuliskannya."
        )
    )

    fun getWordsByEmotion(emotion: String): String {
        return emotionWords[emotion]?.let {
            it[Random.nextInt(it.size)]
        } ?: "Emosi tidak ditemukan. Coba pilih emosi lain."
    }

    fun getEmotionIcon(emotion: String): Int {
        return when (emotion.lowercase()) {
            "marah" -> R.drawable.emotion_marah
            "netral" -> R.drawable.emotion_netral
            "sedih" -> R.drawable.emotion_sedih
            "senang" -> R.drawable.emotion_senang
            else -> 0
        }
    }
}
