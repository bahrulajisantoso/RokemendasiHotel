package com.example.aplikasiskripsi

import com.example.aplikasiskripsi.response.ResponsePostItem

object DataDummy {
    fun generateDummyNewsEntity(): List<ResponsePostItem> {
        val newsList = ArrayList<ResponsePostItem>()
        for (i in 0..10) {
            val news = ResponsePostItem(
                imageSrc = "https://ik.imagekit.io/tvlk/apr-asset/Ixf4aptF5N2Qdfmh4fGGYhTN274kJXuNMkUAzpL5HuD9jzSxIGG5kZNhhHY-p7nw/hotel/asset/20071813-7a73e81865c13c7a42142a304cee9d08.jpeg?_src=imagekit&tr=c-at_max,fo-auto,h-370,q-40,w-700",
                jumlahUlasan = "158",
                jarakKm = "134.7629290216",
                deskripsi2 = "Lokasi Golden Hill by Golden Tulip berada di Batu. Terdapat beberapa tempat menarik di sekitarnya, seperti Mitra Sehat Medika Hospital yang berjarak sekitar 30,64 km dan Indomaret Raden Intan berjarak sekitar 14,65 km. Tentang Golden Hill by Golden Tulip Golden Hill by Golden Tulip adalah tempat bermalam yang tepat bagi Anda yang berlibur bersama keluarga. Nikmati segala fasilitas hiburan untuk Anda dan keluarga. hotel ini adalah pilihan yang pas jika Anda mencari liburan yang tenang dan jauh dari keramaian. Pengalaman menginap Anda tak akan terlupakan berkat pelayanan istimewa yang disertai oleh berbagai fasilitas pendukung untuk kenyamanan Anda. Pusat kebugaran menjadi salah satu fasilitas yang wajib Anda coba saat menginap di tempat ini. Resepsionis siap 24 jam untuk melayani proses check-in, check-out dan kebutuhan Anda yang lain. Jangan ragu untuk menghubungi resepsionis, kami siap melayani Anda. Terdapat restoran yang menyajikan menu lezat ala Golden Hill by Golden Tulip khusus untuk Anda. WiFi tersedia di seluruh area publik properti untuk membantu Anda tetap terhubung dengan keluarga dan teman. Golden Hill by Golden Tulip adalah akomodasi dengan fasilitas baik dan kualitas pelayanan memuaskan menurut sebagian besar tamu. Nikmati pelayanan mewah dan pengalaman tak terlupakan ala Golden Hill by Golden Tulip selama Anda menginap di sini.",
                fasilitas = "Fasilitas Publik Kafe Restoran Restoran untuk sarapan Restoran untuk makan malam Restoran untuk makan siang Layanan kamar Brankas WiFi di area umum Servis Hotel Bellboy Concierge/layanan tamu Resepsionis Resepsionis 24 jam Keamanan 24 jam Penitipan bagasi Fasilitas Kamar Meja Pengering rambut Brankas kamar Lemari es Pancuran TV Umum AC Aula Banquet Area bebas asap rokok Makanan dan Minuman Sarapan Sarapan prasmanan Aksesibilitas Lokasi mudah diakses Aksesibel bagi penyandang disabilitas Fasilitas Bisnis Ruang rapat Fasilitas rapat Kegiatan Lainnya Pusat kebugaran Konektivitas WiFi gratis",
                kemiripan = "0.716763483",
                long = "112.5293949772",
                skor1 = "8.7",
                skor2 = "Mengesankan",
                nama = "Golden Hill by Golden Tulip",
                harga = "Rp 688.706",
                deskirpsi1 = "Golden Hill by Golden Tulip berada di Batu. Resepsionis siap 24 jam untuk melayani proses check-in, check-out dan kebutuhan Anda yang lain. Jangan ragu untuk menghubungi resepsionis, kami siap melayani Anda. WiFi tersedia di seluruh area publik properti untuk membantu Anda tetap terhubung dengan keluarga dan teman.",
                lokasi = "Jalan Oro-oro Ombo Nomor 11, Desa/kelurahan Temas , Kec. Batu, Kota Batu, Provinsi Jawa Timur, Batu, Malang, Jawa Timur, Indonesia, 65315",
                id = "1",
                lat = "-7.8843618438",
                bintang = "bintang 4"
            )
            newsList.add(news)
        }
        return newsList
    }
}