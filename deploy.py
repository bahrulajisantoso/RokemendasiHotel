from flask import Flask, request
import math
import pandas as pd
import string
from sklearn.feature_extraction.text import CountVectorizer, TfidfTransformer, TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import re
import nltk
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.tokenize import RegexpTokenizer
from sklearn.metrics.pairwise import cosine_similarity
from openpyxl import load_workbook
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory

app = Flask(__name__)

@app.route('/post', methods=['POST'])
def input():
    
    if request.method == 'POST':
        lat = float(request.form['lat'])
        long = float(request.form['long'])
        text = request.form['text']

        # %%
        # Lokasi awal
        origin_latitude = lat
        origin_longitude = long

        # input user
        text = text

        # preprocessing
        # def preprocess_text(text):
        #     # Mengubah teks menjadi huruf kecil
        #     text = text.lower()
            
        #     # Mengganti semua tanda baca dengan spasi kosong
        #     text = re.sub(f"[{re.escape(string.punctuation)}]", ' ', text)
            
        #     # Menghilangkan kelebihan spasi
        #     text = re.sub(r'\s+', ' ', text).strip()
    
        #     return text

        # Preprocessing input text
        # text = preprocess_text(text)
        
        # tampilkan data
        print(text)
        
        # %%
        # nama file
        excel_file = 'C:/Users/AJI/Documents/program skripsi/final/merge.xlsx'

        # read data excel
        df_data = pd.read_excel(excel_file)

        # Data yang ingin ditambahkan
        new_data = {'id': '0', 'nama': 'user input', 'harga': '', 'fasilitas': text, 'lat': origin_latitude, 'long': origin_longitude}

        # menambahkan data yang diproses
        result_new_data = pd.concat([pd.DataFrame([new_data]), df_data]).reset_index(drop=True)

        # export excel
        result_new_data.to_excel('C:/Users/AJI/Documents/program skripsi/final/result_new_data.xlsx', index=False)

        # Tampilkan data
        # print(result_new_data)

        # %%
        # read excel
        df_new_data = pd.read_excel('C:/Users/AJI/Documents/program skripsi/final/result_new_data.xlsx')

        # tampilkan data
        # print(df_new_data)
        
        # Pastikan untuk mengunduh tokenizer data dari NLTK jika belum dilakukan
        nltk.download('punkt')
        nltk.download('stopwords')

        # Mendapatkan daftar stopwords bahasa Indonesia dari NLTK
        stop_words = set(stopwords.words('indonesian'))

        # Inisialisasi tokenizer untuk menghapus tanda baca
        tokenizer = RegexpTokenizer(r'\w+')

        # Inisialisasi stemmer dari Sastrawi
        factory = StemmerFactory()
        stemmer = factory.create_stemmer()

        def preprocessing(text):
        
            # case folding
            text = text.lower()
            
            # remove white space
            text = re.sub(r'\s+', ' ', text).strip()
            
            # remove punctuation and tokenize
            tokens = tokenizer.tokenize(text)
            
            # remove stopwords
            tokens = [word for word in tokens if word not in stop_words]
            
            # stemming
            tokens = [stemmer.stem(word) for word in tokens]
        
            return ' '.join(tokens)

        # %%
        # Mengubah NaN menjadi spasi kosong di semua kolom
        df_new_data = df_new_data.fillna('')

        # Menggabungkan semua kolom
        df_new_data['gabungan'] = df_new_data.apply(lambda row: f"{row['lokasi']} {row['bintang']} {row['harga']} {row['jumlah ulasan']} {row['skor1']} {row['skor2']} {row['fasilitas']} {row['deskripsi2']}", axis=1)

        # simpan di variabel corpus
        corpus = df_new_data['gabungan']

        # preprocessing data
        corpus = corpus.apply(preprocessing)

        # Menghilangkan spasi kosong yang terdeteksi sebagai kata
        # corpus = corpus.str.strip()  # Menghapus spasi di awal dan akhir
        # corpus = corpus.replace(r'\s+', ' ', regex=True)  # Mengganti beberapa spasi dengan satu spasi

        # export excel
        corpus.to_excel('C:/Users/AJI/Documents/program skripsi/final/combined.xlsx')

        # tampilkan data
        # print(corpus)

        # %%
        words_set = set()

        for doc in corpus:
            words = doc.split(' ')
            words_set = words_set.union(set(words))
            
        print('Number of words in the corpus:',len(words_set))
        print('The words in the corpus: \n', words_set)

        # %%
        n_docs = len(corpus)         #·Number of documents in the corpus
        n_words_set = len(words_set) #·Number of unique words in the 

        # %%
        # TF
        # Inisialisasi CountVectorizer
        vectorizer = CountVectorizer()

        # Fit dan transform korpus menjadi counts
        X_counts = vectorizer.fit_transform(corpus)

        # Menghitung TF dengan menghilangkan penggunaan IDF
        tf_transformer = TfidfTransformer(use_idf=False, norm=None).fit(X_counts)
        X_tf = tf_transformer.transform(X_counts)

        # Konversi hasil ke DataFrame
        df_tf = pd.DataFrame(X_tf.toarray(), columns=vectorizer.get_feature_names_out())

        # Membagi setiap nilai dengan total kata dalam setiap dokumen untuk mendapatkan TF yang sebenarnya
        doc_lengths = X_counts.sum(axis=1).A1  # Total jumlah kata dalam setiap dokumen
        df_tf = df_tf.div(doc_lengths, axis=0)

        # tampilkan data
        # print(df_tf)

        # %%
        # IDF
        # Inisialisasi TfidfVectorizer
        vectorizer = TfidfVectorizer(use_idf=True, smooth_idf=False)

        # Fit ke korpus dan transform ke matriks
        X_idf = vectorizer.fit_transform(corpus)

        # Mendapatkan IDF
        idf = vectorizer.idf_

        # Menampilkan IDF setiap kata
        idf_dict = dict(zip(vectorizer.get_feature_names_out(), idf))
        # for word, score in idf_dict.items():
        #     print(f'{word:>15}: {score:.6f}')

        # %%
        # TF-IDF
        # Membuat DataFrame untuk menyimpan hasil TF-IDF
        df_tf_idf = df_tf.copy()

        # Melakukan perkalian antara nilai TF dan nilai IDF
        for word in df_tf.columns:
            df_tf_idf[word] = df_tf[word] * idf_dict[word]

        # tampilkan data
        # print(df_tf_idf)
        
        # %%
        # Hitung kemiripan kosinus untuk semua dokumen terhadap dokumen pertama
        for i in range(n_docs):
            cos_sim = cosine_similarity(df_tf_idf.iloc[0].values.reshape(1, -1), df_tf_idf.iloc[i].values.reshape(1, -1))[0][0]
            result_new_data.at[i, 'kemiripan'] = cos_sim

        # urutkan
        result = result_new_data.sort_values(by='kemiripan', ascending=False)
        
        # ambil data teratas
        result = result_new_data.nlargest(21, 'kemiripan')

        # export ke excel
        result.to_excel('C:/Users/AJI/Documents/program skripsi/final/result_tf_idf.xlsx', index=False)
        
        # tampilkan data
        # print(result)

        # %%
        def haversine(lat1, lon1, lat2, lon2):
            # Radius of the Earth in kilometers
            R = 6371 
            
            # Convert latitude and longitude from degrees to radians
            lat1_rad = math.radians(lat1)
            lon1_rad = math.radians(lon1)
            lat2_rad = math.radians(lat2)
            lon2_rad = math.radians(lon2)
            
            # Haversine formula
            dlon = lon2_rad - lon1_rad
            dlat = lat2_rad - lat1_rad
            a = math.sin(dlat/2)**2 + math.cos(lat1_rad) * math.cos(lat2_rad) * math.sin(dlon/2)**2
            c = 2 * math.atan2(math.sqrt(a), math.sqrt(1-a))
            distance = R * c
            return distance

        def calculate_distances_from_excel(filename, origin_lat, origin_lon):
            # Baca data dari file Excel
            df_hf = pd.read_excel(filename)
            
            # Buat kolom baru untuk menyimpan jarak
            df_hf['jarak(km)'] = df_hf.apply(lambda row: haversine(origin_lat, origin_lon, row['lat'], row['long']), axis=1)
            
            return df_hf

        # Nama file Excel yang berisi data lokasi
        excel_file = "C:/Users/AJI/Documents/program skripsi/final/result_tf_idf.xlsx"

        # Hitung jarak dari lokasi awal ke lokasi dalam file Excel
        result_df_hf = calculate_distances_from_excel(excel_file, origin_latitude, origin_longitude)

        # ambil 10 data teratas
        result_df_hf = result_df_hf.nsmallest(11, 'jarak(km)')

        # export excel
        result_df_hf.to_excel('C:/Users/AJI/Documents/program skripsi/final/result.xlsx')
        
        # tampilkan hasil
        print(result_df_hf)

    json_data = result_df_hf.to_json(orient='records')
        
    return json_data
   
if __name__ == "__main__":
    app.run(host='0.0.0.0')